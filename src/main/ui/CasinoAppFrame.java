package ui;

import model.blackjack.*;
import model.casino.*;
import model.prizeshop.*;
import model.roulette.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionListener;

public class CasinoAppFrame extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final String JSON_CASINO = "./data/casino.json";
    private static final String JSON_BJ = "./data/blackjack.json";
    private static final String JSON_PS = "./data/prizeshop.json";

    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    private Casino casino;
    private Shop prizeShop;
    private BlackjackGame blackjackGame;
    private BlackjackRound blackjackRound;
    private CardLayout blackjackLayout;
    private RouletteRound rouletteRound;
    private CardLayout rouletteLayout;
    private JPanel roulettePanel;
    private ArrayList<Integer> rouletteSelection;
    private List<String> rouletteColourSelection;
    private JPanel dealerHandCards;
    private JPanel playerHandCards;
    private JLabel dealerHandValue;
    private JLabel playerHandValue;
    private List<JButton> prizeButtonList;
    private JPanel inventoryPrizesPanel;
    private JPanel blackjackContainer;
    private CardLayout gamesLayout;
    private JPanel gamesContainer;
    private static CardLayout homeLayout;
    private static JPanel homeContainer;

    // EFFECTS: constructs a casinoAppFrame with a new casino, shop, jsonWriter and reader and a welcome page
    public CasinoAppFrame() {
        casino = new Casino(0);
        prizeShop = new Shop(casino);
        jsonWriter = new JsonWriter(JSON_CASINO,JSON_BJ,JSON_PS);
        jsonReader = new JsonReader(JSON_CASINO,JSON_BJ,JSON_PS);
        this.setSize(WIDTH,HEIGHT); // set the size of the frame
        this.setTitle("Welcome to the Casino!"); // set the title of the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close program when frame closes
        this.setResizable(false); // stop the frame from being resized
        frameSetUp();
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: constructs the cardlayout for the casino app, adding the panels, buttons and labels
    public void frameSetUp() {
        homeLayout = new CardLayout(5,5);
        homeContainer = new JPanel(homeLayout);
        panelSetUp();

        createLabel();
        JPanel buttonFrame = createButtons();
        homeContainer.add(buttonFrame, "Buttons");
        homeLayout.show(homeContainer, "Buttons");
        this.add(homeContainer);
    }

    // EFFECTS: creates four panels that require cardlayouts
    public void panelSetUp() {
        setUpGamesPanel();
        setUpBalancePanel();
        setUpPrizesPanel();
        setUpInventoryPanel();
    }

    // MODIFIES: this, prizeshop
    // EFFECTS: loads the casino, prizeshop, inventory and balance data from file.
    // Displaying popups if an exception is caught or if loaded successfully.
    public void loadGameState() {
        if (new File(JSON_CASINO).isFile() && new File(JSON_PS).isFile()) {
            try {
                casino = jsonReader.readCasino();
                prizeShop = jsonReader.readPrizeShop();
                setUpPrizesPanel();
                setUpInventoryPanel();
                prizeShop.setCasino(casino);
                for (Prize prize : casino.getInventory()) {
                    addPrizeToInventory(prize);
                }
                JOptionPane.showMessageDialog(null, "Prizes, balance, and inventory loaded successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Unable to read from file!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No file available!");
        }
    }

    // EFFECTS: saves the casino, blackjack, prizeshop and inventory data to file.
    // displays pop up if saved successfully or if no file is found
    public void saveGameState() {
        try {
            jsonWriter.open();
            jsonWriter.write(casino,blackjackGame,prizeShop);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved successfully!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to save game state!");
        }
    }

    // MODIFIES: this
    // creates the cardlayout panel for the blackjack game and adds the deck, bet and play panels
    public JPanel setUpBlackjackPanel() {
        blackjackLayout = new CardLayout();
        blackjackContainer = new JPanel(blackjackLayout);
        blackjackContainer.add(setUpDeckPanel(), "Decks");
        blackjackContainer.add(setupBlackjackBetPanel(), "Bet");
        blackjackContainer.add(setUpPlayBlackjackPanel(), "PlayBJ");

        blackjackLayout.show(blackjackContainer, "Decks");
        return blackjackContainer;
    }

    // MODIFIES: this, casino
    // EFFECTS: create the results panel for the blackjack game, showing the player what the dealer's cards were
    // and what the player's cards were.
    public JPanel setUpBlackjackResultsPanel() {
        JPanel resultsPanel = new JPanel(new GridLayout(4,1));
        JPanel dealerValuePanel = new JPanel(new GridLayout());
        JPanel playerValuePanel = new JPanel(new GridLayout());
        JPanel resultValuePanel = new JPanel();
        JLabel playerResults = new JLabel();
        JLabel dealerResults = new JLabel();

        if (blackjackRound.checkWin()) {
            playerResults.setText("You won! Your cards totaled a value of: " + blackjackRound.getPlayerCardValue());
            casino.addPlayerBalance(blackjackGame.getCurrentBet());
        } else {
            playerResults.setText("You lost! Your cards totaled a value of: "
                    + blackjackRound.getPlayerCardValue());
            casino.deductPlayerBalance(blackjackGame.getCurrentBet());
        }
        dealerResults.setText("The dealer's cards totaled a value of: " + blackjackRound.getDealerCardValue());
        resultValuePanel.add(playerResults);
        resultValuePanel.add(dealerResults);
        resultsPanel.add(resultValuePanel);
        resultsPanel.add(dealerValuePanel);
        resultsPanel.add(playerValuePanel);
        resultsPanel.add(setUpResultsButtons());
        return resultsPanel;
    }

    // EFFECTS: creates the result buttons to give the player an option to play again or return to the home page
    public JPanel setUpResultsButtons() {
        JPanel buttonPanel = new JPanel(new GridLayout(1,2));
        JButton homeButton = createHomeButton();
        JButton againButton = new JButton("Play again?");
        againButton.addActionListener(e -> resetBlackjack(true));
        homeButton.addActionListener(e -> resetBlackjack(false));
        buttonPanel.add(homeButton);
        buttonPanel.add(againButton);
        return buttonPanel;
    }

    // MODIFIES: this
    // EFFECTS: if the player wants to play again, remove all frame content from the blackjackcontainer,
    // recreate the blackjack content and show the play blackjack screen
    // else remove all content, recreate and show the home page
    public void resetBlackjack(boolean again) {
        if (again) {
            blackjackContainer.removeAll();
            blackjackRound.setCardValues(0,0);
            setUpGamesPanel();
            homeLayout.show(homeContainer, "Games");
            blackjackLayout.show(blackjackContainer, "PlayBJ");
        } else {
            gamesLayout.show(gamesContainer, "Games");
            blackjackContainer.removeAll();
            setUpGamesPanel();
        }
    }

    // EFFECTS: set up play blackjack panel which shows the current dealer's hand and value and the current
    // dealer's hand and value
    // creates buttons to allow the player to choose to hit or stand
    public JPanel setUpPlayBlackjackPanel() {
        JPanel playBlackjackPanel = new JPanel(new GridLayout(3,1));
        JPanel blackjackButtonPanel = new JPanel(new GridLayout(1,3));

        JPanel dealerHandPanel = new JPanel(new GridLayout(3,1));
        dealerHandPanel.add(new JLabel("Dealer's cards: "));

        JPanel playerHandPanel = new JPanel(new GridLayout(3, 1));
        playerHandPanel.add(new JLabel("Player's cards: "));

        JButton dealButton = new JButton("Deal!");
        JButton hitButton = new JButton("Hit!");
        JButton standButton = new JButton("Stand!");

        dealButton.addActionListener(e -> showFirstCards(dealerHandPanel, playerHandPanel, dealButton));
        hitButton.addActionListener(e -> dealNextCard());
        standButton.addActionListener(e -> dealUntilComplete());

        blackjackButtonPanel.add(hitButton);
        blackjackButtonPanel.add(standButton);
        blackjackButtonPanel.add(dealButton);

        playBlackjackPanel.add(dealerHandPanel);
        playBlackjackPanel.add(playerHandPanel);
        playBlackjackPanel.add(blackjackButtonPanel);

        return playBlackjackPanel;
    }

    // MODIFIES: this, blackjackround
    // EFFECTS: if the player has no cards, they must deal cards first. else shows the player their next card.
    // if the players card value is pushed over 21, show the results screen.
    public void dealNextCard() {
        if (blackjackRound.getPlayerCardValue() == 0) {
            JOptionPane.showMessageDialog(null, "You must deal cards first!");
        } else {
            Card newCard = blackjackRound.dealACard(false);
            if (blackjackRound.getPlayerCardValue() > 21) {
                blackjackContainer.add(setUpBlackjackResultsPanel(), "Results");
                blackjackLayout.show(blackjackContainer, "Results");
            } else {
                JLabel newCardLabel = new JLabel(setUpCardImage(newCard));
                playerHandCards.add(newCardLabel);
                playerHandValue.setText("Value: " + blackjackRound.getPlayerCardValue());
                blackjackLayout.show(blackjackContainer, "Bet");
                blackjackLayout.show(blackjackContainer, "PlayBJ");
            }
        }
    }

    // MODIFIES: this, blackjackround
    // EFFECTS: show the player the final values of the dealer and the player's cards
    public void dealUntilComplete() {
        while (blackjackRound.getDealerCardValue() < 17) {
            Card nextCard = blackjackRound.dealACard(true);
            JLabel newCardLabel = new JLabel(setUpCardImage(nextCard));
            dealerHandCards.add(newCardLabel);
            dealerHandValue.setText("Value: " + blackjackRound.getDealerCardValue());
            blackjackLayout.show(blackjackContainer, "Bet");
            blackjackLayout.show(blackjackContainer, "PlayBJ");
        }
        blackjackContainer.add(setUpBlackjackResultsPanel(), "Results");
        blackjackLayout.show(blackjackContainer, "Results");
    }

    // MODIFIES: this, blackjackRound
    // EFFECTS: show the player's first two cards and the dealer's first card
    public void showFirstCards(JPanel dealerHandPanel, JPanel playerHandPanel, JButton dealButton) {
        blackjackRound = new BlackjackRound(blackjackGame);
        dealerHandCards = new JPanel(new GridLayout());
        playerHandCards = new JPanel(new GridLayout());

        blackjackRound.dealFirstCards();
        for (Card c : blackjackRound.getDealerHand()) {
            JLabel cardLabel = new JLabel(setUpCardImage(c));
            dealerHandCards.add(cardLabel);
        }
        dealerHandValue = new JLabel("Value: " + blackjackRound.getDealerCardValue());

        for (Card c : blackjackRound.getPlayerHand()) {
            JLabel cardLabel = new JLabel(setUpCardImage(c));
            playerHandCards.add(cardLabel);
        }
        playerHandValue = new JLabel("Value: " + blackjackRound.getPlayerCardValue());

        dealerHandPanel.add(dealerHandCards);
        dealerHandPanel.add(dealerHandValue);
        playerHandPanel.add(playerHandCards);
        playerHandPanel.add(playerHandValue);
        blackjackLayout.show(blackjackContainer, "Bet");
        blackjackLayout.show(blackjackContainer, "PlayBJ");
        for (ActionListener al : dealButton.getActionListeners()) {
            dealButton.removeActionListener(al);
        }
    }

    // EFFECTS: creates an image based on the cards image field
    public ImageIcon setUpCardImage(Card card) {
        ImageIcon imageIcon = new ImageIcon(card.getCardImage());
        Image cardImage = imageIcon.getImage();
        Image smallerImage = cardImage.getScaledInstance(40,60, Image.SCALE_SMOOTH);
        imageIcon.setImage(smallerImage);

        return imageIcon;
    }

    // EFFECTS: creates the blackjack panel allowing the player to input their preferred bet size
    public JPanel setupBlackjackBetPanel() {
        JPanel blackjackBetPanel = new JPanel(new GridLayout(3,1));

        JLabel numBetQuestion = new JLabel("How much would you like to bet?");
        numBetQuestion.setHorizontalAlignment(JLabel.CENTER);
        SpinnerModel value = new SpinnerNumberModel(0,0,null,1);
        JSpinner numBetField = new JSpinner(value);
        JButton numBetButton = new JButton("Submit");

        numBetButton.addActionListener(e -> setBlackjackBet((Integer) numBetField.getValue()));

        blackjackBetPanel.add(numBetQuestion);
        blackjackBetPanel.add(numBetField);
        blackjackBetPanel.add(numBetButton);

        return blackjackBetPanel;
    }

    // MODIFIES: this, blackjackgame
    // EFFECTS: sets the blackjack bet for the current blackjackgame
    public void setBlackjackBet(int curBet) {
        if (curBet > casino.getPlayerBalance()) {
            JOptionPane.showMessageDialog(null, "You do not have sufficient balance!");
        } else if (curBet > 0) {
            blackjackGame.setCurrentBet(curBet);
            blackjackLayout.show(blackjackContainer, "PlayBJ");
        } else {
            JOptionPane.showMessageDialog(null, "That is an invalid bet size!");
        }

    }

    // EFFECTS: creates a panel that asks the player how many decks they want to use.
    public JPanel setUpDeckPanel() {
        JPanel blackjackDeckPanel = new JPanel(new GridLayout(5,1));

        JLabel numDeckQuestion = new JLabel("How many decks would you like to use? Max is 4:");
        JLabel welcomeLabel = new JLabel("Welcome to blackjack!");
        SpinnerModel value = new SpinnerNumberModel(4,0,4,1);
        JSpinner numdeckField = new JSpinner(value);
        JButton numDeckButton = new JButton("Submit");

        numDeckButton.addActionListener(e -> createBlackJackGame((Integer) numdeckField.getValue()));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        numDeckQuestion.setHorizontalAlignment(JLabel.CENTER);

        blackjackDeckPanel.add(welcomeLabel);
        blackjackDeckPanel.add(numDeckQuestion);
        blackjackDeckPanel.add(numdeckField);
        blackjackDeckPanel.add(numDeckButton);
        blackjackDeckPanel.add(createHomeButton());

        return blackjackDeckPanel;
    }

    // MODIFIES: this, blackjackgame
    // EFFECTS: creates the current blackjackgame according to the selected num of decks
    public void createBlackJackGame(int numDecks) {
        if (numDecks <= 4 && numDecks > 0) {
            blackjackGame = new BlackjackGame(numDecks, casino);
            blackjackLayout.show(blackjackContainer, "Bet");
        } else {
            JOptionPane.showMessageDialog(null, "That is an incorrect number of decks!");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a panel representing the roulette game
    public JPanel setUpRoulettePanel() {
        rouletteLayout = new CardLayout();
        roulettePanel = new JPanel(rouletteLayout);
        rouletteColourSelection = new ArrayList<>();
        rouletteSelection = new ArrayList<>();

        roulettePanel.add(setUpRouletteWelcomePanel(), "Welcome");

        rouletteLayout.show(roulettePanel, "Welcome");
        return roulettePanel;
    }

    // MODIFIES: this
    // EFFECTS: shows the player the results of the roulette game, including a home and play again button
    public void setUpRouletteResultsPanel() {
        JPanel rouletteResults = new JPanel(new GridLayout(4,1));
        JPanel buttonPanel = new JPanel(new GridLayout(1,2));

        JButton againButton = new JButton("Play again?");
        againButton.addActionListener(e -> resetRoulette());
        JButton homeButton = createHomeButton();
        homeButton.addActionListener(e -> gamesLayout.show(gamesContainer, "Games"));
        buttonPanel.add(homeButton);
        buttonPanel.add(againButton);

        rouletteResults.add(setUpResultSelectionPanel());
        rouletteResults.add(setUpWinningsPanel());
        rouletteResults.add(buttonPanel);

        roulettePanel.add(rouletteResults, "Results");
        rouletteLayout.show(roulettePanel, "Results");
    }

    // MODIFIES: this
    // EFFECTS: resets the roulette game after it has completed
    public void resetRoulette() {
        rouletteSelection = new ArrayList<>();
        rouletteColourSelection = new ArrayList<>();
        rouletteLayout.show(roulettePanel, "Welcome");
    }

    // EFFECTS: creates a panel representing the amount the player has lost or won in the game of roulette.
    public JPanel setUpWinningsPanel() {
        JPanel winningsPanel = new JPanel(new GridLayout());
        int numWins = rouletteRound.checkWin();
        int numBets = rouletteRound.getPlayerSelection().size() + rouletteRound.getPlayerColourEvenSelection().size();
        int winnings = rouletteRound.getPlayerBet() * numWins * 4;
        int totalWinnings = winnings - (numBets * rouletteRound.getPlayerBet());

        if (numWins > 0) {
            JLabel wonLabel = new JLabel("You won " + rouletteRound.checkWin() + " times!");
            JLabel winningsLabel = new JLabel("Your account has been credited $" + totalWinnings);
            winningsPanel.add(wonLabel);
            winningsPanel.add(winningsLabel);
        } else {
            JLabel lostLabel = new JLabel("You lost! None of your selections were correct");
            winningsPanel.add(lostLabel);
        }
        if (totalWinnings < 0) {
            casino.deductPlayerBalance(totalWinnings * -1);
        } else {
            casino.addPlayerBalance(totalWinnings);
        }
        return winningsPanel;
    }

    // MODIFIES: rouletteRound
    // EFFECTS: shows the player what number was selected and what their choices were
    public JPanel setUpResultSelectionPanel() {
        JPanel resultsPanel = new JPanel(new GridLayout(2,1));
        JLabel selectedNumberLabel = new JLabel("The selected number was: " + rouletteRound.selectNumber());
        JPanel playerSelectionPanel = new JPanel(new GridLayout(1,2));
        if (!rouletteRound.getPlayerSelection().isEmpty()) {
            JLabel selectedNumbersLabel = new JLabel("You selected the number(s): "
                    + rouletteRound.getPlayerSelection());
            playerSelectionPanel.add(selectedNumbersLabel);
        }
        if (!rouletteRound.getPlayerColourEvenSelection().isEmpty()) {
            JLabel selectedColoursLabel = new JLabel("You selected the colours: "
                    + rouletteRound.getPlayerColourEvenSelection());
            playerSelectionPanel.add(selectedColoursLabel);
        }
        resultsPanel.add(selectedNumberLabel);
        resultsPanel.add(playerSelectionPanel);
        return resultsPanel;
    }

    // MODIFIES: rouletteround
    // EFFECTS: creates a panel with buttons representing the required set up conditions for roulette.
    public JPanel setUpRouletteWelcomePanel() {
        JPanel betPanel = new JPanel(new GridLayout(5,1));

        JLabel welcomeLabel = new JLabel("Welcome to roulette!");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        SpinnerModel value = new SpinnerNumberModel(0,0,null,1);
        JSpinner betSpinner = new JSpinner(value);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> setRouletteBet(rouletteSelection, (Integer) betSpinner.getValue(),
                rouletteColourSelection));

        JPanel inputPanel = new JPanel(new GridLayout(1,3));
        inputPanel.add(setUpRouletteBoardButtons());
        inputPanel.add(setUpColourSelectionButtons());
        inputPanel.add(betSpinner);

        betPanel.add(welcomeLabel);
        betPanel.add(setUpRouletteSelectionLabels());
        betPanel.add(inputPanel);
        betPanel.add(submitButton);
        betPanel.add(createHomeButton());

        return betPanel;
    }

    // EFFECTS: creates labels for each of the button panels for the set up of the roulette game
    public JPanel setUpRouletteSelectionLabels() {
        JPanel labelPanel = new JPanel(new GridLayout(1,3));
        JLabel numberSelectionLabel = new JLabel("What numbers would you like to select?");
        numberSelectionLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel colourSelectionLabel = new JLabel("What colour would you like to select?");
        colourSelectionLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel betLabel = new JLabel("How much would you like to bet?");
        betLabel.setHorizontalAlignment(JLabel.CENTER);
        labelPanel.add(numberSelectionLabel);
        labelPanel.add(colourSelectionLabel);
        labelPanel.add(betLabel);
        return labelPanel;
    }

    // EFFECTS: creates the buttons representing the colour selection for the game of roulette
    public JPanel setUpColourSelectionButtons() {
        JPanel colourButtonPanel = new JPanel(new GridLayout(1,2));
        JButton redButton = new JButton("Red");
        redButton.setBackground(Color.RED);
        redButton.addActionListener(e -> addColourSelection("red"));
        JButton blackButton = new JButton("Black");
        blackButton.setBackground(Color.BLACK);
        blackButton.addActionListener(e -> addColourSelection("black"));
        colourButtonPanel.add(redButton);
        colourButtonPanel.add(blackButton);
        return colourButtonPanel;
    }

    // MODIFIES: this
    // EFFECTS; adds the player's selected colour selection if not already present
    public void addColourSelection(String colour) {
        if (!rouletteColourSelection.contains(colour)) {
            rouletteColourSelection.add(colour);
        }
    }

    // EFFECTS: creates the buttons representing each of the possible roulette wheel numbers, with background colour
    // black if it is even and red if it is odd
    public JPanel setUpRouletteBoardButtons() {
        JPanel numberButtons = new JPanel(new GridLayout(6,7));
        for (int i = 0; i < 36; i++) {
            JButton boardButton = new JButton(String.valueOf(i));
            int finalI = i;
            boardButton.addActionListener(e -> addRouletteNumberSelection(finalI));
            if ((i % 2) == 0) {
                boardButton.setBackground(Color.BLACK);
            } else {
                boardButton.setBackground(Color.RED);
            }
            numberButtons.add(boardButton);
        }
        return numberButtons;
    }

    // MODIFIES: this
    // EFFECTS: adds the roulette number selection
    public void addRouletteNumberSelection(int num) {
        if (!rouletteSelection.contains(num)) {
            rouletteSelection.add(num);
        }
    }

    // MODIFIES: this
    // EFFECTS: if the player has sufficient balance and inputs a valid bet size, creates a new rouletteround
    public void setRouletteBet(ArrayList<Integer> numberSelectionList, int curBet, List<String> colourSelection) {
        if (curBet > casino.getPlayerBalance()) {
            JOptionPane.showMessageDialog(null, "You do not have sufficient balance!");
        } else if (curBet > 0) {
            rouletteRound = new RouletteRound(numberSelectionList, curBet, colourSelection);
            setUpRouletteResultsPanel();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid bet amount!");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates an inventory panel showing all the prizes owned by the player
    public void setUpInventoryPanel() {
        JPanel inventoryPanel = new JPanel(new GridLayout(4,1));
        JLabel inventoryLabel = new JLabel("Here are all the prizes you own!\n Click on any prize to sell it!");

        inventoryLabel.setHorizontalAlignment(JLabel.CENTER);

        inventoryPrizesPanel = new JPanel(new GridLayout(3,3));
        JPanel inventorySubsetPanel = new JPanel(new GridLayout(2, 1));
        JLabel inventorySubsetLabel = new JLabel("Select any of the following buttons to "
                + "highlight prizes of that type!: ");
        inventorySubsetLabel.setHorizontalAlignment(JLabel.CENTER);
        JPanel inventorySubsetButtonPanel = createAnimalTypeButtons();
        inventorySubsetPanel.add(inventorySubsetLabel);
        inventorySubsetPanel.add(inventorySubsetButtonPanel);

        inventoryPanel.add(inventoryLabel);
        inventoryPanel.add(inventoryPrizesPanel);
        inventoryPanel.add(inventorySubsetPanel);
        inventoryPanel.add(createHomeButton());
        homeContainer.add(inventoryPanel, "Inventory");
    }

    // EFFECTS: creates buttons for the subsets of prizes to be viewed
    public JPanel createAnimalTypeButtons() {
        JPanel inventorySubsetButtonPanel = new JPanel(new GridLayout(1, 9));
        createHalfAnimalSubsetButtons(inventorySubsetButtonPanel);
        createRestAnimalSubsetButtons(inventorySubsetButtonPanel);

        return inventorySubsetButtonPanel;
    }

    // EFFECTS: creates first half of animal subset buttons with action listeners
    public void createHalfAnimalSubsetButtons(JPanel inventorySubsetButtonPanel) {
        JButton elephantButton = new JButton("Elephant");
        JButton giraffeButton = new JButton("Giraffe");
        JButton rhinoButton = new JButton("Rhino");
        JButton trexButton = new JButton("T-Rex");
        JButton dogButton = new JButton("Dog");
        elephantButton.addActionListener(e -> showSubsetOfPrizes("Elephant"));
        giraffeButton.addActionListener(e -> showSubsetOfPrizes("Giraffe"));
        rhinoButton.addActionListener(e -> showSubsetOfPrizes("Rhino"));
        trexButton.addActionListener(e -> showSubsetOfPrizes("T-Rex"));
        dogButton.addActionListener(e -> showSubsetOfPrizes("Dog"));
        inventorySubsetButtonPanel.add(elephantButton);
        inventorySubsetButtonPanel.add(giraffeButton);
        inventorySubsetButtonPanel.add(rhinoButton);
        inventorySubsetButtonPanel.add(trexButton);
        inventorySubsetButtonPanel.add(dogButton);
    }

    // EFFECTS: creates second half of animal subset buttons with action listeners
    public void createRestAnimalSubsetButtons(JPanel inventorySubsetButtonPanel) {
        JButton catButton = new JButton("Cat");
        JButton hamsterButton = new JButton("Hamster");
        JButton gerbilButton = new JButton("Gerbil");
        JButton raccoonButton = new JButton("Raccoon");
        catButton.addActionListener(e -> showSubsetOfPrizes("Cat"));
        hamsterButton.addActionListener(e -> showSubsetOfPrizes("Hamster"));
        gerbilButton.addActionListener(e -> showSubsetOfPrizes("Gerbil"));
        raccoonButton.addActionListener(e -> showSubsetOfPrizes("Raccoon"));

        inventorySubsetButtonPanel.add(catButton);
        inventorySubsetButtonPanel.add(hamsterButton);
        inventorySubsetButtonPanel.add(gerbilButton);
        inventorySubsetButtonPanel.add(raccoonButton);
    }

    // MODIFIES: this
    // EFFECTS: highlights the selected subset of prizes in the inventory panel
    public void showSubsetOfPrizes(String type) {
        for (Component c : inventoryPrizesPanel.getComponents()) {
            if (c instanceof JButton) {
                if (((JButton) c).getText().contains(type)) {
                    ((JButton) c).setOpaque(true);
                    c.setBackground(Color.BLUE);
                }
            }
        }

        homeLayout.show(homeContainer, "Inventory");
    }

    // MODIFIES: this
    // EFFECTS: creates the prizes panel with the list of prizes available for purchase, a refresh prizes button and a
    // home button
    public void setUpPrizesPanel() {
        JPanel prizesContainer = new JPanel(new GridLayout(3, 1));
        JLabel prizesLabel = new JLabel("Click on the prize you would like to purchase!");
        prizesLabel.setHorizontalAlignment(JLabel.CENTER);
        JPanel prizesPanel = new JPanel(new GridLayout(3,3));
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(createRefreshShopButton());
        buttonPanel.add(createHomeButton());

        generatePrizeList(prizesPanel);

        prizesContainer.add(prizesLabel);
        prizesContainer.add(prizesPanel);
        prizesContainer.add(buttonPanel);
        homeContainer.add(prizesContainer, "Prizes");
    }

    // MODIFIES: this, casino
    // EFFECTS: shows the player their current balance and a input box for a deposit, which adds the amount to
    // their balance if submit button is pressed
    public void setUpBalancePanel() {
        JPanel balancePanel = new JPanel(new GridLayout(5,1));

        JLabel curBalance = createCenterLabel("Your current balance is: $" + casino.getPlayerBalance());
        balancePanel.add(curBalance);

        JLabel addBalance = createCenterLabel("How much would you like to deposit?");
        balancePanel.add(addBalance);

        SpinnerModel value = new SpinnerNumberModel(0,0,null,1);
        JSpinner newBalance = new JSpinner(value);
        JButton submitButton = new JButton("Submit");

        ActionListener updateBalance = e -> curBalance.setText("Your current balance is: $"
                + casino.getPlayerBalance());

        submitButton.addActionListener(e -> casino.addPlayerBalance((Integer) newBalance.getValue()));
        submitButton.addActionListener(updateBalance);

        Timer timer =  new Timer(1, updateBalance);
        timer.start();

        balancePanel.add(newBalance);
        balancePanel.add(submitButton);
        balancePanel.add(createHomeButton());

        homeContainer.add(balancePanel, "Balance");
    }

    // MODIFIES: this
    // EFFECTS: creates a games panel with a blackjack, roulette, and home button, shows the corresponding
    // page when selected
    public void setUpGamesPanel() {
        gamesLayout = new CardLayout(5,5);
        gamesContainer = new JPanel(gamesLayout);

        JPanel gamesPanel = new JPanel(new GridLayout(3,1));
        JButton blackjackButton = new JButton("Blackjack!");
        blackjackButton.addActionListener(e -> gamesLayout.show(gamesContainer, "Blackjack"));
        gamesPanel.add(blackjackButton);

        JButton rouletteButton = new JButton("Roulette!");
        rouletteButton.addActionListener(e -> gamesLayout.show(gamesContainer, "Roulette"));
        gamesPanel.add(rouletteButton);

        gamesPanel.add(createHomeButton());

        JPanel blackjackPanel = setUpBlackjackPanel();

        gamesContainer.add(blackjackPanel, "Blackjack");

        JPanel roulettePanel = setUpRoulettePanel();
        gamesContainer.add(roulettePanel, "Roulette");

        gamesContainer.add(gamesPanel, "Games");
        gamesLayout.show(gamesContainer, "Games");

        homeContainer.add(gamesContainer, "Games");
    }

    // EFFECTS: creates the home button that routes back to the home page when pressed
    public JButton createHomeButton() {
        JButton homeButton = new JButton("Home");
        homeButton.addActionListener(e -> homeLayout.show(homeContainer, "Buttons"));
        return homeButton;
    }

    // EFFECTS: creates the welcome label with 100x50 and center horizontal alignment
    public void createLabel() {
        JLabel label = new JLabel("Welcome to the casino, what would you like to do?");
        label.setPreferredSize(new Dimension(100,50));
        label.setHorizontalAlignment(JLabel.CENTER);
        homeContainer.add(label);
    }

    // EFFECTS: creates buttons for the home page, games, prizes, balance, inventory, save, and load
    public JPanel createButtons() {
        JPanel buttonFrame = new JPanel();
        buttonFrame.setLayout(new GridLayout(3,3,5,5));
        JButton gamesButton = new JButton("Games!");
        gamesButton.addActionListener(e -> homeLayout.show(homeContainer, "Games"));
        JButton prizesButton = new JButton("Prizes!");
        prizesButton.addActionListener(e -> homeLayout.show(homeContainer, "Prizes"));
        JButton balanceButton = new JButton("Balance!");
        balanceButton.addActionListener(e -> homeLayout.show(homeContainer, "Balance"));
        JButton inventoryButton = new JButton("Inventory!");
        inventoryButton.addActionListener(e -> homeLayout.show(homeContainer, "Inventory"));
        JButton saveButton = new JButton("Save!");
        saveButton.addActionListener(e -> saveGameState());
        JButton loadButton = new JButton("Load!");
        loadButton.addActionListener(e -> loadGameState());
        buttonFrame.add(gamesButton);
        buttonFrame.add(prizesButton);
        buttonFrame.add(balanceButton);
        buttonFrame.add(inventoryButton);
        buttonFrame.add(saveButton);
        buttonFrame.add(loadButton);
        return buttonFrame;
    }

    // EFFECTS: creates a generic center label with the provided text
    public JLabel createCenterLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    // MODIFIES: this
    // EFFECTS: creates a panel that displays the prizes available in the shop as a button
    public void generatePrizeList(JPanel prizePanel) {
        List<Prize> prizeList = prizeShop.getPrizeList();
        prizeButtonList = new ArrayList<>();
        int i = 1;
        for (Prize prize : prizeList) {
            String threeLines = "Prize #" + i
                    + "\nAnimal Type: " + prize.getAnimalType()
                    + "\nPrice: " + prize.getValue();
            JButton button = new JButton("<html>" + threeLines.replaceAll("\\n", "<br>")
                    + "</html>");
            button.addActionListener(e -> purchasePrize(button, prize));
            prizeButtonList.add(button);
            prizePanel.add(button);
            i++;
        }
    }

    // MODIFIES: prizeshop, this
    // EFFECTS: adds the purchased prize to the displayed inventory and displayed balance
    public void purchasePrize(JButton button, Prize prize) {
        if (prizeShop.buyPrize(prize)) {
            button.setText("Purchased");
            addPrizeToInventory(prize);
            for (ActionListener al : button.getActionListeners()) {
                button.removeActionListener(al);
            }
        } else {
            JOptionPane.showMessageDialog(null, "You don't have enough funds!");
        }
    }

    // EFFECTS: creates a button that refreshes the shop with new prizes when pressed
    public JButton createRefreshShopButton() {
        JButton refreshButton = new JButton("Refresh shop");
        refreshButton.addActionListener(e -> showNewPrizes());
        return refreshButton;
    }

    // MODIFIES: prizeshop, this
    // EFFECTS: resets the prize buttons and replaces them with the new prize buttons
    public void showNewPrizes() {
        prizeShop.generatePrizes();
        List<Prize> prizeList = prizeShop.getPrizeList();
        int i = 0;
        for (JButton button : prizeButtonList) {
            Prize prize = prizeList.get(i);
            for (ActionListener al : button.getActionListeners()) {
                button.removeActionListener(al);
            }
            String threeLines = "Prize #" + (i + 1)
                    + "\nAnimal Type: " + prize.getAnimalType()
                    + "\nPrice: " + prize.getValue();
            button.setText("<html>" + threeLines.replaceAll("\\n", "<br>")
                    + "</html>");
            button.addActionListener(e -> purchasePrize(button, prize));
            i++;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds purchased prize to the display inventory
    public void addPrizeToInventory(Prize prize) {
        JButton singlePrize = new JButton();
        String threeLines = "Animal Type: " + prize.getAnimalType() + "\nPrice: " + prize.getValue();
        singlePrize.setText("<html>" + threeLines.replaceAll("\\n", "<br>") + "</html>");
        singlePrize.addActionListener(e -> prizeShop.sellPrize(prize));
        singlePrize.addActionListener(e -> singlePrize.setText("Sold!"));
        inventoryPrizesPanel.add(singlePrize);
    }
}
