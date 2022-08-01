# memory-hole
a test image for memory limit. e.g., to test k8s resource quota.

# parameters
```
<increase in MB per 10 seconds>
```
e.g., `1`, means increase 1MB every 1 second. `0` means no increasing.
JVM itself consumes memory so the initial heap is at least 50MB.
JVM allocate memory in batch, so the heap allocation will not be accurate as 1MB per second.


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
# run
```shell
docker run -e INCREASE=10  memory-hole
```