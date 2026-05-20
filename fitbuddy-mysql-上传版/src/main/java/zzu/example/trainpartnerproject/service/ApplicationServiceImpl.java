package zzu.example.trainpartnerproject.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import zzu.example.trainpartnerproject.entity.Application;
import zzu.example.trainpartnerproject.entity.Partner;
import zzu.example.trainpartnerproject.entity.User;
import zzu.example.trainpartnerproject.mapper.ApplicationMapper;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {
    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private UserService userService;
    @Autowired
    @Lazy
    private PartnerService partnerService;

    @Override
    public String submitApplication(Long applicantId, Long receiverId, Long partnerId) {
        User applicant = userService.getUserById(applicantId);
        if (applicant == null) {
            return "申请人不存在";
        }
        if ("muted".equals(applicant.getStatus()) || "banned".equals(applicant.getStatus())) {
            return "您当前状态无法申请";
        }
        if (applicantId.equals(receiverId)) {
            return "不能申请自己";
        }
        QueryWrapper<Application> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("applicant_id", applicantId)
                   .eq("receiver_id", receiverId)
                   .eq("partner_id", partnerId)
                   .in("status", List.of("pending", "accepted"));
        if (applicationMapper.exists(queryWrapper)) {
            return "您已经申请过该搭档";
        }
        Application application = new Application();
        application.setApplicantId(applicantId);
        application.setReceiverId(receiverId);
        application.setPartnerId(partnerId);
        application.setStatus("pending");
        application.setCreateTime(LocalDateTime.now());
        application.setUpdateTime(LocalDateTime.now());
        applicationMapper.insert(application);
        return "申请已提交";
    }

    @Override
    public List<Application> getPendingApplications(Long receiverId) {
        QueryWrapper<Application> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver_id", receiverId)
                   .eq("status", "pending")
                   .orderByDesc("create_time");
        return applicationMapper.selectList(queryWrapper);
    }

    @Override
    public String handleApplication(Long applicationId, String status) {
        Application application = applicationMapper.selectById(applicationId);
        if (application == null) {
            return "申请不存在";
        }
        if (!"pending".equals(application.getStatus())) {
            return "该申请已被处理";
        }
        application.setStatus(status);
        application.setUpdateTime(LocalDateTime.now());
        applicationMapper.updateById(application);
        return "accepted".equals(status) ? "已接受申请" : "已拒绝申请";
    }

    @Override
    public List<Application> getMyApplications(Long applicantId) {
        QueryWrapper<Application> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("applicant_id", applicantId)
                   .orderByDesc("create_time");
        return applicationMapper.selectList(queryWrapper);
    }

    @Override
    public boolean isPartnered(Long userId1, Long userId2) {
        QueryWrapper<Application> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wrapper -> wrapper
            .and(w -> w.eq("applicant_id", userId1).eq("receiver_id", userId2))
            .or(w -> w.eq("applicant_id", userId2).eq("receiver_id", userId1))
        ).eq("status", "accepted");
        return applicationMapper.exists(queryWrapper);
    }
}