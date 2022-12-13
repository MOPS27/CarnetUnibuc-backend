package com.reportcard.project.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reportcard.project.dtos.CourseRequestDto;
import com.reportcard.project.dtos.CourseResponseDto;
import com.reportcard.project.dtos.StudentResponseDto;
import com.reportcard.project.dtos.StudentsGradesForOneCoursesResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.exceptions.NotFoundException;
import com.reportcard.project.model.StudentCourse;
import com.reportcard.project.services.CourseService;
import com.reportcard.project.services.StudentCourseService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping
	public List<CourseResponseDto> getAll() {
		return courseService.getAll();
	}
	
	@GetMapping("/{courseId}")
	public List<StudentsGradesForOneCoursesResponseDto> getAllStudentsAndGrades(@PathVariable int courseId) throws NotFoundException  {
		List<StudentsGradesForOneCoursesResponseDto> returnValue = new ArrayList<StudentsGradesForOneCoursesResponseDto>();
		List<StudentCourse> list= courseService.getStudentsAndGradesByCourseId(courseId);
		for(StudentCourse sc: list) {
			StudentsGradesForOneCoursesResponseDto object = new StudentsGradesForOneCoursesResponseDto();
			object.setId(sc.getStudent().getId());
			object.setGrade(sc.getGrade());
			object.setGroupNumber(sc.getStudent().getGroup().getGroupCode());
			object.setEmail(sc.getStudent().getEmail());
			object.setLastName(sc.getStudent().getLastName());
			object.setFirstName(sc.getStudent().getFirstName());
			returnValue.add(object);
		}
		return returnValue;
	}
	
	@PostMapping
	public ResponseEntity<CourseResponseDto> create(@Valid @RequestBody CourseRequestDto request) 
	throws URISyntaxException, NotFoundException {

		var response = courseService.create(request);
		
		var responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(new URI(String.format("/courses/%s", response.getId())));
		
		return new ResponseEntity<CourseResponseDto>(response, responseHeaders, HttpStatus.CREATED);
	}
	
	@PostMapping("/{courseId}/groups/{groupId}")
	public List<StudentsGradesForOneCoursesResponseDto> addGroup(@PathVariable int courseId, @PathVariable int groupId) 
	throws URISyntaxException, NotFoundException, DuplicateItemException {
		courseService.addGroup(courseId, groupId);
		return getAllStudentsAndGrades(courseId);
	}
	
	@PostMapping("/{id}/students/{studentId}")
	public  List<StudentsGradesForOneCoursesResponseDto> addStudent(@PathVariable int id, @PathVariable int studentId) 
	throws URISyntaxException, NotFoundException, DuplicateItemException {

		courseService.addStudentToCourse(id, studentId);
		return getAllStudentsAndGrades(id);
	}
}
