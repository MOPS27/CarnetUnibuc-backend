# CarnetUnibuc-backend
University management program - backend

## Configuration steps for MySQL local database
### MySQL commnad line client
### MySQL Workbench

1. Open MySQL command line client
2. Enter the password(if you have set any)
3. ```mysql> create database report_card;```
4. ```mysql> use report_card;```
5. ```mysql> create user 'rootMOPS'@'localhost' identified by 'parola225200';```
6. ```mysql> grant all privileges on report_card.* to 'rootMOPS'@'localhost';```
7. ```mysql> exit;```
8. Open MySQL Workbench
9. Click on + near the MySQL Connections title
10. Connection name ```report_card```
11. username ```rootMOPS```
12. password > store in vault > ```parola225200```
13. test connection > info popup with successful connection
14. Press ok 