package com.project.schoolmanagment.service;


import com.project.schoolmanagment.entity.concrate.AdvisoryTeacher;
import com.project.schoolmanagment.entity.concrate.LessonProgram;
import com.project.schoolmanagment.entity.concrate.Meet;
import com.project.schoolmanagment.entity.concrate.Student;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.StudentDto;
import com.project.schoolmanagment.payload.request.ChooseLessonProgramWithId;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentResponse;
import com.project.schoolmanagment.payload.response.TeacherResponse;
import com.project.schoolmanagment.repository.StudentRepository;
import com.project.schoolmanagment.utils.CheckParameterUpdateMethod;
import com.project.schoolmanagment.utils.CheckSameLessonProgram;
import com.project.schoolmanagment.utils.Messages;
import com.project.schoolmanagment.utils.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {


    private final StudentRepository studentRepository;
    private final ServiceHelpers serviceHelpers;
    private final StudentDto studentDto;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final AdvisoryTeacherService advisoryTeacherService;
    private final LessonProgramService   lessonProgramService;
    private final CheckSameLessonProgram     checkSameLessonProgram;


    public ResponseMessage<StudentResponse> saveStudent(StudentRequest studentRequest) {
        AdvisoryTeacher advisoryTeacher=advisoryTeacherService.getById(studentRequest.getAdvisorTeacherId());
        serviceHelpers.checkDuplicate(
                studentRequest.getUsername(),
                studentRequest.getSsn(),
                studentRequest.getPhoneNumber(),
                studentRequest.getEmail());
        Student student=studentDto.mapStudentRequestToStudent(studentRequest);
        student.setAdvisoryTeacher(advisoryTeacher);
        student.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        student.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
        student.setActive(true);
        student.setStudentNumber(getLastNumber());
        Student savedStudent=studentRepository.save(student);
        return ResponseMessage.<StudentResponse>builder()
                .message(Messages.STUDENT_SAVED_SUCCESSFULLY)
                .httpStatus(HttpStatus.CREATED)
                .object(studentDto.mapStudentToStudentResponse(savedStudent))
                .build();
    }

    private int getLastNumber(){
        if (!studentRepository.findStudent()){
            return 1000;
        }
        return studentRepository.getMaxStudentNumber()+1;
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream().map(studentDto::mapStudentToStudentResponse).collect(Collectors.toList());
    }
    public Student isStudentExist(Long studentid){
        return studentRepository
                .findById(studentid)
                .orElseThrow(()-> new ResourceNotFoundException(String.format(Messages.STUDENT_NOT_FOUND_BY_STUDENT_ID,studentid)));
    }
    private Student isStudentExist(String userName){
        Student student=  studentRepository.findByUsernameEquals(userName);
            if (student.equals(null)){

       throw  new ResourceNotFoundException(Messages.STUDENT_NOT_FOUND_BY_STUDENT_NAME);}
return student;
    }

    public ResponseMessage changeStatus(Long id, boolean status) {
        Student student=isStudentExist(id);
        student.setActive(status);
        studentRepository.save(student);
        return ResponseMessage.builder()
              .message("Student is: "+(status? "active":"passive") )
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage deleteStudentById(Long id) {
        isStudentExist(id);
        studentRepository.deleteById(id);
        return ResponseMessage.builder()
                .message(Messages.STUDENT_DELETED_SUCCESSFULLY)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public Page<StudentResponse> getAllStudentsByPage(int page, int size, String sort, String type) {
        Pageable pageable =serviceHelpers.getPageableWithProperties(page,size,sort,type);

        return studentRepository.findAll(pageable).map(studentDto::mapStudentToStudentResponse);
    }

    public ResponseMessage<StudentResponse> updateStudent(Long id, StudentRequest studentRequest) {
        Student student=isStudentExist(id);
        AdvisoryTeacher advisoryTeacher= advisoryTeacherService.getById(studentRequest.getAdvisorTeacherId());
        if (!CheckParameterUpdateMethod.checkUniquePropertiesForStudent(student,studentRequest)){
            serviceHelpers.checkDuplicate(studentRequest.getUsername(),
                                          studentRequest.getSsn(),
                                          studentRequest.getPhoneNumber(),
                                          studentRequest.getEmail());
        }
        Student updatedStudent=studentDto.mapStudentRequestToUpdatedStudent(studentRequest,id);
        updatedStudent.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        updatedStudent.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
        updatedStudent.setActive(true);
        updatedStudent.setAdvisoryTeacher(advisoryTeacher);
        updatedStudent.setStudentNumber(student.getStudentNumber());
        Student savedStudent=studentRepository.save(updatedStudent);

        return ResponseMessage.<StudentResponse>builder()
                .message(Messages.STUDENT_UPDATED_SUCCESSFULLY)
                .httpStatus(HttpStatus.OK)
                .object(studentDto.mapStudentToStudentResponse(savedStudent))
                .build();
    }

    public List<StudentResponse> getStudentByName(String studentName) {
        List<Student> students = studentRepository.getStudentByNameContaining(studentName);
        if (students.isEmpty()) {
            throw new ResourceNotFoundException(Messages.STUDENT_NOT_FOUND_BY_STUDENT_NAME);
        }
                return students.stream().map(studentDto::mapStudentToStudentResponse).collect(Collectors.toList());
    }

    public Student GetStudentById(Long id) {
        return isStudentExist(id);
    }
    public Student getStudentByUserName(String userName) {
        return isStudentExist(userName);
    }

    public List<StudentResponse> GetAllByUsername(String advisoryUserName) {
        return studentRepository.getStudentByAdvisoryTeacher_Username(advisoryUserName)
                .stream()
                .map(studentDto::mapStudentToStudentResponse)
                .collect(Collectors.toList());
    }

    public ResponseMessage<StudentResponse> chooseLesson(String userName, ChooseLessonProgramWithId chooseLessonProgramWithId) {
        Student student=isStudentExist(userName);
        Set<LessonProgram> lessonProgramSet=lessonProgramService.getAllLessonProgramById(chooseLessonProgramWithId.getLessonProgramId());
        Set<LessonProgram> studentCurentLessonProgramSet=student.getLessonProgramList();
        checkSameLessonProgram.checkLessonProgram(lessonProgramSet,studentCurentLessonProgramSet);
        studentCurentLessonProgramSet.addAll(lessonProgramSet);
        student.setLessonProgramList(studentCurentLessonProgramSet);
        Student savedStudent=studentRepository.save(student);
        return ResponseMessage.<StudentResponse>builder()
                .message(Messages.STUDENT_LESSON_SAVED)
                .httpStatus(HttpStatus.OK)
                .object(studentDto.mapStudentToStudentResponse(savedStudent))
                .build();
    }
    public List<Student> getStudentById(Long[] studentIds) {
        return studentRepository.findByIdsEquals(studentIds);
    }
}
