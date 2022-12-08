package com.reportcard.project.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reportcard.project.dtos.CourseResponseDto;
import com.reportcard.project.model.Course;
import com.reportcard.project.repositories.CourseRepository;

@Service
public class CourseService {

	@Autowired
	CourseRepository CourseRepository;
	
	
	public List<CourseResponseDto> getAll() {
		List<CourseResponseDto> returnValue = new ArrayList<>();

		
		List<Course> programms = CourseRepository.findAll();
		
		for (Course entity : programms) {
			returnValue.add(new ModelMapper().map(entity, CourseResponseDto.class));
		}

		return returnValue;
	}
}
