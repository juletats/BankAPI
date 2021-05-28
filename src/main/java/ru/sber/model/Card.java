package ru.sber.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.h2.util.StringUtils;

public class Card {
    private long idCard;
    private double balance;
    @JsonIgnore
    private Account account;
    @JsonIgnore
    private String status;

    public Card() {
    }

    public Card(long idCard, double balance, Account account, String status) {
        this.idCard = idCard;
        this.balance = balance;
        this.account = account;
        this.status = status;
    }

    public Card(long idCard, double balance) {
        this.idCard = idCard;
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (StringUtils.isNullOrEmpty(status)) {
            throw new IllegalArgumentException("status argument must not be null or empty");
        }
        this.status = status;
    }

    public long getIdCard() {
        return idCard;
    }

    public void setIdCard(long idCard) {
        this.idCard = idCard;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("balance must not be less than 0");
        }
        this.balance = balance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        if (account == null) {
            //...
        }
        this.account = account;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + idCard +
                ", balance=" + balance +
                ", idAccount=" + account +
                ", status='" + status + '\'' +
                '}';
    }
}

