package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrate.ContactMessage;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.request.ContactMessageRequest;
import com.project.schoolmanagment.payload.response.ContactMessageResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.ContactMessageRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactMessageService {
    private final ContactMessageRepository contactMessageRepository;

    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest){

        boolean isSameMessageWithSameEmailForToday=
                contactMessageRepository.existsByEmailEqualsAndDateEquals(contactMessageRequest.getEmail(),LocalDate.now());
        if (isSameMessageWithSameEmailForToday){
            throw new ConflictException(Messages.ALREADY_SEND_A_MESSAGE_TODAY);
        }
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


    public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type,"desc")){
            pageable =PageRequest.of(page,size,Sort.by(sort).descending());
        }
        return contactMessageRepository.findAll(pageable).map(this::createResponse);
    }
    public Page<ContactMessageResponse> searchByEmail(int page, int size, String sort, String type,String email){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type,"desc")){
            pageable =PageRequest.of(page,size,Sort.by(sort).descending());
        }
        return contactMessageRepository.findByEmailEquals(email,pageable).map(this::createResponse);
    }


    public Page<ContactMessageResponse> searchBySubject(int page, int size, String sort, String type, String subject) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type,"desc")){
            pageable =PageRequest.of(page,size,Sort.by(sort).descending());
        }
        return contactMessageRepository.findBySubjectEquals(subject,pageable).map(this::createResponse);
    }

    public void deleteContactMessageById(Long id) {
        contactMessageRepository.deleteById(id);
    }

    public ContactMessage getContactMessageById(Long id) {
        return contactMessageRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Could not find contact message with id: "+ id));
    }


    public List<ContactMessageResponse> getAllAsList() {
        return contactMessageRepository.findAll().stream().map(this::createResponse).collect(Collectors.toList());
    }

//    public void updateContactMessage(Long id, ContactMessageResponse contactMessageResponse) {
//        ContactMessage existingContactMessage=getContactMessageById(id);
//
//        existingContactMessage.set
//
//
//
//    }
}
