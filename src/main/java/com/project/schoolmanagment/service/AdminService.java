package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrate.Admin;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.AdminDto;
import com.project.schoolmanagment.payload.request.AdminRequest;
import com.project.schoolmanagment.payload.response.AdminResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.*;
import com.project.schoolmanagment.utils.ServiceHelpers;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final UserRoleService  userRoleService;
    private final ServiceHelpers serviceHelpers;
    private final AdminDto adminDto;
    private final PasswordEncoder passwordEncoder;

    public ResponseMessage<AdminResponse> save(AdminRequest adminRequest){
        serviceHelpers.checkDuplicate(adminRequest.getUsername(),adminRequest.getSsn(),adminRequest.getPhoneNumber());
        Admin admin=adminDto.mapAdminRequestToAdmin(adminRequest);
        admin.setBuild_in(false);
        if(Objects.equals(adminRequest.getUsername(),"Admin")){
            admin.setBuild_in(true);        }
        admin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
        admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
        Admin savedAdmin=adminRepository.save(admin);
        return ResponseMessage.<AdminResponse>builder()
                .message("Admin saved")
                .httpStatus(HttpStatus.CREATED)
                .object(adminDto.mapAdminToAdminResponse(savedAdmin))
                .build();
    }

    public long countAllAdmins(){
        return adminRepository.count();
    }


    public Page<AdminResponse> getAllAdmins(int page, int size, String sort, String type){
        Pageable pageable= serviceHelpers.getPageableWithProperties(page,size,sort,type);
        return adminRepository.findAll(pageable).map(adminDto::mapAdminToAdminResponse);
    }


    public String deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, id)));
        if (admin.isBuild_in()) {
            throw new ConflictException(Messages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        adminRepository.deleteById(id);
        return Messages.ADMIN_DELETED_SUCCESSFULLY;
    }



}
