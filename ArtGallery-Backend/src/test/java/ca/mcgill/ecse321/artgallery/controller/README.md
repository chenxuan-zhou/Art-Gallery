# RESTful API Tests

Each test goes through a general use-case of the program. Assertions are set to detect errors in the RESTful API. 

## Installation
Postman's [newman](https://learning.postman.com/docs/running-collections/using-newman-cli/command-line-integration-with-newman/) command-line tool is required to run the tests. 

It can be installed using `npm` with the command

```npm install -g newman```

## Run tests

1. Inside `application.properties`, set

    ```spring.jpa.hibernate.ddl-auto=create-drop```

2. Run the application on `localhost:8080`

3. Run test using the command

    ```newman run TestName.json```