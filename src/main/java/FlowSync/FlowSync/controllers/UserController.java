package FlowSync.FlowSync.controllers;

import FlowSync.FlowSync.dto.LoginResponse;
import FlowSync.FlowSync.dto.UserResponseDto;
import FlowSync.FlowSync.enums.ErrorCode;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.User;
import FlowSync.FlowSync.services.NewUserService;
import FlowSync.FlowSync.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final NewUserService newUserService;

    @GetMapping
    public BaseResponse<List<UserResponseDto>> getUsers() {
        return newUserService.getAllUsers();
    }

    @PostMapping("/auth/register")
    public BaseResponse<String> register(@RequestBody User user) {
        return newUserService.createUser(user);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody User user) {
        try {
            BaseResponse<LoginResponse> userResponse =  newUserService.login(user.getUsername(), user.getPassword());
            if (!userResponse.isSuccess()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(BaseResponse.failed("Invalid username or password"));
            }
            LoginResponse loginResponse = userResponse.getData();
            ResponseCookie cookie = ResponseCookie.from(
                            "token",
                            loginResponse.getToken()
                    )
                    .httpOnly(true)
                    .secure(false) // true in production (HTTPS)
                    .path("/")
                    .maxAge(60 * 60 * 24)
                    .sameSite("Lax")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(userResponse);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(BaseResponse.failed(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR.getCode()));
        }
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<BaseResponse<String>> logout(
            HttpServletResponse response
    ) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // true in production with HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok(BaseResponse.success("Successfully logged out"));
    }
}
