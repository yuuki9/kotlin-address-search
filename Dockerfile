# Java 17 이미지를 베이스로 사용
FROM eclipse-temurin:17-jdk-alpine

# 작업 디렉토리 설정
WORKDIR /app

# 로컬의 JAR 파일을 컨테이너로 복사
COPY kotlin-address-search-1.0.1-SNAPSHOT.jar app.jar

# JAR 실행
ENTRYPOINT ["java", "-jar", "app.jar"]