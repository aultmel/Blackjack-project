package org.launchcode.blackjackproject.controllers;

import org.launchcode.blackjackproject.data.UserRepository;
import org.launchcode.blackjackproject.gameplay.Card;
import org.launchcode.blackjackproject.gameplay.Deck;
import org.launchcode.blackjackproject.gameplay.Hand;
import org.launchcode.blackjackproject.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("play")
public class PlayController {

    @Autowired
    private UserRepository userRepository;
    private Integer userId;

    private static Deck deck = new Deck();
    private static int tempBet;
    private static String level;
    private static Hand playerHand = new Hand();
    private static Hand dealerHand = new Hand();
    private static boolean isNewGame = true;

    @GetMapping("profile")
    public String displayUserProfile(@RequestParam Integer userId, Model model) {
        this.userId = userId;
        Optional<User> result = userRepository.findById(userId);
        User user = result.get();
        model.addAttribute("title", "Welcome, " + user.getUsername() + "!");
        model.addAttribute("user", user);
        if (user.getLevel() < 10) {
            int remainingPoints = 1000 - (int)user.getTotalMoneyEarned() % 1000;
            model.addAttribute("remainingPoints", remainingPoints);
        }
        model.addAttribute("userMoney", user.getMoney());
        return "profile";
    }

    @GetMapping
    public String playGameStart(Model model) {
        Optional<User> result = userRepository.findById(userId);
        User user = result.get();
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
        model.addAttribute("userId", user.getId());
        return "play/game";
    }

    @PostMapping
    public String processPlayGame(Model model, @RequestParam String choice) {
        Optional<User> result = userRepository.findById(userId);
        User user = result.get();
        model.addAttribute("userId", user.getId());
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
                user.setMoney(user.getMoney() + tempBet);
                user.setTotalMoneyEarned(user.getTotalMoneyEarned() + tempBet);
                userRepository.save(user);
                String moneyMessage = "You now have $" + user.getMoney();
                model.addAttribute("moneyMessage", moneyMessage);
                model.addAttribute("winOrLoseMessage", "Congratulations!");
            }
            else {
                model.addAttribute("message", "Sorry, the dealer got you!");
                model.addAttribute("winOrLoseMessage", "Better luck next time!");
                String moneyMessage = "";
                if (user.getMoney() == tempBet) {
                    if (user.getLevel() == 0) {
                        user.setMoney(1);
                        moneyMessage = "You now have $1";
                    }
                    else if (user.getLevel() == 1) {
                        user.setMoney(10);
                        moneyMessage = "You now have $10";
                    }
                    else if (user.getLevel() == 2) {
                        user.setMoney(20);
                        moneyMessage = "You now have $20";
                    }
                    else if (user.getLevel() == 3) {
                        user.setMoney(30);
                        moneyMessage = "You now have $30";
                    }
                    else if (user.getLevel() == 4) {
                        user.setMoney(40);
                        moneyMessage = "You now have $40";
                    }
                    else if (user.getLevel() == 5) {
                        user.setMoney(50);
                        moneyMessage = "You now have $50";
                    }
                    else if (user.getLevel() == 6) {
                        user.setMoney(60);
                        moneyMessage = "You now have $60";
                    }
                    else if (user.getLevel() == 7) {
                        user.setMoney(70);
                        moneyMessage = "You now have $70";
                    }
                    else if (user.getLevel() == 8) {
                        user.setMoney(80);
                        moneyMessage = "You now have $80";
                    }
                    else if (user.getLevel() == 9) {
                        user.setMoney(90);
                        moneyMessage = "You now have $90";
                    }
                    else if (user.getLevel() == 10) {
                        user.setMoney(100);
                        moneyMessage = "You now have $100";
                    }

                }
                else {
                    user.setMoney(user.getMoney() - tempBet);
                    moneyMessage = "You now have $" + user.getMoney();

                }
                userRepository.save(user);

                model.addAttribute("moneyMessage", moneyMessage);
            }

            return "play/dealer-turn";
        }
        return "redirect:";
    }

    @GetMapping("select")
    public String selectLevelAndBet(Model model) {
        Optional<User> result = userRepository.findById(userId);
        User user = result.get();
        model.addAttribute("userMoney", user.getMoney());
        model.addAttribute("userId", user.getId());
        return "play/select";
    }

    @PostMapping("select")
    public String processLevelAndBetSelect(Model model, @RequestParam int tempBet, @RequestParam String level) {
        Optional<User> result = userRepository.findById(userId);
        User user = result.get();
        if (tempBet > user.getMoney()) {
            model.addAttribute("error", "You don\'t have enough money for that!");
            return "redirect:/play/select";
        }
        else if (level.equals("medium") && tempBet < 100) {
            model.addAttribute("error", "Minimum bet for medium is $100");
            return "redirect:/play/select";
        }
        else if (level.equals("hard") && tempBet < 200) {
            model.addAttribute("error", "Minimum bet for hard is $200");
            return "redirect:/play/select";
        }
        else {
            this.tempBet = tempBet;
            this.level = level;
            model.addAttribute("userId", user.getId());
            return "redirect:";
        }
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
