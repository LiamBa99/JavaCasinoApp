## What does it do:
The project that I propose to design is a casino. The application will start with 
a prompt for the user to show their ID (enter in birthdate) in order to enter the casino legally. The 
program will check the birthdate against current date and let them in if they meet the requirements. The user
can then withdraw money to gain a cash balance to use to play casino games. Then they can select which 
game they would like to play, including blackjack, roulette and a slot machine. They can deposit their winnings
or withdraw more if they lose. The user will be able to save their balance and return to play again later with the
same balance. The user will be able to purchase prizes from the store with their
earnings.

## Who will use it:
Fans of casino games who don't want to lose real money.

## Why is this project of interest?

**Game Logic**

It provides a platform to practice creating game logic through the statistical rules of casino games. 
In particular, I am interested in discovering how to best replicate the 
odds of cards being pulled from the deck using randomization, while still 
retaining realism through keeping an active counter of cards such that no more than 8 of a
particular card can be pulled in a session if the blackjack table is using 2 decks of cards.

**Input Cleaning and Testing**

Through age verification I am interested in utilizing input checks and cleaning to ensure
the user can not bug the system by inputting improper dates. 

**Statistical House Edge**

Additionally, I believe casino games are interesting because they are always statistically 
in the favour of the house, and by creating the games logic in this program I can get insight 
into why that is the case. 

## User Stories

- As a user, I want to be able to purchase randomly generated prizes to add to my collection
- As a user, I want to be able to view the list of available prizes to purchase
- As a user, I want to be able to view the list of available games to play
- As a user, I want to be able to view my owned prizes
- As a user, I want to be able to choose a number, colour, or group of numbers in roulette.
- As a user, I want to be able to see the dealer deal the cards in blackjack.
- As a user, I want to be able to save my balance and play again later with the saved balance
- As a user, I want to be able to adjust my bet size while playing the slot machine.