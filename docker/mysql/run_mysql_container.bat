@echo off
REM Docker 이미지를 빌드합니다.
docker build -t mysql-image .

REM Docker 컨테이너를 실행합니다.
docker run -d -p 3306:3306 --name my-mysql-container mysql-image

REM 컨테이너 실행 상태 확인
docker ps -a