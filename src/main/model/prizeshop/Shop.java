package model.prizeshop;

import java.util.*;
import model.casino.Casino;
import model.inventory.Inventory;


// represents a shop of prizes that allows the player to buy based on their current funds
public class Shop {
    List<Prize> prizeList; // represents the list of prizes available for purchase
    Casino casino; // represents the casino the player is currently playing at

    // EFFECTS: constructs a shop of prizes, setting playerFunds to equal the user's casino balance
    public Shop(int playerFunds) {

    }

    // MODIFIES: this
    // EFFECTS: randomly generates a numPrizes amount of prizes and adds them to the prizeList
    public void generatePrizes(int numPrizes) {

    }

    // MODIFIES: this
    // EFFECTS: creates a new set of prizes, asks user if they are sure they want to refresh
    public void refreshPrizes(int numPrizes) {

    }

    // EFFECTS: shows the user the list of prizes to be chosen from
    // including their characteristics and value
    public void showPrizes() {

    }

    // REQUIRES: index >= 0
    // MODIFIES: this, casino, inventory
    // EFFECTS: removes the selected prize from the prize list
    // deducts the value from the user's casino balance
    // adds the prize to the user's inventory
    // returns false if the user does not have enough balance to purchase
    // returns true if the user successfully purchases the prize
    public boolean buyPrize(int index) {
        return false;
    }


    // REQUIRES: prize is not null
    // MODIFIES: casino, inventory
    // EFFECTS: removes the prize from the inventory, sells the prize according to 60% of its value
    // adds the sell value to the players current balance
    public boolean sellPrize(Prize prize) {
        return false;
    }
}
