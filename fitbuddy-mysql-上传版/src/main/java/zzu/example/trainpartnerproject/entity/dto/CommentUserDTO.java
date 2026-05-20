package zzu.example.trainpartnerproject.entity.dto;

import zzu.example.trainpartnerproject.entity.Comment;
import zzu.example.trainpartnerproject.entity.User;

import java.time.LocalDateTime;


public class CommentUserDTO {
    private Long id;
    private Long partnerId;
    private Long userId;
    private String username;
    private String content;
    private LocalDateTime createTime;


    public static CommentUserDTO from(Comment comment, User user) {
        CommentUserDTO dto = new CommentUserDTO();
        dto.setId(comment.getId());
        dto.setPartnerId(comment.getPartnerId());
        dto.setUserId(comment.getUserId());
        dto.setUsername(user != null ? user.getUsername() : "匿名用户");
        dto.setContent(comment.getContent());
        dto.setCreateTime(comment.getCreateTime());
        return dto;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}