package com.project.schoolmanagment.payload.request;

import com.project.schoolmanagment.entity.concrate.AdvisoryTeacher;
import com.project.schoolmanagment.entity.concrate.LessonProgram;
import com.project.schoolmanagment.entity.concrate.Meet;
import com.project.schoolmanagment.payload.request.abstracts.BaseUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class StudentRequest extends BaseUserRequest {

    private int studentNumber;
    private String motherName;
    private String fatherName;
    private String email;
//    private AdvisoryTeacher advisoryTeacher;
//    private List<Long> meetList;
//    private Set<Long> lessonProgramSet;
}
