package model.slotgame;

// represents a game of a slot machine
public class SlotGame {

    private int betSize;
    private int pityPercentage;

    // EFFECTS: constructs a SlotGame with base level pityPercentage
    public SlotGame() {

    }

    // REQUIRES: betSize is not null
    // MODIFIES: betSize
    // EFFECT: plays a round of the slot machine
    // stops when player exits, player is out of money, or player changes bet
    public void playARound(int betSize) {

    }

    // MODIFIES: this
    // EFFECTS: sets the betSize to the newBet
    public void setBetSize(int newBet) {

    }

    // EFFECTS: asks the player for their betSize
    public void askPlayerBetSize() {

    }

    // EFFECTS: returns the current betSize
    public int getBetSize() {
        return 0;
    }
}
