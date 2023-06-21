package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concrate.LessonProgram;
import com.project.schoolmanagment.entity.concrate.Teacher;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.request.TeacherRequest;
import com.project.schoolmanagment.payload.response.TeacherResponse;
import com.project.schoolmanagment.service.UserRoleService;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Data
@Component
public class TeacherDto {
    private final UserRoleService userRoleService;

    public Teacher mapTeacherRequestToTeacher(TeacherRequest teacherRequest){
        return Teacher.builder()
                .name(teacherRequest.getName())
                .surname(teacherRequest.getSurname())
                .ssn(teacherRequest.getSsn())
                .username(teacherRequest.getUsername())
                .birthDay(teacherRequest.getBirthDay())
                .birthPlace(teacherRequest.getBirthPlace())
                .password(teacherRequest.getPassword())
                .phoneNumber(teacherRequest.getPhoneNumber())
                .email(teacherRequest.getEmail())
                .isAdvisor(teacherRequest.isAdvisorTeacher())
                .gender(teacherRequest.getGender())
                .build();
    }
    public TeacherResponse mapTeacherToTeacherResponse(Teacher teacher){
        return TeacherResponse.builder()
                .userId(teacher.getId())
                .userName(teacher.getUsername())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .birthDay(teacher.getBirthDay())
                .birthPlace(teacher.getBirthPlace())
                .ssn(teacher.getSsn())
                .phoneNumber(teacher.getPhoneNumber())
                .gender(teacher.getGender())
                .email(teacher.getEmail())
                .build();
    }
//    public Teacher mapTeacherRequestToUpdatedTeacher(TeacherRequest teacherRequest,Long id, Set<LessonProgram> programList){
//        return Teacher.builder().id(id)
//                .username(teacherRequest.getUsername())
//                .ssn(teacherRequest.getSsn())
//                .name(teacherRequest.getName())
//                .surname(teacherRequest.getSurname())
//                .birthPlace(teacherRequest.getBirthPlace())
//                .birthDay(teacherRequest.getBirthDay())
//                .phoneNumber(teacherRequest.getPhoneNumber())
//                .gender(teacherRequest.getGender())
//                .email(teacherRequest.getEmail())
//                .isAdvisor(teacherRequest.getIsAdvisor())
//                .lessonProgramList(programList)
//                .userRole(userRoleService.getUserRole(RoleType.TEACHER))
//                .build();
//    }
}
