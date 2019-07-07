package app.core.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

/**
 * Can be used for logging purposes
 */
public class TimeMesure {

    private static final Logger LOGGER = Logger.getLogger(TimeMesure.class.getName());
    private LocalDateTime startTime;
    private String taskName;

    public TimeMesure(String taskName) {
        this.taskName = taskName;
        this.startTime = LocalDateTime.now();
    }

    public void result() {
        LOGGER.info(String.format("%s finished in %s", taskName, formatTimeDifference(startTime, LocalDateTime.now())));
    }

    private String formatTimeDifference(LocalDateTime before, LocalDateTime toDateTime) {
        LocalDateTime tempDateTime = LocalDateTime.from(before);
        long hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours(hours);

        long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes(minutes);

        long seconds = tempDateTime.until(toDateTime, ChronoUnit.SECONDS);
        tempDateTime = tempDateTime.plusSeconds(seconds);

        long milisec = tempDateTime.until(toDateTime, ChronoUnit.MILLIS);
        return String.format("%d hours, %d minutes, %d seconds, %d millisecods\n", hours, minutes, seconds, milisec);
    }

}
