package by.ageenko.aois2;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class NormalFormsBuilderTest {
    @Test
    void buildSDNF_Conjunction() {
        Set<Character> vars = Set.of('a', 'b');
        List<TruthTableRow> table = List.of(
                new TruthTableRow(Map.of('a', false, 'b', false), false),
                new TruthTableRow(Map.of('a', false, 'b', true), false),
                new TruthTableRow(Map.of('a', true, 'b', false), false),
                new TruthTableRow(Map.of('a', true, 'b', true), true)
        );
        assertEquals("(a∧b)", NormalFormsBuilder.buildSDNF(table, vars));
    }

    @Test
    void buildSKNF_Disjunction() {
        Set<Character> vars = Set.of('a', 'b');
        List<TruthTableRow> table = List.of(
                new TruthTableRow(Map.of('a', false, 'b', false), false),
                new TruthTableRow(Map.of('a', false, 'b', true), true),
                new TruthTableRow(Map.of('a', true, 'b', false), true),
                new TruthTableRow(Map.of('a', true, 'b', true), true)
        );
        assertEquals("(a∨b)", NormalFormsBuilder.buildSKNF(table, vars));
    }

    @Test
    void buildNumericForm_SKNF() {
        Set<Character> vars = Set.of('a', 'b');
        List<TruthTableRow> table = List.of(
                new TruthTableRow(Map.of('a', false, 'b', false), true),
                new TruthTableRow(Map.of('a', false, 'b', true), false),
                new TruthTableRow(Map.of('a', true, 'b', false), false),
                new TruthTableRow(Map.of('a', true, 'b', true), true)
        );
        assertEquals("1, 2", NormalFormsBuilder.buildNumericForm(table, false));
    }
}