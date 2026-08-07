package FlowSync.FlowSync.controllers;

import FlowSync.FlowSync.dto.LoginResponse;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.User;
import FlowSync.FlowSync.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/auth/register")
    public BaseResponse<String> register(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/auth/login")
    public BaseResponse<LoginResponse> login(@RequestBody User user) {
        return userService.login(user.getUsername(), user.getPassword());
    }


}
