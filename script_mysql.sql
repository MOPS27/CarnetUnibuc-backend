use report_card;

select * from programmes;
select * from courses;
select * from students;
select * from subjects;
select * from groupes;

INSERT INTO programmes VALUES (1, "ionel", 12);
INSERT INTO programmes VALUES (2, "gigel", 12);

-- INSERT INTO courses VALUES (1, 12, "Curs 1");
-- INSERT INTO courses VALUES (2, 5, "Curs 2");

INSERT INTO secretaries VALUES (1, "maricica");

SET FOREIGN_KEY_CHECKS=0;
drop table programmes;
drop table courses;
drop table student;
drop table subjects;
drop table groupes;
SET FOREIGN_KEY_CHECKS=1;