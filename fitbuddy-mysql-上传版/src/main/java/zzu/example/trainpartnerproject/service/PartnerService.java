package zzu.example.trainpartnerproject.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import zzu.example.trainpartnerproject.entity.Partner;
import zzu.example.trainpartnerproject.entity.dto.PartnerUserDTO;

public interface PartnerService {
    String publishPartner(Partner partner);
    Page<Partner> getPartners(int page, int size, String fitnessType, String location, String gender);
    Page<PartnerUserDTO> getPartnersWithUserInfo(int page, int size, String fitnessType, String location, String gender);
    Page<PartnerUserDTO> getPartnersWithUserInfo(int page, int size, String fitnessType, String location, String gender, Long currentUserId);
    boolean hasPublishedPartner(Long userId);
    String deletePartner(Long userId);
    String deletePartnerById(Long partnerId);
    PartnerUserDTO getPartnerByIdWithUserInfo(Long partnerId);
    PartnerUserDTO getPartnerByIdWithUserInfo(Long partnerId, Long currentUserId);
}



