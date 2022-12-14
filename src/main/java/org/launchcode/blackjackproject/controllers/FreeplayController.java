package org.launchcode.blackjackproject.controllers;

import org.launchcode.blackjackproject.gameplay.Card;
import org.launchcode.blackjackproject.gameplay.Deck;
import org.launchcode.blackjackproject.gameplay.Hand;
import org.launchcode.blackjackproject.models.TempUser;
import org.launchcode.blackjackproject.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("free")
public class FreeplayController {

    private static Deck deck = new Deck();
    private static int tempBet;
    private static Hand playerHand = new Hand();
    private static Hand dealerHand = new Hand();
    private static boolean isNewGame = true;
    private int money = 100;

    @GetMapping("select")
    public String selectLevelAndBet(Model model) {
        model.addAttribute("money", this.money);
        return "free/select";
    }

    @PostMapping("select")
    public String processLevelAndBetSelect(Model model, @RequestParam int tempBet) {
        if (tempBet > this.money) {
            model.addAttribute("error", "You don\'t have enough money for that!");
            return "redirect:/free/select";
        }
        else {
            this.tempBet = tempBet;
            model.addAttribute("tempBet", tempBet);
            return "redirect:";
        }
    }

    @GetMapping
    public String playGameStart(Model model) {
        if (isNewGame) {
            playerHand.clearHand();
            dealerHand.clearHand();
            deck.shuffle();
        }
        if (playerHand.countCards() == 0) {
            playerHand.addCardToHand(deck.dealCard());
            playerHand.addCardToHand(deck.dealCard());
        }
        if (dealerHand.countCards() == 0) {
            dealerHand.addCardToHand(deck.dealCard());
            dealerHand.addCardToHand(deck.dealCard());
        }
        model.addAttribute("playerHand", playerHand.handToString());
        model.addAttribute("dealerHandFirstCard", dealerHand.getCard(0).cardName());
        model.addAttribute("dealerHandValue", blackjackCardValue(dealerHand.getCard(0)));
        model.addAttribute("playerHandValue", playerHand.blackjackHandValue());

        return "free/play";
    }

    @PostMapping
    public String processPlayGame(Model model, @RequestParam String choice) {
        if (choice.equals("hit") && playerHand.blackjackHandValue() < 21) {
            playerHand.addCardToHand(deck.dealCard());
            isNewGame = false;
            return "redirect:";
        }
        else if (choice.equals("stand") || playerHand.blackjackHandValue() >= 21) {
            while ((dealerHand.blackjackHandValue() < 17 && playerHand.blackjackHandValue() <= 21) || (playerHand.blackjackHandValue() <= 21 && dealerHand.blackjackHandValue() < playerHand.blackjackHandValue())) {
                dealerHand.addCardToHand(deck.dealCard());
            }
            isNewGame = true;
            model.addAttribute("dealerHand", dealerHand.handToString());
            model.addAttribute("playerHand", playerHand.handToString());
            model.addAttribute("playerHandValue", playerHand.blackjackHandValue());
            model.addAttribute("dealerHandValue", dealerHand.blackjackHandValue());
            if (playerWin(playerHand.blackjackHandValue(), dealerHand.blackjackHandValue())) {
                model.addAttribute("message", "Congratulations, you win!");
                this.money += tempBet;
                model.addAttribute("winOrLoseMessage", "Congratulations!");
            }
            else {
                model.addAttribute("message", "Sorry, the dealer got you!");
                if (tempBet == this.money) {
                    this.money = 1;
                }
                else {
                    this.money -= tempBet;
                }
                model.addAttribute("winOrLoseMessage", "Better luck next time!");
            }
            String moneyMessage = "You now have $" + this.money;
            model.addAttribute("moneyMessage", moneyMessage);
            return "free/dealer-turn";
        }
        return "redirect:";
    }

    public int blackjackCardValue(Card card) {
        int rank = card.getRank();
        if (card.getRank() == Card.ACE) {
            rank = 11;
        }
        if (card.getRank() == Card.JACK || card.getRank() == Card.QUEEN || card.getRank() == Card.KING) {
            rank = 10;
        }
        return rank;
    }

    public boolean playerWin(int playerSum, int dealerSum) {
        if (playerSum < 21) {
            if (dealerSum <= 21 && dealerSum >= playerSum) {
                return false;
            }
            else if (dealerSum < playerSum) {
                return true;
            }
            else if (dealerSum > 21) {
                return true;
            }
        }
        if (playerSum == 21) {
            if (dealerSum == 21) {
                return false;
            }
            if (playerSum > dealerSum) {
                return true;
            }
            if (dealerSum > 21) {
                return true;
            }
        }
        if (playerSum > 21) {
            return false;
        }
        return false;
    }

}
