package com.project.schoolmanagment.entity.concretes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvisorTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Teacher teacher;

    @OneToMany
    private List<Student> students;

    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserRole userRole;
}
