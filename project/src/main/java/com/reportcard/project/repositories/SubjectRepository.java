package com.reportcard.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reportcard.project.model.Subject;

@Repository
public interface SubjectRepository  extends JpaRepository<Subject, Integer>{

}
