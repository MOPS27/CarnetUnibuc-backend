package com.reportcard.project.services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reportcard.project.dtos.StudentRequestDto;
import com.reportcard.project.dtos.StudentResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.model.Student;
import com.reportcard.project.repositories.StudentRepository;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepository;

	ModelMapper modelMapper = new ModelMapper();

	public List<StudentResponseDto> getAll() {
		List<StudentResponseDto> returnValue = new ArrayList<>();


		List<Student> students = studentRepository.findAll();

		for (Student entity : students) {
			returnValue.add(modelMapper.map(entity, StudentResponseDto.class));
		}

		return returnValue;
	}

//	public StudentResponseDto create(@Valid StudentRequestDto request) throws DuplicateItemException {
//
//		Student Student = modelMapper.map(request, Student.class);
//
//		validateStudentNameUnique(request.getName());
//
//		var createdStudent = studentRepository.save(Student);
//
//		return modelMapper.map(createdStudent, StudentResponseDto.class);
//	}
	public List<StudentResponseDto> create(@Valid List<StudentRequestDto> request) {

		List<StudentResponseDto> response = new ArrayList<>();
		
		for(StudentRequestDto student : request) {
		Student studentEntity = modelMapper.map(student, Student.class);

		try {
		validateStudentNameUnique(student.getName());
		}
		catch(DuplicateItemException e) {
			continue;
		}

		var createdStudent = studentRepository.save(studentEntity);
		response.add(modelMapper.map(createdStudent, StudentResponseDto.class));
		}

		return response;
	}

	private void validateStudentNameUnique(String name) throws DuplicateItemException {

		var all = studentRepository.findAll();

		var any = all.stream()
				.anyMatch(x -> x.getName().equals(name));

		if (any) {
			throw new DuplicateItemException("Studentul", "numele", name);
		}
	}
}
