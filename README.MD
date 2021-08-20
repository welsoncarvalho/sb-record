## SpringBoot with Java 16 Records ##

It is a simple project demonstrating the use of Spring Boot WebFlux with Java 16 Records. The project is CRUD of users data in MongoDB.

I don't have any project running in production yet, but this simple project is the result of some studies regarding the record's feature. I've been working with Spring Boot for a few years, but only the last 2 applying immutability, and the record feature sounds a good alternative to apply immutability with dependency injection of Spring components.

![Class Diagram](../master/images/diagram.png)

### Curl Endpoints: ###

- **Create User:** curl POST 'http://localhost:8080/user' --header 'Content-Type: application/json' --data-raw '{"firstName": "First Name", "lastName": "Last Name", "birth": "2000-01-01", "email": "test@test.com"}'
- **Update User:** curl PUT 'http://localhost:8080/user/{id}' --header 'Content-Type: application/json' --data-raw '{"firstName": "First Name", "lastName": "Last Name", "birth": "2000-01-01", "email": "test@test.com"}'
- **List Users:** curl GET 'http://localhost:8080/user'
- **Fetch User:** curl GET 'http://localhost:8080/user/{id}'

### To run and build the application: ###

Just run the docker-compose command to run the MongoDB service already configured in docker-compose file:

- docker-compose up -d

For building the application just need to run the maven command with Java 16:

- ./mvn clean package