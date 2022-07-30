# memory-hole
a test image for memory limit. e.g., to test k8s resource quota.

# parameters
```
<initial memory in MB> <increase in MB per 10 seconds>
```
e.g., `3 1`, means starting with 3MB, increase 1MB every 1 second.
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