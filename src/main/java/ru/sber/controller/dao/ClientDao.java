package ru.sber.controller.dao;

import ru.sber.util.DbUtil;

import java.sql.*;

public class ClientDao implements IClientDao {
    private static final String insertClient = "insert into client(fio) VALUES (?)";


    @Override
    public long insertClient(String fio) {
        long idClient = 0;
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertClient, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, fio);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                idClient = rs.getLong("idClient");
            }
        } catch (SQLException e) {
            DbUtil.printSQLException(e);
        }
        return idClient;

    }
}
