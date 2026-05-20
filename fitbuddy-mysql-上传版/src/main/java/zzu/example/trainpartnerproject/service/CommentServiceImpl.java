package zzu.example.trainpartnerproject.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzu.example.trainpartnerproject.entity.Comment;
import zzu.example.trainpartnerproject.entity.User;
import zzu.example.trainpartnerproject.entity.dto.CommentUserDTO;
import zzu.example.trainpartnerproject.mapper.CommentMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserService userService;

    @Override
    public String addComment(Comment comment) {
        if (comment == null) {
            return "评论对象不能为空";
        }
        
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            return "评论内容不能为空";
        }
        
        if (comment.getUserId() == null) {
            return "用户ID不能为空";
        }
        
        if (comment.getPartnerId() == null) {
            return "搭子ID不能为空";
        }
        

        User user = userService.getUserById(comment.getUserId());
        if (user == null) {
            return "用户不存在";
        }
        if ("muted".equals(user.getStatus())) {
            return "您已被禁言，无法发表评论";
        }
        
        try {
            comment.setCreateTime(LocalDateTime.now());
            commentMapper.insert(comment);
            return "评论成功";
        } catch (Exception e) {
            System.err.println("添加评论失败: " + e.getMessage());
            e.printStackTrace();
            return "评论失败: " + e.getMessage();
        }
    }

    @Override
    public List<CommentUserDTO> getCommentsByPartnerId(Long partnerId) {

        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("partner_id", partnerId);
        queryWrapper.orderByDesc("create_time");
        

        List<Comment> comments = commentMapper.selectList(queryWrapper);
        

        List<CommentUserDTO> dtoList = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getUserId() == null) {
                continue;
            }
            User user = userService.getUserById(comment.getUserId());
            CommentUserDTO dto = CommentUserDTO.from(comment, user);
            dtoList.add(dto);
        }
        
        return dtoList;
    }

    @Override
    public String deleteComment(Long commentId, Long currentUserId, String currentUserRole) {

        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) {
            return "评论不存在";
        }
        

        if ("admin".equals(currentUserRole)) {

            commentMapper.deleteById(commentId);
            return "删除成功";
        } else {

            if (comment.getUserId().equals(currentUserId)) {
                commentMapper.deleteById(commentId);
                return "删除成功";
            } else {
                return "无权限删除此评论";
            }
        }
    }
}