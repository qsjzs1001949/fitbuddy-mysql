package zzu.example.trainpartnerproject.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import zzu.example.trainpartnerproject.entity.Partner;
import zzu.example.trainpartnerproject.entity.User;
import zzu.example.trainpartnerproject.entity.dto.PartnerUserDTO;
import zzu.example.trainpartnerproject.mapper.PartnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PartnerServiceImpl extends ServiceImpl<PartnerMapper, Partner> implements PartnerService {
    @Autowired
    private PartnerMapper partnerMapper;
    @Autowired
    private UserService userService;
    @Autowired
    @Lazy
    private ApplicationService applicationService;

    private String encryptContact(String contact) {
        if (contact == null || contact.isEmpty()) {
            return contact;
        }
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < contact.length(); i++) {
            encrypted.append("*");
        }
        return encrypted.toString();
    }

    private String decryptContact(String contact, Long partnerUserId, Long currentUserId) {
        if (contact == null || contact.isEmpty()) {
            return contact;
        }
        if (currentUserId == null) {
            return encryptContact(contact);
        }
        if (currentUserId.equals(partnerUserId)) {
            return contact;
        }
        if (applicationService.isPartnered(currentUserId, partnerUserId)) {
            return contact;
        }
        return encryptContact(contact);
    }

    @Override
    public boolean hasPublishedPartner(Long userId) {
        QueryWrapper<Partner> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return partnerMapper.exists(queryWrapper);
    }

    @Override
    public String publishPartner(Partner partner) {

        User user = userService.getUserById(partner.getUserId());
        if (user == null) {
            return "用户不存在";
        }
        if ("muted".equals(user.getStatus())) {
            return "您已被禁言，无法发布帖子";
        }
        

        if (hasPublishedPartner(partner.getUserId())) {
            return "您已经发布过搭子信息，每个账号只能发布一个搭子信息";
        }
        
        partner.setCreateTime(LocalDateTime.now());
        partnerMapper.insert(partner);
        return "发布成功";
    }

    @Override
    public Page<Partner> getPartners(int page, int size, String fitnessType, String location, String gender) {

        Page<Partner> partnerPage = new Page<>(page, size);
        

        QueryWrapper<Partner> queryWrapper = new QueryWrapper<>();
        

        if (fitnessType != null && !fitnessType.isEmpty()) {
            queryWrapper.like("fitness_type", fitnessType);
        }
        if (location != null && !location.isEmpty()) {
            queryWrapper.like("location", location);
        }
        if (gender != null && !gender.isEmpty()) {
            queryWrapper.eq("gender", gender);
        }
        

        queryWrapper.orderByDesc("create_time");
        

        return partnerMapper.selectPage(partnerPage, queryWrapper);
    }

    @Override
    public Page<PartnerUserDTO> getPartnersWithUserInfo(int page, int size, String fitnessType, String location, String gender) {
        return getPartnersWithUserInfo(page, size, fitnessType, location, gender, null);
    }

    @Override
    public Page<PartnerUserDTO> getPartnersWithUserInfo(int page, int size, String fitnessType, String location, String gender, Long currentUserId) {

        Page<Partner> partnerPage = getPartners(page, size, fitnessType, location, gender);
        

        Page<PartnerUserDTO> dtoPage = new Page<>();
        dtoPage.setCurrent(partnerPage.getCurrent());
        dtoPage.setSize(partnerPage.getSize());
        dtoPage.setTotal(partnerPage.getTotal());
        dtoPage.setPages(partnerPage.getPages());
        

        List<PartnerUserDTO> dtoList = new ArrayList<>();
        for (Partner partner : partnerPage.getRecords()) {
            User user = (partner.getUserId() != null) ? userService.getUserById(partner.getUserId()) : null;
            PartnerUserDTO dto = PartnerUserDTO.from(partner, user);
            String decryptedContact = decryptContact(partner.getContact(), partner.getUserId(), currentUserId);
            dto.setContact(decryptedContact);
            dtoList.add(dto);
        }
        
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }

    @Override
    public String deletePartner(Long userId) {

        if (!hasPublishedPartner(userId)) {
            return "您还没有发布过搭子信息，无法删除";
        }
        

        QueryWrapper<Partner> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        

        int result = partnerMapper.delete(queryWrapper);
        
        if (result > 0) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }

    @Override
    public PartnerUserDTO getPartnerByIdWithUserInfo(Long partnerId) {
        return getPartnerByIdWithUserInfo(partnerId, null);
    }

    @Override
    public PartnerUserDTO getPartnerByIdWithUserInfo(Long partnerId, Long currentUserId) {

        Partner partner = partnerMapper.selectById(partnerId);
        if (partner == null) {
            return null;
        }
        

        User user = userService.getUserById(partner.getUserId());
        

        PartnerUserDTO dto = PartnerUserDTO.from(partner, user);
        String decryptedContact = decryptContact(partner.getContact(), partner.getUserId(), currentUserId);
        dto.setContact(decryptedContact);
        return dto;
    }

    @Override
    public String deletePartnerById(Long partnerId) {

        Partner partner = partnerMapper.selectById(partnerId);
        if (partner == null) {
            return "帖子不存在";
        }
        

        int result = partnerMapper.deleteById(partnerId);
        
        if (result > 0) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }
}