package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concrate.EducationTerm;
import com.project.schoolmanagment.entity.concrate.Lesson;
import com.project.schoolmanagment.entity.concrate.LessonProgram;
import com.project.schoolmanagment.entity.concrate.Meet;
import com.project.schoolmanagment.payload.request.LessonProgramRequest;
import com.project.schoolmanagment.payload.request.MeetRequest;
import com.project.schoolmanagment.payload.request.UpdateMeetRequest;
import com.project.schoolmanagment.payload.response.MeetResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Data
public class MeetDto {


    public Meet mapMeetRequestToMeet(MeetRequest meetRequest) {
        return Meet.builder()
                .date(meetRequest.getDate())
    .startTime(meetRequest.getStartTime())
    .stopTime(meetRequest.getStopTime())
    .description(meetRequest.getDescription())
                .build();
    }
    public MeetResponse mapMeetToMeetResponse(Meet meet){
        return MeetResponse.builder()
                .id(meet.getId())
                .date(meet.getDate())
                .startTime(meet.getStartTime())
                .stopTime(meet.getStopTime())
                .description((meet.getDescription()))
                .advisorTeacherId(meet.getAdvisoryTeacher().getId())
                .teacherSsn(meet.getAdvisoryTeacher().getTeacher().getSsn())
                .teacherName(meet.getAdvisoryTeacher().getTeacher().getName())
                .students(meet.getStudentList())
                .build();
    }

    public Meet mapMeetUpdateRequestToMeet(UpdateMeetRequest updateMeetRequest, Long meetId) {
        return Meet.builder()
                .id(meetId)
                .date(updateMeetRequest.getDate())
                .startTime(updateMeetRequest.getStartTime())
                .stopTime(updateMeetRequest.getStopTime())
                .description(updateMeetRequest.getDescription())
                .build();
    }
}
