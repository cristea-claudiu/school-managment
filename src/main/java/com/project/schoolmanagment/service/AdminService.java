package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrate.Admin;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.payload.request.AdminRequest;
import com.project.schoolmanagment.payload.response.AdminResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.*;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final DeanRepository deanRepository;
    private final ViceDeanRepository viceDeanRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final GuestUserRepository guestUserRepository;
    private final UserRoleService  userRoleService;

    public ResponseMessage save(AdminRequest adminRequest){
        checkDuplicate(adminRequest.getUsername(),adminRequest.getSsn(),adminRequest.getPhoneNumber());
        Admin admin=mapAdminRequestToAdmin(adminRequest);
        admin.setBuild_in(false);
        if(Objects.equals(adminRequest.getName(),"Admin")){
            admin.setBuild_in(false);
        }
        admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
        //we will implement password and encoder here

        Admin savedAdmin=adminRepository.save(admin);
        return ResponseMessage.<AdminResponse>builder()
                .message("Admin saved")
                .httpStatus(HttpStatus.CREATED)
                .object(mapAdminToAdminResponse(savedAdmin))
                .build();
    }

    private AdminResponse mapAdminToAdminResponse(Admin admin){
        return AdminResponse.builder()
                .userId(admin.getId())
                .userName(admin.getUsername())
                .name(admin.getName())
                .surname(admin.getSurname())
                .phoneNumber(admin.getPhoneNumber())
                .gender(admin.getGender())
                .ssn(admin.getSsn())
                .build();
    }

    private Admin mapAdminRequestToAdmin(AdminRequest adminRequest){
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
    //As a requirement all Admin, ViceAdmin, Dean, Student, Teacher, guestUser
    // should have unique userName, ssn and phone number

    public void checkDuplicate(String userName, String ssn, String phone){
        if(adminRepository.existsByUsername(userName)||
            deanRepository.existsByUsername(userName)||
            studentRepository.existsByUsername(userName)||
            teacherRepository.existsByUsername(userName)||
            viceDeanRepository.existsByUsername(userName)||
            guestUserRepository.existsByUsername(userName)){
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME,userName));
        } else if (adminRepository.existsBySsn(ssn)||
                    deanRepository.existsBySsn(ssn)||
                    studentRepository.existsBySsn(ssn)||
                    teacherRepository.existsBySsn(ssn)||
                    viceDeanRepository.existsBySsn(ssn)||
                    guestUserRepository.existsBySsn(ssn)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN,ssn));
        } else if (adminRepository.existsByPhoneNumber(phone)||
                    deanRepository.existsByPhoneNumber(phone)||
                    studentRepository.existsByPhoneNumber(phone)||
                    teacherRepository.existsByPhoneNumber(phone)||
                    viceDeanRepository.existsByPhoneNumber(phone)||
                    guestUserRepository.existsByPhoneNumber(phone)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER,phone));
        }


    }



    public long countAllAdmins(){
        return adminRepository.count();
    }






}
