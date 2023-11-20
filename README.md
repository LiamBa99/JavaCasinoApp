## What does it do:
The project that I propose to design is a casino. The application will start with 
a prompt for the user to show their ID (enter in birthdate) in order to enter the casino legally. The 
program will check the birthdate against current date and let them in if they meet the requirements. The user
can then withdraw money to gain a cash balance to use to play casino games. Then they can select which 
game they would like to play, including blackjack and roulette. They can deposit their winnings
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
- As a user, I want to be able to "hit" to add multiple cards to my hand in blackjack.
- As a user, I want to be able to "stand" to add multiple cards to the dealer's hand in blackjack
- As a user, I want to be able to view the list of available prizes to purchase
- As a user, I want to be able to view the list of available games to play
- As a user, I want to be able to view my owned prizes
- As a user, I want to be able to choose a number, colour, or group of numbers in roulette.
- As a user, I want to be able to see the cards dealt to me in blackjack.
- As a user, I want the option to save my balance, inventory of prizes, and current shop to a file.
- As a user, I want the option to load my previous balance, inventory of prizes, and current shop from a file.
- As a user, I want the option to save my blackjack game state to a file.
- As a user, I want the option to load my blackjack game state from a file.

## Instructions for Grader

- Instructions for adding multiple prizes to inventory
  - Run the program
  - Select the balance button
  - add a large amount to your balance (50000 or so) by clicking submit
  - return to home page by pressing home button
  - press the prizes button
  - click on any prizes to purchase the prize
  - refresh the shop if you want to purchase more than the prizes that are available
  - return to the home page
  - click the inventory button to view all of your purchased prizes
- Instructions for the first required action related to adding multiple prizes to inventory
  - Run the program
  - Click the balance button
  - enter a non-negative number
  - press the home button
  - press the prizes button
  - purchase as many prizes as you can afford
  - press the home button
  - press the inventory button
  - press on any of the prizes you own to sell the prize
- Instructions for the second required action related to adding multiple prizes to inventory
  - Run the program
  - click the balance button
  - enter a non-negative number
  - press the home button
  - press the prizes button
  - purchase as many prizes as you can afford
  - press the home button
  - press the inventory button
  - press any of the subset buttons at the bottom of the page that matches any of your owned prizes
  - observe the highlight on the matching prizes
- Instructions for visual component of project
  - Run the program
  - Select the balance button
  - add any positive amount to your balance 
  - return to home page by pressing the home button
  - click the games button
  - click the blackjack button
  - input the amount of decks you want to use 
  - select your bet size (go big or go home)
  - click submit
  - press deal to start the game
  - images of cards appear representing the cards you and the dealer were dealt
  - hit or stand based on cards dealt
  - more images of cards will appear.
- Instructions for saving:
  - Run the program
  - Select the balance button and enter any positive amount to your balance
  - observe your balance has increased by the corresponding amount
  - press the home button
  - press the prizes button
  - purchase any prizes you would like
  - press home button
  - press inventory button
  - observe the prizes you purchased are present
  - press the home button
  - press the save button
- Instructions for loading
  - (Assuming you've saved first)
  - Press the balance button
  - observe balance is 0
  - press the home button
  - press the inventory button
  - observe inventory is empty
  - press the home button
  - press the load button
  - press the balance button
  - observe balance has changed to your saved amount
  - press the home button
  - press the inventory button
  - observe your previously purchased prizes are present