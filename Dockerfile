FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /workspace

COPY pom.xml .
COPY src ./src

RUN mvn -B -ntp package

FROM eclipse-temurin:25-jre

WORKDIR /app

RUN useradd --system --uid 10001 --create-home app

COPY --from=build --chown=app:app /workspace/target/*.jar /app/app.jar

USER app

EXPOSE 8080

ENV JAVA_OPTS=""

CMD ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar --server.port=${PORT:-8080}"]
