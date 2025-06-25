FROM esclipse-temurin:17
COPY target/*.jar app.jar
LABEL authors="Admin"

ENTRYPOINT ["java", "-jar", "/app.jar"]