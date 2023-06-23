package com.project.schoolmanagment.entity.concrate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.schoolmanagment.entity.abstracts.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true,onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class Teacher extends User {

    @JsonIgnore
    @OneToOne(mappedBy = "teacher",cascade = CascadeType.PERSIST, orphanRemoval = true)
    private AdvisoryTeacher advisoryTeacher;

    @Column(name = "isAdvisor")
    private Boolean isAdvisor;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.REMOVE)
    private List<StudentInfo> studentInfos;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "teacher_lessonprogram",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_program_id")
    )
    private Set<LessonProgram> lessonProgramList;



}
