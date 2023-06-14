package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concrate.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
    boolean existsByUsername(String username);
    boolean existsBySsn(String ssn);
    boolean existsByPhoneNumber(String phone);
    boolean existsByEmail(String email);

    Student findByUsernameEquals (String username);
}
