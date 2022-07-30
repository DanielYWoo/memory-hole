package cn.danielw.memoryhole;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthMonitorController {

    private static Log log = LogFactory.getLog(HealthMonitorController.class);

    private boolean live = true;
    private boolean read = true;


    @GetMapping(path = "/health/liveness", produces = "text/plain")
    public ResponseEntity<String> getLiveness() {
        if (live)
            return new ResponseEntity<>("OK", HttpStatus.OK);
        else
            return new ResponseEntity<>("DOWN", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/health/liveness", produces = "text/plain")
    public ResponseEntity<String> setLiveness(@RequestParam("status") boolean status) {
        log.info("set liveness to " + status);
        live = status;
        return new ResponseEntity<>("SET", HttpStatus.OK);
    }

    @GetMapping(path = "/health/readiness", produces = "text/plain")
    public ResponseEntity<String> getReadiness() {
        if (live)
            return new ResponseEntity<>("OK", HttpStatus.OK);
        else
            return new ResponseEntity<>("DOWN", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/health/readiness", produces = "text/plain")
    public ResponseEntity<String> setReadiness(@RequestParam("status") boolean status) {
        log.info("set readiness to " + status);
        live = status;
        return new ResponseEntity<>("SET", HttpStatus.OK);
    }

}
