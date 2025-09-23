package com.example.myreminder;

import com.example.myreminder.models.Task;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*
 * Unit tests for the Task model class
 */
public class TaskTest {

    private Task task;
    private final String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

    @Before
    public void setUp() {
        // Initialize a fresh task before each test
        task = new Task();
    }

    // ===== TEST CONSTRUCTORS =====

    @Test
    public void testDefaultConstructor() {
        assertNotNull("Task should not be null", task);
        assertFalse("New task should not be done", task.isDone());
        assertNotNull("CreatedAt should be set", task.getCreatedAt());
    }

    @Test
    public void testParameterizedConstructor() {
        // Arrange
        String title = "Test Task";
        String description = "Test Description";
        int priority = 2;
        String dueDate = "2024-12-31 23:59:59";

        // Act
        Task parameterizedTask = new Task(title, description, priority, dueDate);

        // Assert
        assertEquals("Title should match", title, parameterizedTask.getTitle());
        assertEquals("Description should match", description, parameterizedTask.getDescription());
        assertEquals("Priority should match", priority, parameterizedTask.getPriority());
        assertEquals("Due date should match", dueDate, parameterizedTask.getDueDate());
        assertFalse("New task should not be done", parameterizedTask.isDone());
        assertNotNull("CreatedAt should be set", parameterizedTask.getCreatedAt());
    }

    @Test
    public void testFullParameterizedConstructor() {
        // Arrange
        int id = 1;
        String title = "Complete Report";
        String description = "Finish the project report";
        int priority = 3;
        String dueDate = "2024-10-27 15:00:00";
        boolean isDone = true;
        String createdAt = "2024-10-25 10:00:00";

        // Act
        Task fullTask = new Task(id, title, description, priority, dueDate, isDone, createdAt);

        // Assert
        assertEquals("ID should match", id, fullTask.getId());
        assertEquals("Title should match", title, fullTask.getTitle());
        assertEquals("Description should match", description, fullTask.getDescription());
        assertEquals("Priority should match", priority, fullTask.getPriority());
        assertEquals("Due date should match", dueDate, fullTask.getDueDate());
        assertTrue("Task should be done", fullTask.isDone());
        assertEquals("CreatedAt should match", createdAt, fullTask.getCreatedAt());
    }

    // ===== TEST GETTERS AND SETTERS =====

    @Test
    public void testIdGetterAndSetter() {
        // Arrange
        int expectedId = 5;

        // Act
        task.setId(expectedId);
        int actualId = task.getId();

        // Assert
        assertEquals("ID getter/setter should work correctly", expectedId, actualId);
    }

    @Test
    public void testTitleGetterAndSetter() {
        // Arrange
        String expectedTitle = "New Task Title";

        // Act
        task.setTitle(expectedTitle);
        String actualTitle = task.getTitle();

        // Assert
        assertEquals("Title getter/setter should work correctly", expectedTitle, actualTitle);
    }

    @Test
    public void testDescriptionGetterAndSetter() {
        // Arrange
        String expectedDescription = "This is a detailed description of the task";

        // Act
        task.setDescription(expectedDescription);
        String actualDescription = task.getDescription();

        // Assert
        assertEquals("Description getter/setter should work correctly", expectedDescription, actualDescription);
    }

    @Test
    public void testPriorityGetterAndSetter() {
        // Arrange
        int expectedPriority = 3;

        // Act
        task.setPriority(expectedPriority);
        int actualPriority = task.getPriority();

        // Assert
        assertEquals("Priority getter/setter should work correctly", expectedPriority, actualPriority);
    }

    @Test
    public void testDueDateGetterAndSetter() {
        // Arrange
        String expectedDueDate = "2024-11-15 14:30:00";

        // Act
        task.setDueDate(expectedDueDate);
        String actualDueDate = task.getDueDate();

        // Assert
        assertEquals("DueDate getter/setter should work correctly", expectedDueDate, actualDueDate);
    }

    @Test
    public void testIsDoneGetterAndSetter() {
        // Act & Assert - Test false
        task.setDone(false);
        assertFalse("isDone should be false", task.isDone());

        // Act & Assert - Test true
        task.setDone(true);
        assertTrue("isDone should be true", task.isDone());
    }

    @Test
    public void testCreatedAtGetterAndSetter() {
        // Arrange
        String expectedCreatedAt = "2024-10-25 09:00:00";

        // Act
        task.setCreatedAt(expectedCreatedAt);
        String actualCreatedAt = task.getCreatedAt();

        // Assert
        assertEquals("CreatedAt getter/setter should work correctly", expectedCreatedAt, actualCreatedAt);
    }

    // ===== TEST HELPER METHODS =====

    @Test
    public void testGetPriorityText_NormalPriority() {
        // Arrange
        task.setPriority(1);

        // Act
        String priorityText = task.getPriorityText();

        // Assert
        assertEquals("Priority text should be 'عادي' for priority 1", "عادي", priorityText);
    }

    @Test
    public void testGetPriorityText_ImportantPriority() {
        // Arrange
        task.setPriority(2);

        // Act
        String priorityText = task.getPriorityText();

        // Assert
        assertEquals("Priority text should be 'مهم' for priority 2", "مهم", priorityText);
    }

    @Test
    public void testGetPriorityText_UrgentPriority() {
        // Arrange
        task.setPriority(3);

        // Act
        String priorityText = task.getPriorityText();

        // Assert
        assertEquals("Priority text should be 'عاجل' for priority 3", "عاجل", priorityText);
    }

    @Test
    public void testGetPriorityText_InvalidPriority() {
        // Arrange
        task.setPriority(5); // Invalid priority

        // Act
        String priorityText = task.getPriorityText();

        // Assert
        assertEquals("Priority text should be 'غير محدد' for invalid priority", "غير محدد", priorityText);
    }

    @Test
    public void testGetPriorityColor_NormalPriority() {
        // Arrange
        task.setPriority(1);

        // Act
        int colorResId = task.getPriorityColor();

        // Assert
        assertEquals("Priority color should be for normal priority",
                1, colorResId);
    }

    @Test
    public void testGetPriorityColor_ImportantPriority() {
        // Arrange
        task.setPriority(2);

        // Act
        int colorResId = task.getPriorityColor();

        // Assert
        assertEquals("Priority color should be for important priority",
                2, colorResId);
    }

    @Test
    public void testGetPriorityColor_UrgentPriority() {
        // Arrange
        task.setPriority(3);

        // Act
        int colorResId = task.getPriorityColor();

        // Assert
        assertEquals("Priority color should be for urgent priority",
                3, colorResId);
    }

    @Test
    public void testIsOverdue_NotOverdue() {
        // Arrange - Future date
        String futureDate = "2025-12-31 23:59:59";
        task.setDueDate(futureDate);
        task.setDone(false);

        // Act
        boolean isOverdue = task.isOverdue();

        // Assert
        assertFalse("Future task should not be overdue", isOverdue);
    }

    @Test
    public void testIsOverdue_Overdue() {
        // Arrange - Past date
        String pastDate = "2020-01-01 00:00:00";
        task.setDueDate(pastDate);
        task.setDone(false);

        // Act
        boolean isOverdue = task.isOverdue();

        // Assert
        assertTrue("Past due task should be overdue", isOverdue);
    }

    @Test
    public void testIsOverdue_CompletedTask() {
        // Arrange - Past date but completed
        String pastDate = "2020-01-01 00:00:00";
        task.setDueDate(pastDate);
        task.setDone(true);

        // Act
        boolean isOverdue = task.isOverdue();

        // Assert
        assertFalse("Completed task should not be overdue even if past due", isOverdue);
    }

    @Test
    public void testIsOverdue_NullDueDate() {
        // Arrange
        task.setDueDate(null);
        task.setDone(false);

        // Act
        boolean isOverdue = task.isOverdue();

        // Assert
        assertFalse("Task with null due date should not be overdue", isOverdue);
    }

    @Test
    public void testIsOverdue_EmptyDueDate() {
        // Arrange
        task.setDueDate("");
        task.setDone(false);

        // Act
        boolean isOverdue = task.isOverdue();

        // Assert
        assertFalse("Task with empty due date should not be overdue", isOverdue);
    }

    @Test
    public void testIsOverdue_InvalidDateFormat() {
        // Arrange
        task.setDueDate("invalid-date-format");
        task.setDone(false);

        // Act
        boolean isOverdue = task.isOverdue();

        // Assert
        assertFalse("Task with invalid date format should not be overdue", isOverdue);
    }

    @Test
    public void testGetStatusText_PendingTask() {
        // Arrange
        task.setDone(false);

        // Act
        String statusText = task.getStatusText();

        // Assert
        assertEquals("Pending task should show 'قيد الانتظار'", "قيد الانتظار", statusText);
    }

    @Test
    public void testGetStatusText_CompletedTask() {
        // Arrange
        task.setDone(true);

        // Act
        String statusText = task.getStatusText();

        // Assert
        assertEquals("Completed task should show 'منجزة'", "منجزة", statusText);
    }

    @Test
    public void testToString() {
        // Arrange
        task.setId(1);
        task.setTitle("Test Task");
        task.setPriority(2);
        task.setDone(false);

        // Act
        String toStringResult = task.toString();

        // Assert
        assertTrue("toString should contain task ID", toStringResult.contains("id=1"));
        assertTrue("toString should contain task title", toStringResult.contains("title='Test Task'"));
        assertTrue("toString should contain priority text", toStringResult.contains("priority=مهم"));
        assertTrue("toString should contain done status", toStringResult.contains("isDone=false"));
    }

    @Test
    public void testEdgeCases() {
        // Test empty strings
        task.setTitle("");
        task.setDescription("");
        assertEquals("Empty title should be handled", "", task.getTitle());
        assertEquals("Empty description should be handled", "", task.getDescription());

        // Test null values
        task.setTitle(null);
        task.setDescription(null);
        assertNull("Null title should be handled", task.getTitle());
        assertNull("Null description should be handled", task.getDescription());

        // Test priority boundaries
        task.setPriority(0);
        assertEquals("Priority 0 should be handled", 0, task.getPriority());

        task.setPriority(100);
        assertEquals("Priority 100 should be handled", 100, task.getPriority());
    }

    @Test
    public void testTaskEquality() {
        // Arrange
        Task task1 = new Task(1, "Same Task", "Description", 1, "2024-12-31 23:59:59", false, currentTime);
        Task task2 = new Task(1, "Same Task", "Description", 1, "2024-12-31 23:59:59", false, currentTime);

        // Note: Since we don't have equals() method, we test field by field
        assertEquals("IDs should be equal", task1.getId(), task2.getId());
        assertEquals("Titles should be equal", task1.getTitle(), task2.getTitle());
        assertEquals("Priorities should be equal", task1.getPriority(), task2.getPriority());
    }
}