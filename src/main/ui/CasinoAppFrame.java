package ui;

import model.blackjack.*;
import model.casino.*;
import model.prizeshop.*;
import model.roulette.*;
import persistence.*;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CasinoAppFrame extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Casino casino;
    private Shop prizeShop;
    private BlackjackGame blackjackGame;
    private BlackjackRound blackjackRound;
    private CardLayout blackjackLayout;
    private JPanel dealerHandCards;
    private JPanel playerHandCards;
    private JLabel dealerHandValue;
    private JLabel playerHandValue;
    private List<JButton> prizeButtonList;
    private JPanel inventoryPrizesPanel;
    private JPanel blackjackContainer;
    private static CardLayout homeLayout;
    private static JPanel homeContainer;

    public CasinoAppFrame() {
        casino = new Casino(0);
        prizeShop = new Shop(casino);
        this.setSize(WIDTH,HEIGHT); // set the size of the frame
        this.setTitle("Welcome to the Casino!"); // set the title of the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close program when frame closes
        this.setResizable(false); // stop the frame from being resized
        frameSetUp();
        this.setVisible(true);
    }

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

    public void panelSetUp() {
        setUpGamesPanel();
        setUpBalancePanel();
        setUpPrizesPanel();
        setUpInventoryPanel();
    }

    public JPanel setUpBlackjackPanel() {
        blackjackLayout = new CardLayout();
        blackjackContainer = new JPanel(blackjackLayout);
        blackjackContainer.add(setUpDeckPanel(), "Decks");
        blackjackContainer.add(setUpBetPanel(), "Bet");
        blackjackContainer.add(setUpPlayBlackjackPanel(), "PlayBJ");

        blackjackLayout.show(blackjackContainer, "Decks");
        return blackjackContainer;
    }

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

    public void resetBlackjack(boolean again) {
        if (again) {
            blackjackContainer.removeAll();
            setUpGamesPanel();
            homeLayout.show(homeContainer, "Games");
            blackjackLayout.show(blackjackContainer, "PlayBJ");
        } else {
            blackjackContainer.removeAll();
            setUpGamesPanel();
        }
    }

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

    public void dealNextCard() {
        if (blackjackRound.getPlayerCardValue() == 0) {
            JOptionPane.showMessageDialog(null, "You must deal cards first!");
        } else {
            Card newCard = blackjackRound.dealACard(false);
            if (blackjackRound.getPlayerCardValue() > 21) {
                blackjackContainer.add(setUpBlackjackResultsPanel(), "Results");
                blackjackLayout.show(blackjackContainer, "Results");
            } else {
                JLabel newCardLabel = new JLabel(newCard.getCardValue() + "\nof " + newCard.getSuit());

                playerHandCards.add(newCardLabel);
                playerHandValue.setText("Value: " + blackjackRound.getPlayerCardValue());
                blackjackLayout.show(blackjackContainer, "Bet");
                blackjackLayout.show(blackjackContainer, "PlayBJ");
            }
        }
    }

    public void dealUntilComplete() {
        while (blackjackRound.getDealerCardValue() < 17) {
            Card nextCard = blackjackRound.dealACard(true);
            JLabel newCardLabel = new JLabel(nextCard.getCardValue() + "\nof " + nextCard.getSuit());
            dealerHandCards.add(newCardLabel);
            dealerHandValue.setText("Value: " + blackjackRound.getDealerCardValue());
            blackjackLayout.show(blackjackContainer, "Bet");
            blackjackLayout.show(blackjackContainer, "PlayBJ");
        }
        blackjackContainer.add(setUpBlackjackResultsPanel(), "Results");
        blackjackLayout.show(blackjackContainer, "Results");
    }

    public void showFirstCards(JPanel dealerHandPanel, JPanel playerHandPanel, JButton dealButton) {
        dealerHandCards = new JPanel(new GridLayout());
        playerHandCards = new JPanel(new GridLayout());

        blackjackRound.dealFirstCards();
        for (Card c : blackjackRound.getDealerHand()) {
            JLabel dealerCardLabel = new JLabel(c.getCardValue() + "\nof " + c.getSuit());
            dealerHandCards.add(dealerCardLabel);
        }
        dealerHandValue = new JLabel("Value: " + blackjackRound.getDealerCardValue());

        for (Card c : blackjackRound.getPlayerHand()) {
            JLabel playerCardLabel = new JLabel(c.getCardValue() + "\nof " + c.getSuit());
            playerHandCards.add(playerCardLabel);
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

    public JPanel setUpBetPanel() {
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

    // TODO: move to logic class
    public void setBlackjackBet(int curBet) {
        if (curBet > casino.getPlayerBalance()) {
            JOptionPane.showMessageDialog(null, "You do not have sufficient balance!");
        } else if (curBet > 0) {
            blackjackGame.setCurrentBet(curBet);
            blackjackRound = new BlackjackRound(blackjackGame);
            blackjackLayout.show(blackjackContainer, "PlayBJ");
        } else {
            JOptionPane.showMessageDialog(null, "That is an invalid bet size!");
        }

    }

    public JPanel setUpDeckPanel() {
        JPanel blackjackDeckPanel = new JPanel(new GridLayout(5,1));

        JLabel numDeckQuestion = new JLabel("How many decks would you like to use? Max is 4:");
        JLabel welcomeLabel = new JLabel("Welcome to blackjack!");
        SpinnerModel value = new SpinnerNumberModel(0,0,4,1);
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

    // TODO: move to logic class
    public void createBlackJackGame(int numDecks) {
        if (numDecks <= 4 && numDecks > 0) {
            blackjackGame = new BlackjackGame(numDecks, casino);
            blackjackLayout.show(blackjackContainer, "Bet");
        } else {
            JOptionPane.showMessageDialog(null, "That is an incorrect number of decks!");
        }
    }

    public JPanel setUpRoulettePanel() {
        CardLayout rouletteLayout = new CardLayout();
        JPanel roulettePanel = new JPanel(rouletteLayout);
        roulettePanel.add(new JLabel("Welcome to roulette"));
        return roulettePanel;
    }

    public void setUpInventoryPanel() {
        JPanel inventoryPanel = new JPanel(new GridLayout(3,1));
        JLabel inventoryLabel = new JLabel("Here are all the prizes you own!");
        inventoryLabel.setHorizontalAlignment(JLabel.CENTER);

        inventoryPrizesPanel = new JPanel(new GridLayout(3,3));

        inventoryPanel.add(inventoryLabel);
        inventoryPanel.add(inventoryPrizesPanel);
        inventoryPanel.add(createHomeButton());
        homeContainer.add(inventoryPanel, "Inventory");
    }

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

    public void setUpBalancePanel() {
        JPanel balancePanel = new JPanel(new GridLayout(5,1));

        JLabel curBalance = createCenterLabel("Your current balance is: $" + casino.getPlayerBalance());
        balancePanel.add(curBalance);

        JLabel addBalance = createCenterLabel("How much would you like to deposit?");
        balancePanel.add(addBalance);

        SpinnerModel value = new SpinnerNumberModel(0,0,null,1);
        JSpinner newBalance = new JSpinner(value);
        JButton submitButton = new JButton("Submit");

        ActionListener updateBalance = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curBalance.setText("Your current balance is: $" + casino.getPlayerBalance());
            }
        };

        submitButton.addActionListener(e -> casino.addPlayerBalance((Integer) newBalance.getValue()));
        submitButton.addActionListener(updateBalance);

        Timer timer =  new Timer(1, updateBalance);
        timer.start();

        balancePanel.add(newBalance);
        balancePanel.add(submitButton);
        balancePanel.add(createHomeButton());

        homeContainer.add(balancePanel, "Balance");
    }

    public void setUpGamesPanel() {
        CardLayout gamesLayout = new CardLayout(5,5);
        JPanel gamesContainer = new JPanel(gamesLayout);

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

    public JButton createHomeButton() {
        JButton homeButton = new JButton("Home");
        homeButton.addActionListener(e -> homeLayout.show(homeContainer, "Buttons"));
        return homeButton;
    }

    public void createLabel() {
        JLabel label = new JLabel("Welcome to the casino, what would you like to do?");
        label.setPreferredSize(new Dimension(100,50));
        label.setHorizontalAlignment(JLabel.CENTER);
        homeContainer.add(label);
    }

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
        JButton loadButton = new JButton("Load!");
        buttonFrame.add(gamesButton);
        buttonFrame.add(prizesButton);
        buttonFrame.add(balanceButton);
        buttonFrame.add(inventoryButton);
        buttonFrame.add(saveButton);
        buttonFrame.add(loadButton);
        return buttonFrame;
    }

    public JLabel createCenterLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

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

    // TODO: move to new logic class
    public void purchasePrize(JButton button, Prize prize) {
        if (prizeShop.buyPrize(prize)) {
            button.setText("Purchased");
            addPrizeToInventory(prize);
        } else {
            JOptionPane.showMessageDialog(null, "You don't have enough funds!");
        }
    }

    public JButton createRefreshShopButton() {
        JButton refreshButton = new JButton("Refresh shop");
        refreshButton.addActionListener(e -> showNewPrizes());
        return refreshButton;
    }

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

    // TODO: move to new logic class
    public void addPrizeToInventory(Prize prize) {
        JTextField singlePrize = new JTextField();
        singlePrize.setText("\nAnimal Type: " + prize.getAnimalType() + "\nPrice: " + prize.getValue());
        inventoryPrizesPanel.add(singlePrize);
    }


}
