package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concrate.Admin;
import com.project.schoolmanagment.payload.request.AdminRequest;
import com.project.schoolmanagment.payload.response.AdminResponse;
import com.project.schoolmanagment.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

        private final AdminService adminService;
    @PostMapping("/save")//http://localhost:8080/admin/save
    public ResponseEntity<?> save(@RequestBody @Valid AdminRequest adminRequest){
        return ResponseEntity.ok(adminService.save(adminRequest));
    }

    @GetMapping("/getAll") //
    public ResponseEntity<Page<AdminResponse>> getAll(@RequestParam(value = "page",defaultValue = "0")int page,
                                                      @RequestParam(value = "size",defaultValue = "10")int size,
                                                      @RequestParam(value = "sort",defaultValue = "name")String sort,
                                                      @RequestParam(value = "type",defaultValue = "desc")String type){

        Page<AdminResponse> admins= adminService.getAllAdmins(page,size,sort,type);
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")//http://localhost:8080/admin/delete/{id}
    public ResponseEntity<String> delete(@PathVariable Long id){
        return ResponseEntity.ok(adminService.deleteAdmin(id));
    }






}
