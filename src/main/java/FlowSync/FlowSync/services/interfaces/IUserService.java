package FlowSync.FlowSync.services.interfaces;



import FlowSync.FlowSync.dto.LoginResponse;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);

    BaseResponse<String> createUser(User user);

    void updateUser(Long id, User user);

    void deleteUser(Long id);

    BaseResponse<LoginResponse> login(String username, String password);
}
