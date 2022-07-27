## Gradle Wrapper

This project is managed through Gradle. The Gradle Wrapper is provided for your convenience. It is recommended to use it.
On Windows you can execute Gradle commands through the gradlew.bat batch script.
On Linux based operating systems, you can run Gradle commands through the gradlew bash script.

## Endpoint
Run application by executing command ./gradlew bootRun. Check if the server is up on http://localhost:8080 and you need to send
2 citis using Postman and HttpMethod post on http://localhost:8080/v1/locations.
Example in body:
{
"from":"Lausanne",
"to":"Genève"
}
This application will show you few routes and you can choose the appropriate one 