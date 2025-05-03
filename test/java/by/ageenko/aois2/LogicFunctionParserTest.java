package by.ageenko.aois2;

import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class LogicFunctionParserTest {
    @Test
    void extractVariables_SimpleExpression() {
        Set<Character> result = LogicFunctionParser.extractVariables("a & b");
        assertEquals(2, result.size());
        assertTrue(result.contains('a'));
        assertTrue(result.contains('b'));
    }

    @Test
    void extractVariables_ComplexExpression() {
        Set<Character> result = LogicFunctionParser.extractVariables("(a | !b) & (c -> d)");
        assertEquals(4, result.size());
        assertTrue(result.contains('a'));
        assertTrue(result.contains('b'));
        assertTrue(result.contains('c'));
        assertTrue(result.contains('d'));
    }

    @Test
    void toJavaSyntax_Conjunction() {
        assertEquals("a && b", LogicFunctionParser.toJavaSyntax("a & b"));
    }

    @Test
    void toJavaSyntax_Disjunction() {
        assertEquals("a || b", LogicFunctionParser.toJavaSyntax("a | b"));
    }

    @Test
    void toJavaSyntax_Implication() {
        assertEquals("a <= b", LogicFunctionParser.toJavaSyntax("a -> b"));
    }
}