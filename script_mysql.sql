use report_card;

select * from programmes;
select * from courses;
select * from students;
select * from subjects;
select * from groupes;
select * from student_course;

INSERT INTO programmes VALUES (1, "ionel", 12);
INSERT INTO programmes VALUES (2, "gigel", 12);

INSERT INTO student_course VALUES (1, 10, 1, 1);
INSERT INTO subjects VALUES (1, 12, "materia x");
INSERT INTO courses VALUES (1, "2019-2020", "profesor x", 1);


-- INSERT INTO courses VALUES (1, 12, "Curs 1");
-- INSERT INTO courses VALUES (2, 5, "Curs 2");

INSERT INTO secretaries VALUES (1, "maricica");

SET FOREIGN_KEY_CHECKS=0;
drop table programmes;
drop table courses;
drop table students;
drop table subjects;
drop table groupes;
drop table student_course; -- grades
SET FOREIGN_KEY_CHECKS=1;