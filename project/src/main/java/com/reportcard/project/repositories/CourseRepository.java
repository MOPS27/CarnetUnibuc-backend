package com.reportcard.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.reportcard.project.model.Course;
import com.reportcard.project.model.Programme;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

}
