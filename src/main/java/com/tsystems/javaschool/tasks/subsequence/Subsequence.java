package com.tsystems.javaschool.tasks.subsequence;

import java.util.ArrayList;
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

        boolean isTrue = true;

        if (x == null || y == null) throw new IllegalArgumentException();

        else {

            List firstList = new ArrayList(x);
            List secondList = new ArrayList(y);
            int matchesCount = 0;
            int firstListIndex = 0;
            String firstTemporarySymbol, secondTemporarySymbol;


            for (int i = 0; i < secondList.size(); i++) {

                if (matchesCount == firstList.size()) {
                    break;
                }

                try {
                    firstTemporarySymbol = firstList.get(firstListIndex).toString();
                } catch (IndexOutOfBoundsException exc) {
                    isTrue = false;
                    break;
                }

                secondTemporarySymbol = secondList.get(i).toString();

                if (firstTemporarySymbol.equals(secondTemporarySymbol)) {
                    matchesCount++;
                    firstListIndex++;
                }

            }

            System.out.println(matchesCount);
            System.out.println(firstList.size());
            if (matchesCount != firstList.size()) isTrue = false;

        }


        System.out.println(isTrue);
        return isTrue;
    }
}
