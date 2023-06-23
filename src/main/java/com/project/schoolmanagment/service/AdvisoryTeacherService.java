package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrate.AdvisoryTeacher;
import com.project.schoolmanagment.entity.concrate.Teacher;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.AdvisoryTeacherDto;
import com.project.schoolmanagment.repository.AdvisoryTeacherRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvisoryTeacherService {

    private final AdvisoryTeacherRepository advisoryTeacherRepository;
    private final UserRoleService userRoleService;
    private final AdvisoryTeacherDto advisoryTeacherDto;




    public void saveAdvisoryTeacher(Teacher teacher){
        AdvisoryTeacher advisoryTeacher=advisoryTeacherDto.mapTeacherToAdvisoryTeacher(teacher);
        advisoryTeacher.setUserRole(userRoleService.getUserRole(RoleType.ADVISORY_TEACHER));
        advisoryTeacherRepository.save(advisoryTeacher);
    }

    public AdvisoryTeacher getById(Long advisoryTeacherId) {
            return advisoryTeacherRepository.findById(advisoryTeacherId).orElseThrow(()->new ResourceNotFoundException(Messages.ADVISORY_TEACHER_NOT_FOUND_SUCCESSFULLY));
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
}
