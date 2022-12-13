package com.reportcard.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reportcard.project.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	Student getById(Integer studentId);
	List<Student> findAllByGroupId(Integer groupId);
}