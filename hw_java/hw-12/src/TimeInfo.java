import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class TimeInfo {
    private LocalDate date;
    private LocalTime time;
    private LocalDateTime dateTime;
    private ZoneId timeZone;
    private DayOfWeek dayOfWeek;

    // Constructor
    public TimeInfo(LocalDate date, LocalTime time, LocalDateTime dateTime, ZoneId timeZone, DayOfWeek dayOfWeek) {
        this.date = date;
        this.time = time;
        this.dateTime = dateTime;
        this.timeZone = timeZone;
        this.dayOfWeek = dayOfWeek;
    }

    // Getters
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public LocalDateTime getDateTime() { return dateTime; }
    public ZoneId getTimeZone() { return timeZone; }
    public DayOfWeek getDayOfWeek() { return dayOfWeek; }

    @Override
    public String toString() {
        return "TimeInfo{" +
                "date=" + date +
                ", time=" + time +
                ", timeZone=" + timeZone +
                ", dayOfWeek=" + dayOfWeek +
                '}';
    }
    
}