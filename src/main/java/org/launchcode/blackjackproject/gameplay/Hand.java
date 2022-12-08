package org.launchcode.blackjackproject.gameplay;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private ArrayList<Card> cardsInHand;

    public Hand() {
        cardsInHand = new ArrayList<>();
    }

    public void clearHand() {
        cardsInHand.clear();
    }

    public void addCardToHand(Card newCard) {
        cardsInHand.add(newCard);
    }

    public void removeCardFromHand(Card oldCard) {
        cardsInHand.remove(oldCard);
    }

    public int countCards() {
        return cardsInHand.size();
    }

    public Card getCard(int index) {
        if (index < 0 || index >= cardsInHand.size()) {
            throw new IllegalArgumentException("Position " + index + " does not exist in hand");
        }
        else {
            return cardsInHand.get(index);
        }
    }

    public void sortBySuit() {
        ArrayList<Card> newHand = new ArrayList<>();
        int minPosition = 0;
        Card minCard = cardsInHand.get(0);
        while (cardsInHand.size() > 0) {
            for (int i = 0; i < cardsInHand.size(); i++) {
                Card givenCard = cardsInHand.get(i);
                if (givenCard.getSuit() < minCard.getSuit() || (givenCard.getSuit() == minCard.getSuit() && givenCard.getRank() < minCard.getRank())) {
                    minPosition = i;
                    minCard = givenCard;
                }
            }
            cardsInHand.remove(minPosition);
            newHand.add(minCard);
        }
        cardsInHand = newHand;
    }

    public void sortByRank() {
        ArrayList<Card> newHand = new ArrayList<>();
        int minPosition = 0;
        Card minCard = cardsInHand.get(0);
        while (cardsInHand.size() > 0) {
            for (int i = 0; i < cardsInHand.size(); i++) {
                Card givenCard = cardsInHand.get(i);
                if (givenCard.getRank() < minCard.getRank() || (givenCard.getRank() == minCard.getRank() && givenCard.getSuit() < minCard.getSuit())) {
                    minPosition = i;
                    minCard = givenCard;
                }
            }
            cardsInHand.remove(minPosition);
            newHand.add(minCard);
        }
        cardsInHand = newHand;
    }

    public int blackjackHandValue() {
        int[] value = new int[cardsInHand.size()];
        int sum = 0;
        for (int i = 0; i < cardsInHand.size(); i++) {
            if (cardsInHand.get(i).getRank() == 11 || cardsInHand.get(i).getRank() == 12 || cardsInHand.get(i).getRank() == 13) {
                value[i] = 10;
            }
            else if (cardsInHand.get(i).getRank() == Card.ACE) {
                value[i] = 1;
            }
            else {
                value[i] = cardsInHand.get(i).getRank();
            }
            sum += value[i];
        }
        for (int i = 0; i < cardsInHand.size(); i++) {
            if (value[i] == 1) {
                if (sum + 10 <= 21) {
                    value[i] = 11;
                    sum += 10;
                }
            }
        }
        for (int i = 0; i < cardsInHand.size(); i++) {
            if (value[i] == 1) {
                if (sum + 10 <= 21) {
                    value[i] = 11;
                    sum += 10;
                }
            }
        }
        return sum;
    }

    public List<String> handToString() {
        List<String> handAsStrings = new ArrayList<>();
        for (int i = 0; i < cardsInHand.size(); i++) {
            handAsStrings.add(cardsInHand.get(i).cardName());
        }
        return handAsStrings;
    }

}
