package com.project.schoolmanagment.service;


import com.project.schoolmanagment.entity.concrate.AdvisoryTeacher;
import com.project.schoolmanagment.entity.concrate.LessonProgram;
import com.project.schoolmanagment.entity.concrate.Meet;
import com.project.schoolmanagment.entity.concrate.Student;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.mappers.StudentDto;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentResponse;
import com.project.schoolmanagment.repository.StudentRepository;
import com.project.schoolmanagment.utils.Messages;
import com.project.schoolmanagment.utils.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {


    private final StudentRepository studentRepository;
    private final ServiceHelpers serviceHelpers;
    private final StudentDto studentDto;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final LessonProgramService lessonProgramService;
    private final MeetService meetService;
    private final AdvisoryTeacherService advisoryTeacherService;


    public ResponseMessage<StudentResponse> save(StudentRequest studentRequest) {
        serviceHelpers.checkDuplicate(studentRequest.getUsername(),studentRequest.getSsn(),studentRequest.getPhoneNumber(),studentRequest.getEmail());
//       Set<LessonProgram> lessonPrograms = lessonProgramService.getAllLessonProgramById();
//        List<Meet> meetList = meetService.getAllMeetsById(studentRequest.getMeetList());
//        AdvisoryTeacher advisoryTeacher= advisoryTeacherService.getById(studentRequest.getAdvisoryTeacher().getId());
        Student student=studentDto.mapStudentRequestToStudent(studentRequest);
        student.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        student.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));

        Student savedStudent=studentRepository.save(student);
        return ResponseMessage.<StudentResponse>builder()
                .message(Messages.STUDENT_SAVED_SUCCESSFULLY)
                .httpStatus(HttpStatus.CREATED)
                .object(studentDto.mapStudentToStudentResponse(savedStudent))
                .build();
    }
}
