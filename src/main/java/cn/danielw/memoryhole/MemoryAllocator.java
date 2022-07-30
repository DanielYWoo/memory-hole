package cn.danielw.memoryhole;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class MemoryAllocator {

    private static Log log = LogFactory.getLog(MemoryAllocator.class);

    private List<Object[]> pool = new LinkedList<>();
    @Value("${initial}") private int initialMB;
    @Value("${increase}") private int increaseMB;
    private int count;

    @Scheduled(initialDelay = 0, fixedDelay = 1000)
    public void schedule() {
        StringBuilder sb = new StringBuilder("count=");
        sb.append(++count);
        int size;
        if (pool.isEmpty()) {
            sb.append(", initial allocation " + initialMB + "MB");
            size = initialMB;
        } else {
            sb.append(", increase " + increaseMB + "MB");
            size = increaseMB;
        }

        // 1. JVM heap does not increase 1MB by 1MB, it's allocated in batch
        // 2. with JVM compressed OOPs optimization, each pointer is 4 bytes even on 64 bit machines
        // we assume no optimization here, for a test, it's not required to be very accurate
        pool.add(new Object[size * 1024 * 1024 / 4]);

        sb.append(", total=" + (Runtime.getRuntime().totalMemory() / 1024 / 1024) +
                "MB, max=" + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + "MB");
        log.info(sb.toString());
    }
}
