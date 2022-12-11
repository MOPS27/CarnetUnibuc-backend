package com.reportcard.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.reportcard.project.dtos.CourseRequestDto;
import com.reportcard.project.dtos.CourseResponseDto;
import com.reportcard.project.dtos.ProgrammeRequestDto;
import com.reportcard.project.dtos.ProgrammeResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.exceptions.NotFoundException;
import com.reportcard.project.model.Course;
import com.reportcard.project.model.Programme;
import com.reportcard.project.model.Subject;
import com.reportcard.project.repositories.CourseRepository;
import com.reportcard.project.repositories.SubjectRepository;
import com.reportcard.project.services.CourseService;

//@TestConfiguration
//class ValidatorConfiguration {
//	@Bean
//	@Primary
//	Validator getValidator() {
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//		return factory.getValidator();
//	}
//}

//@Import(ValidatorConfiguration.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CourseTests {

	@Mock
	private CourseRepository courseRepositoryMock;
	
	@Mock
	private SubjectRepository subjectRepositoryMock;
	
	
	@InjectMocks
	private CourseService courseService;

	
	@Test
	void course_get() {
		
		var subject = new Subject() {{
			setId(1);
			setName("subj1");
			setCreditCount(5);
		}};
		
		List<Course> courses = new ArrayList<Course>();
		courses.add(new Course() {{ 
			setId(1);
			setSubject(subject);
			setProfessorName("prof1");
			setCalendarYearName("2022-2023"); 
			}});
		courses.add(new Course() {{ 
			setId(2);
			setSubject(subject);
			setProfessorName("prof2");
			setCalendarYearName("2022-2023"); 
			}});
		
		when(courseRepositoryMock.findAll()).thenReturn(courses);
		
		List<CourseResponseDto> response = courseService.getAll();
		
		assertNotNull(response);
		assertEquals(2, response.size());
		
		List<CourseResponseDto> expected = courses.stream()
				.map(p -> new CourseResponseDto() {{ 
					setId(p.getId());
					setProfessorName(p.getProfessorName());
					setSubjectName(subject.getName());
					setCalendarYearName(p.getCalendarYearName());
				}})
				.collect(Collectors.toList());
		
		assertThat(response)
			.usingRecursiveComparison()
			.isEqualTo(expected);
	}
	
	@Test
	void course_create() throws NotFoundException {
		
		var subject = new Subject() {{
			setId(1);
			setName("subj1");
			setCreditCount(5);
		}};
		
		when(subjectRepositoryMock.findById(1)).thenReturn(Optional.of(subject));
		
		var request = new CourseRequestDto() {{
			setProfessorName("prof1");
			setCalendarYearName("2022-2023");
			setSubjectId(1);
		}};
		
		var savedCourse = new Course() {{
			setId(1);
			setProfessorName("prof1");
			setCalendarYearName("2022-2023");
			setSubject(subject);
		}};
		
		when(courseRepositoryMock.save(any(Course.class))).thenReturn(savedCourse);
		
		var response = courseService.create(request);
		
		verify(courseRepositoryMock).save(any(Course.class));
		
		var expected = new CourseResponseDto() {{
			setId(savedCourse.getId());
			setSubjectName(subject.getName());
			setProfessorName(savedCourse.getProfessorName());
			setCalendarYearName(savedCourse.getCalendarYearName());
		}};
		
		assertThat(response)
			.usingRecursiveComparison()
			.isEqualTo(expected);
	}
	
	@Test
	void programme_create_throwsNotFound_whenSubjectNotExist() throws NotFoundException {
		
		when(subjectRepositoryMock.findById(any(Integer.class)))
			.thenReturn(Optional.empty());
		
		var request = new CourseRequestDto() {{
			setProfessorName("prof1");
			setCalendarYearName("2022-2023");
			setSubjectId(1);
		}};
		
		var exception = assertThrows(NotFoundException.class, () -> { 
			courseService.create(request);
		});
		
		var expectedMessage = String.format("%s cu %s = %s nu exista in baza de date", 
				"Materia", "id", Integer.toString(request.getSubjectId()));
		var actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
		
		verify(courseRepositoryMock, never()).save(any(Course.class));
		
	}

}
