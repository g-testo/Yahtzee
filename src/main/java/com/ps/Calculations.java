package com.ps;

import java.util.ArrayList;
import java.util.Collections;

public class Calculations {
    public static int ofAKind(ArrayList<Integer> dice, int numOfOccurrences) {
        int[] diceCounter = {0, 0, 0, 0, 0, 0};

        for (int i = 0; i < dice.size(); i++) {
            int dieValue = dice.get(i);
            diceCounter[dieValue - 1] = diceCounter[dieValue - 1] + 1;
        }

        for (int i = 0; i < diceCounter.length; i++) {
            if (diceCounter[i] > numOfOccurrences-1) {
                return sumOfDice(dice);
            }
        }

        return 0;
    }

    public static int sumOfDice(ArrayList<Integer> dice){
        int total = 0;
        for(int i=0;i<dice.size();i++){
            total += dice.get(i);
        }
        return total;
    }

    public static int upperCombo(ArrayList<Integer> dice, int diceValue){
        int score = 0;
        for(int i=0;i<dice.size();i++){
            if(dice.get(i).equals(diceValue)){
                score+=diceValue;
            }
        }
        return score;
    }

    public static int lowerCombo(ArrayList<Integer> dice, int diceValue){
        int score = 0;
        for(int i=0;i<dice.size();i++){
            if(dice.get(i).equals(diceValue)){
                score+=diceValue;
            }
        }
        return score;
    }

    public static int fullHouse(ArrayList<Integer> dice) {
        int[] diceCounter = {0, 0, 0, 0, 0, 0};
        for (int i = 0; i < dice.size(); i++) {
            int dieValue = dice.get(i);
            diceCounter[dieValue - 1]++;
        }
        boolean threeOfAKind = false;
        boolean twoOfAKind = false;
        for (int i = 0; i < diceCounter.length; i++) {
            if (diceCounter[i] == 3) {
                threeOfAKind = true;
            }
            if (diceCounter[i] == 2) {
                twoOfAKind = true;
            }
        }
        if (threeOfAKind && twoOfAKind) {
            return 25;
        }
        return 0;
    }

    public static int smallStraight(ArrayList<Integer> dice) {
        ArrayList<Integer> unique = getUniqueDice(dice);

        Collections.sort(unique);
        int consecutiveCount = 1;
        for (int i = 1; i < unique.size(); i++) {
            if (unique.get(i) == unique.get(i - 1) + 1) {
                consecutiveCount++;
                if (consecutiveCount >= 4) {
                    return 30;
                }
            } else {
                consecutiveCount = 1;
            }
        }
        return 0;
    }

    public static int largeStraight(ArrayList<Integer> dice) {
        ArrayList<Integer> unique = getUniqueDice(dice);
        Collections.sort(unique);
        if (unique.size() == 5 && ((unique.get(0) == 1 && unique.get(4) == 5) || (unique.get(0) == 2 && unique.get(4) == 6))) {
            return 40;
        }
        return 0;
    }

    public static int chance(ArrayList<Integer> dice) {
        return sumOfDice(dice);
    }

    public static int yahtzee(ArrayList<Integer> dice) {
        int firstValue = dice.get(0);
        for (int i = 1; i < dice.size(); i++) {
            if (!dice.get(i).equals(firstValue)) {
                return 0;
            }
        }
        return 50;
    }

    public static ArrayList<Integer> getUniqueDice(ArrayList<Integer> dice){
        ArrayList<Integer> uniqueDice = new ArrayList<>();
        for (int i = 0; i < dice.size(); i++) {
            if (!uniqueDice.contains(dice.get(i))) {
                uniqueDice.add(dice.get(i));
            }
        }
        return uniqueDice;
    }
}
