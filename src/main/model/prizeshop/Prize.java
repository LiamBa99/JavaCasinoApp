package model.prizeshop;

// Represents an individual prize, consisting of its value, animal type, and rgb colour
public class Prize {
    int value; // represents the value of the prize
    String animalType; // represents the animal type of the prize
    int[] colour; // represents the rgb values of the prize's colour

    // EFFECTS: constructs a prize according to its inputted value, animalType and RGB colour
    public Prize(int value, String animalType, int[] colour) {
        this.value = value;
        this.animalType = animalType;
        this.colour = colour;
    }

    // EFFECTS: returns the value of the prize
    public int getValue() {
        return value;
    }

    // EFFECTS: returns the animal type
    public String getAnimalType() {
        return animalType;
    }

    // EFFECTS: returns the colour of the animal
    public int[] getColour() {
        return colour;
    }
}
