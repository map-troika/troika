FROM ubuntu
RUN mkdir /app
COPY ./target/cnosso-1.0-SNAPSHOT.jar /app
WORKDIR /app
ENTRYPOINT ["java", "-jar", "cnosso-1.0-SNAPSHOT.jar"]
