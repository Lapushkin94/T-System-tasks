package com.tsystems.javaschool.tasks.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */

    static boolean isRevercePolishNotationReturnNull = false;

    public String evaluate(String statement) {

        String result = null;

        // Проверка корректности входных данных.
        if (statementCheck(statement)) {

            // Получения списка элементов из введенной строки.
            List<String> myListOfNumbersAndSymbols = getArrayByPattern(statement);

            // Преобразование строки в формат польской нотации.
            StringBuilder polishNotationResult = polishNotation(myListOfNumbersAndSymbols);

            // Получение ответа на задачу.
            // Метод производит математические операции, в зависимости от переданных
            // данных в формате польской записи.
            double resultDouble = reversePolishNotationResult(polishNotationResult.toString());

            // Результат в виде строки.
            result = "" + resultDouble;

            // Убираем знак после ".", если число целое.
            if (resultDouble % 1 == 0) result = "" + (int) resultDouble;

            if (isRevercePolishNotationReturnNull) result = null;

        }

        // Проверка вывода результата.
        System.out.println(result);

        return result;
    }

    // Метод преобразует строку в список по заданному шаблону.
    public static List<String> getArrayByPattern(String inputString) {

        Pattern patterForNumbersAndArithmeticSymbols = Pattern.compile("(\\d+(\\.\\d+)?)|([+\\-*/()])");

        List<String> listOfNumbersAndSymbols = new ArrayList<>();
        Matcher matcherForNumbersAndArithmeticSymbols = patterForNumbersAndArithmeticSymbols.matcher(inputString);

        while (matcherForNumbersAndArithmeticSymbols.find()) {
            listOfNumbersAndSymbols.add(matcherForNumbersAndArithmeticSymbols.group());
        }

        return listOfNumbersAndSymbols;
    }

    // Преобразование списка в строку в формате польской нотации.
    public static StringBuilder polishNotation(List<String> listOfNumbersAndSymbols) {

        // Реализация обратной польской записи.
        // Результат записывается в строку polishNotationResult.
        StringBuilder polishNotationResult = new StringBuilder();
        Stack<String> stackForArithmeticSymbols = new Stack<>();
        int symbolPriority;

        for (String symbol : listOfNumbersAndSymbols) {
            symbolPriority = getSymbolPriority(symbol);

            if (symbolPriority == 0) {
                polishNotationResult.append(symbol);
            }
            if (symbolPriority == 1) {
                stackForArithmeticSymbols.push(symbol);
            }
            if (symbolPriority > 1) {
                polishNotationResult.append(" ");
                while (!stackForArithmeticSymbols.empty()) {
                    if (getSymbolPriority(stackForArithmeticSymbols.peek()) >= symbolPriority) {
                        polishNotationResult.append(stackForArithmeticSymbols.pop());
                    } else break;
                }
                stackForArithmeticSymbols.push(symbol);
            }
            if (symbolPriority == -1) {
                polishNotationResult.append(" ");
                while (getSymbolPriority(stackForArithmeticSymbols.peek()) != 1) {
                    polishNotationResult.append(stackForArithmeticSymbols.pop());
                }
                stackForArithmeticSymbols.pop();
            }
        }

        while (!stackForArithmeticSymbols.empty()) {
            polishNotationResult.append(stackForArithmeticSymbols.pop());
        }

        // Проверка вывода обратной польской записи.
        System.out.println(polishNotationResult);


        return polishNotationResult;
    }

    // Выполнение математических операций на основании
    // переданной строки в формате польской записи.
    public static double reversePolishNotationResult(String reversePolishNotation) {

        List<String> listOfNumbersAndSymbolsToTransferToResult = getArrayByPattern(reversePolishNotation);
        StringBuilder symbolToOperation = new StringBuilder();
        Stack<Double> stackWithDoubleNumbers = new Stack<>();

        for (int i = 0; i < listOfNumbersAndSymbolsToTransferToResult.size(); i++) {

            if (listOfNumbersAndSymbolsToTransferToResult.get(i).equals(" ")) continue;

            if (getSymbolPriority(listOfNumbersAndSymbolsToTransferToResult.get(i)) == 0) {
                while ((!listOfNumbersAndSymbolsToTransferToResult.get(i).equals(" ")) && getSymbolPriority(listOfNumbersAndSymbolsToTransferToResult.get(i)) == 0) {
                    symbolToOperation.append(listOfNumbersAndSymbolsToTransferToResult.get(i++));

                    if (i == listOfNumbersAndSymbolsToTransferToResult.size()) break;
                    stackWithDoubleNumbers.push(Double.parseDouble(symbolToOperation.toString()));
                    symbolToOperation = new StringBuilder();
                }

            }

            // Математические вычисления.
            if (getSymbolPriority(listOfNumbersAndSymbolsToTransferToResult.get(i)) > 1) {
                double a = stackWithDoubleNumbers.pop(), b = stackWithDoubleNumbers.pop();

                if (listOfNumbersAndSymbolsToTransferToResult.get(i).equals("+")) {
                    stackWithDoubleNumbers.push(b + a);
                }
                if (listOfNumbersAndSymbolsToTransferToResult.get(i).equals("-")) {
                    stackWithDoubleNumbers.push(b - a);
                }
                if (listOfNumbersAndSymbolsToTransferToResult.get(i).equals("*")) {
                    stackWithDoubleNumbers.push(b * a);
                }
                if (listOfNumbersAndSymbolsToTransferToResult.get(i).equals("/")) {
                    // Проверка деления на 0
                    if (a != 0) stackWithDoubleNumbers.push(b / a);
                    else {
                        stackWithDoubleNumbers.push(null);
                        break;
                    }
                }
            }
        }

        // В конечном счете в стеке останется один элемент - результат вычисления.
        // Если в ходе вычисления была попытка деления на 0, тогда
        // данный метод вернет 0, а конечный результат программы будет null.
        try {
            return stackWithDoubleNumbers.pop();
        }
        catch (NullPointerException exc) {
            isRevercePolishNotationReturnNull = true;
            return 0;
        }
    }

    // Метод возвращает приоритет переданного в него символа.
    private static int getSymbolPriority(String symbol) {
        switch (symbol) {
            case "*":
            case "/":
                return 3;
            case "+":
            case "-":
                return 2;
            case "(":
                return 1;
            case ")":
                return -1;
            default:
                return 0;
        }
    }

    // Проверка корректности ввода.
    private static boolean statementCheck(String statementToCheck) {
        boolean isCorrect = true;

        // Проверка на пустой ввод.
        try {
            if (statementToCheck.equals("")) return false;
        }
        catch (NullPointerException exc) {
            return false;
        }

        // Проверка на дублирующиеся символы.
        String statementWithoutNumbersNoSpace = statementToCheck.replaceAll("\\d", "");
        String statementWithoutNumbersAndSpace = statementToCheck.replaceAll("\\d", " ");
        char[] statementWithoutNumbersCharArray = statementWithoutNumbersAndSpace.toCharArray();
        char[] someChar = statementWithoutNumbersNoSpace.trim().toCharArray();

        if (statementWithoutNumbersNoSpace.length() > 1) {
            for (int i = 0; i < statementWithoutNumbersCharArray.length - 1; i++) {
                if (statementWithoutNumbersCharArray[i] != ' ') {
                    if (statementWithoutNumbersCharArray[i] == statementWithoutNumbersCharArray[i + 1]) {
                        isCorrect = false;
                        break;
                    }
                }
            }

            // Проверка на избыточность открывающих\закрывающих скобок.
            int a = 0, b = 0;
            for (char c : someChar) {
                if (c == '(') a++;
                if (c == ')') b++;
            }
            if (a != b) return false;
        }

        // Проверка на недопустимые символы.
        for (char statementSymbol : someChar) {
            switch (statementSymbol) {
                case '+':
                case '-':
                case '*':
                case '/':
                case '(':
                case ')':
                case ' ':
                case '.':
                    break;
                default:
                return false;
            }
        }

        return isCorrect;
    }
}
