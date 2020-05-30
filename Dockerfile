FROM openjdk:7
RUN mkdir /app
COPY ./target/cnosso-1.0-SNAPSHOT-jar-with-dependencies.jar /app
WORKDIR /app
ENTRYPOINT ["java", "-jar", "cnosso-1.0-SNAPSHOT-jar-with-dependencies.jar"]
