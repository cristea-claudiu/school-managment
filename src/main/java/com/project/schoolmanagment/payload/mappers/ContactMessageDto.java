package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concrate.ContactMessage;
import com.project.schoolmanagment.payload.request.ContactMessageRequest;
import com.project.schoolmanagment.payload.response.ContactMessageResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component
public class ContactMessageDto {

    public ContactMessageResponse createResponse(ContactMessage contactMessage){
        return ContactMessageResponse.builder().
                name(contactMessage.getName()).
                subject(contactMessage.getSubject()).
                message(contactMessage.getMessage()).
                email(contactMessage.getEmail()).
                date(LocalDate.now()).
                build();
    }
    public ContactMessage createContactMessage(ContactMessageRequest contactMessageRequest){
        return ContactMessage.builder().
                name(contactMessageRequest.getName()).
                subject(contactMessageRequest.getSubject()).
                message(contactMessageRequest.getMessage()).
                email(contactMessageRequest.getEmail()).
                date(LocalDate.now()).
                build();
    }
}
