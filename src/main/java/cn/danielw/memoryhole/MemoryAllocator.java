package cn.danielw.memoryhole;

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
    @Value("${increase}") private int increaseMB;
    @Value("${initial}") private int initialMB;
    private int count;

    @Scheduled(initialDelay = 0, fixedDelay = 1000)
    public void schedule() {
        StringBuilder sb = new StringBuilder("count=");
        sb.append(++count);
        if (count == 1) {
            sb.append(", initial " + initialMB + "MB");
            initialMB -= 50; //magic
            if (initialMB < 0) initialMB = 0;
            pool.add(new Object[(initialMB) * 1024 * 1024 / 4]);
        } else {
            sb.append(", increase " + increaseMB + "MB");
            pool.add(new Object[increaseMB * 1024 * 1024 / 4]);
        }
        // 1. JVM heap does not increase 1MB by 1MB, it's allocated in batch
        // 2. with JVM compressed OOPs optimization, each pointer is 4 bytes even on 64 bit machines
        // we assume no optimization here, for a test, it's not required to be very accurate

        sb.append(", total=" + (Runtime.getRuntime().totalMemory() / 1024 / 1024) +
                "MB, max=" + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + "MB");
        log.info(sb.toString());
    }
}
