package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concrate.LessonProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface LessonProgramRepository extends JpaRepository<LessonProgram,Long> {


    List<LessonProgram>findByTeachers_IdNull();
    List<LessonProgram>findByTeachers_IdNotNull();



    @Query("SELECT l FROM LessonProgram l INNER JOIN l.teachers teachers where teachers.username=?1")
    Set<LessonProgram> getLessonProgramByTeachersUsername(String username);
   @Query("SELECT l FROM LessonProgram l INNER JOIN l.students students where students.username=?1")
    Set<LessonProgram> getLessonProgramByStudentsUsername(String username);

   @Query("SELECT l FROM LessonProgram l WHERE l.id IN :lessonIdList")
   Set<LessonProgram> getLessonProgramByLessonProgramIdList(Set<Long> lessonIdList);

}
