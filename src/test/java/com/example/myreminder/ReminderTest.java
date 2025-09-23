package com.example.myreminder;

import static org.junit.Assert.*;

import com.example.myreminder.models.Reminder;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/*
 * Unit tests for the Reminder model class
 */
public class ReminderTest {

    private Reminder reminder;
    private final String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

    @Before
    public void setUp() {
        // Initialize a fresh reminder before each test
        reminder = new Reminder();
    }

    // ===== TEST CONSTRUCTORS =====

    @Test
    public void testDefaultConstructor() {
        assertNotNull("Reminder should not be null", reminder);
        assertFalse("New reminder should not be triggered", reminder.isTriggered());
    }

    @Test
    public void testParameterizedConstructor() {
        // Arrange
        int taskId = 5;
        String reminderTime = "2024-10-27 14:30:00";

        // Act
        Reminder parameterizedReminder = new Reminder(taskId, reminderTime);

        // Assert
        assertEquals("Task ID should match", taskId, parameterizedReminder.getTaskId());
        assertEquals("Reminder time should match", reminderTime, parameterizedReminder.getReminderTime());
        assertFalse("New reminder should not be triggered", parameterizedReminder.isTriggered());
    }

    @Test
    public void testFullParameterizedConstructor() {
        // Arrange
        int id = 1;
        int taskId = 10;
        String reminderTime = "2024-10-28 09:00:00";
        boolean isTriggered = true;

        // Act
        Reminder fullReminder = new Reminder(id, taskId, reminderTime, isTriggered);

        // Assert
        assertEquals("ID should match", id, fullReminder.getId());
        assertEquals("Task ID should match", taskId, fullReminder.getTaskId());
        assertEquals("Reminder time should match", reminderTime, fullReminder.getReminderTime());
        assertTrue("Reminder should be triggered", fullReminder.isTriggered());
    }

    // ===== TEST GETTERS AND SETTERS =====

    @Test
    public void testIdGetterAndSetter() {
        // Arrange
        int expectedId = 15;

        // Act
        reminder.setId(expectedId);
        int actualId = reminder.getId();

        // Assert
        assertEquals("ID getter/setter should work correctly", expectedId, actualId);
    }

    @Test
    public void testTaskIdGetterAndSetter() {
        // Arrange
        int expectedTaskId = 25;

        // Act
        reminder.setTaskId(expectedTaskId);
        int actualTaskId = reminder.getTaskId();

        // Assert
        assertEquals("Task ID getter/setter should work correctly", expectedTaskId, actualTaskId);
    }

    @Test
    public void testReminderTimeGetterAndSetter() {
        // Arrange
        String expectedReminderTime = "2024-11-01 18:45:00";

        // Act
        reminder.setReminderTime(expectedReminderTime);
        String actualReminderTime = reminder.getReminderTime();

        // Assert
        assertEquals("Reminder time getter/setter should work correctly", expectedReminderTime, actualReminderTime);
    }

    @Test
    public void testIsTriggeredGetterAndSetter() {
        // Act & Assert - Test false
        reminder.setTriggered(false);
        assertFalse("isTriggered should be false", reminder.isTriggered());

        // Act & Assert - Test true
        reminder.setTriggered(true);
        assertTrue("isTriggered should be true", reminder.isTriggered());
    }

    // ===== TEST HELPER METHODS =====

    @Test
    public void testGetFormattedReminderTime_ValidDate() {
        // Arrange
        String validDate = "2024-10-27 14:30:00";
        reminder.setReminderTime(validDate);

        // Act
        String formattedDate = reminder.getFormattedReminderTime();

        // Assert
        assertNotNull("Formatted date should not be null", formattedDate);
        assertTrue("Formatted date should contain readable format",
                formattedDate.matches(".*\\d{2}.*\\d{4}.*\\d{2}:\\d{2}.*"));
        assertNotEquals("Formatted date should differ from original", validDate, formattedDate);
    }

    @Test
    public void testGetFormattedReminderTime_InvalidDate() {
        // Arrange
        String invalidDate = "invalid-date-format";
        reminder.setReminderTime(invalidDate);

        // Act
        String formattedDate = reminder.getFormattedReminderTime();

        // Assert
        assertEquals("Invalid date should return original string", invalidDate, formattedDate);
    }

    @Test
    public void testGetFormattedReminderTime_NullDate() {
        // Arrange
        reminder.setReminderTime(null);

        // Act
        String formattedDate = reminder.getFormattedReminderTime();

        // Assert
        assertNull("Null date should return null", formattedDate);
    }

    @Test
    public void testGetFormattedReminderTime_EmptyDate() {
        // Arrange
        reminder.setReminderTime("");

        // Act
        String formattedDate = reminder.getFormattedReminderTime();

        // Assert
        assertEquals("Empty date should return empty string", "", formattedDate);
    }

    @Test
    public void testIsDue_FutureDateNotTriggered() {
        // Arrange - Future date, not triggered
        String futureDate = "2025-12-31 23:59:59";
        reminder.setReminderTime(futureDate);
        reminder.setTriggered(false);

        // Act
        boolean isDue = reminder.isDue();

        // Assert
        assertFalse("Future reminder should not be due", isDue);
    }

    @Test
    public void testIsDue_PastDateNotTriggered() {
        // Arrange - Past date, not triggered
        String pastDate = "2020-01-01 00:00:00";
        reminder.setReminderTime(pastDate);
        reminder.setTriggered(false);

        // Act
        boolean isDue = reminder.isDue();

        // Assert
        assertTrue("Past reminder should be due", isDue);
    }

    @Test
    public void testIsDue_PastDateButTriggered() {
        // Arrange - Past date, but already triggered
        String pastDate = "2020-01-01 00:00:00";
        reminder.setReminderTime(pastDate);
        reminder.setTriggered(true);

        // Act
        boolean isDue = reminder.isDue();

        // Assert
        assertFalse("Triggered reminder should not be due even if past", isDue);
    }

    @Test
    public void testIsDue_CurrentDate() throws Exception {
        // Arrange - Current time
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        reminder.setReminderTime(currentDate);
        reminder.setTriggered(false);

        // Small delay to ensure current time passes reminder time
        TimeUnit.MILLISECONDS.sleep(100);

        // Act
        boolean isDue = reminder.isDue();

        // Assert
        assertTrue("Current time reminder should be due", isDue);
    }

    @Test
    public void testIsDue_NullReminderTime() {
        // Arrange
        reminder.setReminderTime(null);
        reminder.setTriggered(false);

        // Act
        boolean isDue = reminder.isDue();

        // Assert
        assertFalse("Null reminder time should not be due", isDue);
    }

    @Test
    public void testIsDue_EmptyReminderTime() {
        // Arrange
        reminder.setReminderTime("");
        reminder.setTriggered(false);

        // Act
        boolean isDue = reminder.isDue();

        // Assert
        assertFalse("Empty reminder time should not be due", isDue);
    }

    @Test
    public void testIsDue_InvalidDateFormat() {
        // Arrange
        reminder.setReminderTime("invalid-date");
        reminder.setTriggered(false);

        // Act
        boolean isDue = reminder.isDue();

        // Assert
        assertFalse("Invalid date format should not be due", isDue);
    }

    @Test
    public void testGetTimeInMillis_ValidDate() {
        // Arrange
        String validDate = "2024-10-27 14:30:00";
        reminder.setReminderTime(validDate);

        // Act
        long timeInMillis = reminder.getTimeInMillis();

        // Assert
        assertTrue("Time in millis should be positive for valid date", timeInMillis > 0);
    }

    @Test
    public void testGetTimeInMillis_InvalidDate() {
        // Arrange
        String invalidDate = "invalid-date";
        reminder.setReminderTime(invalidDate);

        // Act
        long timeInMillis = reminder.getTimeInMillis();

        // Assert
        assertEquals("Invalid date should return 0", 0, timeInMillis);
    }

    @Test
    public void testGetTimeInMillis_NullDate() {
        // Arrange
        reminder.setReminderTime(null);

        // Act
        long timeInMillis = reminder.getTimeInMillis();

        // Assert
        assertEquals("Null date should return 0", 0, timeInMillis);
    }

    @Test
    public void testGetStatusText_NotTriggered() {
        // Arrange
        reminder.setTriggered(false);

        // Act
        String statusText = reminder.getStatusText();

        // Assert
        assertEquals("Not triggered reminder should show 'في انتظار التنبيه'",
                "في انتظار التنبيه", statusText);
    }

    @Test
    public void testGetStatusText_Triggered() {
        // Arrange
        reminder.setTriggered(true);

        // Act
        String statusText = reminder.getStatusText();

        // Assert
        assertEquals("Triggered reminder should show 'تم التنبيه'",
                "تم التنبيه", statusText);
    }

    @Test
    public void testToString() {
        // Arrange
        reminder.setId(3);
        reminder.setTaskId(7);
        reminder.setReminderTime("2024-10-27 14:30:00");
        reminder.setTriggered(false);

        // Act
        String toStringResult = reminder.toString();

        // Assert
        assertTrue("toString should contain ID", toStringResult.contains("id=3"));
        assertTrue("toString should contain task ID", toStringResult.contains("taskId=7"));
        assertTrue("toString should contain formatted reminder time", toStringResult.contains("reminderTime="));
        assertTrue("toString should contain triggered status", toStringResult.contains("isTriggered=false"));
    }

    @Test
    public void testToString_WithFormattedTime() {
        // Arrange
        reminder.setReminderTime("2024-10-27 14:30:00");

        // Act
        String toStringResult = reminder.toString();

        // Assert
        // Should contain the formatted time, not the raw time
        assertFalse("toString should not contain raw time format",
                toStringResult.contains("2024-10-27 14:30:00"));
        assertTrue("toString should contain formatted time",
                toStringResult.contains("reminderTime='"));
    }

    // ===== TEST EDGE CASES AND BOUNDARY CONDITIONS =====

    @Test
    public void testVeryFarFutureDate() {
        // Arrange - Very far future date
        String farFuture = "2030-12-31 23:59:59";
        reminder.setReminderTime(farFuture);
        reminder.setTriggered(false);

        // Act
        boolean isDue = reminder.isDue();
        long timeInMillis = reminder.getTimeInMillis();

        // Assert
        assertFalse("Very far future date should not be due", isDue);
        assertTrue("Time in millis should be large positive number", timeInMillis > 0);
    }

    @Test
    public void testVeryOldDate() {
        // Arrange - Very old date
        String oldDate = "2000-01-01 00:00:00";
        reminder.setReminderTime(oldDate);
        reminder.setTriggered(false);

        // Act
        boolean isDue = reminder.isDue();
        long timeInMillis = reminder.getTimeInMillis();

        // Assert
        assertTrue("Very old date should be due", isDue);
        assertTrue("Time in millis should be positive", timeInMillis > 0);
    }

    @Test
    public void testDateAtMidnight() {
        // Arrange
        String midnight = "2024-10-27 00:00:00";
        reminder.setReminderTime(midnight);
        reminder.setTriggered(false);

        // Act
        String formatted = reminder.getFormattedReminderTime();
        long timeInMillis = reminder.getTimeInMillis();

        // Assert
        assertNotNull("Midnight date should be formatted", formatted);
        assertTrue("Time in millis should be positive", timeInMillis > 0);
    }

    @Test
    public void testDateAtNoon() {
        // Arrange
        String noon = "2024-10-27 12:00:00";
        reminder.setReminderTime(noon);
        reminder.setTriggered(false);

        // Act
        String formatted = reminder.getFormattedReminderTime();
        long timeInMillis = reminder.getTimeInMillis();

        // Assert
        assertNotNull("Noon date should be formatted", formatted);
        assertTrue("Time in millis should be positive", timeInMillis > 0);
        assertTrue("Formatted time should contain 12:00", formatted.contains("12:00"));
    }

    @Test
    public void testLeapYearDate() {
        // Arrange - February 29th in leap year
        String leapYearDate = "2024-02-29 14:30:00";
        reminder.setReminderTime(leapYearDate);
        reminder.setTriggered(false);

        // Act
        boolean isDue = reminder.isDue();
        String formatted = reminder.getFormattedReminderTime();

        // Assert
        assertNotNull("Leap year date should be formatted", formatted);
        assertTrue("Leap year date in past should be due", isDue); // Depends on current date
    }

    @Test
    public void testNegativeTaskId() {
        // Arrange
        int negativeTaskId = -1;
        reminder.setTaskId(negativeTaskId);

        // Assert
        assertEquals("Negative task ID should be handled", negativeTaskId, reminder.getTaskId());
    }

    @Test
    public void testZeroTaskId() {
        // Arrange
        int zeroTaskId = 0;
        reminder.setTaskId(zeroTaskId);

        // Assert
        assertEquals("Zero task ID should be handled", zeroTaskId, reminder.getTaskId());
    }

    @Test
    public void testLargeTaskId() {
        // Arrange
        int largeTaskId = Integer.MAX_VALUE;
        reminder.setTaskId(largeTaskId);

        // Assert
        assertEquals("Large task ID should be handled", largeTaskId, reminder.getTaskId());
    }

    @Test
    public void testReminderEqualityByFields() {
        // Arrange
        Reminder reminder1 = new Reminder(1, 5, "2024-10-27 14:30:00", false);
        Reminder reminder2 = new Reminder(1, 5, "2024-10-27 14:30:00", false);

        // Test field by field equality
        assertEquals("IDs should be equal", reminder1.getId(), reminder2.getId());
        assertEquals("Task IDs should be equal", reminder1.getTaskId(), reminder2.getTaskId());
        assertEquals("Reminder times should be equal", reminder1.getReminderTime(), reminder2.getReminderTime());
        assertEquals("Triggered status should be equal", reminder1.isTriggered(), reminder2.isTriggered());
    }

    @Test
    public void testMultipleStateChanges() {
        // Test multiple state transitions
        reminder.setTriggered(true);
        assertTrue("Should be triggered", reminder.isTriggered());

        reminder.setTriggered(false);
        assertFalse("Should not be triggered", reminder.isTriggered());

        reminder.setTriggered(true);
        assertTrue("Should be triggered again", reminder.isTriggered());
    }

    @Test
    public void testNullHandling() {
        // Test setting null values
        reminder.setReminderTime(null);

        // Assert
        assertNull("Null reminder time should be handled", reminder.getReminderTime());
        assertEquals("Null reminder time should return 0 millis", 0, reminder.getTimeInMillis());
        assertFalse("Null reminder time should not be due", reminder.isDue());
    }

    @Test
    public void testEmptyStringHandling() {
        // Test setting empty strings
        reminder.setReminderTime("");

        // Assert
        assertEquals("Empty reminder time should be handled", "", reminder.getReminderTime());
        assertEquals("Empty reminder time should return 0 millis", 0, reminder.getTimeInMillis());
        assertFalse("Empty reminder time should not be due", reminder.isDue());
    }
}