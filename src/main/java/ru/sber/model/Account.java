package ru.sber.model;

public class Account {
    private long idAccount;
    private Client client;
    private double balance;

    public Account() {
    }

    public Account(Client client, double balance) {
        this.client = client;
        this.balance = balance;
    }

    public Account(long idAccount) {
        this.idAccount = idAccount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;

    }

    public long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(long idAccount) {
        this.idAccount = idAccount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Account{" +
                "idAccount=" + idAccount +
                ", client=" + client +
                ", balance=" + balance +
                '}';
    }
}
