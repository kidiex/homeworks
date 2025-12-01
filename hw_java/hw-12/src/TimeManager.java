import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class TimeManager {

    private final ZoneId defaultTimeZone;
    private final LocalTime workDayStart;
    private final LocalTime workDayEnd;
    private final Set<DayOfWeek> workDays;
    private final Locale locale;
    private List<ScheduledEvent> scheduledEvents;

    //  Def Const
     public TimeManager() {
        this.defaultTimeZone = ZoneId.systemDefault();
        this.workDayStart = LocalTime.of(8, 0); 
        this.workDayEnd = LocalTime.of(18, 0);  
        this.workDays = EnumSet.of(
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, 
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
        );
        this.locale = Locale.getDefault();
        this.scheduledEvents = new ArrayList<>();
    }

    // 2. Par Const
    public TimeManager(ZoneId defaultTimeZone, LocalTime workDayStart, LocalTime workDayEnd, 
                       Set<DayOfWeek> workDays, Locale locale) {
        this.defaultTimeZone = defaultTimeZone;
        this.workDayStart = workDayStart;
        this.workDayEnd = workDayEnd;
        this.workDays = workDays;
        this.locale = locale;
        this.scheduledEvents = new ArrayList<>();
    }

    // 3. Copy Const
    public TimeManager(TimeManager other) {
        this.defaultTimeZone = other.defaultTimeZone;
        this.workDayStart = other.workDayStart;
        this.workDayEnd = other.workDayEnd;
        this.workDays = new HashSet<>(other.workDays); 
        this.locale = other.locale;
        this.scheduledEvents = new ArrayList<>(other.scheduledEvents); // Copy the list elements
    }

    // Getters
    public ZoneId getDefaultTimeZone() { return defaultTimeZone; }
    public LocalTime getWorkDayStart() { return workDayStart; }
    public LocalTime getWorkDayEnd() { return workDayEnd; }
    public Set<DayOfWeek> getWorkDays() { return workDays; }
    public Locale getLocale() { return locale; }
    public List<ScheduledEvent> getScheduledEvents() { return scheduledEvents; };

    // initializeWithCurrentDateTime()
    public void initializeWithCurrentDateTime() {
        this.scheduledEvents.clear();
        
        ZonedDateTime now = ZonedDateTime.now(defaultTimeZone);
        
        System.out.println("System initialized at: " + now);
        System.out.println("Current Zone: " + defaultTimeZone);
        System.out.println("Work settings: " + workDayStart + " - " + workDayEnd);
    }
    // getCurrentTimeInfo
    public TimeInfo getCurrentTimeInfo() {
        ZonedDateTime now = ZonedDateTime.now(defaultTimeZone);
        
        return new TimeInfo(
            now.toLocalDate(),      
            now.toLocalTime(),       
            now.toLocalDateTime(),
            defaultTimeZone,
            now.getDayOfWeek()
        );
    }
    // isCurrentDayWorkDay
    public boolean isCurrentDayWorkDay() {
        DayOfWeek currentDay = LocalDate.now(defaultTimeZone).getDayOfWeek();
        return workDays.contains(currentDay);
    }
    //scheduleEvent

    public void scheduleEvent(String eventName, LocalDateTime eventTime, ZoneId eventTimeZone) {

    ZonedDateTime zdt = ZonedDateTime.of(eventTime, eventTimeZone);
    

    ZonedDateTime now = ZonedDateTime.now(defaultTimeZone);
    Duration timeUntil = Duration.between(now, zdt);

    ZonedDateTime eventInManagerZone = zdt.withZoneSameInstant(defaultTimeZone);
    LocalTime eventLocalTime = eventInManagerZone.toLocalTime();
    
    boolean isWorkTime = !eventLocalTime.isBefore(workDayStart) && 
    !eventLocalTime.isAfter(workDayEnd) &&
    workDays.contains(eventInManagerZone.getDayOfWeek());

    scheduledEvents.add(new ScheduledEvent(eventName, zdt, timeUntil, isWorkTime));
    scheduledEvents.sort((e1, e2) -> e1.eventTime().compareTo(e2.eventTime()));
}   
    //getUpcomingEvents

    public List<ScheduledEvent> getUpcomingEvents(int daysAhead) {
    ZonedDateTime now = ZonedDateTime.now(defaultTimeZone);
    ZonedDateTime limit = now.plusDays(daysAhead);
    
    List<ScheduledEvent> upcoming = new ArrayList<>();
    for (ScheduledEvent event : scheduledEvents) {
        if (event.eventTime().isAfter(now) && event.eventTime().isBefore(limit)) {
            Duration newDuration = Duration.between(now, event.eventTime());

            upcoming.add(new ScheduledEvent(
                event.name(), 
                event.eventTime(), 
                newDuration, 
                event.isWorkTime()
            ));
        }
    }
    return upcoming;
}
    // hasTimeConflicts
    public boolean hasTimeConflicts(LocalDateTime startTime, LocalDateTime endTime) {

    ZonedDateTime checkStart = startTime.atZone(defaultTimeZone);
    ZonedDateTime checkEnd = endTime.atZone(defaultTimeZone);

    for (ScheduledEvent event : scheduledEvents) {

        ZonedDateTime eventStart = event.eventTime();

        ZonedDateTime eventEnd = eventStart.plusHours(1); 

        if (checkStart.isBefore(eventEnd) && checkEnd.isAfter(eventStart)) {
            return true; 
        }
    }
    return false;
}
    // calculateTimeUntilDeadline

    public Duration calculateTimeUntilDeadline(LocalDateTime deadline) {
  
        ZonedDateTime now = ZonedDateTime.now(defaultTimeZone);

        ZonedDateTime target = deadline.atZone(defaultTimeZone);

        return Duration.between(now, target);
    }
    // calculateWorkPeriod
    public Period calculateWorkPeriod(LocalDateTime start, LocalDateTime end) {

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        ZonedDateTime startZdt = start.atZone(defaultTimeZone);
        ZonedDateTime endZdt = end.atZone(defaultTimeZone);
        
        Duration totalDuration = Duration.between(startZdt, endZdt);
        long workSeconds = 0;
        int workDaysCount = 0;

        LocalDate currentDay = startZdt.toLocalDate();
        LocalDate lastDay = endZdt.toLocalDate();

        while (!currentDay.isAfter(lastDay)) {

            if (workDays.contains(currentDay.getDayOfWeek())) {
                workDaysCount++;

                ZonedDateTime workStartToday = ZonedDateTime.of(currentDay, workDayStart, defaultTimeZone);
                ZonedDateTime workEndToday = ZonedDateTime.of(currentDay, workDayEnd, defaultTimeZone);

                if (currentDay.equals(startZdt.toLocalDate())) {
                    if (startZdt.isAfter(workStartToday)) {
                        workStartToday = startZdt;
                    }
                }

                if (currentDay.equals(endZdt.toLocalDate())) {
                    if (endZdt.isBefore(workEndToday)) {
                        workEndToday = endZdt;
                    }
                }

                if (workStartToday.isBefore(workEndToday)) {
                    workSeconds += Duration.between(workStartToday, workEndToday).getSeconds();
                }
            }

            currentDay = currentDay.plusDays(1);
        }

        Duration workDuration = Duration.ofSeconds(workSeconds);
        Duration offDuration = totalDuration.minus(workDuration);

        return new Period(totalDuration, workDuration, offDuration, workDaysCount);
    }

    // generateWorkDaysCalendar
    public List<LocalDate> generateWorkDaysCalendar(int year, int month) {
        List<LocalDate> validWorkDays = new ArrayList<>();

        LocalDate date = LocalDate.of(year, month, 1);

        while (date.getMonthValue() == month) {
            if (workDays.contains(date.getDayOfWeek())) {
                validWorkDays.add(date);
            }
            date = date.plusDays(1);
        }
        
        return validWorkDays;
    }
    // getInternationalTime
    public Map<String, ZonedDateTime> getInternationalTime(ZonedDateTime baseTime, List<String> timeZones) {
        Map<String, ZonedDateTime> result = new HashMap<>();
        
        for (String zoneIdString : timeZones) {
            ZoneId zone = ZoneId.of(zoneIdString);

            ZonedDateTime convertedTime = baseTime.withZoneSameInstant(zone);
            
            result.put(zoneIdString, convertedTime);
        }
        
        return result;
    }

    // isSuitableForAllTimeZones
    public boolean isSuitableForAllTimeZones(ZonedDateTime meetingTime, List<String> participantTimeZones) {
        for (String zoneIdString : participantTimeZones) {
            ZoneId zone = ZoneId.of(zoneIdString);

            ZonedDateTime localMeetingTime = meetingTime.withZoneSameInstant(zone);
            LocalTime localTime = localMeetingTime.toLocalTime();

            if (localTime.isBefore(workDayStart) || localTime.isAfter(workDayEnd)) {
                return false; 
            }
            
            if (!workDays.contains(localMeetingTime.getDayOfWeek())) {
                return false; 
            }
        }
        
        return true; 
    }
    //formatDateTime
    public String formatDateTime(LocalDateTime dateTime, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
            return dateTime.format(formatter);
        } catch (IllegalArgumentException e) {
            return "Error: Invalid pattern - " + e.getMessage();
        }
    }

    // 8.2 parseDateTime
    public LocalDateTime parseDateTime(String dateString, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
            return LocalDateTime.parse(dateString, formatter);
        } catch (DateTimeException e) {
            System.err.println("Failed to parse date: '" + dateString + "' with pattern: '" + pattern + "'");
            throw e; 
        }
    }

    // 8.3 getMultipleFormats
    public Map<String, String> getMultipleFormats(LocalDateTime dateTime) {
        Map<String, String> formats = new LinkedHashMap<>(); 

        formats.put("ISO", dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        

        DateTimeFormatter ruFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", new Locale("ru"));
        formats.put("Russian", dateTime.format(ruFormat));

        DateTimeFormatter usFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a", Locale.US);
        formats.put("US", dateTime.format(usFormat));

        DateTimeFormatter shortFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
                                                         .withLocale(new Locale("ru"));
        formats.put("Short", dateTime.format(shortFormat));
        
    formats.put("Short", dateTime.format(shortFormat));
        
        return formats;
    } // validateTimeRange

    private void validateTimeRange(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
    }
    

} 
    

   