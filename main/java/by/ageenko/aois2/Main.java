package by.ageenko.aois2;

import java.util.Scanner;
import java.util.Set;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите логическую функцию: ");
        String function = scanner.nextLine().trim();

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
    }
}
