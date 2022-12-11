package com.reportcard.project.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Validator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reportcard.project.dtos.CourseRequestDto;
import com.reportcard.project.dtos.CourseResponseDto;
import com.reportcard.project.exceptions.NotFoundException;
import com.reportcard.project.model.Course;
import com.reportcard.project.repositories.CourseRepository;
import com.reportcard.project.repositories.SubjectRepository;

@Service
public class CourseService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	SubjectRepository subjectRepository;
	
	@Autowired
    private Validator validator;
	
	ModelMapper modelMapper = new ModelMapper();
	
	public CourseService() {
		modelMapper.typeMap(Course.class, CourseResponseDto.class)
			.addMappings(mapper -> {
				mapper.map(src -> src.getSubject().getName(), 
						   CourseResponseDto::setSubjectName);
			});
		
		modelMapper.typeMap(CourseRequestDto.class, Course.class)
			.addMappings(mapper -> {
				mapper.skip(Course::setId);
			});
	}
	
	public List<CourseResponseDto> getAll() {
		return courseRepository.findAll()
				.stream()
				.map(x -> modelMapper.map(x, CourseResponseDto.class))
				.collect(Collectors.toList());
	}
	
	public List<CourseResponseDto> getBySubject(int subjectId) throws NotFoundException {
		
		var subject = subjectRepository.findById(subjectId);
		
		if(subject.isEmpty()) {
			throw new NotFoundException("Materia", "id", Integer.toString(subjectId));
		}
		
		return subject.get().getCourses()
				.stream()
				.map(x -> modelMapper.map(x, CourseResponseDto.class))
				.collect(Collectors.toList());
	}
	
	
	public CourseResponseDto create(CourseRequestDto request) throws NotFoundException {
		
		var subject = subjectRepository.findById(request.getSubjectId());
		
		if(subject.isEmpty()) {
			throw new NotFoundException("Materia", "id", Integer.toString(request.getSubjectId()));
		}
		
		Course course = modelMapper.map(request, Course.class);
		course.setSubject(subject.get());
		
		var createdCourse = courseRepository.save(course);
		
		return modelMapper.map(createdCourse, CourseResponseDto.class);
	}
	
}