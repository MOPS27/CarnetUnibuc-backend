package com.reportcard.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import com.reportcard.project.dtos.ProgrammeRequestDto;
import com.reportcard.project.dtos.ProgrammeResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.exceptions.NotFoundException;
import com.reportcard.project.model.Programme;
import com.reportcard.project.repositories.ProgrammeRepository;
import com.reportcard.project.services.ProgrammeService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProgrammeTests {

	@Mock
	private ProgrammeRepository programmeRepositoryMock;
	
	@InjectMocks
	private ProgrammeService programmeService;

	
	@Test
	void programme_get() {
		
		List<Programme> programmes = new ArrayList<Programme>();
		programmes.add(new Programme() {{ 
			setId(1);
			setName("name2");
			setNumberOfYears(3); 
			}});
		programmes.add(new Programme() {{ 
			setId(2);
			setName("name2");
			setNumberOfYears(4); 
			}});
		
		when(programmeRepositoryMock.findAll()).thenReturn(programmes);
		
		List<ProgrammeResponseDto> response = programmeService.getAll();
		
		assertNotNull(response);
		assertEquals(2, response.size());
		
		List<ProgrammeResponseDto> expected = programmes.stream()
				.map(p -> new ProgrammeResponseDto() {{ 
					setId(p.getId());
					setName(p.getName());
					setNumberOfYears(p.getNumberOfYears());
				}})
				.collect(Collectors.toList());
		
		assertThat(response)
			.usingRecursiveComparison()
			.isEqualTo(expected);
	}
	
	@Test
	void programme_create() throws DuplicateItemException {
		
		var request = new ProgrammeRequestDto() {{
			setName(null);
			setNumberOfYears(3);
		}};
		
		var savedProgramme = new Programme() {{
			setId(1);
			setName("name");
			setNumberOfYears(3);
		}};
		
		when(programmeRepositoryMock.save(any(Programme.class))).thenReturn(savedProgramme);
		
		var response = programmeService.create(request);
		
		verify(programmeRepositoryMock).save(any(Programme.class));
		
		var expected = new ProgrammeResponseDto() {{
			setId(savedProgramme.getId());
			setName(savedProgramme.getName());
			setNumberOfYears(savedProgramme.getNumberOfYears());
		}};
		
		assertThat(response)
			.usingRecursiveComparison()
			.isEqualTo(expected);
	}
	
	@Test
	void programme_create_throwsDuplicate_whenNameDuplicate() throws DuplicateItemException {
		
		var request = new ProgrammeRequestDto() {{
			setName("name");
			setNumberOfYears(3);
		}};
		
		var programmes = new ArrayList<Programme>();
		programmes.add(new Programme(){{
			setId(1);
			setName("name");
			setNumberOfYears(3);
		}});
		
		when(programmeRepositoryMock.findAll()).thenReturn(programmes);
		
		var exception = assertThrows(DuplicateItemException.class, () -> { 
			programmeService.create(request);
		});
		
		var expectedMessage = String.format("%s cu %s = %s deja exista", 
				"Programul de studiu", 
				"denumirea", 
				request.getName());
		var actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
		
		verify(programmeRepositoryMock, never()).save(any(Programme.class));
		
	}
	
	@Test
	void programme_delete() throws NotFoundException {
		var programme = new Programme(){{
			setId(1);
			setName("name");
			setNumberOfYears(3);
		}};
		
		when(programmeRepositoryMock.findById(1))
			.thenReturn(Optional.of(programme));
		
		programmeService.delete(1);
		
		verify(programmeRepositoryMock, times(1)).delete(programme);
	}
	
	@Test
	void programme_delete_throwsNotFound() throws NotFoundException {
		when(programmeRepositoryMock.findById(1))
			.thenReturn(Optional.empty());
		
		var exception = assertThrows(NotFoundException.class, () -> { 
			programmeService.delete(1);
		});
		
		verify(programmeRepositoryMock, never()).delete(any(Programme.class));
	
		var expectedMessage = String.format("%s cu %s = %s nu exista in baza de date", 
				"Programul de studiu", 
				"id", 
				1);
		var actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
}
