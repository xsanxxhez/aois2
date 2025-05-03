package by.ageenko.aois2;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class MainIntegrationTest {
    @Test
    void main_SimpleExpression() {
        String input = "a & b\nexit\n";  // Добавляем 'exit' для завершения программы
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Main.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("СДНФ"));
        assertTrue(output.contains("СКНФ"));
    }
}