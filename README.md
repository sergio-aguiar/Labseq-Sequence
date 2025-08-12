# Labseq Project
A Quarkus/Angular project that allows users to calculate labseq sequence values for a given index.

## Running the Project (in development mode)

1. Navigate to the backend directory:

   ```bash
   cd backend
   ```

2. Run the application:

    ```bash
    ./mvnw quarkus:dev
    ```

## Running the Project (building from source code)

1. Navigate to the backend directory:

   ```bash
   cd backend
   ```

2. Package the application:

    ```bash
    ./mvnw clean package
    ```

3. Run the application:

    ```bash
    java -jar target/quarkus-app/quarkus-run.jar
    ```

## Running the Project (with JAR from the releases page)

1. Run the application (with the release JAR in the current command line directory):

    ```bash
    java -jar quarkus-run.jar
    ```

## Accessing the Application

- Open a browser and go to [`http://localhost:8080/`](http://localhost:8080/) to see the Angular-built front-end.

- You can also send GET requests to:
```bash
`http://localhost:8080/labseq/{n}`
```
where `n` is a non-negative Integer up to **100000**.

> The upper-constraint to `n` was added to allow for fulfilling the exercise's highest demand test, while attempting to minimize potential `OutOfMemoryError` issues.

- To view the OpenAPI specification documentation (Swagger-UI), visit:
[`http://localhost:8080/q/swagger-ui/#/`](http://localhost:8080/q/swagger-ui/#/)

- To obtain the OpenAPI specification file, visit:
[`http://localhost:8080/q/openapi`](http://localhost:8080/q/openapi)