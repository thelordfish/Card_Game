# Card Game - Java Multiplayer Deck Game

## Project Overview

This is a multithreaded Java card game where each player draws and discards cards from decks to their left and right. The goal is to collect four cards of the same value to win.  
The game supports concurrent player turns and thread-safe logging of all actions.

## Running the Game

### Requirements
- Java 8 or higher
- Pack file (e.g. `pack.txt`) containing 8 × N integers (one per line), where N is the number of players.

### How to Start
1. Open the project in Eclipse or IntelliJ, or compile via command line.
2. Run the `Main` class in the `cardgame` package.
3. When prompted:
   - Enter the number of players (e.g., `4`).
   - Enter the file path to the pack file (e.g., `C:\Users\Ollie\pack.txt`).

## Output Files

After the game completes:

- Each player will have a log file:
  - `player1_output.txt`
  - `player2_output.txt`
  - ...

  These contain logs of each player’s actions and final result.

- Each deck will also have a file:
  - `deck1_output.txt`
  - `deck2_output.txt`
  - ...

  Showing the final state of each deck.

All files are written to the project root directory (`./`) by default.

## Running the Tests

### Requirements
- JUnit 4

### How to Run
1. Ensure the test files are in the correct package (`cardgame`).
2. Run the test files from your IDE using the built-in test runner.
   - Example test classes:
     - `CardDeckTest.java`
     - `PlayerTest.java`
     - `TurnConcurrencyTest.java`
     - `LoggingTest.java`
     - `OnlyFirstWinnerCounts.java`
     - `AtomicTurnLocking.java`

Alternatively, from the command line:

```bash
javac -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar cardgame/*.java
java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore cardgame.PlayerTest
```

## Features
- Multithreaded player turns with deck locking.
- Turn atomicity guaranteed (draw + discard together).
- Game ends cleanly with all players notified.
- Clean logging for players and decks.
- Error handling for invalid inputs and empty decks.

## Authors
- Oliver Appleby (680049063)
- Raghavendra Iyer (740074572)