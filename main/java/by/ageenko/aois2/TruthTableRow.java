package by.ageenko.aois2;

import java.util.*;

public class TruthTableRow {
    private final Map<Character, Boolean> variableValues;
    private final boolean result;

    public TruthTableRow(Map<Character, Boolean> variableValues, boolean result) {
        this.variableValues = Map.copyOf(variableValues);
        this.result = result;
    }

    public boolean getResult() {
        return result;
    }

    public Map<Character, Boolean> getVariableValues() {
        return Map.copyOf(variableValues);
    }

    public int getNumericRepresentation(Set<Character> variables) {
        int num = 0;
        int shift = variables.size() - 1;
        List<Character> orderedVars = new ArrayList<>(variables);
        orderedVars.sort(Character::compareTo);

        for (char var : orderedVars) {
            if (variableValues.get(var)) {
                num |= (1 << shift);
            }
            shift--;
        }
        return num;
    }
}