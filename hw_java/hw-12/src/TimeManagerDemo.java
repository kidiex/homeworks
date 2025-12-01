import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TimeManagerDemo {
    public static void main(String[] args) {
        // Run all the scenarios
        demoBasicOperations();
        demoEventManagement();
        demoTimeCalculations();
        demoInternationalSupport();
        demoFormattingAndParsing();
        demoErrorHandling();
    }

    // --- Scenario 1: Basic Operations ---
    // --- Сценарий 1: Базовые операции ---
    public static void demoBasicOperations() {
        System.out.println("=== СЦЕНАРИЙ 1: Базовые операции ===");
        
        TimeManager manager = new TimeManager();
        manager.initializeWithCurrentDateTime();

        TimeInfo currentTime = manager.getCurrentTimeInfo();
        
        // Note: We use .getDate() instead of .date() to match our class
        System.out.println(" Текущая дата: " + currentTime.getDate());
        System.out.println(" Текущее время: " + currentTime.getTime());
        System.out.println(" Временная зона: " + currentTime.getTimeZone());
        System.out.println(" День недели: " + currentTime.getDayOfWeek());
        System.out.println(" Рабочий день: " + manager.isCurrentDayWorkDay());

        System.out.println("\n--- Рабочие настройки ---");
        System.out.println("Начало рабочего дня: " + manager.getWorkDayStart());
        System.out.println("Конец рабочего дня: " + manager.getWorkDayEnd());
        
        // This is the line you were missing!
        System.out.println("Рабочие дни: " + manager.getWorkDays());
    }
    // --- Scenario 2: Event Management ---
    public static void demoEventManagement() {
        System.out.println("\n=== SCENARIO 2: Event Management ===");
        TimeManager manager = new TimeManager();

        System.out.println("Scheduling events...");
        manager.scheduleEvent("Team Meeting", LocalDateTime.now().plusHours(2), ZoneId.of("Europe/Moscow"));
        manager.scheduleEvent("Client Call", LocalDateTime.now().plusDays(1).withHour(11).withMinute(0), ZoneId.of("Europe/Moscow"));
        manager.scheduleEvent("Global Sync", LocalDateTime.now().plusDays(2).withHour(16), ZoneId.of("UTC"));

        // FIX: Just "ScheduledEvent"
        List<ScheduledEvent> upcoming = manager.getUpcomingEvents(3);
        
        System.out.println("Upcoming Events (Next 3 Days):");
        for (ScheduledEvent event : upcoming) {
            System.out.println(" - " + event.name() + " | Time: " + event.eventTime() + 
                               " | Work Time: " + event.isWorkTime());
        }

        boolean hasConflict = manager.hasTimeConflicts(
            LocalDateTime.now().plusHours(3), 
            LocalDateTime.now().plusHours(4)
        );
        System.out.println("Has conflict with new event? " + hasConflict);
        System.out.println("--------------------------------------------------");
    }

    // --- Scenario 3: Time Calculations ---
    public static void demoTimeCalculations() {
        System.out.println("\n=== SCENARIO 3: Time Calculations ===");
        TimeManager manager = new TimeManager();

        LocalDateTime deadline = LocalDateTime.now().plusDays(5).plusHours(3);
        Duration remaining = manager.calculateTimeUntilDeadline(deadline);
        System.out.println("Time until deadline: " + remaining.toDays() + " days, " + remaining.toHoursPart() + " hours");

        LocalDateTime start = LocalDateTime.now().withHour(9).withMinute(0);
        LocalDateTime end = start.plusDays(3).withHour(18).withMinute(0); 
        
        // FIX: Just "WorkPeriod"
        Period period = manager.calculateWorkPeriod(start, end);
        
        System.out.println("Work Period Analysis:");
        System.out.println(" - Total Duration: " + period.totalDuration().toHours() + "h");
        System.out.println(" - Actual Work Time: " + period.workTimeDuration().toHours() + "h");
        System.out.println(" - Working Days: " + period.workDaysCount());
        System.out.println("--------------------------------------------------");
    }

    // --- Scenario 4: International Support ---
    public static void demoInternationalSupport() {
        System.out.println("\n=== SCENARIO 4: International Support ===");
        TimeManager manager = new TimeManager();

        ZonedDateTime meetingTime = ZonedDateTime.of(LocalDateTime.now().plusDays(1).withHour(15), ZoneId.of("Europe/Moscow"));
        List<String> offices = Arrays.asList("America/New_York", "Europe/London", "Asia/Tokyo");

        Map<String, ZonedDateTime> worldTimes = manager.getInternationalTime(meetingTime, offices);
        System.out.println("Meeting at 15:00 Moscow is:");
        for (Map.Entry<String, ZonedDateTime> entry : worldTimes.entrySet()) {
            System.out.println(" - " + entry.getKey() + ": " + entry.getValue().format(DateTimeFormatter.ofPattern("HH:mm")));
        }

        boolean looksGood = manager.isSuitableForAllTimeZones(meetingTime, offices);
        System.out.println("Is this time good for everyone? " + looksGood);
        System.out.println("--------------------------------------------------");
    }

    // --- Scenario 5: Formatting and Parsing ---
    public static void demoFormattingAndParsing() {
        System.out.println("\n=== SCENARIO 5: Formatting and Parsing ===");
        TimeManager manager = new TimeManager();
        LocalDateTime sample = LocalDateTime.of(2024, 12, 31, 23, 59);

        Map<String, String> formats = manager.getMultipleFormats(sample);
        System.out.println("Date Formats:");
        formats.forEach((key, value) -> System.out.println(" - " + key + ": " + value));

        String input = "31.12.2024 23:59:59";
        System.out.println("Parsing input: " + input);
        try {
            LocalDateTime parsed = manager.parseDateTime(input, "dd.MM.yyyy HH:mm:ss");
            System.out.println("Success! Parsed object: " + parsed);
        } catch (Exception e) {
            System.out.println("Parse failed: " + e.getMessage());
        }
        System.out.println("--------------------------------------------------");
    }

    // --- Scenario 6: Error Handling ---
    public static void demoErrorHandling() {
        System.out.println("\n=== SCENARIO 6: Error Handling ===");
        TimeManager manager = new TimeManager();

        System.out.println("Testing invalid time range (Start > End)...");
        try {
            manager.calculateWorkPeriod(LocalDateTime.now().plusDays(1), LocalDateTime.now());
        } catch (IllegalArgumentException e) {
            System.out.println("CAUGHT EXPECTED ERROR: " + e.getMessage());
        }
        System.out.println("--------------------------------------------------");
    }
    
}