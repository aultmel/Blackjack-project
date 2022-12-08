package org.launchcode.blackjackproject.models;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class User extends AbstractEntity {

    @NotNull
    private String username;

    @NotNull
    private String pwHash;


    private int level = 0;


    private double money = 100;

    @PositiveOrZero
    private double totalMoneyEarned;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

    public String getUsername() {
        return username;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public int getLevel() {
        if ((int)this.totalMoneyEarned < 1000) {
            this.level = 0;
        }
        else if ((int)this.totalMoneyEarned < 2000) {
            this.level = 1;
        }
        else if ((int)this.totalMoneyEarned < 3000) {
            this.level = 2;
        }
        else if ((int)this.totalMoneyEarned < 4000) {
            this.level = 3;
        }
        else if ((int)this.totalMoneyEarned < 5000) {
            this.level = 4;
        }
        else if ((int)this.totalMoneyEarned < 6000) {
            this.level = 5;
        }
        else if ((int)this.totalMoneyEarned < 7000) {
            this.level = 6;
        }
        else if ((int)this.totalMoneyEarned < 8000) {
            this.level = 7;
        }
        else if ((int)this.totalMoneyEarned < 9000) {
            this.level = 8;
        }
        else if ((int)this.totalMoneyEarned < 10000) {
            this.level = 9;
        }
        else if ((int)this.totalMoneyEarned >= 10000) {
            this.level = 10;
        }
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getTotalMoneyEarned() {
        return totalMoneyEarned;
    }

    public void setTotalMoneyEarned(double totalMoneyEarned) {
        this.totalMoneyEarned = totalMoneyEarned;
    }

}
