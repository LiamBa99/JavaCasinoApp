package model.casino;

import java.util.*;

import model.prizeshop.*;


// represents the casino the user enters
// contains the user's balance
public class Casino {
    private int playerBalance;
    private List<Prize> inventory;

    // EFFECTS: creates a casino with the player's balance and an empty inventory
    public Casino(int playerBalance) {

    }

    // TODO: this
    // EFFECTS: saves the player's current casino session including balance and inventory
    public void saveCasinoSession() {

    }

    // EFFECTS: returns the player's current balance
    public int getPlayerBalance() {
        return 0;
    }

    // REQUIRES: balToAdd >= 0
    // MODIFIES: this
    // EFFECTS: adds value to the player's current balance
    public void addPlayerBalance(int balToAdd) {

    }

    // REQUIRES: balToDeduct >= 0
    // MODIFIES: this
    // EFFECTS: deducts value from the player's current balance,
    // cannot deduct more than current balance
    // returns true if successful, otherwise false
    public boolean deductPlayerBalance(int balToDeduct) {
        return false;
    }

    // REQUIRES: prize is not null
    // MODIFIES: this
    // EFFECTS: adds a given prize to the players inventory if not already present
    // returns true if successfully added, false otherwise
    public boolean addPrize(Prize prize) {
        return false;
    }

    // REQUIRES: prize is not null
    // MODIFIES: this
    // EFFECTS: removes a prize from the players inventory if present
    // returns true if successfully removed, false otherwise
    public boolean removePrize(Prize prize) {
        return false;
    }

    // EFFECTS: returns the prize list
    public List<Prize> getPrizeList() {
        return null;
    }
}
