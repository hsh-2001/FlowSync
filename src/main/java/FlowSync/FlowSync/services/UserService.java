package FlowSync.FlowSync.services;

import FlowSync.FlowSync.dto.LoginResponse;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.User;
import FlowSync.FlowSync.repositories.UserRepository;
import FlowSync.FlowSync.services.interfaces.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository
        ,PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
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
            return BaseResponse.error("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
            return BaseResponse.error("Username not found");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return BaseResponse.error("Wrong password");
        }
        LoginResponse response =
                new LoginResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                );


        return BaseResponse.success(
                "Login successfully",
                response
        );
    }
}
