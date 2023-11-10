package model.prizeshop;

import java.util.*;
import model.casino.Casino;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;


// represents a shop of prizes that allows the player to buy based on their current funds
public class Shop implements Writable {
    List<Prize> prizeList; // represents the list of prizes available for purchase
    Casino casino; // represents the casino the player is currently playing at
    List<String> animalTypeList; // represents the possible types of animals

    // EFFECTS: constructs a shop of prizes with the inputted casino, an empty prize list and the
    // pre-made set of animal types
    public Shop(Casino casino) {
        this.casino = casino;
        animalTypeList = new ArrayList<>();
        animalTypeList.add("Elephant");
        animalTypeList.add("Giraffe");
        animalTypeList.add("Rhino");
        animalTypeList.add("T-Rex");
        animalTypeList.add("Dog");
        animalTypeList.add("Cat");
        animalTypeList.add("Hamster");
        animalTypeList.add("Gerbil");
        animalTypeList.add("Raccoon");
        generatePrizes();
    }

    // EFFECTS: returns this as JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Prizes",prizeListToJson());
        return json;
    }

    // EFFECTS: returns the prize list as a json Array
    public JSONArray prizeListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Prize prize : prizeList) {
            jsonArray.put(prize.toJson());
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: randomly generates 9 prizes and adds them to the prizeList
    public void generatePrizes() {
        prizeList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            int value = random.nextInt(10000);
            int animalType = random.nextInt(8);
            prizeList.add(new Prize(value, animalTypeList.get(animalType), i + 1));
        }
    }

    // REQUIRES: index >= 0
    // MODIFIES: this, casino
    // EFFECTS: removes the selected prize from the prize list
    // deducts the value from the user's casino balance
    // adds the prize to the user's inventory
    // returns false if the user does not have enough balance to purchase
    // returns true if the user successfully purchases the prize
    public boolean buyPrize(int index) {
        boolean purchased = false;
        if (casino.getPlayerBalance() > prizeList.get(index).getValue()) {
            Prize purchasedPrize = prizeList.remove(index);
            casino.getInventory().add(purchasedPrize);
            casino.deductPlayerBalance(purchasedPrize.getValue());
            purchased = true;
        }
        return purchased;
    }

    // MODIFIES: this, casino
    // EFFECTS: removes the selected prize from the prize list
    // deducts the value from the user's casino balance
    // adds the prize to the user's inventory
    // returns false if the user does not have enough balance to purchase
    // returns true if the user successfully purchases the prize
    public boolean buyPrize(Prize prize) {
        boolean purchased = false;
        if (casino.getPlayerBalance() > prize.getValue()) {
            prizeList.remove(prize);
            casino.getInventory().add(prize);
            casino.deductPlayerBalance(prize.getValue());
            purchased = true;
        }
        return purchased;
    }


    // REQUIRES: prize is not null
    // MODIFIES: casino
    // EFFECTS: removes the prize from the inventory, sells the prize according to its value
    // adds the sell value to the players current balance
    public void sellPrize(Prize prize) {
        int prizeIndex = casino.getInventory().indexOf(prize);
        Prize prizeToSell = casino.getInventory().remove(prizeIndex);
        casino.addPlayerBalance(prizeToSell.getValue());
    }

    // EFFECTS: returns the list of prizes available for purchase
    public List<Prize> getPrizeList() {
        return prizeList;
    }

    // EFFECTS: returns the player's current casino
    public Casino getCurrentCasino() {
        return casino;
    }

    // MODIFIES: this
    // EFFECTS: sets the prizelist
    public void setPrizeList(List<Prize> prizeList) {
        this.prizeList = prizeList;
    }

    // MODIFIES: this
    // EFFECTS: sets the casino
    public void setCasino(Casino casino) {
        this.casino = casino;
    }

    // EFFECTS: gets the casino
    public Casino getCasino() {
        return casino;
    }
}
