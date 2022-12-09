package com.reportcard.project.services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reportcard.project.dtos.CourseRequestDto;
import com.reportcard.project.dtos.CourseResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.model.Course;
import com.reportcard.project.repositories.CourseRepository;

@Service
public class CourseService {

	@Autowired
	CourseRepository CourseRepository;

	ModelMapper modelMapper = new ModelMapper();

	public List<CourseResponseDto> getAll() {
		List<CourseResponseDto> returnValue = new ArrayList<>();


		List<Course> programms = CourseRepository.findAll();

		for (Course entity : programms) {
			returnValue.add(modelMapper.map(entity, CourseResponseDto.class));
		}

		return returnValue;
	}

	public CourseResponseDto create(@Valid CourseRequestDto request) throws DuplicateItemException {

		Course Course = modelMapper.map(request, Course.class);

		validateCourseNameUnique(request.getName());

		var createdCourse = CourseRepository.save(Course);

		return modelMapper.map(createdCourse, CourseResponseDto.class);
	}

	private void validateCourseNameUnique(String name) throws DuplicateItemException {

		var all = CourseRepository.findAll();

		var any = all.stream()
				.anyMatch(x -> x.getName().equals(name));

		if (any) {
			throw new DuplicateItemException("Programul de studiu", "denumirea", name);
		}
	}
}
