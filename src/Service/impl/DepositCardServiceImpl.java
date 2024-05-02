package Service.impl;

import entity.Customer;
import entity.DepositCard;

import java.sql.*;

public class DepositCardServiceImpl {
    public Connection connection;

    public DepositCardServiceImpl(Connection connection) {
        this.connection = connection;
    }

    public DepositCard addingDepositCard(DepositCard depositCard) throws SQLException {
        String sql = "INSERT INTO deposit_cards (balance, customer_id) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setDouble(1, depositCard.getBalance());
        preparedStatement.setInt(2, depositCard.getCustomer_id());

        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating deposit card failed, no rows affected.");
        }

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int cardId = generatedKeys.getInt(1);
                depositCard.setId(cardId);
                return depositCard;
            } else {
                throw new SQLException("Creating deposit card failed, no ID obtained.");
            }
        }
    }

}
