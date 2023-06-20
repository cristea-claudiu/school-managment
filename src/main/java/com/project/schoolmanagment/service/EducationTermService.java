package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrate.EducationTerm;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.EducationTermDto;
import com.project.schoolmanagment.payload.request.EducationTermRequest;
import com.project.schoolmanagment.payload.response.EducationTermResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.EducationTermRepository;
import com.project.schoolmanagment.utils.Messages;
import com.project.schoolmanagment.utils.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationTermService {

    private final EducationTermRepository educationTermRepository;
    private final EducationTermDto educationTermDto;
    private final ServiceHelpers serviceHelpers;


    public ResponseMessage<EducationTermResponse> saveEducationTerm(EducationTermRequest educationTermRequest) {
        educationTermDto.checkEducationTermDate(educationTermRequest);
        EducationTerm savedEducationTerm=educationTermRepository.save(educationTermDto.mapEducationTermRequestToEducationTerm(educationTermRequest));
        return ResponseMessage.<EducationTermResponse>builder()
                .message(Messages.EDUCATION_TERM_SAVED_MESSAGE)
                .object(educationTermDto.mapEducationTermToEducationTermResponse(savedEducationTerm))
                .httpStatus(HttpStatus.CREATED)
                .build();

    }

    public EducationTermResponse findEducationTermResponseById(Long id) {
        return educationTermDto.mapEducationTermToEducationTermResponse(educationTermRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String.format(Messages.EDUCATION_TERM_NOT_FOUND_MESSAGE,id))));

    }
    public EducationTerm getEducationTermById(Long id) {
        return educationTermRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String.format(Messages.EDUCATION_TERM_NOT_FOUND_MESSAGE,id)));

    }

    public List<EducationTermResponse> getAllEducationTerms() {
        return educationTermRepository.findAll().stream().map(educationTermDto::mapEducationTermToEducationTermResponse).toList();
    }

    public Page<EducationTermResponse> getAllEducationTermsByPage(int page, int size, String sort, String type) {
        Pageable pageable =serviceHelpers.getPageableWithProperties(page, size, sort, type);
        return educationTermRepository.findAll(pageable).map(educationTermDto::mapEducationTermToEducationTermResponse);
    }

    public ResponseMessage<?> deleteEducationTermById(Long id) {
        educationTermDto.checkEducationTermExist(id);
        educationTermRepository.deleteById(id);
        return ResponseMessage.builder()
                .message(Messages.EDUCATION_TERM_DELETED_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<EducationTermResponse> updateEducationTerm(Long id, EducationTermRequest educationTermRequest) {
        educationTermDto.checkEducationTermExist(id);
        educationTermDto.checkEducationTermDateForUpdate(educationTermRequest);
        EducationTerm updatedEducationTerm=educationTermRepository.save(educationTermDto.mapEducationTermRequestToUpdatedEducationTerm(id,educationTermRequest));
        return ResponseMessage.<EducationTermResponse>builder()
                .message(Messages.EDUCATION_TERM_UPDATED_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(educationTermDto.mapEducationTermToEducationTermResponse(updatedEducationTerm))
                .build();
    }

    public List<EducationTermResponse> getEducationTermsAfterDate(LocalDate date) {
        return educationTermRepository.findByStartDateAfter(date).stream()
                .map(educationTermDto::mapEducationTermToEducationTermResponse).toList();
    }

}
