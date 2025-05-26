FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copiamos el proyecto
COPY . .

# Permisos para ejecutar Maven Wrapper
RUN chmod +x ./mvnw

# Compilamos el JAR
RUN ./mvnw clean package -DskipTests

# Segunda fase: ejecución
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copiamos el JAR compilado (detecta el nombre automáticamente)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
