  <h1 align="center"> Pass In </h1>
  <p align="center">
    <a href="#-tecnologies">Technologies</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
    <a href="#-project">Project</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
    <a href="#memo-license">License</a>
    <p align="center">
    <img alt="License" src="https://img.shields.io/static/v1?label=license&message=MIT&color=49AA26&labelColor=000000">
  </p>
  </p>
  
  <br>
  
  ## 🚀 Tecnologies
  
  This application is built using Spring Boot to develop a robust and scalable API. Additionally, Flyway is integrated to manage database migrations, ensuring a seamless and reliable process for versioning and maintaining the database schema.
  
  - Java & Spring Boot
  - Fliway (migrations)
  - HSQLDB
  - Git & Github 
  
   <br>
  
  ## 💻 Project
  
  This project offers an API for managing events and attendees, featuring robust validations and adhering to SOLID principles in its design.
  
  <br>
  
  ## :memo: License
  
  
  This project is under license from MIT
  
  <br>
  
  ## Endpoints 
  <p>To test the application endpoints you can use the Postman, HttpPie, Insomnia...</p>
  
  <br>
  
  ### Register event
   - <p> To create an event, use the following command: </p>
   
  ```sh
  curl -X POST http://localhost:8080/events \
       -H "Content-Type: application/json" \
       -d {
      "title": "British GP",
      "details": "Formula 1 Grand Prix",
      "maximumAttendees": 10000
          }
  ```
  
  <br>
  
<p> This is the expected response: </p>
  
  ```json
  {
      "eventId": "d1ff0d66-3718-4cf3-914b-eefec1fce64b"
  }
  ```
  
  <br>
  
  ### Get event by id
   - <p> To retrieve a specific event, use the following command: </p>
   
  ```sh
    curl -X GET http://localhost:8080/events/d1ff0d66-3718-4cf3-914b-eefec1fce64b
  ```
  
  <br>
  
<p>This is the expected response:</p>
  
  ```json
  {
      "eventDetailDTO": {
          "id": "d1ff0d66-3718-4cf3-914b-eefec1fce64b",
          "title": "British GP",
          "details": "Formula 1 Grand Prix",
          "slug": "british-gp",
          "maximumAttendees": 10000,
          "attendeesAmount": 0
      }
  }
  ```
  
  <br>
  
  ### Register participant in event
   - <p> To register an attendee, use the following command. After "events/", add the event ID where you want to register on attendee </p>
   
  ```sh
  curl -X POST http://localhost:8080/events/d1ff0d66-3718-4cf3-914b-eefec1fce64b/attendees \
       -H "Content-Type: application/json" \
       -d {
      "name": "Lewis Hamilton",
      "email": "lewish@mail.com"
          }
  ```
  
  <br>
  
<p>This is the expected response:</p>
  
  ```json
  {
      "attendeeId": "50d5ce4c-9fa7-4029-9173-199aa1f2f1e1"
  }
  ```
  
  <br>
  
  ### Check In
   - <p> To check in a participant, use the following command. The parameter after "attendees/" is the attendee ID: </p>
   
  ```sh
  curl -X POST http://localhost:8080/attendees/50d5ce4c-9fa7-4029-9173-199aa1f2f1e1/check-in \
  ```
  
  <br>
  
  <p> you will receive a 201 (created) status in the response body </p>
  
  <br>
  
  ### Attendee badge
   - <p> To get attendee details, use the following command, passing the attendee ID as a parameter: </p>
   
  ```sh
    curl -X GET http://localhost:8080/attendees/50d5ce4c-9fa7-4029-9173-199aa1f2f1e1/badge
  ```
  
  <br>
  
  <p> This is the expected response: </p>
  
  ```json
  {
      "badge": {
          "name": "Lewis Hamilton",
          "email": "lewish@mail.com",
          "checkInUrl": "http://localhost:8080/attendees/50d5ce4c-9fa7-4029-9173-199aa1f2f1e1/check-in",
          "eventId": "d1ff0d66-3718-4cf3-914b-eefec1fce64b"
      }
  }
  ```
  
  <br>
  
  
  ### Event attendees
   - <p> To get attendees for an event, use the following command, passing the event ID as a parameter: </p>
   
  ```sh
    curl -X GET http://localhost:8080/events/attendees/d1ff0d66-3718-4cf3-914b-eefec1fce64b
  ```
  
  <br>
  
<p>This is the expected response:</p>
  
  ```json
  {
      "attendees": [
          {
              "id": "50d5ce4c-9fa7-4029-9173-199aa1f2f1e1",
              "name": "Lewis Hamilton",
              "email": "lewish@mail.com",
              "createdAt": "2024-08-14T21:51:52.33202",
              "checkedInAt": "2024-08-14T21:54:37.105705"
          }
      ]
  }
  ```
  
  <br>
  
  <p>Thanks for your attention, see you next time 💜</p>
  
