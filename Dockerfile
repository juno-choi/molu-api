FROM openjdk:17.0.2-slim

# 개발자 정보
MAINTAINER "ililil9482@naver.com"
#VOLUME /tmp

# jar파일 복사
COPY build/libs/molu-0.1.jar molu.jar
ENTRYPOINT ["java","-jar","molu.jar"]