package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concrate.AdvisoryTeacher;
import com.project.schoolmanagment.entity.concrate.LessonProgram;
import com.project.schoolmanagment.entity.concrate.Meet;
import com.project.schoolmanagment.entity.concrate.Student;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.response.StudentResponse;
import com.project.schoolmanagment.service.UserRoleService;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Data
public class StudentDto {
    private final UserRoleService userRoleService;

    public Student mapStudentRequestToStudent(StudentRequest studentRequest){
        return  Student.builder()
                .fatherName(studentRequest.getFatherName())
                .motherName(studentRequest.getMotherName())
                .birthDay(studentRequest.getBirthDay())
                .birthPlace(studentRequest.getBirthPlace())
                .name(studentRequest.getName())
                .surname(studentRequest.getSurname())
                .password(studentRequest.getPassword())
                .username(studentRequest.getUsername())
                .ssn(studentRequest.getSsn())
                .email(studentRequest.getEmail())
                .phoneNumber(studentRequest.getPhoneNumber())
                .gender(studentRequest.getGender())
                .build();
    }
    public StudentResponse mapStudentToStudentResponse(Student student){
        return StudentResponse.builder()
                .userId(student.getId())
                .userName(student.getUsername())
                .name(student.getName())
                .surname(student.getSurname())
                .birthDay(student.getBirthDay())
                .birthPlace(student.getBirthPlace())
                .phoneNumber(student.getPhoneNumber())
                .gender(student.getGender())
                .email(student.getEmail())
                .fatherName(student.getFatherName())
                .motherName(student.getMotherName())
                .studentNumber(student.getStudentNumber())
                .isActive(student.isActive())
                .build();
    }
    public Student mapStudentRequestToUpdatedStudent(StudentRequest studentRequest,Long id){
        Student student=mapStudentRequestToStudent(studentRequest);
        student.setId(id);
        return student;
    }
}


