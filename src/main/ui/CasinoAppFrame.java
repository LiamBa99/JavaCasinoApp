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
    private List<JButton> prizeButtonList;
    private JPanel inventoryPrizesPanel;
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

        JPanel gamesPanel = new JPanel();
        gamesPanel.add(createHomeButton());
        JButton blackjackButton = new JButton("Blackjack!");
        blackjackButton.addActionListener(e -> gamesLayout.show(gamesContainer, "Blackjack"));
        gamesPanel.add(blackjackButton);

        JButton rouletteButton = new JButton("Roulette!");
        rouletteButton.addActionListener(e -> gamesLayout.show(gamesContainer, "Roulette"));
        gamesPanel.add(rouletteButton);

        JPanel blackjackPanel = new JPanel();
        blackjackPanel.add(new JLabel("Welcome to blackjack"));
        gamesContainer.add(blackjackPanel, "Blackjack");

        JPanel roulettePanel = new JPanel();
        roulettePanel.add(new JLabel("Welcome to roulette"));
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

    public void addPrizeToInventory(Prize prize) {
        JTextField singlePrize = new JTextField();
        singlePrize.setText("\nAnimal Type: " + prize.getAnimalType() + "\nPrice: " + prize.getValue());
        inventoryPrizesPanel.add(singlePrize);
    }


}
