# CarnetUnibuc-backend
University management program - backend


# APIs
1. POST http://localhost:8080/subjects
   Creare materie
   ```
   {
        "name": "Curs 3",
        "creditCount": 12
    }
   ```
2. GET http://localhost:8080/subjects - lista de materii
3. GET http://localhost:8080/subjects/1 - afiseaza o materie dupa id 
4. GET http://localhost:8080/students - lista studentilor
5. POST http://localhost:8080/students - adaugare lista studenti in DB (pot fi adaugate si duplicate nu vor fi luate in considerare), si creare automata a grupelor in DB
   ```
   [
    {
        "lastName":"marcel",
        "firstName":"ionel",
        "email":"gigel@gigeal.com",
        "group":
        {
            "groupCode": 123
        }
    },
    {
        "lastName":"maricica",
        "firstName":"ionel",
        "email":"maricica@gmail.com",
        "group":
        {
            "groupCode": 124
        }
    },
    {
        "lastName":"valetin",
        "firstName":"ana",
        "email":"ana@yahoo.com",
        "group":
        {
            "groupCode": 123
        }
    }
   ]
   ```
6. POST http://localhost:8080/courses/{id}/groups/{groupId}

adauga studentii unei grupe la un curs

daca unul dintre studentii grupei deja e inscris la curs, e ok, doar se asigura ca nu e duplicat

7. POST http://localhost:8080/courses/{id}/students/{studentId}

adauga studentul la un curs

daca studentul e deja inscris la curs, intoarce statuscode 409

8. POST http://localhost:8080/grades - adauga nota unui student la un anumit curs (nu accepta duplicate la aceleasi materii)
   ```
   {
    "studentId":1,
    "courseId":1,
    "grade": 8
    }
   ```
9. GET http://localhost:8080/grades/{studentId} - ia notele si materiile pe un student dat


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
