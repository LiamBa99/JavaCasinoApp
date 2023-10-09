package ui;

import java.util.*;
import model.CasinoGame;
import model.blackjack.BlackjackGame;
import model.blackjack.BlackjackRound;
import model.casino.*;

// represents the casino console app
public class CasinoApp {

    private CasinoGame currentGame;
    private Casino currentCasino;
    private Scanner input;


    // EFFECTS: constructs the Casino console interface
    public CasinoApp() {
        currentCasino = new Casino(0);
        currentGame = null;
    }

    // MODIFIES: this
    // EFFECTS: runs the casino app and interacts with the user
    public void runCasinoApp() {

    }

    // EFFECTS: shows the user the welcome page
    public void displayWelcome() {
        System.out.println("Welcome to the Casino! I hope you brought lots of cash!");

        System.out.println("Would you like to play Games? Press G");
        System.out.println("Would you like to shop for prizes? Press S");
        System.out.println("Would you like to view your balance? Press B");

        String command = input.next().toLowerCase();
        processCommand(command);
    }

    // EFFECTS: processes the command to decide what to execute next
    public void processCommand(String command) {
        if (command.equals("g")) {
            displayAvailableGames();
        } else if (command.equals("s")) {
            displayPrizeShop();
        } else if (command.equals("b")) {
            displayBalance();
        } else if (command.equals("+")) {
            addBalance();
        } else if (command.equals("q")) {
            quitCasino();
        } else if (command.equals("m")) {
            displayWelcome();
        } else if (command.equals("j")) {
            playBlackJack();
        } else if (command.equals("r")) {
            playRoulette();
        }
    }

    // EFFECTS: shows the user their current balance and gives the option to deposit
    public void displayBalance() {
        int currentBalance = currentCasino.getPlayerBalance();
        System.out.println("Your current balance is: " + currentBalance);

        System.out.println("Would you like to add more balance? Press +");

        System.out.println("Return to the menu by pressing m");

        String command = input.next().toLowerCase();
        processCommand(command);
    }

    // MODIFIES: casino
    // EFFECTS: adds balance to the users casino balance
    public void addBalance() {
        System.out.println("How much funds would you like to add?: ");

        int depositAmount = input.nextInt();

        if (depositAmount >= 0) {
            currentCasino.addPlayerBalance(depositAmount);
            System.out.println("Successful deposit!");
        } else {
            System.out.println("Cannot input a negative number! Try again.");
        }

        displayBalance();
    }

    // EFFECTS: shows the user the available games to play and shop option
    public void displayAvailableGames() {
        System.out.println("We have a plethora of games to play here, you may select from TWO WHOLE games: ");

        System.out.println("Blackjack is available! Press J to play!");
        System.out.println("Roulette is available! Press R to play!");

        String command = input.next().toLowerCase();
        processCommand(command);
    }

    public void playBlackJack() {
        boolean keepPlaying = false;
        BlackjackGame playBlackJack;

        System.out.println("Welcome to the BlackJack table!"
                + "You want to get as close to 21 as possible without going over.");
        System.out.println("How many decks would you like to play with? The maximum is 4: ");

        int deckAmount = input.nextInt();
        if (deckAmount < 0) {
            System.out.println("You must enter a number greater than 0 and less than 5. Try again.");
        }

        playBlackJack = new BlackjackGame(deckAmount,currentCasino);

        while (keepPlaying) {
            keepPlaying = playBlackjackRound(playBlackJack);
        }
    }

    public boolean playBlackjackRound(BlackjackGame blackjack) {
        boolean playAgain = false;
        boolean standing = false;
        BlackjackRound currentRound = new BlackjackRound();
        System.out.println("Welcome to Blackjack!");

        System.out.println("How much would you like to bet?");

        int betAmount = input.nextInt();
        if (betAmount < 0 || !blackjack.checkEnoughMoney(betAmount)) {
            System.out.println("You must enter a non-negative number!. Try again.");
        } else {
            blackjack.setCurrentBet(betAmount);
        }

        System.out.println("Here comes the first cards");
        currentRound.dealFirstCards();

        // check for blackjack

        // if no blackjack ask if they want to hit or stand
        while (!standing) {
            System.out.println("You have " + currentRound.getPlayerCardValue() + "Would you like to hit or stand?");
            System.out.println("Press h to hit and s to stand");

            String command = input.next().toLowerCase();

            if (command.equals("h")) {
                currentRound.showNextCard(false);
            } else if (command.equals("s")) {
                standing = true;
                currentRound.dealUntilComplete();
            }
        }

        // show the dealer and player hands

        // check if they won

        // add bet if they won

        // deduct bet if they lost

        // ask if they want to play again
        return playAgain;
    }

    public void playRoulette() {

    }

    // EFFECTS: shows the user the available items to purchase in the shop
    public void displayPrizeShop() {

    }

    public boolean getCommand() {
        return false;
    }

    public void quitCasino() {

    }

}
