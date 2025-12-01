import java.time.Duration;
import java.time.ZonedDateTime;

public class ScheduledEvent {
    private String name;
    private ZonedDateTime eventTime;
    private Duration timeUntilEvent;
    private boolean isWorkTime;

    // Constructor
    public ScheduledEvent(String name, ZonedDateTime eventTime, Duration timeUntilEvent, boolean isWorkTime) {
        this.name = name;
        this.eventTime = eventTime;
        this.timeUntilEvent = timeUntilEvent;
        this.isWorkTime = isWorkTime;
    }

    // Getters
    public String name() { return name; }
    public ZonedDateTime eventTime() { return eventTime; }
    public Duration timeUntilEvent() { return timeUntilEvent; }
    public boolean isWorkTime() { return isWorkTime; }

    @Override
    public String toString() {
        return "ScheduledEvent{" +
                "name='" + name + '\'' +
                ", eventTime=" + eventTime +
                ", isWorkTime=" + isWorkTime +
                '}';
    }
}