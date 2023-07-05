package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrate.AdvisoryTeacher;
import com.project.schoolmanagment.entity.concrate.Teacher;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.AdvisoryTeacherDto;
import com.project.schoolmanagment.payload.response.AdvisorTeacherResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.AdvisoryTeacherRepository;
import com.project.schoolmanagment.utils.Messages;
import com.project.schoolmanagment.utils.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvisoryTeacherService {

    private final AdvisoryTeacherRepository advisoryTeacherRepository;
    private final UserRoleService userRoleService;
    private final AdvisoryTeacherDto advisoryTeacherDto;
    private final ServiceHelpers serviceHelpers;




    public void saveAdvisoryTeacher(Teacher teacher){
        AdvisoryTeacher advisoryTeacher=advisoryTeacherDto.mapTeacherToAdvisoryTeacher(teacher);
        advisoryTeacher.setUserRole(userRoleService.getUserRole(RoleType.ADVISORY_TEACHER));
        advisoryTeacherRepository.save(advisoryTeacher);
    }

    public AdvisoryTeacher getById(Long advisoryTeacherId) {
            return advisoryTeacherRepository.findById(advisoryTeacherId)
                    .orElseThrow(()->new ResourceNotFoundException(Messages.ADVISORY_TEACHER_NOT_FOUND_SUCCESSFULLY));
    }

    public void updateAdvisoryTeacher(boolean status, Teacher teacher) {
        // we are checking the database to find the correct advisory teacher
        Optional<AdvisoryTeacher> advisoryTeacher=advisoryTeacherRepository.getAdvisoryTeacherByTeacher_Id(teacher.getId());
        AdvisoryTeacher.AdvisoryTeacherBuilder advisoryTeacherBuilder=AdvisoryTeacher.builder()
                .teacher(teacher)
                .userRole(userRoleService.getUserRole(RoleType.ADVISORY_TEACHER));
        //do we really have an advisory teacher in DB?
        if(advisoryTeacher.isPresent()){
            //the updated teacher is it really advisory teacher?
            if (status){
                advisoryTeacherBuilder.id(advisoryTeacher.get().getId());
                advisoryTeacherRepository.save(advisoryTeacherBuilder.build());
            }else {
                advisoryTeacherRepository.deleteById(advisoryTeacher.get().getId());
            }
        }
    }

    public List<AdvisorTeacherResponse> GetALLAdvisoryTeachers() {
        return advisoryTeacherRepository
                .findAll()
                .stream()
                .map(advisoryTeacherDto::mapAdvisorTeacherToAdvisorTeacherResponse)
                .collect(Collectors.toList());
    }

    public Page<AdvisorTeacherResponse> search(int page, int size, String sort, String type) {
        Pageable pageable=serviceHelpers.getPageableWithProperties(page, size, sort, type);
        return advisoryTeacherRepository.findAll(pageable).map(advisoryTeacherDto::mapAdvisorTeacherToAdvisorTeacherResponse);
    }

    public ResponseMessage deleteAdvisoryTeacherById(Long id) {
        AdvisoryTeacher advisoryTeacher=getById(id);
        advisoryTeacherRepository.deleteById(advisoryTeacher.getId());
        return ResponseMessage.<AdvisoryTeacher>builder()
                .message(Messages.ADVISORY_TEACHER_DELETED)
                .httpStatus(HttpStatus.OK)
                .build();
    }
    public AdvisoryTeacher getAdvisoryTeacherByUsername(String username) {
        return advisoryTeacherRepository.getAdvisoryTeacherByTeacherUsername(username).orElseThrow(() -> new ResourceNotFoundException(Messages.ADVISORY_TEACHER_NOT_FOUND_SUCCESSFULLY));

    }
}
