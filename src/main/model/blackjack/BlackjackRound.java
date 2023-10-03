package model.blackjack;

public class BlackjackRound {

    private boolean win; // represents whether the player has won the round
    private int playerCardValue;
    private int dealerCardValue;

    // EFFECTS: constructs a round of blackjack with win set to false,
    // and player and dealer card value set to 0
    public BlackjackRound() {

    }

    // MODIFIES: this
    // EFFECTS: removes a card from the dealer's deck at random and returns it
    public Card chooseACard() {
        return null;
    }


    // EFFECTS: prints the card randomly removed from the dealer list either on the dealer side or the player side
    public void showACard(boolean dealer) {

    }

    // MODIFIES: this
    // EFFECTS: sets this.win to true if the user won the round, false if the dealer won
    public void checkWin() {

    }

    // EFFECTS: asks the user if they would like to hit, stand, split, or double.
    // return 0,1,2,3 respectively according to the user choice
    public int getUserChoice() {
        return 0;
    }
}
