package ru.sber.controller;

import ru.sber.controller.dao.CardDao;
import ru.sber.controller.dao.ICardDao;
import ru.sber.model.Card;

import java.util.List;

public class Operations {
    private static final ICardDao cardDao = new CardDao();

    public static boolean addNewCardByAccount(long accountId) {
        return cardDao.insertCardByAccount(accountId);
    }

    public static boolean addNewCardByClient(String fio) {
        return cardDao.insertCardByClient(fio);
    }

    public static List<Card> readCardsByAccount(long accountId) {
        return cardDao.readAccountCards(accountId);
    }

    public static List<Card> readCardsByClient(Long clientId) {
        return cardDao.readClientsCards(clientId);
    }

    public static boolean depositMoney(long cardId, double amount) {
        return cardDao.updateCardBalance(cardId, amount);
    }

    public static double checkBalanceByAccount(long accountId) {
        return cardDao.readAccountBalance(accountId);
    }

    public static double checkBalanceByCard(long cardId) {
        return cardDao.readCardBalance(cardId);
    }

}
