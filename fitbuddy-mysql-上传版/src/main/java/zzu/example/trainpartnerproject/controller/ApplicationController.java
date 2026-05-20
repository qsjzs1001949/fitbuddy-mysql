package zzu.example.trainpartnerproject.controller;

import org.springframework.web.bind.annotation.*;
import zzu.example.trainpartnerproject.entity.Application;
import zzu.example.trainpartnerproject.service.ApplicationService;
import javax.annotation.Resource;
import java.util.List;

@RestController
public class ApplicationController {
    @Resource
    private ApplicationService applicationService;

    @PostMapping("/application/submit")
    public String submitApplication(@RequestParam Long applicantId, @RequestParam Long receiverId, @RequestParam Long partnerId) {
        return applicationService.submitApplication(applicantId, receiverId, partnerId);
    }

    @GetMapping("/application/pending")
    public List<Application> getPendingApplications(@RequestParam Long receiverId) {
        return applicationService.getPendingApplications(receiverId);
    }

    @PostMapping("/application/handle")
    public String handleApplication(@RequestParam Long applicationId, @RequestParam String status) {
        return applicationService.handleApplication(applicationId, status);
    }

    @GetMapping("/application/my")
    public List<Application> getMyApplications(@RequestParam Long applicantId) {
        return applicationService.getMyApplications(applicantId);
    }
}