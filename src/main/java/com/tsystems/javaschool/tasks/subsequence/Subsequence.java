package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        // TODO: Implement the logic here

        // Результат.
        boolean result = true;

        // Проверка на null.
        if (x == null || y == null) throw new IllegalArgumentException();

        // Вычисление результата.
        else {

            // Количество совпадений элементов списка 'x' с элементами списка 'y'.
            int matchesCount = 0;

            // Индекс списка 'x'.
            int firstListIndex = 0;

            String firstTemporarySymbol, secondTemporarySymbol;

            for (Object o : y) {

                // Если все элементы списка 'x' имеются в списке 'y' - завершаем работу.
                if (matchesCount == x.size()) {
                    break;
                }

                // Если прошли по всему списку 'x', но совпадений недостаточно - завершаем работу, ответ false.
                try {
                    firstTemporarySymbol = x.get(firstListIndex).toString();
                } catch (IndexOutOfBoundsException exc) {
                    result = false;
                    break;
                }

                secondTemporarySymbol = o.toString();

                // Если получили совпадение элементов, переходим к следующему элементу списка 'x'
                // и увеличиваем счетчик совпадений.
                if (firstTemporarySymbol.equals(secondTemporarySymbol)) {
                    matchesCount++;
                    firstListIndex++;
                }

            }

            // Проверка вывода количества совпадений и размера списка 'x'.
            System.out.println(matchesCount);
            System.out.println(x.size());

            // Если прошли по всему списку 'y', но совпадений недостаточно - ответ false.
            if (matchesCount != x.size()) result = false;

        }

        System.out.println(result);
        return result;
    }
}
