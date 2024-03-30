FROM openjdk:17-slim

COPY ./target/c24-diploma-project-1.0-SNAPSHOT.war /app/
WORKDIR /app

CMD ["java", "-jar", "c24-diploma-project-1.0-SNAPSHOT.war"]