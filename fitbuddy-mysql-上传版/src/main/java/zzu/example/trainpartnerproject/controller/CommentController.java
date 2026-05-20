package zzu.example.trainpartnerproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zzu.example.trainpartnerproject.entity.Comment;
import zzu.example.trainpartnerproject.entity.Partner;
import zzu.example.trainpartnerproject.entity.User;
import zzu.example.trainpartnerproject.entity.dto.CommentUserDTO;
import zzu.example.trainpartnerproject.entity.dto.PartnerUserDTO;
import zzu.example.trainpartnerproject.service.CommentService;
import zzu.example.trainpartnerproject.service.PartnerService;
import zzu.example.trainpartnerproject.service.UserService;
import javax.annotation.Resource;
import java.util.List;

@RestController
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private PartnerService partnerService;
    @Resource
    private UserService userService;

    @PostMapping("/comments")
    public String addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @GetMapping(value = "/comments", produces = "application/json;charset=UTF-8")
    public List<CommentUserDTO> getComments(@RequestParam Long partnerId) {
        return commentService.getCommentsByPartnerId(partnerId);
    }

    @GetMapping(value = "/partner/{partnerId}", produces = "application/json;charset=UTF-8")
    public PartnerUserDTO getPartnerById(@PathVariable Long partnerId) {
        return partnerService.getPartnerByIdWithUserInfo(partnerId);
    }

    @PostMapping(value = "/comments/{commentId}/delete", produces = "application/json;charset=UTF-8")
    public String deleteComment(@PathVariable Long commentId, @RequestParam Long currentUserId, @RequestParam String currentUserRole) {
        return commentService.deleteComment(commentId, currentUserId, currentUserRole);
    }
}