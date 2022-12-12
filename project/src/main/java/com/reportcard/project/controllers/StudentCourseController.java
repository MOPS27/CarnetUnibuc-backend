package com.reportcard.project.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reportcard.project.dtos.StudentCourseRequestDto;
import com.reportcard.project.dtos.StudentCourseResponseDto;
import com.reportcard.project.dtos.SubjectsGradesResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.exceptions.NotFoundException;
import com.reportcard.project.services.StudentCourseService;

@CrossOrigin
@RestController
@RequestMapping("/grades")
public class StudentCourseController {

	@Autowired
	private StudentCourseService studentCourseService;
	
	@GetMapping("/{studentId}")
	public List<SubjectsGradesResponseDto> getByStudent(@PathVariable int studentId) throws NotFoundException {
		return studentCourseService.getSubjectsAndGradesByStudent(studentId);
	}
	
	@PostMapping
	public StudentCourseResponseDto create(@Valid @RequestBody StudentCourseRequestDto request) throws NotFoundException, DuplicateItemException {
		return studentCourseService.create(request);
	}
	
}
