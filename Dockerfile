FROM eclipse-temurin:21-jre-ubi10-minimal

WORKDIR /app

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]