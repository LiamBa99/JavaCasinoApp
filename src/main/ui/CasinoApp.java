package ui;

import java.util.*;
import model.CasinoGame;
import model.blackjack.BlackjackGame;
import model.blackjack.BlackjackRound;
import model.casino.*;
import model.blackjack.*;
import model.prizeshop.*;
import java.awt.*;

// represents the casino console app
public class CasinoApp {

    private CasinoGame currentGame;
    private Casino currentCasino;
    private Scanner input;
    private boolean keepGoing;
    private Shop prizeShop;


    // EFFECTS: constructs the Casino console interface
    public CasinoApp() {
        currentCasino = new Casino(0);
        prizeShop = new Shop(currentCasino);
        input = new Scanner(System.in);
        keepGoing = true;
        runCasinoApp();
    }

    // MODIFIES: this
    // EFFECTS: runs the casino app and interacts with the user
    public void runCasinoApp() {
        while (keepGoing) {
            displayWelcome();
        }
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
        } else if (command.equals("m")) {
            displayWelcome();
        } else if (command.equals("j")) {
            playBlackJack();
        } else if (command.equals("r")) {
            playRoulette();
        } else if (command.equals("q")) {
            quitCasino();
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
        boolean keepPlaying = true;
        BlackjackGame playBlackJack;

        System.out.println("Welcome to the BlackJack table!"
                + " You want to get as close to 21 as possible without going over.");
        System.out.println("How many decks would you like to play with? The maximum is 4: ");

        int deckAmount = input.nextInt();
        while (deckAmount < 0 || deckAmount > 4) {
            System.out.println("You must enter a number greater than 0 and less than 5. Try again.");
            deckAmount = input.nextInt();
        }

        playBlackJack = new BlackjackGame(deckAmount,currentCasino);
        currentGame = playBlackJack;

        while (keepPlaying) {
            keepPlaying = playBlackjackRound(playBlackJack);
        }
        displayWelcome();
    }

    public boolean playBlackjackRound(BlackjackGame blackjack) {
        boolean playAgain = false;

        // blackjack set up
        BlackjackRound currentRound = new BlackjackRound(blackjack);

        System.out.println("Welcome to Blackjack!");

        System.out.println("How much would you like to bet?");

        int betAmount = input.nextInt();
        while (betAmount < 0 || !blackjack.checkEnoughMoney(betAmount)) {
            System.out.println("You must enter a non-negative number that is within your balance!. Try again.");
            betAmount = input.nextInt();
        }

        blackjack.setCurrentBet(betAmount);

        System.out.println("Here comes the first cards");

        currentRound.dealFirstCards();

        showCurrentCards(currentRound);

        // game logic
        currentRound = blackJackGameLogic(currentRound);

        // results
        playAgain = blackJackResultsPresenter(currentRound,betAmount);

        return playAgain;
    }

    public BlackjackRound blackJackGameLogic(BlackjackRound currentRound) {
        if (!isBlackjack(currentRound)) {
            boolean standing = false;
            while (!standing) {
                System.out.println("You have " + currentRound.getPlayerCardValue()
                        + " Would you like to hit or stand?");
                System.out.println("Press h to hit and s to stand");

                String command = input.next().toLowerCase();

                if (command.equals("h")) {
                    while (!standing) {
                        currentRound.dealACard(false);
                        showCurrentCards(currentRound);
                        if (currentRound.getPlayerCardValue() > 21) {
                            System.out.println("Oops! You busted");
                            standing = true;
                        } else if (currentRound.getPlayerCardValue() == 21) {
                            System.out.println("You hit 21!");
                            standing = true;
                        } else {
                            System.out.println("Hit again? Press h to hit or s to stand");
                            command = input.next().toLowerCase();
                            if (command.equals("s")) {
                                standing = true;
                                System.out.println("Standing!");
                                currentRound.dealUntilComplete();
                            }
                        }
                    }
                } else if (command.equals("s")) {
                    standing = true;
                    System.out.println("Standing!");
                    currentRound.dealUntilComplete();
                }
            }
        }
        return currentRound;
    }

    public boolean blackJackResultsPresenter(BlackjackRound currentRound,int betAmount) {
        showCurrentCards(currentRound);
        boolean playAgain = false;
        System.out.println("The dealer ended up with: " + currentRound.getDealerCardValue());
        System.out.println("You ended up with: " + currentRound.getPlayerCardValue());

        // check if they won
        if (currentRound.checkWin()) {
            System.out.println("You won!");
            currentCasino.addPlayerBalance(betAmount);
        } else {
            System.out.println("Oops you lost!");
            currentCasino.deductPlayerBalance(betAmount);
        }

        System.out.println("Do you want to play again? y for yes or n for no");
        String command = input.next().toLowerCase();

        if (command.equals("y")) {
            playAgain = true;
        } else if (command.equals("n")) {
            playAgain = false;
        } else {
            System.out.println("That was not a valid response, try again!");
        }

        return playAgain;
    }

    public void playRoulette() {

    }

    // EFFECTS: shows the user the available items to purchase in the shop
    public void displayPrizeShop() {
        System.out.println("These are the prizes currently available!: ");

        for (Prize prize : prizeShop.getPrizeList()) {
            int r = prize.getColour()[0];
            int g = prize.getColour()[1];
            int b = prize.getColour()[2];
            Color c = new Color(r, g, b);
            System.out.println("Price: " + prize.getValue() + " Animal Type: " + prize.getAnimalType());
        }

        System.out.println("Would you like to purchase a prize? input y for yes or n for no: ");
        String command = input.next().toLowerCase();
        if (command.equals("y")) {
            buyPrize();
        } else {
            displayWelcome();
        }
    }

    public void buyPrize() {
        ArrayList<String> validInputs = new ArrayList<>(Arrays.asList("r","m","1","2","3","4","5","6","7","8","9"));

        System.out.println("Input 1 - 9 to select the corresponding prize. \n Press r to refresh the shop or press m"
                + " to return to the menu!");

        String command = input.next().toLowerCase();

        while (!validInputs.contains(command)) {
            System.out.println("That's not a valid input, please try again!");
        }

        if (command.equals("r")) {
            prizeShop.generatePrizes();
            displayPrizeShop();
        } else if (command.equals("m")) {
            displayWelcome();
        }

        if (Integer.parseInt(command) > 0 && Integer.parseInt(command) < 10) {
            if (prizeShop.buyPrize(Integer.parseInt(command) - 1)) {
                System.out.println("You successfully purchased the prize!");
            } else {
                System.out.println("You don't have enough funds! Try again later.");
            }
        }

        anotherPurchase();
    }

    public void anotherPurchase() {
        System.out.println("Make another purchase? y or n");
        String command = input.next().toLowerCase();
        if (command.equals("y")) {
            displayPrizeShop();
        } else {
            displayWelcome();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets keepGoing to false
    public void quitCasino() {
        keepGoing = false;
    }

    // EFFECTS: checks if the player received blackjack
    public boolean isBlackjack(BlackjackRound currentRound) {
        if (currentRound.getPlayerCardValue() == 21) {
            return true;
        }
        return false;
    }

    public void showCurrentCards(BlackjackRound currentRound) {
        System.out.println("Dealer's cards are: ");
        for (Card card : currentRound.getDealerHand()) {
            System.out.println(card.getCardValue() + " of " + card.getSuit());
        }

        System.out.println("Your cards are: ");
        for (Card card : currentRound.getPlayerHand()) {
            System.out.println(card.getCardValue() + " of " + card.getSuit());
        }
    }

}
