package pri.piccus.finkeeper.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pri.piccus.finkeeper.core.Finkeeper;

@Component
public class CronScheduleRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(CronScheduleRunner.class);

    @Autowired
    final Finkeeper finkeeper;

    public CronScheduleRunner(Finkeeper finkeeper) {
        this.finkeeper = finkeeper;
    }

    @Scheduled(fixedDelay = 30000)
    public void run() {
        LOGGER.info("It's show time.");
        try {
            finkeeper.keep();
        } catch (Exception e) {
            LOGGER.error("show is break.cause=", e);
        }
    }

}
