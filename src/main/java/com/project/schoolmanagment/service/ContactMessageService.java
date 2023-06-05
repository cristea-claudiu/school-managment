package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrate.ContactMessage;
import com.project.schoolmanagment.payload.request.ContactMessageRequest;
import com.project.schoolmanagment.payload.response.ContactMessageResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ContactMessageService {
    private final ContactMessageRepository contactMessageRepository;

    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest){
        ContactMessage contactMessage=createContactMessage(contactMessageRequest);
        ContactMessage savedData=contactMessageRepository.save(contactMessage);
        return ResponseMessage.<ContactMessageResponse>builder().
                message("Contact Message Created Successfully").
                httpStatus(HttpStatus.CREATED).
                object(createResponse(savedData)).
                build();
    }
    private ContactMessageResponse createResponse(ContactMessage contactMessage){
        return ContactMessageResponse.builder().
                name(contactMessage.getName()).
                subject(contactMessage.getSubject()).
                message(contactMessage.getMessage()).
                email(contactMessage.getEmail()).
                date(LocalDate.now()).
                build();
    }

    private ContactMessage createContactMessage(ContactMessageRequest contactMessageRequest){
        return ContactMessage.builder().
                name(contactMessageRequest.getName()).
                subject(contactMessageRequest.getSubject()).
                message(contactMessageRequest.getMessage()).
                email(contactMessageRequest.getEmail()).
                date(LocalDate.now()).
                build();

    }












}
