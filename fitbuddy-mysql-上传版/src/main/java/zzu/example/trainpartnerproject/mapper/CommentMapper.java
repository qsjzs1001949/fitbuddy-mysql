package zzu.example.trainpartnerproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import zzu.example.trainpartnerproject.entity.Comment;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}