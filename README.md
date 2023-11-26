# SpringQuizer

Spring Quizer is a proprietary quizzing application written in java. 
<br>
The question database comes from a third-party provider like opentdb.com .
<br>
The application allows registration, login, gameplay, access to the history of your games and the scoreboard of all players. 
Gameplay is based on selecting a category of questions and then choosing a difficulty level: 
easy mode is five boolean questions,
medium mode is ten multi choice questions
and hard mode is 15 multi choice questions.

![img.png](readme/login.png)
![img.png](readme/game.png)
![img.png](readme/account.png)

<br>
The application exposes restapi allowing to download scoreboards of all players,
a set of questions tailored to the selected category and difficulty,
information about the games of individual players based on userName. Example curl calls
<br>
<br>
curl http://3.67.98.254:8190/quizer/api/scoreboard
<br>
curl http://3.67.98.254:8190/quizer/api/films/easy
<br>
curl http://3.67.98.254:8190/quizer/api/random/hard
<br>
curl http://3.67.98.254:8190/quizer/api/game-info/Arczi
<br>
<br>
# Activation Method

Ensure that you have Docker installed on your environment.

1. **Clone the Git repository:**
    ```bash
    git clone https://github.com/your-username/your-repo.git
    ```

2. **Build the JAR file (Linux command):**
    ```bash
    ./gradlew build
    ```

3. **Build the Docker image:**
    ```bash
    docker build -t spring-quizer .
    ```

4. **Launch the container:**
    ```bash
    docker-compose up -d
    ```

5. **Access the application:**
   The application will be available at [http://localhost:8190/quizer](http://localhost:8190/quizer).

Follow these steps to activate the Spring Quizer application. If you encounter any issues, ensure that Docker is properly installed on your system.

<br>
# Technologies Used

The following technologies were utilized in the production process of this project:

- **Java**: Primary programming language for application development.
  
- **Spring Boot**: Framework for building Java-based applications.

- **Spring Security**: Framework for securing Java applications.

- **PostgreSQL**: Relational database management system.

- **Thymeleaf**: Server-side Java template engine for web applications.

- **Lombok**: Library to reduce boilerplate code in Java.

- **Flyway**: Database migration tool for Java.

- **API opentdb.com**: External API for trivia questions.

- **Wiremock**: Mocking tool for HTTP-based services.

- **Rest Assured**: Testing framework for REST APIs.

- **Mockito**: Mocking framework for unit tests.

- **Docker**: Containerization platform for packaging applications.

- **AWS (Amazon Web Services)**: Cloud computing services for hosting and deployment.

Feel free to explore the individual components of the project to understand how these technologies are integrated.

