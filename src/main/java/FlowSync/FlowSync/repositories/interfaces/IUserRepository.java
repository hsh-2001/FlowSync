package FlowSync.FlowSync.repositories.interfaces;

import FlowSync.FlowSync.dto.UserResponseDto;
import FlowSync.FlowSync.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    List<UserResponseDto> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    String create(User user);

    boolean update(User user);

    boolean delete(Long id);
}
