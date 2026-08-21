package FlowSync.FlowSync.services;

import FlowSync.FlowSync.anotations.LogExecutionTime;
import FlowSync.FlowSync.dto.LoginResponse;
import FlowSync.FlowSync.dto.UserResponseDto;
import FlowSync.FlowSync.entities.EUserEntity;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.User;
import FlowSync.FlowSync.repositories.NewUserRepository;
import FlowSync.FlowSync.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class NewUserService implements IUserService {
    private final NewUserRepository newUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @LogExecutionTime
    public BaseResponse<List<UserResponseDto>> getAllUsers() {
        List<EUserEntity> users = newUserRepository.findAll();
        List<UserResponseDto> result = users.stream()
                .map(user -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setName(user.getName());
                    return dto;
                })
                .toList();
        return  BaseResponse.success(result);
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        EUserEntity existing = newUserRepository.findByUsername(username).orElseThrow(() -> new RuntimeException(("User not found!")));
        User user = new User();
        user.setId(existing.getId());
        user.setUsername(existing.getUsername());
        user.setEmail(existing.getEmail());
        user.setName(existing.getName());
        user.setPassword(existing.getPassword());
        return user;
    }

    @Override
    public BaseResponse<String> createUser(User user) {
        EUserEntity eUserEntity = new EUserEntity();
        eUserEntity.setUsername(user.getUsername());
        eUserEntity.setEmail(user.getEmail());
        eUserEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        eUserEntity.setName(user.getName());
        eUserEntity.setUserCode(generateUserCode());
        eUserEntity.setCreatedDt(LocalDateTime.now());

        newUserRepository.save(eUserEntity);
        return BaseResponse.success("Create new user successfully");
    }

    @Override
    public void updateUser(Long id, User user) {

    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    @LogExecutionTime
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
