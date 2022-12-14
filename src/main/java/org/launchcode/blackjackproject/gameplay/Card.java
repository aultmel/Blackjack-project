package org.launchcode.blackjackproject.gameplay;

public class Card {

    public static final int CLUBS = 0;
    public static final int DIAMONDS = 1;
    public static final int HEARTS = 2;
    public static final int SPADES = 3;
    public static final int JOKER = 4;

    public static final int ACE = 1;
    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;

    private final int rank;
    private final int suit;
    private String cardLocation;

    public Card() {
        suit = JOKER;
        rank = 1;
    }

    public Card(int rank, int suit) {
        if (suit != CLUBS && suit != DIAMONDS && suit != HEARTS && suit != SPADES && suit != JOKER) {
            throw new IllegalArgumentException("Illegal suit");
        }
        if (suit != JOKER && (rank < 1 || rank > 13)) {
            throw new IllegalArgumentException("Illegal rank");
        }
        this.rank = rank;
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }

    public String getCardLocation() {
        return cardLocation;
    }

    public void setCardLocation(String cardLocation) {
        this.cardLocation = cardLocation;
    }

    public String ranksAsString() {
        if (suit == JOKER) {
            return "" + rank;
        }
        else {
            if (rank == ACE) {
                return "Ace";
            }
            if (rank == 2) {
                return "2";
            }
            if (rank == 3) {
                return "3";
            }
            if (rank == 4) {
                return "4";
            }
            if (rank == 5) {
                return "5";
            }
            if (rank == 6) {
                return "6";
            }
            if (rank == 7) {
                return "7";
            }
            if (rank == 8) {
                return "8";
            }
            if (rank == 9) {
                return "9";
            }
            if (rank == 10) {
                return "10";
            }
            if (rank == JACK) {
                return "Jack";
            }
            if (rank == QUEEN) {
                return "Queen";
            }
            return "King";
        }
    }

    public String suitsAsString() {
        if (suit == CLUBS) {
            return "Clubs";
        }
        if (suit == DIAMONDS) {
            return "Diamonds";
        }
        if (suit == HEARTS) {
            return "Hearts";
        }
        if (suit == SPADES) {
            return "Spades";
        }
        return "Joker";
    }


    public String cardName() {
        if (suit == JOKER) {
            if (rank == 1) {
                return "Joker";
            }
            else {
                return "Joker #" + rank;
            }
        }
        else return this.ranksAsString() + " of " + this.suitsAsString();
    }

}
