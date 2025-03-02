
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/cost-efficient-deplyment-0.0.1-SNAPSHOT.jar cost-efficient-deplyment-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "cost-efficient-deplyment-0.0.1-SNAPSHOT.jar"]