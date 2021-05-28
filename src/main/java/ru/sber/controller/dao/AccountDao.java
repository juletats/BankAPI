package ru.sber.controller.dao;

import ru.sber.util.DbUtil;

import java.sql.*;

public class AccountDao implements IAccountDao {
    private static final String insertAccount = "insert into account (balance,idclient) VALUES (?,?)";
    private static final String selectAccount = "select idaccount from account where idClient=(?)";

    @Override
    public long insertAccount(long clientId) {
        long idAccount = 0;
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertAccount, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, 0.0);
            preparedStatement.setLong(2, clientId);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                idAccount = rs.getLong("idAccount");
            }
        } catch (SQLException e) {
            DbUtil.printSQLException(e);
        }
        return idAccount;
    }


}
