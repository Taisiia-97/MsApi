# MS API Documentation

This API provides functionality for user authentication, registration, and management of items. Below is the overview of how to interact with the API and set up the environment.
Swagger documentation is available localhost:3000/swagger-ui/index.html.
Database will be filled, you can you use the user for testing with login: user1@gmail.com and password: Kotekkkkkkkkkk12!

## Installation

1. Clone the repository: `git clone https://github.com/Taisiia-97/gitApplication.git`
2. Navigate to the project directory: `cd gitApplication`
3. Build the project.
4. Run the application.


## API Endpoints

### Authentication & User Management

#### **POST /login**
Authenticate with the platform.

**Request Body Example:**

```json
{
  "login": "user@domain.com",
  "password": "SomePassword1"
}

Responses:

    200 OK
        Returns a JWT token upon successful authentication.
