package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrate.AdvisoryTeacher;
import com.project.schoolmanagment.entity.concrate.Meet;
import com.project.schoolmanagment.entity.concrate.Student;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.MeetDto;
import com.project.schoolmanagment.payload.request.MeetRequest;
import com.project.schoolmanagment.payload.request.UpdateMeetRequest;
import com.project.schoolmanagment.payload.response.DeanResponse;
import com.project.schoolmanagment.payload.response.MeetResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.MeetRepository;
import com.project.schoolmanagment.repository.StudentRepository;
import com.project.schoolmanagment.utils.Messages;
import com.project.schoolmanagment.utils.ServiceHelpers;
import com.project.schoolmanagment.utils.TimeControl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetService {
    private final MeetRepository meetRepository;
    private final StudentRepository studentRepository;
    private final AdvisoryTeacherService advisoryTeacherService;
    private final StudentService studentService;
    private final MeetDto meetDto;
    private final ServiceHelpers serviceHelpers;
    public List<Meet> getAllMeetsById(List<Long> meetListId) {
        return meetRepository.findAllById(meetListId);
    }

    public ResponseMessage<MeetResponse> saveMeet(String username, MeetRequest meetRequest) {
       AdvisoryTeacher advisoryTeacher= advisoryTeacherService.getAdvisoryTeacherByUsername(username);
        TimeControl.checkTimeWithException(meetRequest.getStartTime(),meetRequest.getStopTime());

    for (Long studentId : meetRequest.getStudentIds()){
        studentService.isStudentExist(studentId);
        checkMeetConflict(studentId,meetRequest.getDate(),meetRequest.getStartTime(),meetRequest.getStopTime());
    }
    List<Student> students=studentService.getStudentById(meetRequest.getStudentIds());
    Meet meet=meetDto.mapMeetRequestToMeet(meetRequest);
    meet.setStudentList(students);
    meet.setAdvisoryTeacher(advisoryTeacher);
    Meet savedMeet= meetRepository.save(meet);
    return ResponseMessage.<MeetResponse>builder()
            .message(Messages.MEET_SAVED)
            .object(meetDto.mapMeetToMeetResponse(savedMeet))
            .httpStatus(HttpStatus.CREATED)
            .build();
}

    private void checkMeetConflict(Long studentId, LocalDate date, LocalTime startTime, LocalTime stopTime){
        List<Meet>meets = meetRepository.findByStudentList_IdEquals(studentId);
        for (Meet meet :meets){
            LocalTime existingStartTime = meet.getStartTime();
            LocalTime existingStopTime = meet.getStopTime();

            if(meet.getDate().equals(date) &&
                    ((startTime.isAfter(existingStartTime) && startTime.isBefore(existingStopTime)) ||
                            (stopTime.isAfter(existingStartTime) && stopTime.isBefore(existingStopTime)) ||
                            (startTime.isBefore(existingStartTime) && stopTime.isAfter(existingStopTime)) ||
                            (startTime.equals(existingStartTime) && stopTime.equals(existingStopTime)))){
                throw new ConflictException(Messages.MEET_HOURS_CONFLICT_ERROR);
            }
        }
    }

    public List<MeetResponse> getAll(){
        return meetRepository.findAll().stream().map(meetDto::mapMeetToMeetResponse).collect(Collectors.toList());
    }


    public ResponseMessage<MeetResponse> getMeetById(Long meetId) {
        return ResponseMessage.<MeetResponse>builder()
                .message(Messages.MEET_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(meetDto.mapMeetToMeetResponse(isMeetExistById(meetId)))
                .build();

    }

    private Meet isMeetExistById(Long meetId) {
        return meetRepository.findById(meetId).orElseThrow(()->new ResourceNotFoundException(Messages.MEET_NOT_EXIST));
    }

    public ResponseMessage delete(Long meetId) {
        meetRepository.deleteById(isMeetExistById(meetId).getId());
            return ResponseMessage.builder()
                    .message(Messages.MEET_FOUND)
                    .httpStatus(HttpStatus.OK)
                    .build();
    }

    public ResponseMessage<MeetResponse> updateMeet(UpdateMeetRequest updateMeetRequest,Long meetId) {
        Meet meet=isMeetExistById(meetId);
        TimeControl.checkTimeWithException(updateMeetRequest.getStartTime(),updateMeetRequest.getStopTime());
        if(!(meet.getDate().equals(updateMeetRequest.getDate())&&
                meet.getStartTime().equals(updateMeetRequest.getStartTime())&&
                meet.getStopTime().equals(updateMeetRequest.getStopTime()))){
            for (Long studentId: updateMeetRequest.getStudentIds()){
                checkMeetConflict(studentId, updateMeetRequest.getDate(),updateMeetRequest.getStartTime(), updateMeetRequest.getStopTime());
            }
        }
        List<Student>students=studentService.getStudentById(updateMeetRequest.getStudentIds());
        Meet updatedMeet=meetDto.mapMeetUpdateRequestToMeet(updateMeetRequest,meetId);
        updatedMeet.setStudentList(students);
        updatedMeet.setAdvisoryTeacher(meet.getAdvisoryTeacher());
        Meet savedMeet=meetRepository.save(updatedMeet);

        return ResponseMessage.<MeetResponse>builder()
                .object(meetDto.mapMeetToMeetResponse(savedMeet))
                .message(Messages.MEET_UPDATED)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseEntity getAllMeetByTeacher(HttpServletRequest httpServletRequest) {
        String userName=serviceHelpers.getUsernameAttribute(httpServletRequest);
        AdvisoryTeacher advisoryTeacher=advisoryTeacherService.getAdvisoryTeacherByUsername(userName);
        List<MeetResponse> meetResponsesList=meetRepository.getByAdvisoryTeacher_IdEquals(advisoryTeacher.getId()).stream().map(meetDto::mapMeetToMeetResponse).collect(Collectors.toList());
        return ResponseEntity.ok(meetResponsesList);
    }

    public ResponseEntity<List<MeetResponse>> getAllMeetByStudent(HttpServletRequest httpServletRequest) {
        String userName=serviceHelpers.getUsernameAttribute(httpServletRequest);
        Student student=studentService.getStudentByUserName(userName);
        List<MeetResponse> meetResponsesList=meetRepository.findByStudentList_IdEquals(student.getId()).stream().map(meetDto::mapMeetToMeetResponse).collect(Collectors.toList());
        return ResponseEntity.ok(meetResponsesList);
    }

    public Page<MeetResponse> search(int page, int size) {
       Pageable pageable =serviceHelpers.getPageableWithProperties(page, size);
       return meetRepository.findAll(pageable).map(meetDto::mapMeetToMeetResponse);
    }

    public ResponseEntity<Page<MeetResponse>> getAllMeetByTeacherAsPage(HttpServletRequest httpServletRequest, int page, int size) {
        String username=serviceHelpers.getUsernameAttribute(httpServletRequest);
        AdvisoryTeacher advisoryTeacher=advisoryTeacherService.getAdvisoryTeacherByUsername(username);
       Pageable pageable=serviceHelpers.getPageableWithProperties(page,size);
       return ResponseEntity.ok(meetRepository.findByAdvisoryTeacher_IdEquals(advisoryTeacher.getId(),pageable).map(meetDto::mapMeetToMeetResponse));
    }
}
