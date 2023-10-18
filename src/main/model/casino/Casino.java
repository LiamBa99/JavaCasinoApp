package model.casino;

import java.util.*;

import model.prizeshop.*;
import org.json.*;
import persistence.Writable;


// represents the casino the user enters
// contains the user's balance
public class Casino implements Writable {
    private int playerBalance; // represents the player's balance
    private final ArrayList<Prize> inventory; // represents the player's inventory of prizes

    // EFFECTS: creates a casino with the player's balance and an empty inventory
    public Casino(int playerBalance) {
        this.playerBalance = playerBalance;
        inventory = new ArrayList<>();
    }

    // EFFECTS: returns this as JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("balance", playerBalance);
        json.put("inventory", inventoryToJson());
        return json;
    }

    // EFFECTS: returns the prizes in the inventory as a JSON array
    public JSONArray inventoryToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Prize prize : inventory) {
            jsonArray.put(prize.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns the player's current balance
    public int getPlayerBalance() {
        return playerBalance;
    }

    // REQUIRES: balToAdd >= 0
    // MODIFIES: this
    // EFFECTS: adds value to the player's current balance
    public void addPlayerBalance(int balToAdd) {
        playerBalance += balToAdd;
    }

    // REQUIRES: balToDeduct >= 0
    // MODIFIES: this
    // EFFECTS: deducts value from the player's current balance,
    // cannot deduct more than current balance
    // returns true if successful, otherwise false
    public boolean deductPlayerBalance(int balToDeduct) {
        if (balToDeduct <= playerBalance) {
            playerBalance -= balToDeduct;
        } else {
            return false;
        }
        return true;
    }

    // REQUIRES: prize is not null
    // MODIFIES: this
    // EFFECTS: adds a given prize to the players inventory if not already present
    // returns true if successfully added, false otherwise
    public boolean addPrize(Prize prize) {
        return (inventory.add(prize));
    }

    // REQUIRES: prize is not null
    // MODIFIES: this
    // EFFECTS: removes a prize from the players inventory if present
    // returns true if successfully removed, false otherwise
    public boolean removePrize(Prize prize) {
        if (inventory.contains(prize)) {
            inventory.remove(prize);
        } else {
            return false;
        }
        return true;
    }

    // EFFECTS: returns the prize list
    public ArrayList<Prize> getInventory() {
        return inventory;
    }
}
