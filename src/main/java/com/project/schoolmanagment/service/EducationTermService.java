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
        checkEducationTermDate(educationTermRequest);
        EducationTerm savedEducationTerm=educationTermRepository.save(educationTermDto.mapEducationTermRequestToEducationTerm(educationTermRequest));

        return ResponseMessage.<EducationTermResponse>builder()
                .message("Education Term saved")
                .object(educationTermDto.mapEducationTermToEducationTermResponse(savedEducationTerm))
                .httpStatus(HttpStatus.CREATED)
                .build();

    }

    private void checkEducationTermDate(EducationTermRequest educationTermRequest){
        if (educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())){
            throw new ResourceNotFoundException(Messages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
        }
        if (educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate())){
            throw new ResourceNotFoundException(Messages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
        }
        if(educationTermRepository.existsByTermAndYear(educationTermRequest.getTerm(),educationTermRequest.getStartDate().getYear())){
            throw new ResourceNotFoundException(Messages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);
        }
    }
    private void checkEducationTermDateForUpdate(EducationTermRequest educationTermRequest){
        if (educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())){
            throw new ResourceNotFoundException(Messages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
        }
        if (educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate())){
            throw new ResourceNotFoundException(Messages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
        }
    }
    private EducationTerm checkEducationTermExist(Long id){
        EducationTerm term=educationTermRepository.findByIdEquals(id);
        if(term==null){
            throw new ResourceNotFoundException("Could not find Education term with id " + id);
        }
        return term;

    }

    public EducationTermResponse findEducationTermById(Long id) {
        return educationTermDto.mapEducationTermToEducationTermResponse(educationTermRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Could not find Education term with id " + id)));

    }

    public List<EducationTermResponse> getAllEducationTerms() {
        return educationTermRepository.findAll().stream().map(educationTermDto::mapEducationTermToEducationTermResponse).toList();
    }

    public Page<EducationTermResponse> getAllEducationTermsByPage(int page, int size, String sort, String type) {
        Pageable pageable =serviceHelpers.getPageableWithProperties(page, size, sort, type);
        return educationTermRepository.findAll(pageable).map(educationTermDto::mapEducationTermToEducationTermResponse);
    }

    public ResponseMessage<?> deleteEducationTermById(Long id) {
        checkEducationTermExist(id);
        educationTermRepository.deleteById(id);
        return ResponseMessage.builder()
                .message("Education Term deleted successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<EducationTermResponse> updateEducationTerm(Long id, EducationTermRequest educationTermRequest) {
        checkEducationTermExist(id);
        checkEducationTermDateForUpdate(educationTermRequest);
        EducationTerm updatedEducationTerm=educationTermRepository.save(educationTermDto.mapEducationTermRequestToUpdatedEducationTerm(id,educationTermRequest));
        return ResponseMessage.<EducationTermResponse>builder()
                .message( "Education term Updated successfully")
                .httpStatus(HttpStatus.OK)
                .object(educationTermDto.mapEducationTermToEducationTermResponse(updatedEducationTerm))
                .build();
    }

    public List<EducationTermResponse> getEducationTermsAfterDate(LocalDate date) {
        return educationTermRepository.findByStartDateAfter(date).stream()
                .map(educationTermDto::mapEducationTermToEducationTermResponse).toList();
    }

}
