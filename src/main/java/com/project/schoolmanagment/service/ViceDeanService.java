package com.project.schoolmanagment.service;


import com.project.schoolmanagment.entity.concrate.ViceDean;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.ViceDeanDto;
import com.project.schoolmanagment.payload.request.ViceDeanRequest;
import com.project.schoolmanagment.payload.response.DeanResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.ViceDeanResponse;
import com.project.schoolmanagment.repository.ViceDeanRepository;
import com.project.schoolmanagment.utils.CheckParameterUpdateMethod;
import com.project.schoolmanagment.utils.ServiceHelpers;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViceDeanService {


    private final ServiceHelpers serviceHelpers;
    private final UserRoleService userRoleService;
    private final ViceDeanRepository viceDeanRepository;
    private final PasswordEncoder passwordEncoder;
    private final ViceDeanDto viceDeanDto;

    public ResponseMessage<ViceDeanResponse> saveViceDean(ViceDeanRequest viceDeanRequest) {
        serviceHelpers.checkDuplicate(viceDeanRequest.getUsername(),viceDeanRequest.getSsn(),viceDeanRequest.getPhoneNumber());
        ViceDean viceDean = viceDeanDto.mapViceDeanRequestToViceDean(viceDeanRequest);
        viceDean.setPassword(passwordEncoder.encode(viceDeanRequest.getPassword()));
        viceDean.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));

        ViceDean savedViceDean=viceDeanRepository.save(viceDean);

        return ResponseMessage.<ViceDeanResponse>builder()
                .message("ViceDean saved")
                .httpStatus(HttpStatus.CREATED)
                .object(viceDeanDto.mapViceDeanToViceDeanResponse(savedViceDean))
                .build();
    }

    public Optional<ViceDean> isViceDeanExist(Long viceDeanId) {
        Optional<ViceDean> viceDean = Optional.ofNullable(viceDeanRepository.findById(viceDeanId).orElseThrow(() -> new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, viceDeanId))));
        return viceDean;
    }


    public ResponseMessage<?> deleteViceDeanById(Long viceDeanId) {
            Optional<ViceDean> viceDean=isViceDeanExist(viceDeanId);

            viceDeanRepository.deleteById(viceDeanId);
            return ResponseMessage.<DeanResponse>builder()
                    .message("Dean deleted successfully")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }

    public ResponseMessage<ViceDeanResponse> getViceDeanById(Long viceDeanId) {
            return ResponseMessage.<ViceDeanResponse>builder()
                    .message("Dean found successfully")
                    .httpStatus(HttpStatus.OK)
                    .object(viceDeanDto.mapViceDeanToViceDeanResponse(isViceDeanExist(viceDeanId).get()))
                    .build();

    }

    public Page<ViceDeanResponse> getAllViceDeansByPage(int page, int size, String sort, String type) {
        Pageable pageable= serviceHelpers.getPageableWithProperties(page, size, sort, type);

        return  viceDeanRepository.findAll(pageable).map(viceDeanDto::mapViceDeanToViceDeanResponse);
    }

    public List<ViceDeanResponse> getAllViceDeans() {
        return viceDeanRepository.findAll().stream().map(viceDeanDto::mapViceDeanToViceDeanResponse).toList();
    }

    public ResponseMessage<ViceDeanResponse> update(ViceDeanRequest viceDeanRequest, Long viceDeanId) {
        Optional<ViceDean> viceDean = isViceDeanExist(viceDeanId);
        if (!CheckParameterUpdateMethod.checkUniqueProperties(viceDean.get(),viceDeanRequest)) {
            serviceHelpers.checkDuplicate(viceDeanRequest.getUsername(),
                    viceDeanRequest.getSsn(),
                    viceDeanRequest.getPhoneNumber());
        }
        ViceDean updatedViceDean=viceDeanDto.mapDeanRequestToUpdatedDean(viceDeanRequest,viceDeanId);
        updatedViceDean.setPassword(passwordEncoder.encode(viceDeanRequest.getPassword()));
        ViceDean savedViceDean =viceDeanRepository.save(updatedViceDean);
        return ResponseMessage.<ViceDeanResponse>builder()
                .message("ViceDean Updated successfully")
                .httpStatus(HttpStatus.OK)
                .object(viceDeanDto.mapViceDeanToViceDeanResponse(savedViceDean))
                .build();
    }
}

















