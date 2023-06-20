package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrate.ContactMessage;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.ContactMessageDto;
import com.project.schoolmanagment.payload.request.ContactMessageRequest;
import com.project.schoolmanagment.payload.response.ContactMessageResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.ContactMessageRepository;
import com.project.schoolmanagment.utils.Messages;
import com.project.schoolmanagment.utils.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactMessageService {
    private final ContactMessageRepository contactMessageRepository;
    private final ServiceHelpers serviceHelpers;
    private final ContactMessageDto  contactMessageDto;

    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest){

        boolean isSameMessageWithSameEmailForToday=
                contactMessageRepository.existsByEmailEqualsAndDateEquals(contactMessageRequest.getEmail(),LocalDate.now());
        if (isSameMessageWithSameEmailForToday){
            throw new ConflictException(Messages.ALREADY_SEND_A_MESSAGE_TODAY);
        }
        ContactMessage contactMessage=contactMessageDto.createContactMessage(contactMessageRequest);
        ContactMessage savedData=contactMessageRepository.save(contactMessage);
        return ResponseMessage.<ContactMessageResponse>builder().
                message("Contact Message Created Successfully").
                httpStatus(HttpStatus.CREATED).
                object(contactMessageDto.createResponse(savedData)).
                build();
    }

    public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type){
        Pageable pageable = serviceHelpers.getPageableWithProperties(page,size,sort,type);
        return contactMessageRepository.findAll(pageable).map(contactMessageDto::createResponse);
    }

    public Page<ContactMessageResponse> searchByEmail(int page, int size, String sort, String type,String email){
        Pageable pageable =serviceHelpers.getPageableWithProperties(page,size,sort,type);
        return contactMessageRepository.findByEmailEquals(email,pageable).map(contactMessageDto::createResponse);
    }


    public Page<ContactMessageResponse> searchBySubject(int page, int size, String sort, String type, String subject) {
        Pageable pageable =serviceHelpers.getPageableWithProperties(page,size,sort,type);
        return contactMessageRepository.findBySubjectEquals(subject,pageable).map(contactMessageDto::createResponse);
    }

    public void deleteContactMessageById(Long id) {
        contactMessageRepository.deleteById(id);
    }

    public ContactMessage getContactMessageById(Long id) {
        return contactMessageRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(String.format(Messages.CONTACT_MESSAGE_WITH_ID_NOT_FOUND,id)));
    }


    public List<ContactMessageResponse> getAllAsList() {
        return contactMessageRepository.findAll().stream().map(contactMessageDto::createResponse).collect(Collectors.toList());
    }

    public void updateContactMessage(Long id, ContactMessageResponse contactMessageResponse) {
        ContactMessage existingContactMessage=getContactMessageById(id);
        boolean idExist = contactMessageRepository.existsById(existingContactMessage.getId());
        if (!contactMessageRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(Messages.CONTACT_MESSAGE_WITH_ID_NOT_FOUND,id));
        }
        existingContactMessage.setMessage(contactMessageResponse.getMessage());
        existingContactMessage.setName(contactMessageResponse.getName());
        existingContactMessage.setSubject(contactMessageResponse.getSubject());
        try {
            contactMessageRepository.save(existingContactMessage);
        }
        catch (Exception e) {throw new RuntimeException(Messages.CONTACT_MESSAGE_FAILED_TO_UPDATE,e);
        }
    }
}
