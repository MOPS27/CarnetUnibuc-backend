package com.reportcard.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reportcard.project.model.Student;
import com.reportcard.project.model.StudentCourse;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer> {

	List<StudentCourse> findAllByStudentId(Integer studentId);
	StudentCourse getById(Integer studentCourseId);
	List<StudentCourse> findAllByCourseId(int courseId);
}
