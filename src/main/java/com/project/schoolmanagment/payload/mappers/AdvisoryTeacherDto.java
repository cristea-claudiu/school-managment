package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concrate.AdvisoryTeacher;
import com.project.schoolmanagment.entity.concrate.Teacher;
import com.project.schoolmanagment.payload.response.AdvisorTeacherResponse;
import com.project.schoolmanagment.repository.AdvisoryTeacherRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AdvisoryTeacherDto {



    public AdvisoryTeacher mapTeacherToAdvisoryTeacher(Teacher teacher) {
       return AdvisoryTeacher.builder().teacher(teacher).build();
    }
    public AdvisorTeacherResponse mapAdvisorTeacherToAdvisorTeacherResponse(AdvisoryTeacher advisoryTeacher){
        return AdvisorTeacherResponse.builder()
                .advisorTeacherId(advisoryTeacher.getId())
                .teacherName(advisoryTeacher.getTeacher().getName())
                .teacherSurname(advisoryTeacher.getTeacher().getSurname())
                .teacherSSN(advisoryTeacher.getTeacher().getSsn())
                .build();
    }
}
