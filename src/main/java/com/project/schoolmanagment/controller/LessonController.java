package com.project.schoolmanagment.controller;


import com.project.schoolmanagment.entity.concrate.Lesson;
import com.project.schoolmanagment.payload.request.EducationTermRequest;
import com.project.schoolmanagment.payload.request.LessonRequest;
import com.project.schoolmanagment.payload.response.EducationTermResponse;
import com.project.schoolmanagment.payload.response.LessonResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;


    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<LessonResponse> saveLesson(@RequestBody @Valid LessonRequest lessonRequest){
    return lessonService.saveLesson(lessonRequest);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage deleteLesson(@PathVariable Long id) {
        return lessonService.deleteLessonById(id);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    public LessonResponse getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @GetMapping("/getLessonByName")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    public ResponseMessage<LessonResponse> getLessonByLessonName(@RequestParam String lessonName) {
        return lessonService.getLessonByLessonName(lessonName);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    public Page<LessonResponse> search(@RequestParam(value = "page",defaultValue = "0")int page,
                                       @RequestParam(value = "size",defaultValue = "10")int size,
                                       @RequestParam(value = "sort",defaultValue = "lessonName")String sort,
                                       @RequestParam(value = "type",defaultValue = "desc")String type){
        return lessonService.findByPage(page,size, sort, type);
    }
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    public List<LessonResponse> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<LessonResponse> updateLesson(@PathVariable Long id , @RequestBody LessonRequest lessonRequest){
        return lessonService.updateLesson(id,lessonRequest);
    }
    @GetMapping("/getAllLessonByLessonId")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    public Set<Lesson> getAllLessonByLessonId(@RequestParam(name = "lessonId") Set<Long> id ){
        return lessonService.getAllLessonByLessonId(id);
    }








}
