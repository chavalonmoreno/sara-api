FROM openjdk:8
LABEL maintainer="alexismoreno2404@gmail.com"
VOLUME /sara-app
ADD build/libs/sara-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar","/app.jar"]