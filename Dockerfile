FROM openjdk:17.0.2-jdk
COPY build/libs/memory-hole-0.0.1-SNAPSHOT.jar main.jar
CMD ["java","-XX:MaxRAMPercentage=100", "-XX:MinHeapFreeRatio=1", "-jar","/main.jar"]
