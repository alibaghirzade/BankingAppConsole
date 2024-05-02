package Service.impl;


import entity.CreditCard;
import entity.Customer;
import entity.DepositCard;

import java.sql.*;

public class CreditCardServiceImpl {
    public Connection connection;

    public CreditCardServiceImpl(Connection connection) {
        this.connection = connection;
    }

    public CreditCard addingCreditCard(CreditCard creditCard) throws SQLException {
        String sql = "INSERT INTO credit_card(balance, customer_id, over_draft_limit, interest)" +
                " VALUES (?, ?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setDouble(1, creditCard.getBalance());
        preparedStatement.setInt(2, creditCard.getCustomer_id());
        preparedStatement.setInt(3, creditCard.getOver_draft_limit());
        preparedStatement.setDouble(4, creditCard.getInterest());


        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating credit card failed, no rows affected.");
        }

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int cardId = generatedKeys.getInt(1);
                creditCard.setId(cardId);
                return creditCard;
            } else {
                throw new SQLException("Creating deposit card failed, no ID obtained.");
            }
        }
    }

}
