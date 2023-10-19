package persistence;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    String casinoTestDestination;
    String blackjackTestDestination;
    String prizeShopTestDestination;
    JsonReader testReader;

    @Test
    void testBlackjackReaderNoDataInFile() {
        try {
            PrintWriter blackjackWriter = new PrintWriter(new File("./data/emptyBlackjack.json"));
        } catch (FileNotFoundException f) {
            fail("File should be found");
        }


        casinoTestDestination = "./data/testCasino.json";
        blackjackTestDestination = "./data/emptyBlackjack.json";
        prizeShopTestDestination = "./data/testPrizeShop.json";
        testReader = new JsonReader(casinoTestDestination, blackjackTestDestination, prizeShopTestDestination);


        try {
            assertNull(testReader.readBlackjack());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    void testReaderNoFile() {
        casinoTestDestination = "./data/fakeFile1.json";
        blackjackTestDestination = "./data/fakeFile2.json";
        prizeShopTestDestination = "./data/fakeFile3.json";
        testReader = new JsonReader(casinoTestDestination, blackjackTestDestination, prizeShopTestDestination);

        try {
            testReader.readCasino();
            fail("Exception expected");
        } catch (IOException e) {
            // pass
        }

        try {
            testReader.readPrizeShop();
            fail("Exception expected");
        } catch (IOException i) {
            // pass
        }
    }
}
