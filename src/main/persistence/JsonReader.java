package persistence;

import model.casino.*;
import model.blackjack.*;
import model.prizeshop.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads casino and blackjack data from JSON stored in file
public class JsonReader {
    private String casinoSource;
    private String blackjackSource;
    private String prizeshopSource;


    // EFFECTS: constructs a reader to read from source file
    public JsonReader(String casinoSource, String blackjackSource, String prizeshopSource) {
        this.casinoSource = casinoSource;
        this.blackjackSource = blackjackSource;
        this.prizeshopSource = prizeshopSource;
    }

    // EFFECTS: reads casino from file and returns it
    // throws IOException if an error occurs reading data from file
    public Casino readCasino() throws IOException {
        String jsonData = readFile(casinoSource);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCasino(jsonObject);
    }

    // EFFECTS: reads blackjack from file and returns it
    // throws IOException if an error occurs reading data from file
    public BlackjackGame readBlackjack() throws IOException {
        String jsonData = readFile(blackjackSource);
        if (jsonData.isEmpty()) {
            return null;
        }
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBlackjackGame(jsonObject);
    }

    // EFFECTS: reads the prizeshop from file and returns it
    // throws IOException if an error occurs reading data from file
    public Shop readPrizeShop() throws IOException {
        String jsonData = readFile(prizeshopSource);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePrizeShop(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: casino
    // EFFECTS: parses casino from JSON Object and returns it
    private Casino parseCasino(JSONObject jsonObject) {
        int balance = jsonObject.getInt("balance");
        Casino casino = new Casino(balance);
        JSONArray jsonArray = jsonObject.getJSONArray("inventory");
        for (Object json : jsonArray) {
            JSONObject prize = (JSONObject) json;
            String type = prize.getString("type");
            int value = prize.getInt("value");
            casino.addPrize(new Prize(value, type,0));
        }

        return casino;
    }

    // MODIFIES: blackjackGame
    // EFFECTS: parses blackjackGame from JSON Object and returns it
    private BlackjackGame parseBlackjackGame(JSONObject jsonObject) {
        if (jsonObject.isEmpty()) {
            return null;
        } else {
            ArrayList<Card> dealerDeck = new ArrayList<>();

            int bet = jsonObject.getInt("bet");
            int numCards = jsonObject.getInt("numCards");
            int numDecks = jsonObject.getInt("numDecks");

            JSONArray jsonArray = jsonObject.getJSONArray("dealerDeck");
            for (Object json : jsonArray) {
                JSONObject card = (JSONObject) json;
                String suit = card.getString("suit");
                int value = card.getInt("value");
                dealerDeck.add(new Card(value, suit));
            }
            BlackjackGame blackjackGame = new BlackjackGame(0,new Casino(0));
            blackjackGame.setDealerDecks(dealerDeck);
            blackjackGame.setNumOfCards(numCards);
            blackjackGame.setCurrentBet(bet);
            blackjackGame.setNumOfDecks(numDecks);
            return blackjackGame;
        }
    }

    // MODIFIES: shop
    // EFFECTS: parses prizeshop file and returns it
    private Shop parsePrizeShop(JSONObject jsonObject) {
        ArrayList<Prize> prizeList = new ArrayList<>();
        Casino casino = new Casino(0);
        JSONArray jsonArray = jsonObject.getJSONArray("Prizes");
        for (Object json : jsonArray) {
            JSONObject prize = (JSONObject) json;
            String type = prize.getString("type");
            int value = prize.getInt("value");
            int index = prize.getInt("index");
            prizeList.add(new Prize(value, type, index));
        }

        Shop shop = new Shop(casino);
        shop.setPrizeList(prizeList);
        return shop;
    }

}
