package com.project.schoolmanagment.payload.mappers;


import com.project.schoolmanagment.entity.concrate.Dean;
import com.project.schoolmanagment.entity.concrate.ViceDean;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.request.ViceDeanRequest;
import com.project.schoolmanagment.payload.response.ViceDeanResponse;
import com.project.schoolmanagment.service.UserRoleService;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ViceDeanDto {

private final UserRoleService userRoleService;
    public  ViceDean mapViceDeanRequestToViceDean(ViceDeanRequest viceDeanRequest) {
        return  ViceDean.builder()
                .username(viceDeanRequest.getUsername())
                .name(viceDeanRequest.getName())
                .surname(viceDeanRequest.getSurname())
                .password(viceDeanRequest.getPassword())
                .ssn(viceDeanRequest.getSsn())
                .birthDay(viceDeanRequest.getBirthDay())
                .birthPlace(viceDeanRequest.getBirthPlace())
                .phoneNumber(viceDeanRequest.getPhoneNumber())
                .gender(viceDeanRequest.getGender())
                .build();
    }

    public  ViceDeanResponse mapViceDeanToViceDeanResponse(ViceDean viceDean) {
        return ViceDeanResponse.builder()
                .userId(viceDean.getId())
                .userName(viceDean.getUsername())
                .name(viceDean.getName())
                .surname(viceDean.getSurname())
                .phoneNumber(viceDean.getPhoneNumber())
                .birthDay(viceDean.getBirthDay())
                .birthPlace(viceDean.getBirthPlace())
                .gender(viceDean.getGender())
                .ssn(viceDean.getSsn())
                .build();
    }

    public ViceDean mapDeanRequestToUpdatedDean(ViceDeanRequest viceDeanRequest, Long viceDeanId) {
        return ViceDean.builder().id(viceDeanId)
                .username(viceDeanRequest.getUsername())
                .ssn(viceDeanRequest.getSsn())
                .name(viceDeanRequest.getName())
                .surname(viceDeanRequest.getSurname())
                .birthPlace(viceDeanRequest.getBirthPlace())
                .birthDay(viceDeanRequest.getBirthDay())
                .phoneNumber(viceDeanRequest.getPhoneNumber())
                .gender(viceDeanRequest.getGender())
                .userRole(userRoleService.getUserRole(RoleType.MANAGER))
                .build();
    }
}
