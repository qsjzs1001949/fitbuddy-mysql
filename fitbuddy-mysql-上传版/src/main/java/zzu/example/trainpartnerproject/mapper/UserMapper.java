package zzu.example.trainpartnerproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import zzu.example.trainpartnerproject.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> { // 补全泛型
}