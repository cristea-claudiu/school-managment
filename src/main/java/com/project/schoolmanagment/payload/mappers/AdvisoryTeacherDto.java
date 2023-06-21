package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concrate.AdvisoryTeacher;
import com.project.schoolmanagment.entity.concrate.Teacher;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AdvisoryTeacherDto {



    public AdvisoryTeacher mapTeacherToAdvisoryTeacher(Teacher teacher) {
       return AdvisoryTeacher.builder().teacher(teacher).build();
    }
}
