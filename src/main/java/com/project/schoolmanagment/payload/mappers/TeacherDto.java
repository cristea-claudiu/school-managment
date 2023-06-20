package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concrate.Dean;
import com.project.schoolmanagment.entity.concrate.Teacher;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.request.DeanRequest;
import com.project.schoolmanagment.payload.request.TeacherRequest;
import com.project.schoolmanagment.payload.response.DeanResponse;
import com.project.schoolmanagment.payload.response.TeacherResponse;
import com.project.schoolmanagment.service.UserRoleService;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TeacherDto {
    private final UserRoleService userRoleService;

    public Teacher mapTeacherRequestToTeacher(TeacherRequest teacherRequest){
        return  Teacher.builder()
                .username(teacherRequest.getUsername())
                .name(teacherRequest.getName())
                .surname(teacherRequest.getSurname())
                .password(teacherRequest.getPassword())
                .ssn(teacherRequest.getSsn())
                .birthDay(teacherRequest.getBirthDay())
                .birthPlace(teacherRequest.getBirthPlace())
                .phoneNumber(teacherRequest.getPhoneNumber())
                .gender(teacherRequest.getGender())
                .build();
    }
    public TeacherResponse mapTeacherToTeacherResponse(Teacher teacher){
        return TeacherResponse.builder()
                .userId(teacher.getId())
                .userName(teacher.getUsername())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .phoneNumber(teacher.getPhoneNumber())
                .birthDay(teacher.getBirthDay())
                .birthPlace(teacher.getBirthPlace())
                .gender(teacher.getGender())
                .ssn(teacher.getSsn())
                .build();
    }
    public Teacher mapTeacherRequestToUpdatedTeacher(TeacherRequest teacherRequest,Long id){
        return Teacher.builder().id(id)
                .username(teacherRequest.getUsername())
                .ssn(teacherRequest.getSsn())
                .name(teacherRequest.getName())
                .surname(teacherRequest.getSurname())
                .birthPlace(teacherRequest.getBirthPlace())
                .birthDay(teacherRequest.getBirthDay())
                .phoneNumber(teacherRequest.getPhoneNumber())
                .gender(teacherRequest.getGender())
                .userRole(userRoleService.getUserRole(RoleType.TEACHER))
                .build();
    }
}
