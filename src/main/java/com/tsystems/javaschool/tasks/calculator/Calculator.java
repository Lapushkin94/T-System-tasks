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
    public String evaluate(String statement) {
        // TODO: Implement the logic here

        List<String> myListWithNumbersAndSymbols = toArrayReformation(statement);

        String currentString = "";
        Stack<String> stackForArithmeticSymbols = new Stack<>();
        int priority;

        for (int i = 0; i < myListWithNumbersAndSymbols.size(); i++) {
            priority = getSymbolPriority(myListWithNumbersAndSymbols.get(i));

            if (priority == 0) {
                currentString += myListWithNumbersAndSymbols.get(i);
            }
            if (priority == 1) {
                stackForArithmeticSymbols.push(myListWithNumbersAndSymbols.get(i));
            }
            if (priority > 1) {
                currentString += " ";
                while (!stackForArithmeticSymbols.empty()) {
                    if (getSymbolPriority(stackForArithmeticSymbols.peek()) >= priority) {
                        currentString += stackForArithmeticSymbols.pop();
                    }
                    else break;
                }
                stackForArithmeticSymbols.push(myListWithNumbersAndSymbols.get(i));
            }
            if (priority == -1) {
                currentString += " ";
                while (getSymbolPriority(stackForArithmeticSymbols.peek()) != 1) {
                    currentString += stackForArithmeticSymbols.pop();
                }
                stackForArithmeticSymbols.pop();
            }
        }

        while (!stackForArithmeticSymbols.empty()) {
            currentString += stackForArithmeticSymbols.pop();
        }
        System.out.println(currentString);
        double resultDouble = RPNtoResult(currentString);
        String result = "" + resultDouble;

        return result;
    }

    public static List<String> toArrayReformation(String inputString) {

        Pattern patterForNumbersAndArithmeticSymbols = Pattern.compile("(\\d+(\\.\\d+)?)|(\\+|\\-|\\*|\\/|\\(|\\))");

        List<String> listWithNumbersAndSymbols = new ArrayList<>();
        Matcher matcherForNumbersAndArithmeticSymbols = patterForNumbersAndArithmeticSymbols.matcher(inputString);

        while (matcherForNumbersAndArithmeticSymbols.find()) {
            listWithNumbersAndSymbols.add(matcherForNumbersAndArithmeticSymbols.group());
        }

        return listWithNumbersAndSymbols;
    }

    public static double RPNtoResult(String rpnResult) {

        List<String> listOfNumbersAndSymbolsToTransferToResult = toArrayReformation(rpnResult);
        String symbolToOperation = new String();
        Stack<Double> stackWithDoubleNumbers = new Stack<>();

        for (int i = 0; i < listOfNumbersAndSymbolsToTransferToResult.size(); i++) {

            if (listOfNumbersAndSymbolsToTransferToResult.get(i).equals(" ")) continue;

            if (getSymbolPriority(listOfNumbersAndSymbolsToTransferToResult.get(i)) == 0) {
                while((!listOfNumbersAndSymbolsToTransferToResult.get(i).equals(" ")) && getSymbolPriority(listOfNumbersAndSymbolsToTransferToResult.get(i)) == 0) {
                    symbolToOperation += listOfNumbersAndSymbolsToTransferToResult.get(i++);

                    if (i == listOfNumbersAndSymbolsToTransferToResult.size()) {
                        break;
                    }
                    stackWithDoubleNumbers.push(Double.parseDouble(symbolToOperation));
                    symbolToOperation = new String();
                }

            }

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
                    stackWithDoubleNumbers.push(b / a);
                }


            }

        }

        return stackWithDoubleNumbers.pop();
    }

    private static int getSymbolPriority(String symbol) {
        if (symbol.equals("*") || symbol.equals("/"))
            return 3;
        else if (symbol.equals("+") || symbol.equals("-"))
            return 2;
        else if (symbol.equals("("))
            return 1;
        else if (symbol.equals(")"))
            return -1;
        else
            return 0;
    }

}
