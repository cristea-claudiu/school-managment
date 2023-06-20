package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concrate.Admin;
import com.project.schoolmanagment.payload.request.AdminRequest;
import com.project.schoolmanagment.payload.response.AdminResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AdminDto {


    public AdminResponse mapAdminToAdminResponse(Admin admin){
        return AdminResponse.builder()
                .userId(admin.getId())
                .userName(admin.getUsername())
                .name(admin.getName())
                .surname(admin.getSurname())
                .phoneNumber(admin.getPhoneNumber())
                .birthDay(admin.getBirthDay())
                .birthPlace(admin.getBirthPlace())
                .gender(admin.getGender())
                .ssn(admin.getSsn())
                .build();
    }

    public Admin mapAdminRequestToAdmin(AdminRequest adminRequest){
        return Admin.builder()
                .username(adminRequest.getUsername())
                .name(adminRequest.getName())
                .surname(adminRequest.getSurname())
                .password(adminRequest.getPassword())
                .ssn(adminRequest.getSsn())
                .birthDay(adminRequest.getBirthDay())
                .birthPlace(adminRequest.getBirthPlace())
                .phoneNumber(adminRequest.getPhoneNumber())
                .gender(adminRequest.getGender()).build();
    }
}
