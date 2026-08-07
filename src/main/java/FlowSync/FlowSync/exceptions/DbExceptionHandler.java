package FlowSync.FlowSync.exceptions;

import FlowSync.FlowSync.models.BaseResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DbExceptionHandler {

    // Duplicate key, NOT NULL, FK constraint
    @ExceptionHandler(DataIntegrityViolationException.class)
    public BaseResponse<String> handleDataIntegrity(
            DataIntegrityViolationException e) {

        return BaseResponse.failed(
                "Data already exists or violates database constraint"
        );
    }


    // SQL syntax error
    @ExceptionHandler(BadSqlGrammarException.class)
    public BaseResponse<String> handleSqlError(
            BadSqlGrammarException e) {

        return BaseResponse.failed(
                "Invalid SQL query"
        );
    }

    // No data found
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public BaseResponse<String> handleEmptyResult(
            EmptyResultDataAccessException e) {

        return BaseResponse.failed(
                "Data not found"
        );
    }

    // Database connection problem
    @ExceptionHandler(DataAccessResourceFailureException.class)
    public BaseResponse<String> handleConnectionError(
            DataAccessResourceFailureException e) {

        return BaseResponse.failed(
                "Database connection failed"
        );
    }

    // Default database error
    @ExceptionHandler(DataAccessException.class)
    public BaseResponse<String> handleDatabaseException(
            DataAccessException e) {

        return BaseResponse.failed(
                "Database error occurred"
        );
    }
}