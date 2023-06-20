package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrate.Lesson;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.LessonDto;
import com.project.schoolmanagment.payload.request.LessonRequest;
import com.project.schoolmanagment.payload.response.LessonResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.LessonRepository;
import com.project.schoolmanagment.utils.Messages;
import com.project.schoolmanagment.utils.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonDto  lessonDto;
    private final ServiceHelpers serviceHelpers;


    public ResponseMessage<LessonResponse> saveLesson(LessonRequest lessonRequest) {
        isLessonExistByName(lessonRequest.getLessonName());
        Lesson savedLesson=lessonRepository.save(lessonDto.mapLessonRequestToLesson(lessonRequest));

        return ResponseMessage.<LessonResponse>builder()
                .object(lessonDto.mapLessonToLessonResponse(savedLesson))
                .message(Messages.LESSON_SAVED)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public LessonResponse getLessonById(Long id) {
        return lessonDto.mapLessonToLessonResponse(lessonRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String.format(Messages.NOT_FOUND_LESSON_MESSAGE,id))));
    }

    public ResponseMessage deleteLessonById(Long id) {
        isLessonExistById(id);
        lessonRepository.deleteById(id);

        return ResponseMessage.builder()
                .message(Messages.LESSON_DELETED)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<LessonResponse> getLessonByLessonName(String lessonName) {
        return ResponseMessage.<LessonResponse>builder()
                .object(lessonDto.mapLessonToLessonResponse(lessonRepository.getLessonByLessonName(lessonName)
                        .orElseThrow(()-> new ResourceNotFoundException(String.format(Messages.NOT_FOUND_LESSON_MESSAGE,lessonName)))))
                .message(String.format(Messages.FOUND_LESSON_MESSAGE,lessonName))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public Page<LessonResponse> findByPage(int page, int size, String sort, String type) {
        Pageable pageable =serviceHelpers.getPageableWithProperties(page, size, sort, type);
        return lessonRepository.findAll(pageable).map(lessonDto::mapLessonToLessonResponse);
    }
    private boolean isLessonExistByName(String lessonName) {
        boolean lessonExist = lessonRepository.existsLessonByLessonNameEqualsIgnoreCase(lessonName);
        if (lessonExist) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_LESSON_MESSAGE, lessonName));
        }
            return false;
    }
    private void isLessonExistById(Long id) {
        lessonRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format(Messages.NOT_FOUND_LESSON_MESSAGE,id)));
    }


    public List<LessonResponse> getAllLessons() {
        return lessonRepository.findAll().stream().map(lessonDto::mapLessonToLessonResponse).toList();
    }

    public ResponseMessage<LessonResponse> updateLesson(Long id, LessonRequest lessonRequest) {
        isLessonExistById(id);
        Lesson updatedLesson =lessonRepository.save(lessonDto.mapLessonRequestToUpdatedLesson(id, lessonRequest));
        return ResponseMessage.<LessonResponse>builder()
                .message(Messages.LESSON_UPDATED)
                .httpStatus(HttpStatus.OK)
                .object(lessonDto.mapLessonToLessonResponse(updatedLesson))
                .build();
    }

    public Set<Lesson> getAllLessonByLessonId(Set<Long> id) {
        Set<Lesson> lessons = lessonRepository.findAllByLessonId(id);
        if (lessons.isEmpty()) {
            throw new ResourceNotFoundException(Messages.NOT_FOUND_LESSONS_MESSAGE);
        }
        return lessons;
    }

}
