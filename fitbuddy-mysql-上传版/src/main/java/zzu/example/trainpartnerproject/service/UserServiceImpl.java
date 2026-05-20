package zzu.example.trainpartnerproject.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zzu.example.trainpartnerproject.entity.AdminPermissionCode;
import zzu.example.trainpartnerproject.entity.User;
import zzu.example.trainpartnerproject.mapper.AdminPermissionCodeMapper;
import zzu.example.trainpartnerproject.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminPermissionCodeMapper adminPermissionCodeMapper;

    @Override
    public String login(User user) {

        User existingUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (existingUser == null) {
            return "用户名不存在";
        }
        

        if (!user.getPassword().equals(existingUser.getPassword())) {
            return "密码错误";
        }
        

        if ("banned".equals(existingUser.getStatus())) {
            return "账户已被封禁";
        }
        

        if (user.getRole() != null && "admin".equals(user.getRole())) {

            if (existingUser.getRole() == null || !"admin".equals(existingUser.getRole())) {
                return "该账号不是管理员账号";
            }
        } else {

        }
        

        return existingUser.getId().toString() + ":登录成功";
    }

    @Override
    public User getUserByUsername(String username) {

        return userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public String register(User user) {

        User existingUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (existingUser != null) {
            return "用户名已存在";
        }

        user.setCreateTime(java.time.LocalDateTime.now());
        user.setStatus("active");
        

        String role = user.getRole();
        if (role != null && "admin".equals(role)) {

            String permissionCode = user.getPermissionCode();
            if (permissionCode == null || permissionCode.isEmpty()) {
                return "请输入管理员权限码";
            }

            AdminPermissionCode codeRecord = adminPermissionCodeMapper.selectOne(
                new QueryWrapper<AdminPermissionCode>().eq("code", permissionCode)
            );
            if (codeRecord == null) {
                return "无效的管理员权限码";
            }

            user.setRole("admin");
        } else {

            user.setRole("user");
        }
        
        userMapper.insert(user);
        return "注册成功";
    }

    @Override
    public List<User> getAllUsers() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("role", "admin");
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public String updateUserStatus(Long userId, String status) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return "用户不存在";
        }

        if ("admin".equals(user.getRole())) {
            return "不能修改管理员账号状态";
        }
        user.setStatus(status);
        userMapper.updateById(user);
        return "用户状态更新成功";
    }

    @Override
    public String updateUserRole(Long userId, String role) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return "用户不存在";
        }

        if ("admin".equals(user.getRole())) {
            return "不能修改管理员账号角色";
        }
        user.setRole(role);
        userMapper.updateById(user);
        return "用户角色更新成功";
    }
}