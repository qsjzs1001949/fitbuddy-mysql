package zzu.example.trainpartnerproject.service;

import zzu.example.trainpartnerproject.entity.User;
import java.util.List;

public interface UserService {
    User getUserByUsername(String username);
    User getUserById(Long id);
    String login(User user);
    String register(User user);
    List<User> getAllUsers();
    String updateUserStatus(Long userId, String status);
    String updateUserRole(Long userId, String role);
}