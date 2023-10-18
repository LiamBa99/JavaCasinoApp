package model.prizeshop;

import org.json.JSONObject;
import persistence.Writable;

// Represents an individual prize, consisting of its value, animal type, and rgb colour
public class Prize implements Writable {
    int index;
    int value; // represents the value of the prize
    String animalType; // represents the animal type of the prize

    // EFFECTS: constructs a prize according to its inputted value, animalType and RGB colour
    public Prize(int value, String animalType, int index) {
        this.value = value;
        this.animalType = animalType;
        this.index = index;
    }

    // EFFECTS: returns the value of the prize
    public int getValue() {
        return value;
    }

    // EFFECTS: returns the animal type
    public String getAnimalType() {
        return animalType;
    }


    // EFFECTS: returns the prize object as a json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("value", value);
        json.put("type", animalType);
        json.put("index",index);
        return json;
    }

}
