package com.reportcard.project.services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reportcard.project.dtos.SubjectRequestDto;
import com.reportcard.project.dtos.SubjectResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.model.Subject;
import com.reportcard.project.repositories.SubjectRepository;

@Service
public class SubjectService {

	@Autowired
	SubjectRepository subjectRepository;

	ModelMapper modelMapper = new ModelMapper();

	public List<SubjectResponseDto> getAll() {
		List<SubjectResponseDto> returnValue = new ArrayList<>();


		List<Subject> subjects = subjectRepository.findAll();

		for (Subject entity : subjects) {
			returnValue.add(modelMapper.map(entity, SubjectResponseDto.class));
		}

		return returnValue;
	}

	public SubjectResponseDto create(@Valid SubjectRequestDto request) throws DuplicateItemException {

		Subject subject = modelMapper.map(request, Subject.class);

		validateSubjectNameUnique(request.getName());

		var createdSubject = subjectRepository.save(subject);

		return modelMapper.map(createdSubject, SubjectResponseDto.class);
	}

	private void validateSubjectNameUnique(String name) throws DuplicateItemException {

		var all = subjectRepository.findAll();

		var any = all.stream()
				.anyMatch(x -> x.getName().equals(name));

		if (any) {
			throw new DuplicateItemException("Materia", "denumirea", name);
		}
	}
}
