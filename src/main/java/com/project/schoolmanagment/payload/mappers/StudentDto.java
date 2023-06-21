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
                .username(studentRequest.getUsername())
                .name(studentRequest.getName())
                .surname(studentRequest.getSurname())
                .password(studentRequest.getPassword())
                .ssn(studentRequest.getSsn())
                .birthDay(studentRequest.getBirthDay())
                .birthPlace(studentRequest.getBirthPlace())
                .phoneNumber(studentRequest.getPhoneNumber())
                .gender(studentRequest.getGender())
                .email(studentRequest.getEmail())
                .studentNumber(studentRequest.getStudentNumber())
                .motherName(studentRequest.getMotherName())
                .fatherName(studentRequest.getFatherName())
//                .advisoryTeacher(advisoryTeacher)
//                .lessonProgramList(lessonProgramSet)
//                .meetList(meetList)
                .build();
    }
    public StudentResponse mapStudentToStudentResponse(Student student){
        return StudentResponse.builder()
                .userId(student.getId())
                .userName(student.getUsername())
                .name(student.getName())
                .surname(student.getSurname())
                .phoneNumber(student.getPhoneNumber())
                .birthDay(student.getBirthDay())
                .birthPlace(student.getBirthPlace())
                .gender(student.getGender())
                .ssn(student.getSsn())
                .email(student.getEmail())
//                .lessonProgramSet(student.getLessonProgramList())
                .studentNumber(student.getStudentNumber())
                .motherName(student.getMotherName())
                .fatherName(student.getFatherName())
                .isActive(student.isActive())
//                .meetList(student.getMeetList())
//                .advisoryTeacher(student.getAdvisoryTeacher())
                .build();
    }
    public Student mapStudentRequestToUpdatedStudent(StudentRequest studentRequest,Long id, Set<LessonProgram> lessonProgramSet, List<Meet> meetList){
        return Student.builder().id(id)
                .username(studentRequest.getUsername())
                .ssn(studentRequest.getSsn())
                .name(studentRequest.getName())
                .surname(studentRequest.getSurname())
                .birthPlace(studentRequest.getBirthPlace())
                .birthDay(studentRequest.getBirthDay())
                .phoneNumber(studentRequest.getPhoneNumber())
                .gender(studentRequest.getGender())
                .email(studentRequest.getEmail())
                .studentNumber(studentRequest.getStudentNumber())
                .motherName(studentRequest.getMotherName())
                .fatherName(studentRequest.getFatherName())
//                .advisoryTeacher(studentRequest.getAdvisoryTeacher())
                .meetList(meetList)
                .lessonProgramList(lessonProgramSet)
                .userRole(userRoleService.getUserRole(RoleType.STUDENT))
                .build();
    }
}


