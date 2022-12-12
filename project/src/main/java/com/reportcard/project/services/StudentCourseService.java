package com.reportcard.project.services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reportcard.project.dtos.StudentCourseRequestDto;
import com.reportcard.project.dtos.StudentCourseResponseDto;
import com.reportcard.project.dtos.SubjectRequestDto;
import com.reportcard.project.dtos.SubjectResponseDto;
import com.reportcard.project.dtos.SubjectsGradesResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.exceptions.NotFoundException;
import com.reportcard.project.model.Course;
import com.reportcard.project.model.Student;
import com.reportcard.project.model.StudentCourse;
import com.reportcard.project.model.Subject;
import com.reportcard.project.repositories.CourseRepository;
import com.reportcard.project.repositories.StudentCourseRepository;
import com.reportcard.project.repositories.StudentRepository;

@Service
public class StudentCourseService {

	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	StudentCourseRepository studentCourseRepository;
	
	@Autowired
	CourseRepository courseRepository;

	@Autowired
	CourseService courseService;
	
	@Autowired
	StudentService studentService;
	
	ModelMapper modelMapper = new ModelMapper();
	
	public List<SubjectsGradesResponseDto> getSubjectsAndGradesByStudent(Integer studentId) throws NotFoundException {
		List<SubjectsGradesResponseDto> returnValue = new ArrayList<>();

		var student = studentRepository.findById(studentId);
		if(student.isEmpty()) {
			throw new NotFoundException("Studentul", "id", Integer.toString(studentId));
		}

		List<StudentCourse> studentCourses = studentCourseRepository.findAllById(studentId);
		for(StudentCourse sc: studentCourses) {
			SubjectsGradesResponseDto value = new SubjectsGradesResponseDto();
			value.setGrade(sc.getGrade());
			value.setSubject(modelMapper.map(sc.getCourse().getSubject(), SubjectResponseDto.class));
			returnValue.add(value);
		}
		
		return returnValue;
	}
	
	public StudentCourseResponseDto create(@Valid StudentCourseRequestDto request) throws NotFoundException, DuplicateItemException{

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		StudentCourse val = modelMapper.map(request, StudentCourse.class);
		Integer studentId = request.getStudentId();
		Integer courseId = request.getCourseId();
		
		
		Student student = studentRepository.getById(studentId);
		if(student == null) {
			throw new NotFoundException("Studentul", "id", Integer.toString(studentId));
		}
		
		Course course = courseRepository.getById(courseId);
		
		
		if(course == null) {
			throw new NotFoundException("Cursul", "id", Integer.toString(studentId));
		}
		validateUnique(course.getSubject().getName());
		
		val.setStudent(student);
		val.setCourse(course);

		var createdVal = studentCourseRepository.save(val);

		return modelMapper.map(createdVal, StudentCourseResponseDto.class);
	}
	
	private void validateUnique(String name) throws DuplicateItemException {

		var all = studentCourseRepository.findAll();

		var any = all.stream().anyMatch(x -> x.getCourse().getSubject().getName().equals(name));

		if (any) {
			throw new DuplicateItemException("Materia", "denumirea", name);
		}
	}
}
