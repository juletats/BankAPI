package ru.sber.controller.dao;

import ru.sber.model.Card;

import java.util.List;

public interface ICardDao {

    boolean insertCardByAccount(long idAccount);

    boolean insertCardByClient(String fio);

    List<Card> readAccountCards(long accountId);

    List<Card> readClientsCards(long clientId);

    boolean updateCardBalance(long cardId, double amount);

    void updateAccountBalance(long accountId);

    double readCardBalance(long cardId);

    double readAccountBalance(long accountId);
}
