use report_card;

select * from programmes;

INSERT INTO programmes VALUES (1, "ionel", 12);
INSERT INTO programmes VALUES (2, "gigel", 12);

SET FOREIGN_KEY_CHECKS=0;
drop table programmes;
SET FOREIGN_KEY_CHECKS=1;