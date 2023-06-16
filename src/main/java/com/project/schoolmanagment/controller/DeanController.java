package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.payload.request.DeanRequest;
import com.project.schoolmanagment.payload.response.DeanResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.DeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("dean")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
public class DeanController {

    private final DeanService deanService;

    @PostMapping("/save")
    public ResponseMessage<DeanResponse> save(@RequestBody @Valid DeanRequest deanRequest){
       return deanService.save(deanRequest);
    }
    @PutMapping("/update/{userId}")
    public ResponseMessage<DeanResponse> Update(@RequestBody @Valid DeanRequest deanRequest, @PathVariable Long userId){
        return deanService.update(deanRequest,userId);

    }

    @DeleteMapping("/delete/{userId}")
    public ResponseMessage<?> deleteDeanById(@PathVariable Long userId) {
        return deanService.deleteDeanById(userId);
    }

    @DeleteMapping("/delete")
    public ResponseMessage<?> deleteDeanByIdWithParam(@RequestParam("id") Long userId) {
        return deanService.deleteDeanById(userId);
    }

    @GetMapping("/getManagerById/{userId}")
    public ResponseMessage<DeanResponse> getDeanById(@PathVariable Long userId){
        return deanService.getDeanById(userId);
    }

    @GetMapping("/getAll")
    public List<DeanResponse> getAllDeans(){
        return deanService.getAllDeans();

    }

    @GetMapping("/search")
    public Page<DeanResponse> getAllDeansByPage(@RequestParam(value = "page",defaultValue = "0")int page,
                                                @RequestParam(value = "size",defaultValue = "10")int size,
                                                @RequestParam(value = "sort",defaultValue = "name")String sort,
                                                @RequestParam(value = "type",defaultValue = "desc")String type){
        return deanService.getAllDeansByPage(page,size,sort,type);
    }



}
