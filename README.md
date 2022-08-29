# memory-hole
A test image for memory limit. e.g., to test k8s resource quota.

# parameters
```
docker run -e INCREASE=10 -e INITIAL=100 memory-hole
```
`INCREASE=10` means increase 10MB every 1 second. `0` means no increasing.
`INITIAL=100` means starts with 100MB.
JVM itself consumes memory so the initial heap is at least 50MB.
JVM allocate memory in batch, so the heap allocation will not be accurate as 1MB per second.



Note, this test application will respect the container limit, you can verify this by
```shell
docker run -m 300MB openjdk:17 java  -XX:MaxRAMPercentage=100  -XshowSettings:VM -version
```

# probes
```
liveness:   GET /health/liveness
readiness:  GET /health/readiness
```
To toggle a probe to on/off
```
curl -sv -X PUT  http://localhost:8080/health/liveness\?status\=false
or
curl -sv -X PUT  http://localhost:8080/health/liveness\?status\=true
```

# build
```
./gradlew clean build
docker built -t memory-hole .
```

Will rewrite it in C when I have spare time.
