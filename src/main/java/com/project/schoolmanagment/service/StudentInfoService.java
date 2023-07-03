package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrate.*;
import com.project.schoolmanagment.entity.enums.Note;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.StudentInfoDto;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentInfoRequest;
import com.project.schoolmanagment.payload.response.StudentInfoResponse;
import com.project.schoolmanagment.repository.StudentInfoRepository;
import com.project.schoolmanagment.utils.Messages;
import com.project.schoolmanagment.utils.ServiceHelpers;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentInfoService {

    private final StudentInfoRepository studentInfoRepository;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final EducationTermService educationTermService;
    private final LessonService lessonService;
    private final StudentInfoDto studentInfoDto;
    private final ServiceHelpers serviceHelpers;

    @Value("${midterm.exam.impact.percentage}")
    private Double midtermExamPercentage;
    @Value("${final.exam.impact.percentage}")
    private Double finalExamPercentage;

    public ResponseMessage<StudentInfoResponse> saveStudentInfo(String teacherUsername, StudentInfoRequest studentInfoRequest) {
        //we need entity for creation of StudentInfos
        Student student=studentService.isStudentExist(studentInfoRequest.getStudentId());
        Teacher teacher=teacherService.getTeacherByUsername(teacherUsername);
        Lesson lesson=lessonService.isLessonExistById(studentInfoRequest.getLessonId());
        EducationTerm educationTerm=educationTermService.getEducationTermById(studentInfoRequest.getEducationTermId());

        //does the student really have only one lesson according to this lesson name
        checkSameLesson(studentInfoRequest.getStudentId(),lesson.getLessonName());
        //we need grade calculation
        Note note =checkLetterGrade(calculateExamAverage(studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam()));

        StudentInfo studentInfo=studentInfoDto.mapStudentInfoRequestToStudentInfo(
                studentInfoRequest
                ,note,
                calculateExamAverage(studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam())
                    );
        studentInfo.setStudent(student);
        studentInfo.setEducationTerm(educationTerm);
        studentInfo.setTeacher(teacher);
        studentInfo.setLesson(lesson);
        StudentInfo savedStudentInfo = studentInfoRepository.save(studentInfo);
            return ResponseMessage.<StudentInfoResponse>builder()
                    .message(Messages.STUDENT_INFO_SAVED)
                    .httpStatus(HttpStatus.OK)
                    .object(studentInfoDto.mapStudentInfoToStudentInfoResponse(savedStudentInfo))
                    .build();
    }


    private void checkSameLesson(Long studentId,String lessonName){
       boolean isLessonDuplicationExist= studentInfoRepository.getAllByStudentId_Id(studentId)
               .stream()
               .anyMatch((e)->e.getLesson().getLessonName().equalsIgnoreCase(lessonName));
       if (isLessonDuplicationExist){
           throw new ConflictException(String.format(Messages.ALREADY_REGISTER_LESSON_MESSAGE,lessonName));
       }
    }

    private Double calculateExamAverage(Double midtermExam,Double finalExam){
        return ((midtermExam*midtermExamPercentage) + (finalExam*finalExamPercentage));
    }


    private Note checkLetterGrade(Double average){

        if(average<50.0) {
            return Note.FF;
        } else if (average>=50.0 && average<55) {
            return Note.DD;
        } else if (average>=55.0 && average<60) {
            return Note.DC;
        } else if (average>=60.0 && average<65) {
            return Note.CC;
        } else if (average>=65.0 && average<70) {
            return Note.CB;
        } else if (average>=70.0 && average<75) {
            return Note.BB;
        } else if (average>=75.0 && average<80) {
            return Note.BA;
        } else {
            return Note.AA;
        }
    }


    public List<StudentInfoResponse> getAllStudentInfo() {
        return studentInfoRepository.findAll()
                .stream()
                .map(studentInfoDto::mapStudentInfoToStudentInfoResponse)
                .collect(Collectors.toList());
    }

    public ResponseMessage deleteStudentInfo(Long studentInfoId) {
      StudentInfo studentInfo=isStudentInfoExistById(studentInfoId);

        studentInfoRepository.deleteById(studentInfoId);
        return ResponseMessage.builder()
                .message(Messages.STUDENT_DELETED_SUCCESSFULLY)
                .httpStatus(HttpStatus.OK)
                .build();
    }
    public StudentInfo isStudentInfoExistById(Long id) {
        boolean isExist=studentInfoRepository.existsByIdEquals(id);
        if (!isExist){
            throw new ResourceNotFoundException(Messages.STUDENT_INFO_NOT_FOUND);
        }else {
            return studentInfoRepository.findById(id).get() ;
        }
    }
    public Page<StudentInfoResponse> search(int page, int size, String sort, String type) {
        Pageable pageable= serviceHelpers.getPageableWithProperties(page, size, sort, type);
        return studentInfoRepository.findAll(pageable).map(studentInfoDto::mapStudentInfoToStudentInfoResponse);
    }
}
