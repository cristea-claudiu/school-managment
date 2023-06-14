package com.project.schoolmanagment.payload.mappers;



import com.project.schoolmanagment.entity.concrate.Dean;
import com.project.schoolmanagment.payload.request.DeanRequest;
import com.project.schoolmanagment.payload.response.DeanResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class DeanDto {

    public Dean mapDeanRequestToDean(DeanRequest deanRequest){
        return  Dean.builder()
                .username(deanRequest.getUsername())
                .name(deanRequest.getName())
                .surname(deanRequest.getSurname())
                .password(deanRequest.getPassword())
                .ssn(deanRequest.getSsn())
                .birthDay(deanRequest.getBirthDay())
                .birthPlace(deanRequest.getBirthPlace())
                .phoneNumber(deanRequest.getPhoneNumber())
                .gender(deanRequest.getGender())
                .build();
    }
    public DeanResponse mapDeanToDeanResponse(Dean dean){
        return DeanResponse.builder()
                .userId(dean.getId())
                .userName(dean.getUsername())
                .name(dean.getName())
                .surname(dean.getSurname())
                .phoneNumber(dean.getPhoneNumber())
                .birthDay(dean.getBirthDay())
                .birthPlace(dean.getBirthPlace())
                .gender(dean.getGender())
                .ssn(dean.getSsn())
                .build();
    }








}
