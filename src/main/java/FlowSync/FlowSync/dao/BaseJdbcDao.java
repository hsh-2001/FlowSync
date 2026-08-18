package FlowSync.FlowSync.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BaseJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    public Map<String, Object> executeProcedure(
            String procedureName,
            Map<String, Object> parameters,
            SqlParameter... sqlParameters
    ) {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName(procedureName)
                .declareParameters(sqlParameters);

        return call.execute(parameters);
    }

    public <T> T getColumn(
            ResultSet rs,
            String columnName,
            Class<T> type
    ) {
        try {
            return rs.getObject(columnName, type);

        } catch (SQLException e) {
            if (e.getErrorCode() == 17006) {
                throw new IllegalArgumentException(
                        "Invalid column '" + columnName +
                                "' in result set",
                        e
                );
            }
            throw new RuntimeException(
                    "Failed to read column '" + columnName + "'",
                    e
            );
        }
    }
}