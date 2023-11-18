package model.blackjack;

import java.util.*;

// represents a set of 52 cards
public class CardDeck {
    private final List<List<String>> cardImageList;

    private final List<Card> cardDeck; // tracks the list of cards remaining

    // EFFECTS: adds 52 unique cards to cardDeck
    public CardDeck() {
        cardImageList = new ArrayList<>();
        createRedCardsImageList();
        createBlackCardsImageList();
        this.cardDeck = createUniqueCards();
    }

    // MODIFIES: this
    // EFFECTS: generates a list of unique cards, starting with ace of spades and ending with king of hearts
    public List<Card> createUniqueCards() {
        String[] suitList = {"Hearts","Diamonds","Clubs","Spades"};
        List<Card> fullDeck = new ArrayList<>();
        int suitIndex = 0;
        int cardValue = 1;
        int faceValue = 10;
        String currentSuit = suitList[suitIndex];
        int x = 10;
        loopThroughCards(fullDeck, faceValue, currentSuit, suitIndex, x, cardValue, suitList);
        return fullDeck;
    }

    public void loopThroughCards(List<Card> fullDeck, int faceValue, String currentSuit, int suitIndex, int x,
                                       int cardValue, String[] suitList) {
        for (int i = 1; i <= 52; i++) {
            if (i % 13 == 11 || i % 13 == 12 || i % 13 == 0) {
                fullDeck.add(new Card(faceValue, currentSuit, cardImageList.get(suitIndex).get(x)));
                x++;
            } else if ((i - 1) % 13 == 0) {
                fullDeck.add(new Card(11, currentSuit, cardImageList.get(suitIndex).get(0)));
            } else {
                fullDeck.add(new Card(cardValue, currentSuit, cardImageList.get(suitIndex).get(cardValue - 1)));
            }
            cardValue++;
            if (i % 13 == 0 && i != 52) {
                suitIndex++;
                currentSuit = suitList[suitIndex];
                cardValue = 1;
                x = 10;
            }
        }
    }

    // EFFECTS: returns the card deck
    public List<Card> getCardDeck() {
        return cardDeck;
    }

    // MODIFIES: this
    // EFFECTS: creates a list of image locations for the red cards
    public void createRedCardsImageList() {
        List<String> diamondsImages = new ArrayList<>(Arrays.asList("./data/images/ace_of_diamonds.png",
                "./data/images/2_of_diamonds.png","./data/images/3_of_diamonds.png","./data/images/4_of_diamonds.png",
                "./data/images/5_of_diamonds.png", "./data/images/6_of_diamonds.png","./data/images/7_of_diamonds.png",
                "./data/images/8_of_diamonds.png", "./data/images/9_of_diamonds.png","./data/images/10_of_diamonds.png",
                "./data/images/jack_of_diamonds2.png","./data/images/queen_of_diamonds2.png",
                "./data/images/king_of_diamonds2.png"));
        List<String> heartsImages = new ArrayList<>(Arrays.asList("./data/images/ace_of_hearts.png",
                "./data/images/2_of_hearts.png","./data/images/3_of_hearts.png","./data/images/4_of_hearts.png",
                "./data/images/5_of_hearts.png", "./data/images/6_of_hearts.png","./data/images/7_of_hearts.png",
                "./data/images/8_of_hearts.png", "./data/images/9_of_hearts.png","./data/images/10_of_hearts.png",
                "./data/images/jack_of_hearts2.png","./data/images/queen_of_hearts2.png",
                "./data/images/king_of_hearts2.png"));
        cardImageList.add(heartsImages);
        cardImageList.add(diamondsImages);
    }

    // MODIFIES: this
    // EFFECTS: creates a list of image locations for the black cards
    public void createBlackCardsImageList() {
        List<String> clubsImages = new ArrayList<>(Arrays.asList("./data/images/ace_of_clubs.png",
                "./data/images/2_of_clubs.png", "./data/images/3_of_clubs.png", "./data/images/4_of_clubs.png",
                "./data/images/5_of_clubs.png", "./data/images/6_of_clubs.png", "./data/images/7_of_clubs.png",
                "./data/images/8_of_clubs.png", "./data/images/9_of_clubs.png", "./data/images/10_of_clubs.png",
                "./data/images/jack_of_clubs2.png", "./data/images/queen_of_clubs2.png",
                "./data/images/king_of_clubs2.png"));
        List<String> spadesImages = new ArrayList<>(Arrays.asList("./data/images/ace_of_spades.png",
                "./data/images/2_of_spades.png","./data/images/3_of_spades.png","./data/images/4_of_spades.png",
                "./data/images/5_of_spades.png", "./data/images/6_of_spades.png","./data/images/7_of_spades.png",
                "./data/images/8_of_spades.png", "./data/images/9_of_spades.png","./data/images/10_of_spades.png",
                "./data/images/jack_of_spades2.png","./data/images/queen_of_spades2.png",
                "./data/images/king_of_spades2.png"));
        cardImageList.add(clubsImages);
        cardImageList.add(spadesImages);
    }
}
