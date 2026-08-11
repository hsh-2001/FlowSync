package FlowSync.FlowSync.services.interfaces;



import FlowSync.FlowSync.dto.LoginResponse;
import FlowSync.FlowSync.dto.UserResponseDto;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.User;

import java.util.List;

public interface IUserService {
    BaseResponse<List<UserResponseDto>> getAllUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);

    BaseResponse<String> createUser(User user);

    void updateUser(Long id, User user);

    void deleteUser(Long id);

    BaseResponse<LoginResponse> login(String username, String password);
}
