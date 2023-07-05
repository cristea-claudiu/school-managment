package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrate.EducationTerm;
import com.project.schoolmanagment.entity.concrate.Lesson;
import com.project.schoolmanagment.entity.concrate.LessonProgram;
import com.project.schoolmanagment.exception.BadRequestException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.LessonProgramDto;
import com.project.schoolmanagment.payload.request.LessonProgramRequest;
import com.project.schoolmanagment.payload.response.LessonProgramResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.LessonProgramRepository;
import com.project.schoolmanagment.utils.Messages;
import com.project.schoolmanagment.utils.ServiceHelpers;
import com.project.schoolmanagment.utils.TimeControl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonProgramService {

    private final LessonProgramRepository lessonProgramRepository;
    private final LessonProgramDto lessonProgramDto;
    private final LessonService lessonService;
    private final EducationTermService educationTermService;
    private final ServiceHelpers serviceHelpers;

    public ResponseMessage<LessonProgramResponse> saveLessonProgram(LessonProgramRequest lessonProgramRequest) {
        Set<Lesson> lessons = lessonService.getAllLessonByLessonId(lessonProgramRequest.getLessonIdList());
        EducationTerm educationTerm=educationTermService.getEducationTermById(lessonProgramRequest.getEducationTermId());
        if (lessons.size() == 0) {
            throw new ResourceNotFoundException(Messages.NOT_FOUND_LESSON_IN_LIST);
        }
        TimeControl.checkTimeWithException(lessonProgramRequest.getStartTime(),lessonProgramRequest.getStopTime());
        LessonProgram lessonProgram=lessonProgramDto.mapLessonProgramRequestToLessonProgram(lessonProgramRequest,lessons,educationTerm);
        LessonProgram savedLessonProgram= lessonProgramRepository.save(lessonProgram);

        return ResponseMessage.<LessonProgramResponse>builder()
                .object(lessonProgramDto.mapLessonProgramToLessonProgramResponse(savedLessonProgram))
                .message(Messages.LESSON_PROGRAM_SAVED)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }


    public LessonProgramResponse getLessonProgramById(Long id) {
        return lessonProgramDto.mapLessonProgramToLessonProgramResponse(isLessonProgramExistById(id));
    }

    public Page<LessonProgramResponse> findByPage(int page, int size, String sort, String type) {
        Pageable pageable =serviceHelpers.getPageableWithProperties(page, size, sort, type);
        return lessonProgramRepository.findAll(pageable).map(lessonProgramDto::mapLessonProgramToLessonProgramResponse);
    }


    public List<LessonProgramResponse> getLessonProgramByList() {
        return lessonProgramRepository.findAll().stream().map(lessonProgramDto::mapLessonProgramToLessonProgramResponse).collect(Collectors.toList());
    }

    public List<LessonProgramResponse> getAllLessonProgramUnassigned() {
       return lessonProgramRepository.findByTeachers_IdNull().stream().map(lessonProgramDto::mapLessonProgramToLessonProgramResponse).collect(Collectors.toList());
    }

    public List<LessonProgramResponse> getAllLessonProgramAssigned() {
        return lessonProgramRepository.findByTeachers_IdNotNull().stream().map(lessonProgramDto::mapLessonProgramToLessonProgramResponse).collect(Collectors.toList());

    }

    public ResponseMessage deleteLessonProgramById(Long id) {
       LessonProgram lesson= isLessonProgramExistById(id);
       lessonProgramRepository.delete(lesson);
        return ResponseMessage.builder()
                .message(Messages.LESSON_PROGRAM_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    private LessonProgram isLessonProgramExistById(Long id) {
       return lessonProgramRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(Messages.LESSON_PROGRAM_NOT_FOUND));
    }
    public Set<LessonProgramResponse> getAllLessonProgramByTeacher(String username) {
        Set<LessonProgramResponse> lessonSet= lessonProgramRepository
                .getLessonProgramByTeachersUsername(username)
                .stream()
                .map(lessonProgramDto::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toSet());
        if (lessonSet.isEmpty()){
            throw new ResourceNotFoundException(Messages.LESSON_PROGRAM_NOT_FOUND);
        }
        return lessonSet;

    }

    public Set<LessonProgramResponse> getAllLessonProgramByStudent(String username) {
        Set<LessonProgramResponse> lessonSet= lessonProgramRepository
                .getLessonProgramByStudentsUsername(username)
                .stream()
                .map(lessonProgramDto::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toSet());
        if (lessonSet.isEmpty()){
            throw new ResourceNotFoundException(Messages.LESSON_PROGRAM_NOT_FOUND);
        }else {
            return lessonSet;
        }
    }

    public Set<LessonProgram> getAllLessonProgramById(Set<Long> lessonProgramId) {
        Set<LessonProgram> programList= lessonProgramRepository.getLessonProgramByLessonProgramIdList(lessonProgramId);
        if (programList.isEmpty()){
            throw new BadRequestException(Messages.LESSON_PROGRAM_NOT_FOUND);
        }
        return programList;
    }
}

