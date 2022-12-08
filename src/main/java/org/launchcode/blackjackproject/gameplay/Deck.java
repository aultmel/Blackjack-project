package org.launchcode.blackjackproject.gameplay;

import java.util.Random;

public class Deck {

    private Card[] cards;

    private int cardsUsed;

    public Deck() {
        this(false);
    }

    public Deck(boolean includeJokers) {
        if (includeJokers) {
            cards = new Card[54];
        }
        else {
            cards = new Card[52];
        }
        int numCards = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 14; j++) {
                cards[numCards] = new Card(j, i);
                numCards++;
            }
        }
        if (includeJokers) {
            cards[53] = new Card(1, Card.JOKER);
            cards[54] = new Card(2, Card.JOKER);
        }
        cardsUsed = 0;
    }

    public void shuffle() {
        Random rng = new Random();
        int x;
        for (int i = 0; i < cards.length; i++) {
            x = rng.nextInt(cards.length);
            Card temp = cards[i];
            cards[i] = cards[x];
            cards[x] = temp;
        }
        cardsUsed = 0;
    }

    public Card dealCard() {
        if (cardsUsed == cards.length) {
            throw new IllegalArgumentException("Cannot deal out of an empty deck");
        }
        cardsUsed++;
        return cards[cardsUsed - 1];
    }

    public int cardsLeft() {
        return cards.length - cardsUsed;
    }

}
