package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concrate.EducationTerm;
import com.project.schoolmanagment.entity.concrate.Lesson;
import com.project.schoolmanagment.entity.concrate.LessonProgram;
import com.project.schoolmanagment.payload.request.LessonProgramRequest;
import com.project.schoolmanagment.payload.response.LessonProgramResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@Component
public class LessonProgramDto {


    public LessonProgram mapLessonProgramRequestToLessonProgram(LessonProgramRequest lessonProgramRequest, Set<Lesson> lessonSet, EducationTerm educationTerm) {
        return LessonProgram.builder()
                .day(lessonProgramRequest.getDay())
                .startTime(lessonProgramRequest.getStartTime())
                .stopTime(lessonProgramRequest.getStopTime())
                .lesson(lessonSet)
                .educationTerm(educationTerm)
                .build();
    }

    public LessonProgramResponse mapLessonProgramToLessonProgramResponse(LessonProgram lessonProgram) {
        return LessonProgramResponse.builder()
                .lessonProgramId(lessonProgram.getId())
                .day(lessonProgram.getDay())
                .startTime(lessonProgram.getStartTime())
                .stopTime(lessonProgram.getStopTime())
                .lessonName(lessonProgram.getLesson())
                .educationTerm(lessonProgram.getEducationTerm())
                .build();
    }

    public LessonProgram mapLessonProgramRequestToUpdatedLessonProgram(Long lessonProgramId , LessonProgramRequest lessonProgramRequest, Set<Lesson> lessonSet, EducationTerm educationTerm) {
        return LessonProgram.builder()
                .id(lessonProgramId)
                .day(lessonProgramRequest.getDay())
                .startTime(lessonProgramRequest.getStartTime())
                .stopTime(lessonProgramRequest.getStopTime())
                .lesson(lessonSet)
                .educationTerm(educationTerm)
                .build();
    }
}
