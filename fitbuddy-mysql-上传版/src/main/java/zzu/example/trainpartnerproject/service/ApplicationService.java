package zzu.example.trainpartnerproject.service;

import zzu.example.trainpartnerproject.entity.Application;
import java.util.List;

public interface ApplicationService {
    String submitApplication(Long applicantId, Long receiverId, Long partnerId);
    List<Application> getPendingApplications(Long receiverId);
    String handleApplication(Long applicationId, String status);
    List<Application> getMyApplications(Long applicantId);
    boolean isPartnered(Long userId1, Long userId2);
}