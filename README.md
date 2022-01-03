

# Project 1: School registration system

## Design and implement simple school registration system
- Assuming you already have a list of students
- Assuming you already have a list of courses
- A student can register to multiple courses
- A course can have multiple students enrolled in it.
- A course has 50 students maximum
- A student can register to 5 course maximum

## Provide the following API:
- Create students CRUD
- Create courses CRUD
- Create API for students to register to courses
- Create Report API for admin user to view all relationships between students and courses
+ Filter all students with a specific course
+ Filter all courses for a specific student
+ Filter all courses without any students
+ Filter all students without any courses

2. Wrap everything in docker-compose and update README.md with following details:
• Endpoints and payloads
• How to setup project

3. Technology stack:
• Java/Groovy
• Gradle/Maven
• Spring Boot
• Docker (docker-compose)
• JUnit
• MySQL
• Other technologies or frameworks which can help you.

4. Provide unit tests at the minimum
5. Project can be stored under any version control system like GitHub, GitLab etc.
6. Code needs to be production-ready and best representing your skillset.
7. IMPORTANT: Please use the tech stack described above

# How setup the project:

1. To build the project need havw installed docker, docker-compose,  openjdk 8 and maven.
Run maven to build and package the project:

       mvn clean install package

2. To create the docker image:

       docker-compose build

3. To run docker container:

       docker-compose up -d
    
4. Open your browser in [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
    
    You can use swagger to try all API.

# Endpoints and Payloads

## Student Controller 

### Create new Student:

    
    POST  /api/student

Payload

    {
      "id": 0,
      "firstName": "string",
      "lastName": "string",
      "registrations": [
        {
          "id": 0
        }
      ]
    }

### Retrive all Student

    GET /api/students 
 
### Retrive specific Student

    GET /api/students/{id} 
    
### Update Student

   PUT /api/students/{id}
   
Payload

       {
         "id": 0,
         "firstName": "string",
         "lastName": "string",
         "registrations": [
           {
             "id": 0
           }
         ]
       }
       
 ### Delete Student
 
    DELETE /api/students/{id}
    
 ## Course Controller 
 
 ### Create new course
    
    POST /api/courses
    
    {
      "id": 0,
      "name": "String",
      "registrations": [
        {
          "id": 0
        }
      ]
    }
    
### Retrive all couses

    GET /api/courses 
 
### Retrive specific courss

    GET /api/courses/{id} 
    
### Update course

   PUT /api/courses/{id}
   
Payload

    {
      "id": 0,
      "name": "String",
      "registrations": [
        {
          "id": 0
        }
      ]
    }
       
 ### Delete course
 
    DELETE /api/couser/{id}
 
## Registry Controller

### Registry student to a course

    POST /api/student/{studentID}/register/{courseID}

Example:
   
    http://localhost:8080/api/students/9/register/4
    


### Unregistry student to a course

    POST /api/student/{studentID}/unregister/{courseID}

Example:
   
    http://localhost:8080/api/unstudents/9/register/4
    
    
 ## Report
 
 ### Filter all students with a specific course
 
    GET /api/reports/course/{id}/students
    
 ### Filter all courses for a specific student
 
     GET /api/reports/student/{id}/courses
 
 ### Filter all courses without any students

    /api/reports/allCoursesWithoutAnyStudents 

 ### Filter all students without any courses

    GET /api/reports/allStudentsWithoutAnyCourses
