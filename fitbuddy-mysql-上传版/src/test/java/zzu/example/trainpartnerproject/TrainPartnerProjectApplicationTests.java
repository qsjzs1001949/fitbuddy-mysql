package zzu.example.trainpartnerproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zzu.example.trainpartnerproject.mapper.UserMapper;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TrainPartnerProjectApplicationTests {

    @Test
    void contextLoads() {

    }


    @Autowired(required = false)
    private UserMapper userMapper; // 如果有的话

    @Test
    void whenUserMapperInjected_thenNotNull() {

        assertThat(userMapper).isNotNull();
    }

}
