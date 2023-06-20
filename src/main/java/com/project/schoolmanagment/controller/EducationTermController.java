package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.payload.request.EducationTermRequest;
import com.project.schoolmanagment.payload.response.EducationTermResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("educationTerms")
@RequiredArgsConstructor
public class EducationTermController {


  private final EducationTermService educationTermService;

   @PostMapping("/save")
   @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
   public ResponseMessage<EducationTermResponse> saveEducationTerm(@RequestBody @Valid EducationTermRequest educationTermRequest){
        return educationTermService.saveEducationTerm(educationTermRequest);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public EducationTermResponse getEducationTerm(@PathVariable Long id){
       return educationTermService.findEducationTermResponseById(id);

    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public List<EducationTermResponse> getAllEducationTerms(){
        return educationTermService.getAllEducationTerms();

    }
    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public Page<EducationTermResponse> getAllEducationTermsByPage(@RequestParam(value = "page",defaultValue = "0")int page,
                                                                  @RequestParam(value = "size",defaultValue = "10")int size,
                                                                  @RequestParam(value = "sort",defaultValue = "startDate")String sort,
                                                                  @RequestParam(value = "type",defaultValue = "desc")String type){
       return educationTermService.getAllEducationTermsByPage(page,size,sort,type);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<?> deleteEducationTermById(@PathVariable Long id){
       return educationTermService.deleteEducationTermById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<EducationTermResponse> updateEducationTerm(@PathVariable Long id ,@RequestBody EducationTermRequest educationTermRequest){
        return educationTermService.updateEducationTerm(id,educationTermRequest);
    }

    @PostMapping("/searchByDate")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public List<EducationTermResponse> getEducationTermsAfterDate(@RequestParam String date){
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        return educationTermService.getEducationTermsAfterDate(localDate);
    }

}
