package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concrate.AdvisoryTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdvisoryTeacherRepository extends JpaRepository<AdvisoryTeacher, Long> {


    Optional<AdvisoryTeacher>getAdvisoryTeacherByTeacher_Id(Long advisoryTeacherId);
    Optional<AdvisoryTeacher>getAdvisoryTeacherByTeacherUsername(String username);









}
