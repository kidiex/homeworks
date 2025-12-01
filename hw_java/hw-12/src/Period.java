import java.time.Duration;

public class Period {
    private Duration totalDuration;
    private Duration workTimeDuration;
    private Duration offTimeDuration;
    private int workDaysCount;

    // Constructor
    public Period(Duration totalDuration, Duration workTimeDuration, Duration offTimeDuration, int workDaysCount) {
        this.totalDuration = totalDuration;
        this.workTimeDuration = workTimeDuration;
        this.offTimeDuration = offTimeDuration;
        this.workDaysCount = workDaysCount;
    }

    // Getters
    public Duration totalDuration() { return totalDuration; }
    public Duration workTimeDuration() { return workTimeDuration; }
    public Duration offTimeDuration() { return offTimeDuration; }
    public int workDaysCount() { return workDaysCount; }
    
    @Override
    public String toString() {
        return "WorkPeriod{" +
                "total=" + totalDuration.toHours() + "h" +
                ", work=" + workTimeDuration.toHours() + "h" +
                ", off=" + offTimeDuration.toHours() + "h" +
                ", days=" + workDaysCount +
                '}';
    }
}