# Fase de build
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copiamos el proyecto
COPY . .

# ⚠️ Damos permisos de ejecución a mvnw (esto evita el error 126)
RUN chmod +x ./mvnw

# Compilamos sin tests para evitar fallos en Railway
RUN ./mvnw clean package -DskipTests

# Fase de ejecución
FROM eclipse-temurin:17-jdk AS run
WORKDIR /app

# Copiamos el JAR generado desde el contenedor anterior
COPY --from=build /app/target/personalFinance-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto (Railway usará $PORT)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
