package FlowSync.FlowSync.repositories;

import FlowSync.FlowSync.dto.UserListResponse;
import FlowSync.FlowSync.models.User;
import FlowSync.FlowSync.repositories.interfaces.IUserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements IUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<UserListResponse> findAll() {
        String sql = """
                SELECT
                    ID,
                    USER_CODE,
                    NAME,
                    USERNAME,
                    EMAIL,
                    PASSWORD,
                    CREATED_DT,
                    UPDATED_DT,
                    DELETED_DT,
                    RULE_ID,
                    GRP_ID
                FROM TMS_USERS
                WHERE DELETED_DT IS NULL
                ORDER BY ID
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapUserList(rs));
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = """
                SELECT
                    ID,
                    USER_CODE,
                    NAME,
                    USERNAME,
                    EMAIL,
                    PASSWORD,
                    CREATED_DT,
                    UPDATED_DT,
                    DELETED_DT,
                    RULE_ID,
                    GRP_ID
                FROM TMS_USERS
                WHERE ID = ?
                """;

        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> mapUser(rs), id);

        return users.stream().findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = """
                SELECT
                    ID,
                    USER_CODE,
                    NAME,
                    USERNAME,
                    EMAIL,
                    PASSWORD,
                    CREATED_DT,
                    UPDATED_DT,
                    DELETED_DT,
                    RULE_ID,
                    GRP_ID
                FROM TMS_USERS
                WHERE USERNAME = ?
                """;

        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> mapUser(rs), username);

        return users.stream().findFirst();
    }

    @Override
    public String create(User user) {
        String sql = """
                INSERT INTO TMS_USERS
                (
                    USER_CODE,
                    NAME,
                    USERNAME,
                    EMAIL,
                    PASSWORD,
                    CREATED_DT,
                    RULE_ID,
                    GRP_ID
                )
                VALUES
                (
                    ?, ?, ?, ?, ?, ?, ?, ?
                )
                """;
        if (user.getCreatedDt() == null) {
            user.setCreatedDt(LocalDateTime.now());
        }

         int result = jdbcTemplate.update(
                sql,
                user.getUserCode(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                Timestamp.valueOf(user.getCreatedDt()),
                user.getRuleId(),
                user.getGrpId()
        );
         if (result > 0) {
             return "Create successfully!";
         } else  {
             return  "Create failed!";
         }
    }

    @Override
    public boolean update(User user) {
        String sql = """
                    UPDATE TMS_USERS
                    SET
                """;
        return false;
    }

    @Override
    public boolean delete(Long id) {
        String sql = """
                    DELETE FROM TMS_USERS
                    WHERE ID = ?
                """;
        int result = jdbcTemplate.update(sql, id);
        return result > 0;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getLong("ID"));
        user.setUserCode(rs.getString("USER_CODE"));
        user.setName(rs.getString("NAME"));
        user.setUsername(rs.getString("USERNAME"));
        user.setEmail(rs.getString("EMAIL"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setCreatedDt(rs.getTimestamp("CREATED_DT").toLocalDateTime());

        if (rs.getTimestamp("UPDATED_DT") != null) {
            user.setUpdatedDt(rs.getTimestamp("UPDATED_DT").toLocalDateTime());
        }

        if (rs.getTimestamp("DELETED_DT") != null) {
            user.setDeletedDt(rs.getTimestamp("DELETED_DT").toLocalDateTime());
        }

        user.setRuleId(rs.getInt("RULE_ID"));
        user.setGrpId(rs.getString("GRP_ID"));

        return user;
    }


    private UserListResponse mapUserList(ResultSet rs) throws SQLException {
        UserListResponse user = new UserListResponse();
        user.setId(rs.getLong("ID"));
        user.setUserCode(rs.getString("USER_CODE"));
        user.setName(rs.getString("NAME"));
        user.setUsername(rs.getString("USERNAME"));
        user.setEmail(rs.getString("EMAIL"));
        user.setCreatedDt(rs.getTimestamp("CREATED_DT").toLocalDateTime());
        if (rs.getTimestamp("UPDATED_DT") != null) {
            user.setUpdatedDt(rs.getTimestamp("UPDATED_DT").toLocalDateTime());
        }
        user.setRuleId(rs.getInt("RULE_ID"));
        user.setGrpId(rs.getString("GRP_ID"));

        return user;
    }
}
