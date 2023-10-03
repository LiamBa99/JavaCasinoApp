package model.roulettegame;

import java.util.*;

// represents a round of Roulette, with the player bet, player selection and a roulette board where
// numbers 00 and 0 have the colour green associated
// 00 is represented by the first 0 in the array
// even numbers have the colour red associated
// ood numbers have the colour black associated
public class RouletteRound {
    private int playerSelection;
    private int playerBet;
    private int[] rouletteBoard = {0,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,
            16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};

    // REQUIRES: 38 > playerSelection >= 0, playerBet > 0
    // EFFECTS: creates a roulette round with the player's selected number, their bet size and a roulette board
    public RouletteRound(int playerSelection, int playerBet) {

    }

}
