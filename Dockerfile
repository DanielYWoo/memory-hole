FROM openjdk:17-jdk-alpine
COPY build/libs/memory-hole-0.0.1-SNAPSHOT.jar main.jar
CMD ["java","-jar","/main.jar", "--initial 10", "--increase 1"]