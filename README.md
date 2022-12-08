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

## Configuration steps for Eclipse 
1. Configure a new workspace
From eclipse IDE Laucnher browse your working directory. A new workspace will be created.
![image](https://user-images.githubusercontent.com/61749814/206441203-4188b06f-67bc-4cd2-8209-acfe832af924.png)

3. Import your project

![image](https://user-images.githubusercontent.com/61749814/206441518-0b3eadf5-b5fa-49f9-ab0c-97d7dd1b0ff3.png)

Then browse the directory with the project.

5. Change the workspace to dark theme

To change the color theme in your editor: Go to Window | Preferences | General | Appearance | Color Theme. The list of available Eclipse color themes is displayed. A restart of the application will be required.
