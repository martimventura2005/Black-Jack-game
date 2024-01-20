import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BlackjackGame {

    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame();
        game.startGame();
    }

    private List<Card> deck;
    private List<Card> playerHand;
    private int playerScore;

    public BlackjackGame() {
        deck = new ArrayList<>();
        playerHand = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }
    //Instert the deck using string with the suits and the ranks
    private void initializeDeck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String suit : suits) {
            for (String rank : ranks) {
                Card card = new Card(suit, rank);
                deck.add(card);
            }
        }
    }

    //Class used to shuffle the deck
    private void shuffleDeck() {
        Collections.shuffle(deck);
    }

    //the starting game code
    private void startGame() {
        dealInitialCards();
        showHands();

        while (getPlayerScore() < 21) {
            if (getPlayerDecision()) {
                dealCardToPlayer();
                showHands();
            } else {
                break;
            }
        }

        endGame();
    }
    //deal random the cards to the player
    private void dealInitialCards() {
        dealCardToPlayer();
        dealCardToPlayer();
    }

    private void dealCardToPlayer() {
        Card card = deck.remove(0);
        playerHand.add(card);
        updatePlayerScore();
    }
    //count the player score and give "Ace" two different scores (1 or 11)
    private void updatePlayerScore() {
        playerScore = 0;
        int numAces = 0;

        for (Card card : playerHand) {
            playerScore += card.getValue();

            if (card.getRank().equals("Ace")) {
                numAces++;
            }
        }

        while (numAces > 0 && playerScore > 21) {
            playerScore -= 10;
            numAces--;
        }
    }

    private int getPlayerScore() {
        return playerScore;
    }
    //Player scanner input to get the hit or stand decision
    private boolean getPlayerDecision() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to hit? (y/n): ");
        String decision = scanner.nextLine().toLowerCase();
        return decision.equals("y");
    }
    //the hand with the cards and the respective score
    private void showHands() {
        System.out.println("Your hand: " + playerHand + " (Score: " + getPlayerScore() + ")");
    }

    //the end of the game with the three possible ends
    private void endGame() {
        showHands();

        if (getPlayerScore() == 21) {
            System.out.println("Congratulations! You got Blackjack!");
        } else if (getPlayerScore() > 21) {
            System.out.println("Bust! You went over 21. You lose.");
        } else {
            System.out.println("You chose to stand. Your final score is: " + getPlayerScore());
        }
    }
    //the class used to give the card it's suit and rank
    private static class Card {
        private final String suit;
        private final String rank;

        public Card(String suit, String rank) {
            this.suit = suit;
            this.rank = rank;
        }

        //The value of each card
        public int getValue() {
            if (rank.equals("Ace")) {
                return 11;
            } else if (rank.equals("King") || rank.equals("Queen") || rank.equals("Jack")) {
                return 10;
            } else {
                return Integer.parseInt(rank);
            }
        }
        public String getRank() {
            return rank;
        }

        @Override
        public String toString() {
            return rank + " of " + suit;
        }
    }
}
