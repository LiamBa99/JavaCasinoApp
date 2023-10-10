package model.roulette;

import model.CasinoGame;

import java.util.*;

// represents a round of Roulette, with the player bets, player selections and a roulette board where
// numbers 00 and 0 have the colour green associated
// 00 is represented by the first 0 in the array
// even numbers have the colour red associated
// ood numbers have the colour black associated
public class RouletteRound implements CasinoGame {
    private final int[] playerSelection;
    private final List<String> playerColourEvenSelection;
    private final int playerBet;
    private final int[] rouletteBoard = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,
            16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};

    // REQUIRES: players selection contains values 38 > x >= 0, playerBets contains values   > 0
    // player selection size and player bet size > 0
    // EFFECTS: creates a roulette round with the player's selected number, their bet size and a roulette board
    public RouletteRound(int[] playerSelection, int playerBet, List<String> playerColourEvenSelection) {
        this.playerSelection = playerSelection;
        this.playerBet = playerBet;
        this.playerColourEvenSelection = playerColourEvenSelection;
    }


    // EFFECTS: select a random number from the rouletteBoard
    public int selectNumber() {
        Random random = new Random();
        return rouletteBoard[random.nextInt(rouletteBoard.length - 1)];
    }

    // REQUIRES: winning number has been selected
    // EFFECTS: check if the randomly selected number matches the player selection
    // return one if the player got the colour or number right, 2 if they got both right, 0 if the player loses
    public int checkWin() {
        int win = 0;
        boolean isRed;
        int winningNumber = selectNumber();

        isRed = (winningNumber % 2 == 0);

        if (isRed && playerColourEvenSelection.contains("red")) {
            win += 1;
        } else if (!isRed && playerColourEvenSelection.contains("black")) {
            win += 1;
        }

        for (int i : playerSelection) {
            if (i == winningNumber) {
                win += 1;
            }
        }

        return win;
    }


    // EFFECTS: returns the players bet size
    public int getPlayerBet() {
        return playerBet;
    }

    // EFFECTS: returns the players number selections
    public int[] getPlayerSelection() {
        return playerSelection;
    }

    // EFFECTS: returns the roulette board
    public int[] getRouletteBoard() {
        return rouletteBoard;
    }

    public List<String> getPlayerColourEvenSelection() {
        return playerColourEvenSelection;
    }

}
