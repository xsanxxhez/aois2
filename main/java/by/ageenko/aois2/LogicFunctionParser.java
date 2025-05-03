package by.ageenko.aois2;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogicFunctionParser {
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("[a-e]");

    public static Set<Character> extractVariables(String expression) {
        Set<Character> variables = new TreeSet<>();
        Matcher matcher = VARIABLE_PATTERN.matcher(expression);
        while (matcher.find()) {
            variables.add(matcher.group().charAt(0));
        }
        return variables;
    }

    public static String toJavaSyntax(String expression) {
        return expression.replace("&", "&&")
                .replace("|", "||")
                .replace("!", "!")
                .replace("->", "<=")
                .replace("~", "==");
    }
}