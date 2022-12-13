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

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.reportcard.project.dtos.CourseResponseDto;
import com.reportcard.project.dtos.GroupRequestDto;
import com.reportcard.project.dtos.StudentCourseRequestDto;
import com.reportcard.project.dtos.StudentCourseResponseDto;
import com.reportcard.project.dtos.StudentRequestDto;
import com.reportcard.project.dtos.StudentResponseDto;
import com.reportcard.project.exceptions.DuplicateItemException;
import com.reportcard.project.exceptions.NotFoundException;
import com.reportcard.project.model.Course;
import com.reportcard.project.model.Group;
import com.reportcard.project.model.Student;
import com.reportcard.project.model.StudentCourse;
import com.reportcard.project.model.Subject;
import com.reportcard.project.repositories.CourseRepository;
import com.reportcard.project.repositories.StudentCourseRepository;
import com.reportcard.project.repositories.StudentRepository;
import com.reportcard.project.services.CourseService;
import com.reportcard.project.services.StudentCourseService;
import com.reportcard.project.services.StudentService;


// grades
@SpringBootTest

@ExtendWith(MockitoExtension.class)
public class StudentCourseTests {

	@Mock
	private StudentCourseRepository studentCourseRepositoryMock;
	
	@Mock
	private StudentRepository studentRepositoryMock;
	
	@Mock
	private CourseRepository courseRepositoryMock;
	
	@InjectMocks
	private StudentCourseService studentCourseService;
	
	@InjectMocks
	private CourseService courseService;
	
	@InjectMocks
	private StudentService studentService;

	ModelMapper modelMapper = new ModelMapper();
	
	@Test
	void studentCourse_get() {
		
		List<StudentCourse> studentCourses = new ArrayList<StudentCourse>();
		studentCourses.add(new StudentCourse() {{ 
			setId(1);
			setStudent(new Student() {{
				setId(1);
				setLastName("Studentul x");
				setFirstName("y");
				setEmail("studentxy@gmail.com");
				setGroup(new Group() {{
						setGroupCode(123);
						}});
			}});
			setCourse(new Course() {{
				setId(1);
				setSubject(new Subject() {{
							setId(1);
							setName("subj1");
							setCreditCount(5);
				}});
				setProfessorName("prof1");
				setCalendarYearName("2022-2023"); 
			}});
			}});
		
		studentCourses.add(new StudentCourse() {{ 
			setId(2);
			setStudent(new Student() {{
				setId(2);
				setLastName("Studentul z");
				setFirstName("t");
				setEmail("studentzt@gmail.com");
				setGroup(new Group() {{
						setGroupCode(123);
						}});
			}});
			setCourse(new Course() {{
				setId(2);
				setSubject(new Subject() {{
							setId(1);
							setName("subj2");
							setCreditCount(5);
				}});
				setProfessorName("prof2");
				setCalendarYearName("2022-2023"); 
			}});
			}});
		
		when(studentCourseRepositoryMock.findAll()).thenReturn(studentCourses);
		
		List<StudentCourseResponseDto> response = studentCourseService.getAll();
		
		assertNotNull(response);
		assertEquals(2, response.size());
		
		List<StudentCourseResponseDto> expected = studentCourses.stream()
				.map(p -> new StudentCourseResponseDto() {{ 
					setId(p.getId());
					setStudent(modelMapper.map(p.getStudent(), StudentResponseDto.class));
					setCourse(modelMapper.map(p.getCourse(), CourseResponseDto.class));
					setGrade(p.getGrade());
				}})
				.collect(Collectors.toList());
		
		assertThat(response)
			.usingRecursiveComparison()
			.isEqualTo(expected);
	}
	
	@Test
	void studentCourse_create() throws DuplicateItemException, NotFoundException {
		
		var request = new StudentCourseRequestDto() {{
			setStudentId(1);
			setCourseId(1);
			setGrade(10);
		}};
		
		var savedStudentCourse = new StudentCourse() {{
			setId(1);
			setStudent(new Student() {{
				setId(1);
				setLastName("Studentul a");
				setFirstName("b");
				setEmail("studentab@gmail.com");
				setGroup(new Group() {{
						setGroupCode(123);
						}});
			}});
			setCourse(new Course() {{
				setId(1);
				setSubject(new Subject() {{
							setId(1);
							setName("subj2");
							setCreditCount(5);
				}});
				setProfessorName("prof2");
				setCalendarYearName("2022-2023"); 
			}});
			setGrade(10);
		}};
		
		
		Mockito.lenient().when(studentRepositoryMock.save(any(Student.class))).thenReturn(new Student());
		Mockito.lenient().when(courseRepositoryMock.save(any(Course.class))).thenReturn(new Course());
		Mockito.lenient().when(studentCourseRepositoryMock.save(any(StudentCourse.class))).thenReturn(savedStudentCourse);
					
		var response = studentCourseService.create(request);
		
		verify(studentCourseRepositoryMock).save(any(StudentCourse.class));
		
		var expected = new StudentCourseResponseDto() {{
			System.out.println(savedStudentCourse.getId());
			System.out.println(savedStudentCourse.getCourse());
			System.out.println(savedStudentCourse.getStudent());
			System.out.println(savedStudentCourse.getGrade());
			setId(savedStudentCourse.getId());
			setCourse(modelMapper.map(savedStudentCourse.getCourse(), CourseResponseDto.class));
			setStudent(modelMapper.map(savedStudentCourse.getStudent(), StudentResponseDto.class));
			setGrade(savedStudentCourse.getGrade());
		}};
		
		assertThat(response)
			.usingRecursiveComparison()
			.isEqualTo(expected);
	}
	
//	@Test
//	void studentCourse_create_throws_NotFound_whenNotFound() throws DuplicateItemException, NotFoundException {
//		
//		var request = new StudentCourseRequestDto() {{
//			setStudentId(1);
//			setCourseId(1);
//			setGrade(10);
//		}};
//		
//		var savedStudentCourse = new StudentCourse() {{
//			setId(1);
//			setStudent(new Student() {{
//				setId(3);
//				setLastName("Studentul a");
//				setFirstName("b");
//				setEmail("studentab@gmail.com");
//				setGroup(new Group() {{
//						setGroupCode(123);
//						}});
//			}});
//			setCourse(new Course() {{
//				setId(1);
//				setSubject(new Subject() {{
//							setId(1);
//							setName("subj2");
//							setCreditCount(5);
//				}});
//				setProfessorName("prof2");
//				setCalendarYearName("2022-2023"); 
//			}});
//			setGrade(10);
//		}};
//		
//		Student savedStudent = null;
//		Mockito.lenient().when(studentRepositoryMock.save(any(Student.class))).thenReturn(savedStudent);
//		Mockito.lenient().when(studentCourseRepositoryMock.save(any(StudentCourse.class))).thenReturn(savedStudentCourse);
//		
//		var exception = assertThrows( NotFoundException .class, () -> { 
//			studentCourseService.create(request);
//		});
//		
//		var expectedMessage = String.format("%s cu %s = %s nu exista in baza de date", 
//				"Studentul", 
//				"id", 
//				"1");
//		var actualMessage = exception.getMessage();
//		
//		assertEquals(expectedMessage, actualMessage);
//		
//		verify(studentCourseRepositoryMock, never()).save(any(StudentCourse.class));
//			
////		var response = studentCourseService.create(request);
////		
////		verify(studentCourseRepositoryMock).save(any(StudentCourse.class));
////		
////		var expected = new StudentCourseResponseDto() {{
////			System.out.println(savedStudentCourse.getId());
////			System.out.println(savedStudentCourse.getCourse());
////			System.out.println(savedStudentCourse.getStudent());
////			System.out.println(savedStudentCourse.getGrade());
////			setId(savedStudentCourse.getId());
////			setCourse(modelMapper.map(savedStudentCourse.getCourse(), CourseResponseDto.class));
////			setStudent(modelMapper.map(savedStudentCourse.getStudent(), StudentResponseDto.class));
////			setGrade(savedStudentCourse.getGrade());
////		}};
////		
////		assertThat(response)
////			.usingRecursiveComparison()
////			.isEqualTo(expected);
//	}
	
//	@Test
//	void studentCourse_create_throwsDuplicate_whenNameDuplicate() throws DuplicateItemException {
//		
//
//		var request = new StudentCourseRequestDto() {{
//			setStudentId(1);
//			setCourseId(1);
//			setGrade(10);
//		}};
//		
//		var savedStudentCourse = new StudentCourse() {{
//			setId(1);
//			setStudent(new Student() {{
//				setId(1);
//				setLastName("Studentul a");
//				setFirstName("b");
//				setEmail("studentab@gmail.com");
//				setGroup(new Group() {{
//						setGroupCode(123);
//						}});
//			}});
//			setCourse(new Course() {{
//				setId(1);
//				setSubject(new Subject() {{
//							setId(1);
//							setName("subj2");
//							setCreditCount(5);
//				}});
//				setProfessorName("prof2");
//				setCalendarYearName("2022-2023"); 
//			}});
//			setGrade(10);
//		}};
//		
//		Student savedStudent = null;
//		when(studentRepositoryMock.save(any(Student.class))).thenReturn(savedStudent);
//		when(studentCourseRepositoryMock.save(any(StudentCourse.class))).thenReturn(savedStudentCourse);
//		
//		var exception = assertThrows(DuplicateItemException.class, () -> { 
//			studentCourseService.create(request);
//		});
//		
//		var expectedMessage = String.format("%s cu %s = %s deja exista", 
//				"Cursul", 
//				"denumirea", 
//				request.getCourseId().toString());
//		var actualMessage = exception.getMessage();
//		
//		assertEquals(expectedMessage, actualMessage);
//		
//		verify(studentCourseRepositoryMock, never()).save(any(StudentCourse.class));
//		
//	}

}
