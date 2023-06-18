package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concrate.EducationTerm;
import com.project.schoolmanagment.entity.concrate.Lesson;
import com.project.schoolmanagment.payload.request.EducationTermRequest;
import com.project.schoolmanagment.payload.request.LessonRequest;
import com.project.schoolmanagment.payload.response.EducationTermResponse;
import com.project.schoolmanagment.payload.response.LessonResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LessonDto {
    public Lesson mapLessonRequestToLesson(LessonRequest lessonRequest) {
        return Lesson.builder()
                .lessonName(lessonRequest.getLessonName())
                .creditScore(lessonRequest.getCreditScore())
                .isCompulsory(lessonRequest.getIsCompulsory())
                .build();
    }

    public LessonResponse mapLessonToLessonResponse(Lesson lesson) {
        return LessonResponse.builder()
                .lessonId(lesson.getLessonId())
                .lessonName(lesson.getLessonName())
                .creditScore(lesson.getCreditScore())
                .isCompulsory(lesson.getIsCompulsory())
                .build();
    }

    public Lesson mapLessonRequestToUpdatedLesson(Long lessonId , LessonRequest lessonRequest) {
        return Lesson.builder()
                .lessonId(lessonId)
                .lessonName(lessonRequest.getLessonName())
                .creditScore(lessonRequest.getCreditScore())
                .isCompulsory(lessonRequest.getIsCompulsory())
                .build();
    }
}
