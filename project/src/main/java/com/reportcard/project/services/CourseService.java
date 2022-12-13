package com.reportcard.project.services;

import static java.util.stream.Collectors.toCollection;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.security.auth.x500.X500Principal;
import javax.validation.Validator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reportcard.project.dtos.CourseRequestDto;
import com.reportcard.project.dtos.CourseResponseDto;
import com.reportcard.project.dtos.StudentResponseDto;
import com.reportcard.project.dtos.SubjectResponseDto;
import com.reportcard.project.dtos.SubjectsGradesResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.exceptions.NotFoundException;
import com.reportcard.project.model.Course;
import com.reportcard.project.model.Student;
import com.reportcard.project.model.StudentCourse;
import com.reportcard.project.repositories.CourseRepository;
import com.reportcard.project.repositories.GroupRepository;
import com.reportcard.project.repositories.StudentCourseRepository;
import com.reportcard.project.repositories.StudentRepository;
import com.reportcard.project.repositories.SubjectRepository;
@Service
public class CourseService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	StudentCourseRepository studentCourseRepository;

	
	ModelMapper modelMapper = new ModelMapper();
	
	public CourseService() {
		modelMapper.typeMap(Course.class, CourseResponseDto.class)
			.addMappings(mapper -> {
				mapper.map(src -> src.getSubject(), 
						   CourseResponseDto::setSubject);
//				mapper.map(src -> { 
//							return src.getStudents() == null 
//									? new ArrayList<Student>()
//									: src.getStudents();
//						   },
						   //CourseResponseDto::setStudents);
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
	
	public List<StudentCourse> getStudentsAndGradesByCourseId(Integer courseId) throws NotFoundException {

		var course = courseRepository.findById(courseId);
		if(course.isEmpty()) {
			throw new NotFoundException("Cursul", "id", Integer.toString(courseId));
		}

		return studentCourseRepository.findAllByCourseId(courseId);
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
	
	public CourseResponseDto addGroup(int id, int groupId) throws NotFoundException {
		
		var courseOptional = courseRepository.findById(id);

		if(courseOptional.isEmpty()) {
			throw new NotFoundException("Cursul", "id", Integer.toString(id));
		}
		
		var course = courseOptional.get();
		
		var group = groupRepository.findById(groupId);
		
		if(group.isEmpty()) {
			throw new NotFoundException("Grupa", "id", Integer.toString(groupId));
		}
		
		//var courseStudents = course.getStudents();
		
		var groupStudents = group.get().getStudents();
		
		// join already existing students with newly added students
		// and remove duplicate students
//		List<Student> newCourseStudents = Stream.concat(courseStudents.stream(), groupStudents.stream())
//                .collect(collectingAndThen(
//                			toCollection(() -> new TreeSet<>(comparingInt(Student::getId))),
//                			ArrayList::new));
		
		//course.setStudents(newCourseStudents);

		var updatedCourse = courseRepository.save(course);
		
		return modelMapper.map(updatedCourse, CourseResponseDto.class);
		
	}
	
	public void addStudentToCourse(int courseId, Integer studentId) 
			throws NotFoundException, DuplicateItemException {
		
		var course = courseRepository.getById(courseId);

		if(course == null) {
			throw new NotFoundException("Cursul", "id", Integer.toString(courseId));
		}
		
		
		var student = studentRepository.getById(studentId);
		
		if(student == null) {
			throw new NotFoundException("Studentul", "id", Integer.toString(studentId));
		}
		
		StudentCourse createdVal = null;
		StudentCourse result = validateUnique(studentId, courseId);
		if(result == null) {
			// nu exista => creez unul nou
			StudentCourse sc = new StudentCourse();
			sc.setCourse(course);
			sc.setStudent(student);
			sc.setGrade(0);
			studentCourseRepository.save(sc);
		}else {
			throw new DuplicateItemException("Studentul este inscris deja", "id", studentId.toString());
		}
		
		//var courseStudents = course.getStudents();
		
		// if student already added to course, throw
//		if(courseStudents.stream().anyMatch(x -> x.getId() == studentId)) {
//			throw new DuplicateItemException("Studentul", "id", Integer.toString(studentId));
//		}
		
		// add student to the list of already existing students
//		List<Student> newCourseStudents 
//		= Stream.concat(
//					courseStudents.stream(), 
//					Arrays.asList(student).stream())
//                .collect(Collectors.toList());
//		
//		course.setStudents(newCourseStudents);

		
		//return null;
	}
	
	private StudentCourse validateUnique(Integer studentId, Integer courseId) {

		List<StudentCourse> all = studentCourseRepository.findAll();

		for(StudentCourse sc : all) {
			if(sc.getCourse().getId() == courseId && sc.getStudent().getId() == studentId) {
				return sc;
			}
		}
		return null;
	}
	
	
}