package zzu.example.trainpartnerproject.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import zzu.example.trainpartnerproject.entity.Comment;
import zzu.example.trainpartnerproject.entity.dto.CommentUserDTO;

import java.util.List;

public interface CommentService {
    String addComment(Comment comment);
    List<CommentUserDTO> getCommentsByPartnerId(Long partnerId);
    String deleteComment(Long commentId, Long currentUserId, String currentUserRole);
}
