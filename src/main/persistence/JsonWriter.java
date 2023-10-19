package persistence;

import model.casino.*;
import model.blackjack.*;
import model.prizeshop.*;

import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of casino data to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter casinoWriter;
    private PrintWriter blackjackWriter;
    private PrintWriter prizeShopWriter;
    private String casinoDestination;
    private String blackjackDestination;
    private String prizeShopDestination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String casinoDestination, String blackjackDestination, String prizeShopDestination) {
        this.casinoDestination = casinoDestination;
        this.blackjackDestination = blackjackDestination;
        this.prizeShopDestination = prizeShopDestination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer, throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        casinoWriter = new PrintWriter(new File(casinoDestination));
        blackjackWriter = new PrintWriter(new File(blackjackDestination));
        prizeShopWriter = new PrintWriter(new File(prizeShopDestination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of casino to file and blackjackgame if selected
    public void write(Casino casino, BlackjackGame blackjackGame, Shop prizeShop) {
        JSONObject json1;

        if (blackjackGame != null) {
            json1 = blackjackGame.toJson();
            saveToBJFile(json1.toString(TAB));
        } else {
            // do nothing
        }

        JSONObject json2 = casino.toJson();
        JSONObject json3 = prizeShop.toJson();

        saveToCFile(json2.toString(TAB));
        saveToPSFile(json3.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        casinoWriter.close();
        blackjackWriter.close();
        prizeShopWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    public void saveToCFile(String json) {
        casinoWriter.print(json);
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    public void saveToBJFile(String json) {
        blackjackWriter.print(json);
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    public void saveToPSFile(String json) {
        prizeShopWriter.print(json);
    }
}
