package by.ageenko.aois2;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class TruthTableGeneratorTest {

    @Test
    void generate_TwoVariables() {
        Set<Character> vars = Set.of('a', 'b');
        List<TruthTableRow> table = TruthTableGenerator.generate("a & b", vars);
        assertEquals(4, table.size());
        assertFalse(table.get(0).getResult()); // 0 & 0
        assertFalse(table.get(1).getResult()); // 0 & 1
        assertFalse(table.get(2).getResult()); // 1 & 0
        assertTrue(table.get(3).getResult());  // 1 & 1
    }

    @Test
    void generate_WithEquivalence() {
        Set<Character> vars = Set.of('a', 'b');
        List<TruthTableRow> table = TruthTableGenerator.generate("a ~ b", vars);
        assertEquals(4, table.size());
        assertTrue(table.get(0).getResult());  // 0 ~ 0 = 1
        assertFalse(table.get(1).getResult()); // 0 ~ 1 = 0
        assertFalse(table.get(2).getResult()); // 1 ~ 0 = 0
        assertTrue(table.get(3).getResult());  // 1 ~ 1 = 1
    }

    @Test
    void generate_WithImplication() {
        Set<Character> vars = Set.of('a', 'b');
        List<TruthTableRow> table = TruthTableGenerator.generate("a -> b", vars);
        assertEquals(4, table.size());
        assertTrue(table.get(0).getResult());  // 0 -> 0 = 1
        assertTrue(table.get(1).getResult());  // 0 -> 1 = 1
        assertFalse(table.get(2).getResult()); // 1 -> 0 = 0
        assertTrue(table.get(3).getResult());  // 1 -> 1 = 1
    }

    @Test
    void generate_WithNegation() {
        Set<Character> vars = Set.of('a');
        List<TruthTableRow> table = TruthTableGenerator.generate("!a", vars);
        assertEquals(2, table.size());
        assertTrue(table.get(0).getResult());  // !0 = 1
        assertFalse(table.get(1).getResult()); // !1 = 0
    }

    @Test
    void generate_WithComplexExpression() {
        Set<Character> vars = Set.of('a', 'b');
        List<TruthTableRow> table = TruthTableGenerator.generate("!(a & b) | (b -> a)", vars);
        assertEquals(4, table.size());
        assertTrue(table.get(0).getResult());  // !(0 & 0) | (0 -> 0) = 1
        assertTrue(table.get(1).getResult());  // !(0 & 1) | (1 -> 0) = 1
        assertTrue(table.get(2).getResult()); // !(1 & 0) | (0 -> 1) = 0
        assertTrue(table.get(3).getResult());  // !(1 & 1) | (1 -> 1) = 1
    }

    @Test
    void generate_WithParentheses() {
        Set<Character> vars = Set.of('a', 'b');
        List<TruthTableRow> table = TruthTableGenerator.generate("(a | b) & !(a & b)", vars);
        assertEquals(4, table.size());
        assertFalse(table.get(0).getResult()); // (0 | 0) & !(0 & 0) = 0
        assertTrue(table.get(1).getResult());  // (0 | 1) & !(0 & 1) = 1
        assertTrue(table.get(2).getResult());  // (1 | 0) & !(1 & 0) = 1
        assertFalse(table.get(3).getResult()); // (1 | 1) & !(1 & 1) = 0
    }

    @Test
    void printTruthTableTest() {
        // Проверка вывода таблицы истинности
        Set<Character> vars = Set.of('a', 'b');
        List<TruthTableRow> table = TruthTableGenerator.generate("a & b", vars);

        // Перенаправляем вывод в поток
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TruthTableGenerator.printTruthTable(table, vars);

        String output = outContent.toString();
        assertTrue(output.contains("Таблица истинности"));
        assertTrue(output.contains("|   a   |   b   | Результат |"));
        assertFalse(output.contains("|   0   |   0   |     0     |"));
        assertFalse(output.contains("|   1   |   1   |     1     |"));
    }
}