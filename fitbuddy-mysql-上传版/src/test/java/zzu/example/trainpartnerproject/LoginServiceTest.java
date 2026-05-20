package zzu.example.trainpartnerproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zzu.example.trainpartnerproject.entity.User;
import zzu.example.trainpartnerproject.service.UserService;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 登录业务单元测试（直接操作数据库验证逻辑）
 */
@SpringBootTest
public class LoginServiceTest {

    @Autowired
    private UserService userService;


    @Test
    public void testGetUserByUsername() {

        User user = userService.getUserByUsername("riceshower");

        assertNotNull(user, "数据库中未查询到该用户");
        assertEquals("riceshower", user.getUsername(), "用户名查询不匹配");
        System.out.println("✅ 数据库用户查询成功：" + user);
    }


    @Test
    public void testLoginSuccess() {
        User user = new User();
        user.setUsername("riceshower");
        user.setPassword("123456");
        String result = userService.login(user); // 数据库明文密码
        assertEquals("登录成功", result, "正确凭证登录失败");
        System.out.println("✅ 正确凭证登录测试通过");
    }


    @Test
    public void testLoginWrongPassword() {
        User user = new User();
        user.setUsername("riceshower");
        user.setPassword("654321");
        String result = userService.login(user);
        assertEquals("密码错误", result, "错误密码验证异常");
        System.out.println("✅ 错误密码登录测试通过");
    }

    @Test
    public void testLoginNonExistUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("123456");
        String result = userService.login(user);
        assertEquals("用户名不存在", result, "不存在用户验证异常");
        System.out.println("✅ 不存在用户登录测试通过");
    }
}