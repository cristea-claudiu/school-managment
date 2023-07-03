package com.project.schoolmanagment.controller;
import com.project.schoolmanagment.payload.response.AdvisorTeacherResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.AdvisoryTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@RequestMapping("/advisorTeacher")
@RestController
@RequiredArgsConstructor
public class AdvisoryTeacherController {

    private final AdvisoryTeacherService advisoryTeacherService;

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getAll")
    public List<AdvisorTeacherResponse> getAllAdvisoryTeachers() {
        return advisoryTeacherService.GetALLAdvisoryTeachers();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @GetMapping("/search")
    public Page<AdvisorTeacherResponse> search(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ){
        return advisoryTeacherService.search(page,size,sort,type);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANTMANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteAdvisoryTeacher(@PathVariable Long id){
        return advisoryTeacherService.deleteAdvisoryTeacherById(id);

    }



}
