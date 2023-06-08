package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.entity.concrate.ContactMessage;
import com.project.schoolmanagment.payload.request.ContactMessageRequest;
import com.project.schoolmanagment.payload.response.ContactMessageResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("contactMessages")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping("/save")
    public ResponseMessage<ContactMessageResponse> save(@RequestBody @Valid ContactMessageRequest contactMessageRequest){
       return contactMessageService.save(contactMessageRequest);
    }

@GetMapping("/getAll")
    public Page<ContactMessageResponse> getAll(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
            @RequestParam(value = "sort",defaultValue = "date") String sort,
            @RequestParam(value = "type",defaultValue = "desc") String type){
        return contactMessageService.getAll(page,size,sort,type);
    }

    /**
     *
     * @param page number of selected page
     * @param size of the page
     * @param sort sort propriety
     * @param type DESC or ASC
     * @param email
     * @return ContactMessageResponse
     */
    @GetMapping("/searchByEmail")
    public Page<ContactMessageResponse> searchByEmail(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
            @RequestParam(value = "sort",defaultValue = "date") String sort,
            @RequestParam(value = "type",defaultValue = "desc") String type,
            @RequestParam(value = "email")String email){
        return contactMessageService.searchByEmail(page, size, sort, type, email);
}

    /**
     *
     * @param page number of selected page
     * @param size of the page
     * @param sort sort propriety
     * @param type DESC or ASC
     * @param subject
     * @return ContactMessageResponse
     */
    @GetMapping("/searchBySubject")
public Page<ContactMessageResponse> searchBySubject(
        @RequestParam(value = "page",defaultValue = "0") int page,
        @RequestParam(value = "size",defaultValue = "10") int size,
        @RequestParam(value = "sort",defaultValue = "date") String sort,
        @RequestParam(value = "type",defaultValue = "desc") String type,
        @RequestParam(value = "subject")String subject){
    return contactMessageService.searchBySubject(page, size, sort, type, subject);
}

@DeleteMapping("/delete/{id}")
public ResponseEntity<Map<String, String>> deleteContactMessageById(@PathVariable Long id) {
    contactMessageService.deleteContactMessageById(id);
    Map<String, String> map = new HashMap<>();
    map.put("message", "Message deleted successfully.");
    return ResponseEntity.ok(map);
    }

    @GetMapping("/getALLAsList")
    public ResponseEntity<List<ContactMessageResponse>> getAllAsList() {
        List<ContactMessageResponse> contactMessageResponses = contactMessageService.getAllAsList();
        return ResponseEntity.ok(contactMessageResponses);
    }
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Map<String, String>> updateContactMessage(@Valid @PathVariable Long id,@RequestBody ContactMessageResponse contactMessageResponse){
//        contactMessageService.updateContactMessage(id,contactMessageResponse);
//        Map<String, String> map = new HashMap<>();
//        map.put("message", "Message updated successfully.");
//        map.put("status", "true");
//        return new ResponseEntity<>(map, HttpStatus.OK);
//    }











}