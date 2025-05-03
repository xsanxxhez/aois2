package by.ageenko.aois2;

import java.util.Scanner;
import java.util.Set;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Введите логическую функцию (или 'exit' для выхода): ");
                String function = scanner.nextLine().trim();

                if (function.equalsIgnoreCase("exit")) {
                    break;
                }

                Set<Character> variables = LogicFunctionParser.extractVariables(function);
                List<TruthTableRow> table = TruthTableGenerator.generate(function, variables);

                TruthTableGenerator.printTruthTable(table, variables);

                System.out.println("\nСовершенная дизъюнктивная нормальная форма (СДНФ):");
                System.out.println(NormalFormsBuilder.buildSDNF(table, variables));

                System.out.println("\nСовершенная конъюнктивная нормальная форма (СКНФ):");
                System.out.println(NormalFormsBuilder.buildSKNF(table, variables));

                System.out.println("\nЧисловые формы:");
                System.out.println(NormalFormsBuilder.buildNumericForm(table, true) + " - СДНФ");
                System.out.println(NormalFormsBuilder.buildNumericForm(table, false) + " - СКНФ");

                System.out.println("\nИндексная форма:");
                System.out.println(NormalFormsBuilder.buildIndexForm(table));

            } catch (IllegalArgumentException e) {
                System.out.println("\nОшибка: " + e.getMessage());
                System.out.println("Допустимые форматы ввода:");
                System.out.println("- Простые выражения: a, b, !c");
                System.out.println("- Логические операции: a & b, a | b, a -> b, a ~ b");
                System.out.println("- Комбинации: (a | b) & c, !(a & b) -> c");
                System.out.println("- Переменные должны быть от a до e");
                System.out.println("Попробуйте снова.\n");
            }
        }

        System.out.println("Программа завершена.");
        scanner.close();
    }
}