package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concrate.Student;
import com.project.schoolmanagment.payload.response.StudentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    boolean existsByUsername(String username);
    boolean existsBySsn(String ssn);
    boolean existsByPhoneNumber(String phone);
    boolean existsByEmail(String email);
    @Query(value = "SELECT (count (s)>0) FROM Student s")
    boolean findStudent();
    @Query(value = "SELECT MAX (s.studentNumber) FROM Student s")
    int getMaxStudentNumber();

    Student findByUsernameEquals (String username);

    List<Student> getStudentByNameContaining(String studentName);
    @Query("DELETE FROM Student s WHERE s.id=:id")
    void deleteById(@Param("id") Long id);

    @Query("SELECT s FROM Student s WHERE s.advisoryTeacher.teacher.username=:username")
    List<Student> getStudentByAdvisoryTeacher_Username(String username);
    @Query("SELECT s FROM Student s WHERE s.id IN :id")
    List<Student> findByIdsEquals(Long[] id);


}
