# Base image: 베이스 이미지로 사용할 이미지를 지정
FROM openjdk:21-jdk

# Maintainer
LABEL authors="chris"

# Workdir: 컨테이너 내부에서 작업할 디렉토리를 지정
WORKDIR /app

# Update and install packages: 컨테이너 내부에서 필요한 패키지를 설치 (예시)
RUN apt-get update && apt-get install -y \
    procps \
    vim \
    curl \
    wget \
    git \
    unzip \
    zip

# Copy the jar file into the container: 로컬에서 빌드한 jar 파일을 컨테이너로 복사
COPY /build/libs/*.jar app.jar

# Expose the port: 컨테이너에서 사용할 포트를 노출
EXPOSE 8080

# Run the Application: 컨테이너가 시작될 때 실행할 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]