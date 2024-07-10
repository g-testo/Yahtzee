package com.ps;

import java.util.ArrayList;

public class Combination {
    private int id;
    private String displayName;

    public Combination(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public int calculateScore(ArrayList<Integer> dice){

        if(this.id < 7){
            return Calculations.upperCombo(dice, this.id);
        }

        switch (this.id){
            case 7:
                return Calculations.ofAKind(dice, 3);
            case 8:
                return Calculations.ofAKind(dice, 4);
            case 9:
                return Calculations.fullHouse(dice);
            case 10:
                return Calculations.smallStraight(dice);
            case 11:
                return Calculations.largeStraight(dice);
            case 12:
                return Calculations.chance(dice);
            case 13:
                return Calculations.yahtzee(dice);
            default:
                System.out.println("Command not found. Please try again");
        }
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
