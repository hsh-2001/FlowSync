package FlowSync.FlowSync.repositories.interfaces;

import FlowSync.FlowSync.dto.UserListResponse;
import FlowSync.FlowSync.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    List<UserListResponse> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    String create(User user);

    boolean update(User user);

    boolean delete(Long id);
}
