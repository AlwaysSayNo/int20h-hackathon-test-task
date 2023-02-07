FROM openjdk:17-alpine3.14
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} hackathon_int20h_test_task.jar
ENTRYPOINT ["java","-jar","/hackathon_int20h_test_task.jar", "--spring.profiles.active=prod"]
