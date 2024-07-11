package com.ps;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private static Game game = new Game();
    static Scanner scanner = new Scanner(System.in);
    public static void display() {
        init();
    }

    private static void init() {
        int mainMenuCommand;
        do {
            System.out.println("Let's play some Yahtzee!");
            System.out.println("What would you like to do?");
            System.out.println("1) Start Game");
            System.out.println("2) Quit");

            mainMenuCommand = scanner.nextInt();

            switch (mainMenuCommand) {
                case 1:
                    handleStartGame();
                    break;
                case 2:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Command not found");
            }
        } while (mainMenuCommand != 2);
    }

    private static void handleStartGame(){
        int subMenuCommand;
        do {
            ArrayList<Integer> currentRollArr = game.getCurrentRoll();

            String strNumbers = "";

            for(int i=0;i<currentRollArr.size();i++){
                strNumbers += currentRollArr.get(i) + " ";
            }

            System.out.printf("You rolled the following: %s\n", strNumbers);

            System.out.println("What would you like to do next?");
            System.out.printf("1) Reroll (%d rolls left)\n", game.getCurrentRerollsLeft());
            System.out.println("2) Choose combination");
            System.out.println("3) BACK");

            subMenuCommand = scanner.nextInt();

            switch (subMenuCommand) {
                case 1:
                    if(game.getCurrentRerollsLeft() > 0){
                        System.out.println("Dice have been rolled");
                        handleRerollDice();
                    } else {
                        System.out.println("No rerolls left");
                    }
                    break;
                case 2:
                    chooseCombination();
                    break;
                case 3:
                    System.out.println("Back to the main menu...");
                    break;
                default:
                    System.out.println("Command not found");
            }
        } while (subMenuCommand != 3);
    }

    private static void displayCombinations(){
        ArrayList<Combination> combinations = game.getCombinations();
        int numOfRowsToDisplay = (int)Math.ceil(combinations.size()/2);
        ArrayList<Integer> currentRoll = game.getCurrentRoll();

        for(int i = 0;i<numOfRowsToDisplay;i++){
            Combination displayLeftCombo = combinations.get(i);
            Combination displayRightCombo = combinations.get(i+numOfRowsToDisplay);
            String leftSide = String.format("%d) %-6s- %-2d      ",
                    displayLeftCombo.getId(),
                    displayLeftCombo.getDisplayName(),
                    displayLeftCombo.calculateScore(currentRoll)
            );
            String rightSide = String.format("%2d) %-16s - %-2d\n",
                    displayRightCombo.getId(),
                    displayRightCombo.getDisplayName(),
                    displayRightCombo.calculateScore(currentRoll)
            );

            System.out.print(
                    (displayLeftCombo.getHasUsedThisGame() ? "Closed             ": leftSide) +
                    (displayRightCombo.getHasUsedThisGame() ? "Closed\n": rightSide)
            );
        }
    }

    private static void chooseCombination(){
        displayCombinations();
        System.out.println("Please choose a combination...");
        int chosenComboId = scanner.nextInt();
        boolean allCombosUsed = true;

        for(Combination combination: game.getCombinations()){
            if(combination.getId() == 0){
                continue;
            }
            if(combination.getId() == chosenComboId){
                combination.setHasUsedThisGame(true);
                combination.setRecordedScore(combination.calculateScore(game.getCurrentRoll()));

                boolean[] diceToReroll= {true,true,true,true,true};
                game.rollDice(diceToReroll);
            }
            if(!combination.getHasUsedThisGame()){
                allCombosUsed = false;
            }
        }
        if(allCombosUsed){
            handleEndGame();
        }
    }

    private static void handleEndGame(){
        int score = 0;
        for(Combination combination: game.getCombinations()) {
            if(combination.getRecordedScore() != null){
                score += combination.getRecordedScore();
            }
        }
        System.out.printf("Great game! \n Your final score was %d \n", score);

        ArrayList<Combination> combinations = game.getCombinations();
        int numOfRowsToDisplay = (int)Math.ceil(combinations.size()/2);

        System.out.println("Results: \n ");
        for(int i = 0;i<numOfRowsToDisplay;i++){
            Combination displayLeftCombo = combinations.get(i);
            Combination displayRightCombo = combinations.get(i+numOfRowsToDisplay);
            String leftSide = String.format("%d) %-6s- %-2d      ",
                    displayLeftCombo.getId(),
                    displayLeftCombo.getDisplayName(),
                    displayLeftCombo.getRecordedScore()
            );
            String rightSide = String.format("%2d) %-16s - %-2d\n",
                    displayRightCombo.getId(),
                    displayRightCombo.getDisplayName(),
                    displayRightCombo.getRecordedScore()
            );
            System.out.print(leftSide + rightSide);
        }
        System.exit(0);
    }

    private static void handleRerollDice(){
        int rerollDiceMenuCommand;
        boolean[] diceToReroll = {false, false, false, false, false};
        ArrayList<Integer> currentRollArr = game.getCurrentRoll();

        do {
            String rollToDisplay = "";
            for(int i = 0; i < currentRollArr.size();i++){
                rollToDisplay += i+1 + ") " + currentRollArr.get(i) + (diceToReroll[i] ? "(Reroll)": "") + "\n";
            }
            rollToDisplay += "6) DONE";
            System.out.println(rollToDisplay);

            System.out.println("What dice would you like to reroll?");

            rerollDiceMenuCommand = scanner.nextInt();

            int diceIndex = rerollDiceMenuCommand-1;

            if(rerollDiceMenuCommand == 6){
                game.rollDice(diceToReroll);
            } else if(rerollDiceMenuCommand > 0 && rerollDiceMenuCommand < 6) {
                diceToReroll[diceIndex] = !diceToReroll[diceIndex];
            } else {
                System.out.println("Command not found");
            }
        } while (rerollDiceMenuCommand != 6);
    }

}
