package com.reportcard.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reportcard.project.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer>{

}
