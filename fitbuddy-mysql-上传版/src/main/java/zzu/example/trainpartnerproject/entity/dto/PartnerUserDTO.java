package zzu.example.trainpartnerproject.entity.dto;

import zzu.example.trainpartnerproject.entity.Partner;
import zzu.example.trainpartnerproject.entity.User;

import java.time.LocalDateTime;


public class PartnerUserDTO {
    private Long id;
    private Long userId;
    private String username; // User表的用户名
    private String name;
    private String gender;
    private String fitnessType;
    private String location;
    private String bio;
    private String contact;
    private LocalDateTime createTime;


    public static PartnerUserDTO from(Partner partner, User user) {
        PartnerUserDTO dto = new PartnerUserDTO();
        dto.setId(partner.getId());
        dto.setUserId(partner.getUserId());
        dto.setUsername(user != null ? user.getUsername() : "");
        dto.setName(partner.getName());
        dto.setGender(partner.getGender());
        dto.setFitnessType(partner.getFitnessType());
        dto.setLocation(partner.getLocation());
        dto.setBio(partner.getBio());
        dto.setContact(partner.getContact());
        dto.setCreateTime(partner.getCreateTime());
        return dto;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFitnessType() {
        return fitnessType;
    }

    public void setFitnessType(String fitnessType) {
        this.fitnessType = fitnessType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}