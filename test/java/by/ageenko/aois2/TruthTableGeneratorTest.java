package by.ageenko.aois2;

import org.junit.jupiter.api.Test;
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
}