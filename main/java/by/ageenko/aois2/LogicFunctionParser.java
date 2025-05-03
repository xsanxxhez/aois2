package by.ageenko.aois2;

import java.util.*;
import java.util.regex.*;

public class LogicFunctionParser {
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("[a-e]");
    private static final Pattern VALID_EXPRESSION = Pattern.compile(
            "^([a-e!()\\s]+([&|~]|->)[a-e!()\\s]+|[!()a-e\\s]+)$"
    );

    public static Set<Character> extractVariables(String expression) {
        validateExpression(expression);
        Set<Character> variables = new TreeSet<>();
        Matcher matcher = VARIABLE_PATTERN.matcher(expression);
        while (matcher.find()) {
            variables.add(matcher.group().charAt(0));
        }
        return variables;
    }

    public static String toJavaSyntax(String expression) {
        validateExpression(expression);
        return expression.replace("&", "&&")
                .replace("|", "||")
                .replace("!", "!")
                .replace("->", "<=")
                .replace("~", "==")
                .replaceAll("\\s+", "");
    }

    private static void validateExpression(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Пустое выражение");
        }

        // Проверка баланса скобок
        int balance = 0;
        for (char c : expression.toCharArray()) {
            if (c == '(') balance++;
            if (c == ')') balance--;
            if (balance < 0) break;
        }
        if (balance != 0) {
            throw new IllegalArgumentException("Несбалансированные скобки");
        }

        // Проверка допустимых символов
        if (!expression.matches("^[a-e&|!~()\\s->]+$")) {
            throw new IllegalArgumentException("Недопустимые символы в выражении");
        }

        // Проверка корректности операторов
        if (expression.matches(".*([&|~]|->){2,}.*") ||
                expression.matches("^[&|~>].*|.*[&|~<-]$")) {
            throw new IllegalArgumentException("Некорректное расположение операторов");
        }
    }
}