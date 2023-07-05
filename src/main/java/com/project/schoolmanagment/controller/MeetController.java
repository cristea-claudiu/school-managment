package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.payload.request.MeetRequest;
import com.project.schoolmanagment.payload.request.UpdateMeetRequest;
import com.project.schoolmanagment.payload.response.MeetResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.MeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("meet")
@RequiredArgsConstructor
public class MeetController {
    private final MeetService meetService;

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @PostMapping("/save")
    public ResponseMessage<MeetResponse> saveMeet(HttpServletRequest httpServletRequest,
                                                  @RequestBody @Valid MeetRequest meetRequest){
        String username= (String) httpServletRequest.getAttribute("username");
        return meetService.saveMeet(username,meetRequest);
    }
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    @GetMapping("/getAll")
    public List<MeetResponse> getALL(){

        return meetService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    @GetMapping("/getMeetById/{meetId}")
    public ResponseMessage<MeetResponse> getMeetById(@PathVariable Long meetId){

        return meetService.getMeetById(meetId);
    }

    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    @DeleteMapping("/delete/{meetId}")
    public ResponseMessage delete(@PathVariable Long meetId){

        return meetService.delete(meetId);
    }
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    @PutMapping("/update/{meetId}")
    public ResponseMessage<MeetResponse> updateMeet(@RequestBody @Valid UpdateMeetRequest updateMeetRequest, @PathVariable Long meetId){

        return meetService.updateMeet(updateMeetRequest,meetId);
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @GetMapping("/getAllMeetByAdvisoryTeacherAsList")
    public ResponseEntity<List<MeetResponse>> getAllMeetByTeacher(HttpServletRequest httpServletRequest){

        return meetService.getAllMeetByTeacher(httpServletRequest);
    }
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @GetMapping("/getAllMeetByAdvisoryStudentAsList")
    public ResponseEntity<List<MeetResponse>> getAllMeetByStudent(HttpServletRequest httpServletRequest){

        return meetService.getAllMeetByStudent(httpServletRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping("/search")
    public Page<MeetResponse> search(@RequestParam(value = "page") int page,
                                     @RequestParam(value = "size") int size ){

        return meetService.search(page,size);
    }
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @GetMapping("/getAllMeetByAdvisorAsPage")
    public ResponseEntity<Page<MeetResponse>> getAllMeetByTeacherAsPage(HttpServletRequest httpServletRequest,
                                                                  @RequestParam(value = "page") int page,
                                                                  @RequestParam(value = "size") int size){

        return meetService.getAllMeetByTeacherAsPage(httpServletRequest,page,size);
    }


}
