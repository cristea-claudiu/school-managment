package com.project.schoolmanagment.payload.response;

import com.project.schoolmanagment.entity.concretes.LessonProgram;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.NamedEntityGraph;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TeacherResponse {

    private Long teacherId;
    private String name;
    private String surname;
    private String ssn;
    private String phoneNumber;
    private Set<LessonProgram> lessonPrograms;
}
