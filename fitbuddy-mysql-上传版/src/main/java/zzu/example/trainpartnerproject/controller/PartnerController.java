package zzu.example.trainpartnerproject.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import zzu.example.trainpartnerproject.entity.Partner;
import zzu.example.trainpartnerproject.entity.User;
import zzu.example.trainpartnerproject.entity.dto.PartnerUserDTO;
import zzu.example.trainpartnerproject.service.PartnerService;
import zzu.example.trainpartnerproject.service.UserService;
import javax.annotation.Resource;

@RestController
public class PartnerController {
    @Resource
    private PartnerService partnerService;

    @PostMapping("/partner")
    public String publishPartner(@RequestBody Partner partner) {
        return partnerService.publishPartner(partner);
    }

    @GetMapping(value = "/partner", produces = "application/json;charset=UTF-8")
    public Page<PartnerUserDTO> getPartners(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String fitnessType,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Long currentUserId) {

        int pageNumber = page - 1;
        return partnerService.getPartnersWithUserInfo(pageNumber, size, fitnessType, location, gender, currentUserId);
    }

    @DeleteMapping("/partner/delete")
    public String deletePartner(@RequestParam Long userId) {
        return partnerService.deletePartner(userId);
    }
    

    @DeleteMapping("/admin/partner/delete")
    public String deletePartnerById(@RequestParam Long partnerId) {
        return partnerService.deletePartnerById(partnerId);
    }

    @GetMapping(value = "/partner/detail", produces = "application/json;charset=UTF-8")
    public PartnerUserDTO getPartnerDetail(
            @RequestParam Long partnerId,
            @RequestParam(required = false) Long currentUserId) {
        return partnerService.getPartnerByIdWithUserInfo(partnerId, currentUserId);
    }
}