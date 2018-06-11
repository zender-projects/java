package log.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLog4j {

    private static final Logger logger = LoggerFactory.getLogger(Slf4jLog4j.class);

    public static void main(String[] args) {
        logger.info("Current Time: {}", System.currentTimeMillis());
        logger.info("Current Time: " + System.currentTimeMillis());
        logger.info("Current Time: {}", System.currentTimeMillis());
        logger.trace("trace log");
        logger.warn("warn log");
        logger.debug("debug log");
        logger.info("info log");
        logger.error("error log");
    }
}
