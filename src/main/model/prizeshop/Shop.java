package model.prizeshop;

import java.util.*;
import model.casino.Casino;


// represents a shop of prizes that allows the player to buy based on their current funds
public class Shop {
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


    // MODIFIES: this
    // EFFECTS: randomly generates 9 prizes and adds them to the prizeList
    public void generatePrizes() {
        prizeList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            int[] colour = {random.nextInt(255),random.nextInt(255),random.nextInt(255)};
            int value = random.nextInt(10000);
            int animalType = random.nextInt(8);
            prizeList.add(new Prize(value, animalTypeList.get(animalType), colour));
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
}
