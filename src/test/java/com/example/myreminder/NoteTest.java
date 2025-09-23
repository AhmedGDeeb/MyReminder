package com.example.myreminder;

import com.example.myreminder.models.Note;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/*
 * Unit tests for the Task model class
 */
public class NoteTest {

    private Note note;
    private final String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

    @Before
    public void setUp() {
        // Initialize a fresh note before each test
        note = new Note();
    }

    // ===== TEST CONSTRUCTORS =====

    @Test
    public void testDefaultConstructor() {
        assertNotNull("Note should not be null", note);
        assertNotNull("CreatedAt should be set", note.getCreatedAt());
        assertNotNull("UpdatedAt should be set", note.getUpdatedAt());
        assertEquals("CreatedAt and UpdatedAt should be equal initially",
                note.getCreatedAt(), note.getUpdatedAt());
    }

    @Test
    public void testParameterizedConstructor() {
        // Arrange
        String noteText = "This is a test note";
        String tag = "Test";

        // Act
        Note parameterizedNote = new Note(noteText, tag);

        // Assert
        assertEquals("Note text should match", noteText, parameterizedNote.getNoteText());
        assertEquals("Tag should match", tag, parameterizedNote.getTag());
        assertNotNull("CreatedAt should be set", parameterizedNote.getCreatedAt());
        assertNotNull("UpdatedAt should be set", parameterizedNote.getUpdatedAt());
        assertEquals("CreatedAt and UpdatedAt should be equal",
                parameterizedNote.getCreatedAt(), parameterizedNote.getUpdatedAt());
    }

    @Test
    public void testFullParameterizedConstructor() {
        // Arrange
        int id = 1;
        String noteText = "Meeting notes from today";
        String tag = "Work";
        String createdAt = "2024-10-25 09:00:00";
        String updatedAt = "2024-10-25 10:30:00";

        // Act
        Note fullNote = new Note(id, noteText, tag, createdAt, updatedAt);

        // Assert
        assertEquals("ID should match", id, fullNote.getId());
        assertEquals("Note text should match", noteText, fullNote.getNoteText());
        assertEquals("Tag should match", tag, fullNote.getTag());
        assertEquals("CreatedAt should match", createdAt, fullNote.getCreatedAt());
        assertEquals("UpdatedAt should match", updatedAt, fullNote.getUpdatedAt());
    }

    // ===== TEST GETTERS AND SETTERS =====

    @Test
    public void testIdGetterAndSetter() {
        // Arrange
        int expectedId = 10;

        // Act
        note.setId(expectedId);
        int actualId = note.getId();

        // Assert
        assertEquals("ID getter/setter should work correctly", expectedId, actualId);
    }

    @Test
    public void testNoteTextGetterAndSetter() throws Exception {
        // Arrange
        String expectedNoteText = "This is updated note text";
        String initialUpdatedAt = note.getUpdatedAt();

        // Small delay to ensure timestamp difference
        TimeUnit.MILLISECONDS.sleep(10001);

        // Act
        note.setNoteText(expectedNoteText);
        String actualNoteText = note.getNoteText();
        String newUpdatedAt = note.getUpdatedAt();

        // Assert
        assertEquals("Note text should match", expectedNoteText, actualNoteText);
        assertNotEquals("UpdatedAt should change after setting note text", initialUpdatedAt, newUpdatedAt);
        assertTrue("New UpdatedAt should be later than initial",
                isLaterTimestamp(newUpdatedAt, initialUpdatedAt));
    }

    @Test
    public void testTagGetterAndSetter() throws Exception {
        // Arrange
        String expectedTag = "Personal";
        String initialUpdatedAt = note.getUpdatedAt();

        // Small delay to ensure timestamp difference
        TimeUnit.MILLISECONDS.sleep(1001);

        // Act
        note.setTag(expectedTag);
        String actualTag = note.getTag();
        String newUpdatedAt = note.getUpdatedAt();

        // Assert
        assertEquals("Tag should match", expectedTag, actualTag);
        assertNotEquals("UpdatedAt should change after setting tag", initialUpdatedAt, newUpdatedAt);
        assertTrue("New UpdatedAt should be later than initial",
                isLaterTimestamp(newUpdatedAt, initialUpdatedAt));
    }

    @Test
    public void testCreatedAtGetterAndSetter() {
        // Arrange
        String expectedCreatedAt = "2024-10-25 08:00:00";

        // Act
        note.setCreatedAt(expectedCreatedAt);
        String actualCreatedAt = note.getCreatedAt();

        // Assert
        assertEquals("CreatedAt should match", expectedCreatedAt, actualCreatedAt);
    }

    @Test
    public void testUpdatedAtGetterAndSetter() {
        // Arrange
        String expectedUpdatedAt = "2024-10-25 11:00:00";

        // Act
        note.setUpdatedAt(expectedUpdatedAt);
        String actualUpdatedAt = note.getUpdatedAt();

        // Assert
        assertEquals("UpdatedAt should match", expectedUpdatedAt, actualUpdatedAt);
    }

    // ===== TEST HELPER METHODS =====

    @Test
    public void testGetFormattedCreatedAt() {
        // Arrange
        String validDate = "2024-10-25 14:30:00";
        note.setCreatedAt(validDate);

        // Act
        String formattedDate = note.getFormattedCreatedAt();

        // Assert
        assertNotNull("Formatted date should not be null", formattedDate);
        assertTrue("Formatted date should contain date components",
                formattedDate.matches(".*\\d{2}/\\d{2}/\\d{4}.*"));
    }

    @Test
    public void testGetFormattedCreatedAt_InvalidDate() {
        // Arrange
        String invalidDate = "invalid-date-format";
        note.setCreatedAt(invalidDate);

        // Act
        String formattedDate = note.getFormattedCreatedAt();

        // Assert
        assertEquals("Invalid date should return original string", invalidDate, formattedDate);
    }

    @Test
    public void testGetFormattedUpdatedAt() {
        // Arrange
        String validDate = "2024-10-25 16:45:00";
        note.setUpdatedAt(validDate);

        // Act
        String formattedDate = note.getFormattedUpdatedAt();

        // Assert
        assertNotNull("Formatted date should not be null", formattedDate);
        assertTrue("Formatted date should contain date components",
                formattedDate.matches(".*\\d{2}/\\d{2}/\\d{4}.*"));
    }

    @Test
    public void testHasTag_WithTag() {
        // Arrange
        note.setTag("Important");

        // Act
        boolean hasTag = note.hasTag();

        // Assert
        assertTrue("Note with tag should return true", hasTag);
    }

    @Test
    public void testHasTag_WithEmptyTag() {
        // Arrange
        note.setTag("");

        // Act
        boolean hasTag = note.hasTag();

        // Assert
        assertFalse("Note with empty tag should return false", hasTag);
    }

    @Test
    public void testHasTag_WithWhitespaceTag() {
        // Arrange
        note.setTag("   ");

        // Act
        boolean hasTag = note.hasTag();

        // Assert
        assertFalse("Note with whitespace tag should return false", hasTag);
    }

    @Test
    public void testHasTag_WithNullTag() {
        // Arrange
        note.setTag(null);

        // Act
        boolean hasTag = note.hasTag();

        // Assert
        assertFalse("Note with null tag should return false", hasTag);
    }

    @Test
    public void testGetPreviewText_ShortText() {
        // Arrange
        String shortText = "Short note";
        note.setNoteText(shortText);

        // Act
        String preview = note.getPreviewText();

        // Assert
        assertEquals("Short text should return as is", shortText, preview);
    }

    @Test
    public void testGetPreviewText_LongText() {
        // Arrange
        String longText = "This is a very long note text that exceeds the hundred character limit " +
                "and should be truncated with an ellipsis at the end of the preview.";
        note.setNoteText(longText);

        // Act
        String preview = note.getPreviewText();

        // Assert
        assertTrue("Preview should be truncated", preview.length() <= 103); // 100 + "..."
        assertTrue("Preview should end with ellipsis", preview.endsWith("..."));
        assertEquals("Preview should be first 100 characters + ...",
                longText.substring(0, 100) + "...", preview);
    }

    @Test
    public void testGetPreviewText_Exactly100Characters() {
        // Arrange
        String exactText = "A".repeat(100);
        note.setNoteText(exactText);

        // Act
        String preview = note.getPreviewText();

        // Assert
        assertEquals("Exactly 100 characters should not be truncated", exactText, preview);
    }

    @Test
    public void testGetPreviewText_101Characters() {
        // Arrange
        String text101 = "A".repeat(101);
        note.setNoteText(text101);

        // Act
        String preview = note.getPreviewText();

        // Assert
        assertEquals("101 characters should be truncated to 100 + ...",
                "A".repeat(100) + "...", preview);
    }

    @Test
    public void testGetPreviewText_NullText() {
        // Arrange
        note.setNoteText(null);

        // Act
        String preview = note.getPreviewText();

        // Assert
        assertEquals("Null text should return empty string", "", preview);
    }

    @Test
    public void testGetPreviewText_EmptyText() {
        // Arrange
        note.setNoteText("");

        // Act
        String preview = note.getPreviewText();

        // Assert
        assertEquals("Empty text should return empty string", "", preview);
    }

    @Test
    public void testToString() {
        // Arrange
        note.setId(5);
        note.setNoteText("This is a test note for toString method");
        note.setTag("Testing");
        note.setUpdatedAt("2024-10-25 12:00:00");

        // Act
        String toStringResult = note.toString();

        // Assert
        assertTrue("toString should contain ID", toStringResult.contains("id=5"));
        assertTrue("toString should contain preview text", toStringResult.contains("noteText='"));
        assertTrue("toString should contain tag", toStringResult.contains("tag='Testing'"));
        assertTrue("toString should contain formatted updatedAt", toStringResult.contains("updatedAt="));
    }

    // ===== TEST EDGE CASES AND BOUNDARY CONDITIONS =====

    @Test
    public void testNoteTextWithSpecialCharacters() {
        // Arrange
        String specialText = "Note with special chars: @#$%^&*()\nNew line\tTab";
        note.setNoteText(specialText);

        // Assert
        assertEquals("Special characters should be preserved", specialText, note.getNoteText());
    }

    @Test
    public void testVeryLongTag() {
        // Arrange
        String longTag = "A".repeat(1000);
        note.setTag(longTag);

        // Assert
        assertEquals("Very long tag should be handled", longTag, note.getTag());
    }

    @Test
    public void testTagWithSpecialCharacters() {
        // Arrange
        String specialTag = "Tag-With-Special_Chars@123";
        note.setTag(specialTag);

        // Assert
        assertEquals("Special characters in tag should be preserved", specialTag, note.getTag());
    }

    @Test
    public void testMultipleUpdates() throws Exception {
        // Arrange
        String initialText = "Initial text";
        String initialTag = "Initial";
        note.setNoteText(initialText);
        note.setTag(initialTag);

        String firstUpdatedAt = note.getUpdatedAt();
        TimeUnit.MILLISECONDS.sleep(10001);

        // Act - First update
        note.setNoteText("Updated text");
        String secondUpdatedAt = note.getUpdatedAt();
        TimeUnit.MILLISECONDS.sleep(10001);

        // Act - Second update
        note.setTag("Updated tag");
        String thirdUpdatedAt = note.getUpdatedAt();

        // Assert
        assertTrue("Second update should be later than first",
                isLaterTimestamp(secondUpdatedAt, firstUpdatedAt));
        assertTrue("Third update should be later than second",
                isLaterTimestamp(thirdUpdatedAt, secondUpdatedAt));
    }

    @Test
    public void testNoteEqualityByFields() {
        // Arrange
        Note note1 = new Note(1, "Same note", "Same tag", currentTime, currentTime);
        Note note2 = new Note(1, "Same note", "Same tag", currentTime, currentTime);

        // Test field by field equality
        assertEquals("IDs should be equal", note1.getId(), note2.getId());
        assertEquals("Note texts should be equal", note1.getNoteText(), note2.getNoteText());
        assertEquals("Tags should be equal", note1.getTag(), note2.getTag());
        assertEquals("CreatedAt should be equal", note1.getCreatedAt(), note2.getCreatedAt());
        assertEquals("UpdatedAt should be equal", note1.getUpdatedAt(), note2.getUpdatedAt());
    }

    @Test
    public void testNullHandling() {
        // Test setting null values
        note.setNoteText(null);
        note.setTag(null);
        note.setCreatedAt(null);
        note.setUpdatedAt(null);

        // Assert
        assertNull("Null note text should be handled", note.getNoteText());
        assertNull("Null tag should be handled", note.getTag());
        assertNull("Null createdAt should be handled", note.getCreatedAt());
        assertNull("Null updatedAt should be handled", note.getUpdatedAt());
    }

    // ===== HELPER METHOD FOR TIMESTAMP COMPARISON =====

    private boolean isLaterTimestamp(String later, String earlier) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date laterDate = sdf.parse(later);
            Date earlierDate = sdf.parse(earlier);
            return laterDate != null && earlierDate != null && laterDate.after(earlierDate);
        } catch (Exception e) {
            return false;
        }
    }
}