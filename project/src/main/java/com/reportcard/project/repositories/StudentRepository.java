package com.reportcard.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reportcard.project.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	Student getById(Integer studentId);
}