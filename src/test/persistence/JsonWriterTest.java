package persistence;

import model.casino.*;
import model.blackjack.*;
import model.prizeshop.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    String casinoTestDestination;
    String blackjackTestDestination;
    String prizeShopTestDestination;
    JsonWriter testWriter;
    JsonReader testReader;


    @BeforeEach
    void runBefore() {
        casinoTestDestination = "./data/testCasino.json";
        blackjackTestDestination = "./data/testBlackjack.json";
        prizeShopTestDestination = "./data/testPrizeShop.json";
        testWriter = new JsonWriter(casinoTestDestination, blackjackTestDestination, prizeShopTestDestination);
        testReader = new JsonReader(casinoTestDestination, blackjackTestDestination, prizeShopTestDestination);

    }

    @Test
    void testConstructor() {
        try {
            testWriter.open();
        } catch (FileNotFoundException f) {
            fail("caught exception when not expected");
        }
    }

    @Test
    void testOpen() {
        String illegalCasinoTestDestination = "./data\0wrongname.json";
        try {
        JsonWriter testWriter2 = new JsonWriter(illegalCasinoTestDestination, blackjackTestDestination,
                prizeShopTestDestination);
            testWriter2.open();
            fail("Exception not caught");
        } catch (FileNotFoundException f) {
            System.out.println("Caught exception");
            // test passes
        }
    }

    @Test
    void testWriteEmptyCasinoBlackJPrizeS() {
        Casino casino = new Casino(0);
        BlackjackGame blackjackGame = new BlackjackGame(0, casino);
        Shop prizeShop = new Shop(casino);
        Prize firstPrize = prizeShop.getPrizeList().get(0);
        try {
            testWriter.open();
            testWriter.write(casino, blackjackGame, prizeShop);
            testWriter.close();

            casino = testReader.readCasino();
            blackjackGame = testReader.readBlackjack();
            prizeShop = testReader.readPrizeShop();

            assertEquals(0,casino.getPlayerBalance());
            assertEquals(0, blackjackGame.getNumOfDecks());
            assertEquals(firstPrize.getValue(), prizeShop.getPrizeList().get(0).getValue());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    void testWriteGeneralCasinoBlackJPrizeS() {
        Casino casino = new Casino(50000);
        BlackjackGame blackjackGame = new BlackjackGame(4, casino);
        Shop prizeShop = new Shop(casino);

        // purchase a prize
        prizeShop.buyPrize(4);
        // store the prize
        Prize purchasedPrize = casino.getInventory().get(0);
        // store the new balance
        int newBal = casino.getPlayerBalance();
        // remove a random card
        blackjackGame.removeRandomCard();
        // get the number of cards remaining
        int numCards = blackjackGame.getNumOfCards();

        try {
            testWriter.open();
            testWriter.write(casino, blackjackGame, prizeShop);
            testWriter.close();

            casino = testReader.readCasino();
            blackjackGame = testReader.readBlackjack();
            prizeShop = testReader.readPrizeShop();

            assertEquals(newBal,casino.getPlayerBalance());
            assertEquals(purchasedPrize.getValue(),casino.getInventory().get(0).getValue());

            assertEquals(numCards, blackjackGame.getNumOfCards());

            assertEquals(8,prizeShop.getPrizeList().size());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

}
