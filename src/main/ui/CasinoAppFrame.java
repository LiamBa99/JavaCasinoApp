package ui;

import javax.swing.*;
import java.awt.*;

public class CasinoAppFrame extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    JButton gamesButton;
    JButton prizesButton;
    JButton balanceButton;
    JButton inventoryButton;
    JButton saveButton;
    JButton loadButton;

    public CasinoAppFrame() {
        this.setSize(WIDTH,HEIGHT); // set the size of the frame
        this.setTitle("Welcome to the Casino!"); // set the title of the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close program when frame closes
        this.setResizable(false); // stop the frame from being resized
        this.setLayout(new BorderLayout());
        frameSetUp();
        this.setVisible(true);
    }

    public void frameSetUp() {
        createLabel();
        JPanel buttonFrame = createButtons();
        this.add(buttonFrame, BorderLayout.CENTER);
    }

    public void createLabel() {
        JLabel label = new JLabel("Welcome to the casino, what would you like to do?");
        label.setPreferredSize(new Dimension(100,50));
        label.setHorizontalAlignment(JLabel.CENTER);
        this.add(label,BorderLayout.NORTH);
    }

    public JPanel createButtons() {
        JPanel buttonFrame = new JPanel();
        buttonFrame.setLayout(new GridLayout(3,3,5,5));
        gamesButton = new JButton("Games!");
        prizesButton = new JButton("Prizes!");
        balanceButton = new JButton("Balance!");
        inventoryButton = new JButton("Inventory!");
        saveButton = new JButton("Save!");
        loadButton = new JButton("Load!");
        buttonFrame.add(gamesButton);
        buttonFrame.add(prizesButton);
        buttonFrame.add(balanceButton);
        buttonFrame.add(inventoryButton);
        buttonFrame.add(saveButton);
        buttonFrame.add(loadButton);
        return buttonFrame;
    }

}
