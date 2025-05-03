package by.ageenko.aois2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TruthTableGenerator {
    public static List<TruthTableRow> generate(String expression, Set<Character> variables) {
        List<TruthTableRow> table = new ArrayList<>();
        int varCount = variables.size();
        List<Character> varList = new ArrayList<>(variables);
        varList.sort(Character::compareTo);

        for (int i = 0; i < (1 << varCount); i++) {
            Map<Character, Boolean> values = new HashMap<>();
            for (int j = 0; j < varCount; j++) {
                values.put(varList.get(j), (i & (1 << (varCount - 1 - j))) != 0);
            }
            boolean result = evaluate(expression, values);
            table.add(new TruthTableRow(values, result));
        }

        return table;
    }


    private static boolean evaluate(String expression, Map<Character, Boolean> values) {
        String expr = expression.replace(" ", "")
                .replace("&&", "&")
                .replace("||", "|")
                .replace("==", "~")
                .replace("<=", "->");

        for (Map.Entry<Character, Boolean> entry : values.entrySet()) {
            expr = expr.replace(entry.getKey().toString(),
                    entry.getValue() ? "1" : "0");
        }

        // Обработка сложных отрицаний
        expr = expr.replace("!1", "0")
                .replace("!0", "1");

        // Обработка скобок
        while (expr.contains("(")) {
            expr = processParentheses(expr);
        }

        return evaluateSimple(expr);
    }

    private static String processParentheses(String expr) {
        // Используем Matcher для обработки скобок
        Pattern pattern = Pattern.compile("\\(([^()]+)\\)");
        Matcher matcher = pattern.matcher(expr);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String innerExpr = matcher.group(1);
            boolean evalResult = evaluateSimple(innerExpr);
            matcher.appendReplacement(result, evalResult ? "1" : "0");
        }
        matcher.appendTail(result);

        return result.toString();
    }

    private static boolean evaluateSimple(String expr) {
        // Упрощаем выражение шаг за шагом
        String prev;
        do {
            prev = expr;
            expr = expr.replace("!1", "0")
                    .replace("!0", "1")
                    .replace("1&1", "1").replace("1&0", "0")
                    .replace("0&1", "0").replace("0&0", "0")
                    .replace("1|1", "1").replace("1|0", "1")
                    .replace("0|1", "1").replace("0|0", "0")
                    .replace("1->1", "1").replace("1->0", "0")
                    .replace("0->1", "1").replace("0->0", "1")
                    .replace("1~1", "1").replace("1~0", "0")
                    .replace("0~1", "0").replace("0~0", "1");
        } while (!expr.equals(prev));

        return expr.equals("1");
    }


    private static boolean evaluateBooleanExpression(String expr) {
        expr = expr.replace("!1", "0")
                .replace("!0", "1")
                .replace("1&1", "1").replace("1&0", "0")
                .replace("0&1", "0").replace("0&0", "0")
                .replace("1|1", "1").replace("1|0", "1")
                .replace("0|1", "1").replace("0|0", "0")
                .replace("1->1", "1").replace("1->0", "0")
                .replace("0->1", "1").replace("0->0", "1")
                .replace("1~1", "1").replace("1~0", "0")
                .replace("0~1", "0").replace("0~0", "1");

        if (expr.equals("1")) return true;
        if (expr.equals("0")) return false;

        // Если выражение не упростилось до одного символа, рекурсивно продолжаем
        return evaluateBooleanExpression(expr);
    }
    public static void printTruthTable(List<TruthTableRow> table, Set<Character> variables) {
        // Сортируем переменные для единообразия
        List<Character> sortedVars = new ArrayList<>(variables);
        sortedVars.sort(Character::compareTo);

        // Шапка таблицы
        System.out.println("\nТаблица истинности:");
        System.out.println("+" + "-".repeat(8 * (sortedVars.size() + 1)) + "+");

        // Заголовки столбцов
        System.out.print("|");
        for (char var : sortedVars) {
            System.out.printf("   %c   |", var);
        }
        System.out.println(" Результат |");
        System.out.println("+" + "-".repeat(8 * (sortedVars.size() + 1)) + "+");

        // Строки таблицы
        for (TruthTableRow row : table) {
            System.out.print("|");
            for (char var : sortedVars) {
                System.out.printf("   %d   |", row.getVariableValues().get(var) ? 1 : 0);
            }
            System.out.printf("   %d   |\n", row.getResult() ? 1 : 0);
        }
        System.out.println("+" + "-".repeat(8 * (sortedVars.size() + 1)) + "+");
    }
}