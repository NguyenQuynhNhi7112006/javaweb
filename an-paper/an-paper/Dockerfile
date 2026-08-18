# Bước 1: Build file JAR từ source code Java
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Bước 2: Chạy ứng dụng Spring Boot
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/an-paper-1.0.0.jar app.jar

# Tạo thư mục uploads để lưu ảnh/video sản phẩm & đánh giá
RUN mkdir -p /app/uploads

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]