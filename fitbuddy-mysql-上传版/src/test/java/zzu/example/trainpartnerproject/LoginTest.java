package zzu.example.trainpartnerproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zzu.example.trainpartnerproject.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LoginTest {

    @Autowired
    private UserService userService;


    @Test
    public void testOnlyOne() {

        zzu.example.trainpartnerproject.entity.User user = new zzu.example.trainpartnerproject.entity.User();
        user.setUsername("test1553");
        user.setPassword("123456");
        String result = userService.login(user);
        assertEquals("用户名不存在", result);
        System.out.println("✅ 最终测试通过：所有逻辑正常");
    }
}