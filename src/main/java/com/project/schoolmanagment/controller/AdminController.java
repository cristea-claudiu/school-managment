package com.project.schoolmanagment.controller;


import com.project.schoolmanagment.payload.request.AdminRequest;
import com.project.schoolmanagment.payload.response.AdminResponse;
import com.project.schoolmanagment.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

        private final AdminService adminService;
    @PostMapping("/save")//http://localhost:8080/admin/save
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> save(@RequestBody @Valid AdminRequest adminRequest){
        return ResponseEntity.ok(adminService.save(adminRequest));
    }

    @GetMapping("/getAll") //
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<AdminResponse>> getAll(@RequestParam(value = "page",defaultValue = "0")int page,
                                                      @RequestParam(value = "size",defaultValue = "10")int size,
                                                      @RequestParam(value = "sort",defaultValue = "name")String sort,
                                                      @RequestParam(value = "type",defaultValue = "desc")String type){

        Page<AdminResponse> admins= adminService.getAllAdmins(page,size,sort,type);
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")//http://localhost:8080/admin/delete/{id}
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return ResponseEntity.ok(adminService.deleteAdmin(id));
    }






}
