package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concrate.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

public interface LessonRepository extends JpaRepository<Lesson, Long> {


    boolean existsLessonByLessonNameEqualsIgnoreCase(String lessonName);
    Optional<Lesson> getLessonByLessonName(String lessonName);

    @Query(value = "SELECT l FROM Lesson l WHERE l.lessonId IN :lessonIds")
    Set<Lesson> findAllByLessonId(@Param("lessonIds") Set<Long> lessonIds);
}
