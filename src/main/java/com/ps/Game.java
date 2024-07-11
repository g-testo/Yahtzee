package com.ps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Game {
    private int currentRound;
    private int currentRerollsLeft;

    private ArrayList<Integer> currentRoll = new ArrayList<>();

    private ArrayList<Combination> combinations = new ArrayList<>();

    public Game(){
        populateCombinations();

        this.currentRound = 1;
        this.currentRerollsLeft = 2;
        rollDice();
    }

    private void populateCombinations(){
        try{
            BufferedReader bufReader = new BufferedReader(new FileReader("combinations.csv"));

            String input;
            while((input = bufReader.readLine()) != null){
                String[] splitLine = input.split(",");
                int id = Integer.parseInt(splitLine[0]);
                String displayName = splitLine[1];
                combinations.add(new Combination(id, displayName));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void rollDice(){
        int numOfDice = this.currentRoll.size();
        int diceToRoll = 5 - numOfDice;

        for(int i=0;i<diceToRoll;i++){
            int randomNum = generateNewDieValue();
            this.currentRoll.add(randomNum);
        }
    }

    public void rollDice(boolean[] diceToReroll){
        this.currentRerollsLeft--;

        if(this.currentRerollsLeft < 0){
            System.out.println("No rerolls left");
            return;
        }

        for(int i=0;i<this.currentRoll.size();i++){

            if(diceToReroll[i]){
                int newDieValue = generateNewDieValue();
                this.currentRoll.set(i, newDieValue);
            }

        }

    }

    public static int generateNewDieValue(){
        return (int)(Math.random() * 6) + 1;
    }

    public void startNewRound(){
        this.currentRound++;
        this.currentRerollsLeft = 2;
    }

    public int getCurrentRound(){
        return this.currentRound;
    }

    public int getCurrentRerollsLeft(){
        return this.currentRerollsLeft;
    }

    public ArrayList<Integer> getCurrentRoll() {
        return this.currentRoll;
    }

    public ArrayList<Combination> getCombinations() {
        return combinations;
    }
}
