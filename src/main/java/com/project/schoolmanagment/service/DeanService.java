package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrate.Dean;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.DeanDto;
import com.project.schoolmanagment.payload.request.DeanRequest;
import com.project.schoolmanagment.payload.response.DeanResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.DeanRepository;
import com.project.schoolmanagment.utils.CheckParameterUpdateMethod;
import com.project.schoolmanagment.utils.ServiceHelpers;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeanService {

    private final ServiceHelpers serviceHelpers;
    private final DeanDto deanDto;
    private final DeanRepository deanRepository;
    private final UserRoleService  userRoleService;
    private final PasswordEncoder passwordEncoder;
    //TODO use mapsturct in your 3. repository
     public ResponseMessage<DeanResponse> save(DeanRequest deanRequest){
         serviceHelpers.checkDuplicate(deanRequest.getUsername(),deanRequest.getSsn(),deanRequest.getPhoneNumber());
         Dean dean=deanDto.mapDeanRequestToDean(deanRequest);
         dean.setPassword(passwordEncoder.encode(deanRequest.getPassword()));
         dean.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));

         Dean savedDean=deanRepository.save(dean);
         return ResponseMessage.<DeanResponse>builder()
                 .message("Dean saved")
                 .httpStatus(HttpStatus.CREATED)
                 .object(deanDto.mapDeanToDeanResponse(savedDean))
                 .build();
     }


    public ResponseMessage<DeanResponse> update(DeanRequest deanRequest, Long deanId) {
        Optional<Dean> dean = isDeanExist(deanId);
         if (!CheckParameterUpdateMethod.checkUniqueProperties(dean.get(),deanRequest)) {
            serviceHelpers.checkDuplicate(deanRequest.getUsername(),
                                        deanRequest.getSsn(),
                                        deanRequest.getPhoneNumber());
        }
        Dean updatedDean=deanDto.mapDeanRequestToUpdatedDean(deanRequest,deanId);
        updatedDean.setPassword(passwordEncoder.encode(deanRequest.getPassword()));
        Dean savedDean =deanRepository.save(updatedDean);
        return ResponseMessage.<DeanResponse>builder()
                .message("Dean Updated successfully")
                .httpStatus(HttpStatus.OK)
                .object(deanDto.mapDeanToDeanResponse(savedDean))
                .build();
    }


    public Optional<Dean> isDeanExist(Long deanId) {
        Optional<Dean> dean = deanRepository.findById(deanId);
        deanRepository.findById(deanId).orElseThrow(()->new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, deanId)));
        return dean;
    }


    public ResponseMessage<?> deleteDeanById(Long deanId) {
    Optional<Dean> dean=isDeanExist(deanId);

    deanRepository.deleteById(deanId);
        return ResponseMessage.<DeanResponse>builder()
                .message("Dean deleted successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<DeanResponse> getDeanById(Long deanId) {
         return ResponseMessage.<DeanResponse>builder()
                 .message("Dean found successfully")
                 .httpStatus(HttpStatus.OK)
                 .object(deanDto.mapDeanToDeanResponse(isDeanExist(deanId).get()))
                 .build();
    }


    public List<DeanResponse> getAllDeans() {
         return deanRepository.findAll().stream().map(deanDto::mapDeanToDeanResponse).toList();
    }

    public Page<DeanResponse> getAllDeansByPage(int page, int size, String sort, String type) {
        Pageable pageable = serviceHelpers.getPageableWithProperties(page,size,sort,type);

        return  deanRepository.findAll(pageable).map(deanDto::mapDeanToDeanResponse);
    }
}
