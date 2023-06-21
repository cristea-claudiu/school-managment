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
}
