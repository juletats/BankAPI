package ru.sber.controller.dao;

import ru.sber.model.Account;
import ru.sber.model.Card;
import ru.sber.model.Client;
import ru.sber.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDao implements ICardDao {
    private static final String insertCard = "insert into card (status, balance,idaccount) VALUES (?,?,?)";
    private static final String cardsByAccountNumber = "select * from card as ca" +
            " join account as a on ca.IDACCOUNT = a.IDACCOUNT" +
            " join client as c on a.IDCLIENT = c.IDCLIENT " +
            "where ca.idaccount=?";
    private static final String clientCards = "select DISTINCT * from card c" +
            " join ACCOUNT A on c.IDACCOUNT = A.IDACCOUNT" +
            " join CLIENT C2 on C2.IDCLIENT = A.IDCLIENT" +
            " where a.IDCLIENT=?";
    private static final String updateCardBalance = "update card set balance=balance+(?) where idCard=(?)";
    private static final String updateAccountBalance = "update account set balance =(Select SUM(balance) from card where idAccount=?) where idAccount=?";
    private static final String selectCardBalance = "select balance from card where idCard=(?)";
    private static final String selectAccountBalance = "select balance from account where idAccount =(?)";

    private static final IClientDao clientDao = new ClientDao();
    private static final IAccountDao accountDao = new AccountDao();

    private static Card fromResultSet(ResultSet rs) throws SQLException {
        final Client client = new Client();
        client.setIdClient(rs.getLong("idClient"));
        client.setFio(rs.getString("fio"));
        System.out.println(client);
        final Account account = new Account();
        account.setIdAccount(rs.getLong("idAccount"));
        account.setBalance(rs.getDouble("balance"));
        account.setClient(client);
        System.out.println(account);

        final Card card = new Card();
        card.setIdCard(rs.getLong("idCard"));
        card.setBalance(rs.getDouble("balance"));
        card.setStatus(rs.getString("status"));
        card.setAccount(account);
        System.out.println(card);
        return card;
    }

    @Override
    public boolean insertCardByAccount(long idAccount) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertCard);
            preparedStatement.setString(1, "opened");
            preparedStatement.setDouble(2, 0.0);
            preparedStatement.setLong(3, idAccount);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            DbUtil.printSQLException(e);
            return false;
        }
    }

    @Override
    public boolean insertCardByClient(String fio) {
        long idClient = clientDao.insertClient(fio);
        long idAccount = accountDao.insertAccount(idClient);
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertCard);
            preparedStatement.setString(1, "opened");
            preparedStatement.setDouble(2, 0);
            preparedStatement.setLong(3, idAccount);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            DbUtil.printSQLException(e);
            return false;
        }
    }

    @Override
    public List<Card> readAccountCards(long accountId) {
        List<Card> resultList = new ArrayList<>();
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(cardsByAccountNumber);
            preparedStatement.setLong(1, accountId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                resultList.add(fromResultSet(rs));
            }
        } catch (SQLException e) {
            DbUtil.printSQLException(e);
            return Collections.emptyList();
        }
        return resultList;
    }

    @Override
    public List<Card> readClientsCards(long clientId) {
        List<Card> resultList = new ArrayList<>();
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(clientCards);
            preparedStatement.setLong(1, clientId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                resultList.add(fromResultSet(rs));
            }
        } catch (SQLException e) {
            DbUtil.printSQLException(e);
            return Collections.emptyList();
        }
        return resultList;
    }

    @Override
    public boolean updateCardBalance(long cardId, double amount) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateCardBalance);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setLong(2, cardId);
            int update = preparedStatement.executeUpdate();
            return update > 0;
        } catch (SQLException e) {
            DbUtil.printSQLException(e);
            return false;
        }
    }

    @Override
    public void updateAccountBalance(long accountId) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateAccountBalance);
            preparedStatement.setLong(1, accountId);
            preparedStatement.setLong(2, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DbUtil.printSQLException(e);
        }
    }

    @Override
    public double readCardBalance(long cardId) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectCardBalance);
            preparedStatement.setLong(1, cardId);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getDouble("balance");
        } catch (SQLException e) {
            DbUtil.printSQLException(e);
            return -404;
        }
    }

    @Override
    public double readAccountBalance(long accountId) {
        updateAccountBalance(accountId);
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectAccountBalance);
            preparedStatement.setLong(1, accountId);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getDouble("balance");
        } catch (SQLException e) {
            DbUtil.printSQLException(e);
            return -404;
        }
    }
}
