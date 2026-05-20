package zzu.example.trainpartnerproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zzu.example.trainpartnerproject.entity.User;
import zzu.example.trainpartnerproject.service.UserService;
import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/user/name")
    public User getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/user/register")
    public String registerUser(@RequestBody User user) {
        return userService.register(user);
    }
    
    @PostMapping("/user/login")
    public String loginUser(@RequestBody User user) {
        return userService.login(user);
    }
    

    @GetMapping("/admin/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    

    @PostMapping("/admin/user/status")
    public String updateUserStatus(@RequestParam Long userId, @RequestParam String status) {
        return userService.updateUserStatus(userId, status);
    }
    

    @PostMapping("/admin/user/role")
    public String updateUserRole(@RequestParam Long userId, @RequestParam String role) {
        return userService.updateUserRole(userId, role);
    }
}