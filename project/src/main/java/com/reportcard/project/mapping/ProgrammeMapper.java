package com.reportcard.project.mapping;

import org.mapstruct.Mapper;

import com.reportcard.project.dtos.ProgrammeResponseDto;
import com.reportcard.project.model.Programme;

@Mapper(componentModel = "spring")
public interface ProgrammeMapper {
	ProgrammeResponseDto toProgrammeResponseDto(Programme programme);
}
