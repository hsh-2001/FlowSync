package FlowSync.FlowSync.services;

import FlowSync.FlowSync.dto.LoginResponse;
import FlowSync.FlowSync.dto.UserListResponse;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.User;
import FlowSync.FlowSync.repositories.UserRepository;
import FlowSync.FlowSync.services.interfaces.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository
        ,PasswordEncoder passwordEncoder ,JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public BaseResponse<List<UserListResponse>> getAllUsers() {
        return BaseResponse.success(userRepository.findAll());
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> result = userRepository.findByUsername(username);
        return result.orElse(null);
    }

    @Override
    public BaseResponse<String> createUser(User user) {
        User existingUser = getUserByUsername(user.getUsername());
        if (existingUser != null) {
            return BaseResponse.failed("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserCode(generateUserCode());
        user.setGrpId("G01");
        user.setRuleId(1);
        String result = userRepository.create(user);
        return BaseResponse.success(result);
    }

    @Override
    public void updateUser(Long id, User user) {
        userRepository.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    public BaseResponse<LoginResponse> login(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) {
            return BaseResponse.failed("Username not found");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return BaseResponse.failed("Wrong password");
        }
        String token = jwtService.generateToken(user.getUsername(), user.getId());
        LoginResponse response =
                new LoginResponse(
                        user.getId(),
                        user.getName(),
                        user.getUsername(),
                        user.getEmail(),
                        token
                );
        return BaseResponse.success(
                "Login successfully",
                response
        );
    }

    private String generateUserCode() {
        Random random = new Random();
        return String.format("U_A%03d", random.nextInt(1000));
    }
}
