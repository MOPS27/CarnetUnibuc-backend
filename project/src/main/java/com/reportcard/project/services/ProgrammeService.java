package com.reportcard.project.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.reportcard.project.dtos.ProgrammeResponseDto;
import com.reportcard.project.mapping.ProgrammeMapper;
import com.reportcard.project.repositories.ProgrammeRepository;

@Service
public class ProgrammeService {

	private final ProgrammeRepository programmeRepository;
	
	private final ProgrammeMapper mapper;
	
	@Autowired
	public ProgrammeService(ProgrammeRepository programmeRepository, ProgrammeMapper mapper) {
		this.programmeRepository = programmeRepository;
		this.mapper = mapper;
	}
	
	public List<ProgrammeResponseDto> getAll() {
		return programmeRepository.findAll().stream()
			.map(p -> mapper.toProgrammeResponseDto(p))
			.collect(Collectors.toList());
	}
}
