package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concrate.Student;
import com.project.schoolmanagment.payload.request.ChooseLessonProgramWithId;
import com.project.schoolmanagment.payload.request.StudentRequest;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentResponse;
import com.project.schoolmanagment.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("students")
@RequiredArgsConstructor
public class StudentController {


private final StudentService studentService;

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    @PostMapping("/save")
    public ResponseMessage<StudentResponse> saveStudent(@RequestBody @Valid StudentRequest studentRequest){
        return studentService.saveStudent(studentRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/changeStatus")
    public ResponseMessage changeStatus (@RequestParam Long id, @RequestParam boolean status){
        return studentService.changeStatus(id,status);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getAll")
    public List<StudentResponse> getAllStudents(){
        return studentService.getAllStudents();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteStudentById(@PathVariable Long id){
        return studentService.deleteStudentById(id);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/search")
    public Page<StudentResponse> getAllStudentsByPage(@RequestParam(value = "page",defaultValue = "0")int page,
                                                   @RequestParam(value = "size",defaultValue = "10")int size,
                                                   @RequestParam(value = "sort",defaultValue = "name")String sort,
                                                   @RequestParam(value = "type",defaultValue = "desc")String type){
        return studentService.getAllStudentsByPage(page,size,sort,type);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @PutMapping("/update/{id}")
    public ResponseMessage<StudentResponse>updateStudent(@PathVariable Long id,
                                                         @RequestBody @Valid StudentRequest studentRequest){
        return studentService.updateStudent(id,studentRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getStudentByName")
    public List<StudentResponse> getStudentByName(@RequestParam("name") String studentName){
        return studentService.getStudentByName(studentName);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getById")
    public Student GetStudentById(@RequestParam("id") Long id){
        return studentService.GetStudentById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping("/GetAllByAdvisoryId")
    public List<StudentResponse> GetAllByUsername(HttpServletRequest request){
        String userName=request.getHeader("username");
        return studentService.GetAllByUsername(userName);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
    @PostMapping("/chooseLesson")
    public ResponseMessage<StudentResponse> chooseLesson(HttpServletRequest request,
                                                         @RequestBody @Valid ChooseLessonProgramWithId chooseLessonProgramWithId){
        String userName=request.getHeader("username");
        return studentService.chooseLesson(userName,chooseLessonProgramWithId);
    }

}
