package com.reportcard.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reportcard.project.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

}
