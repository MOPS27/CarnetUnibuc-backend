package com.reportcard.project.services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reportcard.project.dtos.GroupRequestDto;
import com.reportcard.project.dtos.GroupResponseDto;
import com.reportcard.project.dtos.StudentRequestDto;
import com.reportcard.project.dtos.StudentResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.model.Group;
import com.reportcard.project.model.Student;
import com.reportcard.project.repositories.GroupRepository;
import com.reportcard.project.repositories.StudentRepository;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	GroupRepository groupRepository;

	@Autowired
	GroupService groupService;

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

		for (StudentRequestDto student : request) {
			Student studentEntity = modelMapper.map(student, Student.class);

			try {
				validateStudentNameUnique(student.getEmail());
			} catch (DuplicateItemException e) {
				continue;
			}
			GroupResponseDto groupDto = createGroup(student.getGroup());
			Group group = modelMapper.map(groupDto, Group.class); 
			studentEntity.setGroup(group);

			var createdStudent = studentRepository.save(studentEntity);
			response.add(modelMapper.map(createdStudent, StudentResponseDto.class));
		}

		return response;
	}

	private GroupResponseDto createGroup(GroupRequestDto request) {
		
		Group group = modelMapper.map(request, Group.class);

		var entity = validateUnique(request.getGroupCode());
		if (entity == null) {
			var createdGroup = groupRepository.save(group);
			return modelMapper.map(createdGroup, GroupResponseDto.class);
		}
		return modelMapper.map(entity, GroupResponseDto.class);
	}

	private Group validateUnique(Integer groupCode) 
	{
		var all = groupRepository.findAll();
		for (Group group : all) {
			if (group.getGroupCode() == groupCode) {
				return group;
			}
		}
		return null;
	}

	private void validateStudentNameUnique(String email) throws DuplicateItemException {

		var all = studentRepository.findAll();

		var any = all.stream().anyMatch(x -> x.getEmail().equals(email));

		if (any) {
			throw new DuplicateItemException("Studentul", "email", email);
		}
	}
}
