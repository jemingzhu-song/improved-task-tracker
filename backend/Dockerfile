FROM openjdk:18-jdk-oracle

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 5050

ENTRYPOINT ["java","-jar","/app.jar"]