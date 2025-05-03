package by.ageenko.aois2;

import java.util.*;
import java.util.stream.Collectors;

public class NormalFormsBuilder {
    public static String buildSDNF(List<TruthTableRow> table, Set<Character> variables) {
        List<String> terms = table.stream()
                .filter(TruthTableRow::getResult)
                .map(row -> buildTerm(row, variables, true))
                .collect(Collectors.toList());
        return String.join(" ∨ ", terms);
    }

    public static String buildSKNF(List<TruthTableRow> table, Set<Character> variables) {
        List<String> terms = table.stream()
                .filter(row -> !row.getResult())
                .map(row -> buildTerm(row, variables, false))
                .collect(Collectors.toList());
        return String.join(" ∧ ", terms);
    }

    private static String buildTerm(TruthTableRow row, Set<Character> variables, boolean forSDNF) {
        List<String> literals = new ArrayList<>();
        List<Character> orderedVars = new ArrayList<>(variables);
        orderedVars.sort(Character::compareTo);

        for (char var : orderedVars) {
            boolean value = row.getVariableValues().get(var);
            if (forSDNF) {
                literals.add(value ? String.valueOf(var) : "¬" + var);
            } else {
                literals.add(value ? "¬" + var : String.valueOf(var));
            }
        }
        return "(" + String.join(forSDNF ? "∧" : "∨", literals) + ")";
    }

    public static String buildNumericForm(List<TruthTableRow> table, boolean forSDNF) {
        List<Integer> numbers = table.stream()
                .filter(row -> forSDNF ? row.getResult() : !row.getResult())
                .map(row -> row.getNumericRepresentation(row.getVariableValues().keySet()))
                .sorted()
                .collect(Collectors.toList());
        return numbers.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    public static String buildIndexForm(List<TruthTableRow> table) {
        StringBuilder binary = new StringBuilder();
        for (TruthTableRow row : table) {
            binary.append(row.getResult() ? "1" : "0");
        }
        int decimal = Integer.parseInt(binary.toString(), 2);
        return decimal + " - " + binary;
    }
}