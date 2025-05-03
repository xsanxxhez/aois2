package by.ageenko.aois2;

import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class TruthTableRowTest {
    @Test
    void getNumericRepresentation_TwoVariables() {
        TruthTableRow row = new TruthTableRow(Map.of('a', false, 'b', true), true);
        assertEquals(1, row.getNumericRepresentation(Set.of('a', 'b')));
    }

    @Test
    void getNumericRepresentation_ThreeVariables() {
        TruthTableRow row = new TruthTableRow(Map.of('a', true, 'b', false, 'c', true), true);
        assertEquals(5, row.getNumericRepresentation(Set.of('a', 'b', 'c')));
    }
}