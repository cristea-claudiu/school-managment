package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrate.Admin;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.payload.request.AdminRequest;
import com.project.schoolmanagment.payload.response.AdminResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.*;
import com.project.schoolmanagment.utils.FieldControl;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final UserRoleService  userRoleService;
    private final FieldControl fieldControl;

    public ResponseMessage save(AdminRequest adminRequest){
        fieldControl.checkDuplicate(adminRequest.getUsername(),adminRequest.getSsn(),adminRequest.getPhoneNumber());
        Admin admin=mapAdminRequestToAdmin(adminRequest);
        admin.setBuild_in(false);
        if(Objects.equals(adminRequest.getUsername(),"Admin")){
            admin.setBuild_in(true);
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
                .birthDay(admin.getBirthDay())
                .birthPlace(admin.getBirthPlace())
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
    public long countAllAdmins(){
        return adminRepository.count();
    }


    public Page<AdminResponse> getAllAdmins(int page, int size, String sort, String type){
        Pageable pageable= PageRequest.of(page, size, Sort.by(sort).ascending());
        if(Objects.equals(type,"desc")){
            pageable= PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return adminRepository.findAll(pageable).map(this::mapAdminToAdminResponse);
    }


    public String deleteAdmin(Long id){
        Optional<Admin> admin =adminRepository.findById(id);

        //TODO please divide the cases and throw meaningfully response messages
        if (admin.isPresent() && admin.get().isBuild_in()){
            throw new ConflictException(Messages.NOT_PERMITED_METHOD_MESSAGE);
        }

        if (admin.isPresent()){
            adminRepository.deleteById(id);
            return Messages.ADMIN_DELETED_SUCCESSFULLY;
        }
        return String.format(Messages.NOT_FOUND_USER_MESSAGE,id);

    }



}
