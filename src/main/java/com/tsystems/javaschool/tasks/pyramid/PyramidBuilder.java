package com.tsystems.javaschool.tasks.pyramid;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */

    static int pyramideStairsCount = 0;
    static int pyramideWidthCount = 0;
    static boolean isThereNullsInInputNumbers = false;

    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here

        int countOfInputNumbers = inputNumbers.size();

        // Проверка на null во входном массиве.
        isThereNullsInInputArray(inputNumbers);
        if (isThereNullsInInputNumbers) {
            System.out.println("Пирамиду построить нельзя, в массиве нули");
            return null;
        }

        // Проверка соответствия размерности.
        canWeBuildThePyramyde(countOfInputNumbers);
        if (pyramideStairsCount == 0) {
            System.out.println("Пирамиду построить нельзя, неподходящее количество элементов");
            return null;
        }

        // После успешной проверки строим пирамиду.
        else return getPyramide(inputNumbers);
    }

    // Проверяем, можно ли построить пирамиду,
    // минимальная высота равна 1.
    private static void canWeBuildThePyramyde(int numberCount) {

        int dynamicNumberCount = 0;
        int dynamicStairsCount = 1;
        int dynamicWidthCount = 1;

        while (true) {
            dynamicNumberCount += dynamicStairsCount;
            if (numberCount == dynamicNumberCount) {
                pyramideStairsCount = dynamicStairsCount;
                pyramideWidthCount = dynamicWidthCount;
                break;
            }
            if (numberCount < dynamicStairsCount) {
                pyramideStairsCount = 0;
                break;
            }
            else {
                dynamicStairsCount++;
                dynamicWidthCount += 2;
            }
        }

    }

    // Проверяем, есть ли в пирамиде нули.
    private static void isThereNullsInInputArray(List<Integer> inputNumbersCheck) {
        for (Integer i : inputNumbersCheck) {
            if (i == null) {
                isThereNullsInInputNumbers = true;
                break;
            }
        }
    }


    private static int[][] getPyramide(List<Integer> inputNumbers) {

        int countOfInputNumbers = inputNumbers.size();

        List<Integer> sortedInputList = inputNumbers.stream().sorted().collect(Collectors.toList());

        // Создал матрицу.
        int[][] result = new int[pyramideStairsCount][pyramideWidthCount];

        // Заполнение матрицы нулями.
        for (int[] stair : result) {
            Arrays.fill(stair, 0);
        }

        int centerOfResultMatrix = pyramideWidthCount / 2;
        int countOfNumbersOnStair = 1;
        int arrayCount = 0;

        // Заполнение пирамиды числами.
        for (int i = 0, offset = 0; i < pyramideStairsCount; i++, offset++, countOfNumbersOnStair++) {
            int paintStart = centerOfResultMatrix - offset;
            for (int j = 0; j < countOfNumbersOnStair * 2; j += 2, arrayCount++) {
                result[i][paintStart + j] = sortedInputList.get(arrayCount);
            }
        }

        // Проверка вывода построенной пирамиды.
        for (int[] stair : result) {
            for (int column : stair)
                System.out.print(column + " ");
            System.out.println();
        }

        return result;
    }

}
