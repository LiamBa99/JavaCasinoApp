package ui;

import java.util.*;
import model.CasinoGame;
import model.blackjack.BlackjackGame;
import model.blackjack.BlackjackRound;
import model.casino.*;
import model.blackjack.*;
import model.prizeshop.*;
import model.roulette.RouletteRound;

import java.awt.*;

// represents the casino console app
public class CasinoApp {

    private final Casino currentCasino; // represents the casino the player is in
    private final Scanner input; // represents the inputs the player enters
    private boolean keepGoing; // represents if the player would like to continue playing
    private final Shop prizeShop; // represents the casino's shop of prizes


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
        System.out.println("Would you like to view your Inventory? Press I");

        String command = input.next().toLowerCase();
        processCommand(command);
    }

    // EFFECTS: shows the user their owned prizes
    public void displayInventory() {
        ArrayList<Prize> inventory = currentCasino.getInventory();
        if (inventory.size() == 0) {
            System.out.println("Oh no, your inventory is empty! Go add some prizes");
        }

        for (Prize prize : inventory) {
            System.out.println("Price: " + prize.getValue() + " Animal Type: " + prize.getAnimalType());
        }

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
        } else if (command.equals("i")) {
            displayInventory();
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

    // MODIFIES: this, BlackjackGame, BlackjackRound, CardDeck
    // EFFECTS: sets up the prerequisites for blackjack, getting number of decks until they do not want
    // to play anymore
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
        // represents the current game being played
        CasinoGame currentGame = playBlackJack;

        while (keepPlaying) {
            keepPlaying = playBlackjackRound(playBlackJack);
        }
        displayWelcome();
    }

    // MODIFIES: this, Casino, BlackjackGame, BlackjackRound, CardDeck
    // EFFECTS: starts the round of blackjack, sets player bet amount, deals first cards
    public boolean playBlackjackRound(BlackjackGame blackjack) {
        boolean playAgain;

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

    // MODIFIES: this, Casino, BlackjackGame, BlackjackRound, CardDeck
    // EFFECTS: asks the player if they want to hit or stand, hitting until they want to stop. When player is standing
    // deal the dealer's cards until game is complete
    // returns the instance of the round
    // if block in itself is longer than 25 lines, cant split into helpers
    @SuppressWarnings("methodlength")
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

    // MODIFIES: this, Casino, BlackjackGame, BlackjackRound, CardDeck
    // EFFECTS: shows the user the results of the game, check if they won and distribute balance accordingly.
    // returns true if they want to play again
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

    // EFFECTS: starts the roulette system, asks the player if they would like to select a colour and creates
    // a roulette round with the selected colours and numbers. recurs if the player wants to play again, otherwise
    // return to the menu
    public void playRoulette() {
        ArrayList<String> colourSelection = new ArrayList<>();
        ArrayList<Integer> numberSelection;
        int numBets = 0;

        System.out.println("Welcome to Roulette!");

        int betAmount = getRouletteBet();

        System.out.println("Would you like to select a colour? y or n");
        String command = input.next().toLowerCase();
        while (!(command.equals("y") || command.equals("n"))) {
            System.out.println("That is not a valid input! Try again");
            command = input.next().toLowerCase();
        }

        if (command.equals("y")) {
            colourSelection = getRouletteColourSelection();
            numBets += colourSelection.size();
        }

        numberSelection = getRouletteNumberChoice();
        numBets += numberSelection.size();

        RouletteRound currentRound = new RouletteRound(numberSelection,betAmount,colourSelection);

        if (playRouletteRound(currentRound,numBets)) {
            playRoulette();
        }

        displayWelcome();
    }

    // MODIFIES: Casino, RouletteRound, this
    // EFFECTS: deducts total bet and adds winnings if player won, shows winning number and asks the player if
    // they would like to play again, returning true if they want to play again, false otherwise
    public boolean playRouletteRound(RouletteRound currentRound, int numBets) {
        int numWins = currentRound.checkWin();
        int playerBet = currentRound.getPlayerBet();
        int totalBet = numBets * playerBet;

        currentCasino.deductPlayerBalance(totalBet);

        int payout = (numWins * playerBet) * 2;

        currentCasino.addPlayerBalance(payout);

        System.out.println("The winning number was: " + currentRound.getSelectedNumber());
        if (numWins > 0) {
            System.out.println("Congratulations you have won " + numWins + " times!");

        } else {
            System.out.println("I'm sorry you did not select any winning tiles.");
        }

        System.out.println("Would you like to play again? y or n");
        String command = input.next().toLowerCase();
        while (!(command.equals("y") || command.equals("n"))) {
            System.out.println("That is not a valid input! Try again");
            command = input.next().toLowerCase();
        }
        return command.equals("y");
    }

    // gets the bet amount from the player
    public int getRouletteBet() {
        System.out.println("How much would you like to bet per choice?: ");
        int betAmount = input.nextInt();

        while (betAmount < 0 || betAmount > currentCasino.getPlayerBalance()) {
            System.out.println("Invalid bet amount! Must be greater than 0 and less than your balance.");
            betAmount = input.nextInt();
        }

        return betAmount;
    }

    // EFFECTS: gets the colour selection of the player
    public ArrayList<String> getRouletteColourSelection() {
        ArrayList<String> colourSelection = new ArrayList<>();

        System.out.println("Would you like to bet on red, black or both? Input r, b or 2");
        String command = input.next().toLowerCase();
        while (!(command.equals("r") || command.equals("b") || command.equals("2"))) {
            System.out.println("That is not a valid input! Try again");
            command = input.next().toLowerCase();
        }

        if (command.equals("r")) {
            colourSelection.add("red");
        } else if (command.equals("b")) {
            colourSelection.add("black");
        }
        colourSelection.add("red");
        colourSelection.add("black");
        return colourSelection;
    }

    // EFFECTS: gets the number choices the player wants to select
    public ArrayList<Integer> getRouletteNumberChoice() {
        ArrayList<Integer> choiceList = new ArrayList<>();
        boolean anotherChoice = true;

        while (anotherChoice) {
            System.out.println("What numbers would you like to select? The range is 0 to 36");
            int numberChoice = input.nextInt();

            while (!(numberChoice >= 0 && numberChoice <= 36)) {
                numberChoice = input.nextInt();
                System.out.println("Not a valid choice! Please try again.");
            }

            System.out.println("Successfully chose: " + numberChoice);
            choiceList.add(numberChoice);

            System.out.println("Would you like to select another number? y or n");
            String command = input.next().toLowerCase();
            while (!(command.equals("y") || command.equals("n"))) {
                System.out.println("That is not a valid choice! Please try again.");
                command = input.next().toLowerCase();
            }
            if (command.equals("n")) {
                anotherChoice = false;
            }
        }
        return choiceList;
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

    // MODIFIES: this, Casino, PrizeShop
    // EFFECTS: gets the input of the prize the user would like to purchase, adding the prize to their inventory
    // calls another prize to ask the user if they want to purchase another
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

    // MODIFIES: this, Casino, PrizeShop
    // EFFECTS: asks the user if they want to make another purchase
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
        return currentRound.getPlayerCardValue() == 21;
    }

    // EFFECTS: shows the user their current cards
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
