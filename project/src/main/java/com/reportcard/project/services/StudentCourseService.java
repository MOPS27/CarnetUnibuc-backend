package com.reportcard.project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reportcard.project.dtos.CourseResponseDto;
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
	
	public List<StudentCourseResponseDto> getAll() {
		return studentCourseRepository.findAll()
				.stream()
				.map(x -> modelMapper.map(x, StudentCourseResponseDto.class))
				.collect(Collectors.toList());
	}
	
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
	
	public Integer getGradeByCourseAndStudentId(Integer courseId, Integer studentId) {

		List<StudentCourse> list = studentCourseRepository.findAll();
		
		for(StudentCourse l : list) {
			if(l.getCourse().getId() == courseId && l.getStudent().getId() == studentId) {
				return l.getGrade();
			}
		}
		
		return -1;
	}
	
	public StudentCourseResponseDto create(@Valid StudentCourseRequestDto request) throws NotFoundException{

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
		
		StudentCourse createdVal = null;
		var result = validateUnique( studentId, courseId);
		if(result == null) {
			// nu exista => creez unul nou
			val.setStudent(student);
			val.setCourse(course);

			createdVal = studentCourseRepository.save(val);
		}else {
			createdVal = updateGrade(result, request.getGrade());
		}
		
		return modelMapper.map(createdVal, StudentCourseResponseDto.class);
	}
	
	public StudentCourse updateGrade(Integer studentCourseId, Integer grade){
		System.out.println("StudentCourse id " + studentCourseId.toString());
		StudentCourse sc = studentCourseRepository.getById(studentCourseId);
		sc.setGrade(grade);

		return studentCourseRepository.save(sc);
	}
	
	private Integer validateUnique(Integer studentId, Integer courseId) {

		List<StudentCourse> all = studentCourseRepository.findAll();

		for(StudentCourse sc : all) {
			if(sc.getCourse().getId() == courseId && sc.getId() == studentId) {
				return sc.getId();
			}
		}
		return null;
	}
}
