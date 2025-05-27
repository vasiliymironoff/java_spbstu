# Используем образ Maven с JDK 17 для сборки
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /workspace

# Копируем файлы конфигурации Maven
COPY pom.xml ./
COPY src ./src

# Сборка приложения
RUN mvn clean package -DskipTests

# Используем образ JDK 17 для выполнения приложения
FROM eclipse-temurin:17-jdk-jammy AS runtime
WORKDIR /app

# Копируем собранный JAR файл из предыдущего этапа
COPY --from=build /workspace/target/*.jar app.jar

# Открываем порт 8080
EXPOSE 8080

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
