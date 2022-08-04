FROM openjdk:8-jdk-alpine
COPY ./build/libs/backend-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8081:8081
