FROM openjdk:16.0.2-jdk
VOLUME /tmp
ADD target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]