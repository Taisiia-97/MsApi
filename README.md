# MS API Documentation

This API provides functionality for user authentication, registration, and management of items. Below is the overview of how to interact with the API and set up the environment.
Swagger documentation is available localhost:3000/swagger-ui/index.html.
Database will be filled, you can you use the user for testing with login: user1@gmail.com and password: Kotekkkkkkkkkk12!

## Installation

1. Clone the repository: `git clone https://github.com/Taisiia-97/MsApi.git`
2. Navigate to the project directory: `cd MsApi`
3. Set credentials for your database in application.yml, you need to set username and password.
4. Build the project using.
5. Run the application.

## Dependencies

- Java 17
- Spring Boot 3
- Spring Security
- JWT
- MySQL and Hibernate
- Liquibase
- Open Api
- Lombok

## Usage

 ## Register a new user http://localhost:3000/register
 ## Example request:
  ```json
{
  "login": "user@domain.com",
  "password": "SomePassword1!"
}
```

## Login and generate token http://localhost:3000/login
## Example request:
  ```json
{
  "login": "user@domain.com",
  "password": "SomePassword1!"
}
```
## Example response:
  ```json
{
  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMjlAZ21haWwuY29tIiwiaWF0IjoxNzQxNDc2NzM2LCJleHAiOjE3NDE0ODAzMzZ9.6zb3F1e-kCmxWyIzYLVGBGA99hCfuv6r6mT1fJS7ZTg"
}
```
## Create a new item http://localhost:3000/items using POST method and use token for Bearer Authorization or add Authorize in swagger and put your token
## Get all items for user http://localhost:3000/items using GET method and use token for Bearer Authorization or add Authorize in swagger and put your token
## Example response:
  ```json
[
  {
    "id": "3ee09787-29ea-4adf-8570-28f1999fb17f",
    "nane": "My item"
  }
]
```



