package Service.impl;

import Service.CustomerService;
import entity.CreditCard;
import entity.Customer;
import entity.DepositCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl {
    public Connection connection;

    public CustomerServiceImpl(Connection connection) {
        this.connection = connection;
    }

    public Customer insertCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers(name, surname, birth_date) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getSurname());
        preparedStatement.setDate(3, customer.getBirth_date());

        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating customer failed, no rows affected.");
        }

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int customerId = generatedKeys.getInt(1);
                customer.setId(customerId);
                return customer;
            } else {
                throw new SQLException("Creating customer failed, no ID obtained.");
            }
        }
    }

    public Customer getCustomerById(int id) throws SQLException {
        String sql = "SELECT * FROM customers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    Date birthDate = resultSet.getDate("birth_date");
                    return new Customer(id, name, surname, birthDate);
                }
            }
        }
        return null;
    }

    public Customer getCustomerWithCardsById(int id) throws SQLException {
        String sql = "SELECT c.id, c.name, c.surname, c.birth_date, " +
                "d.id AS deposit_card_id, d.balance AS deposit_card_balance, " +
                "cr.id AS credit_card_id, cr.balance AS credit_card_balance " +
                "FROM customers c " +
                "LEFT JOIN deposit_cards d ON c.id = d.customer_id " +
                "LEFT JOIN credit_card cr ON c.id = cr.customer_id " +
                "WHERE c.id = ?";

        Customer customer = null;
        List<DepositCard> depositCards = new ArrayList<>();
        List<CreditCard> creditCards = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    if (customer == null) {
                        String name = resultSet.getString("name");
                        String surname = resultSet.getString("surname");
                        Date birthDate = resultSet.getDate("birth_date");
                        customer = new Customer(id, name, surname, birthDate);
                    }

                    int depositCardId = resultSet.getInt("deposit_card_id");
                    if (!resultSet.wasNull()) {
                        double depositCardBalance = resultSet.getDouble("deposit_card_balance");
                        depositCards.add(new DepositCard(depositCardId, depositCardBalance));
                    }

                    int creditCardId = resultSet.getInt("credit_card_id");
                    if (!resultSet.wasNull()) {
                        double creditCardBalance = resultSet.getDouble("credit_card_balance");
                        creditCards.add(new CreditCard(creditCardId, creditCardBalance));
                    }
                }
            }
        }

        if (customer != null) {
            customer.setDepositCards(depositCards);
            customer.setCreditCards(creditCards);
        }

        return customer;
    }

    public void addDepositCardToExistingAccount(Integer customerId, Double balance) throws SQLException {
        DepositCard depositCard = new DepositCard(customerId, balance);

        String sql = "INSERT INTO deposit_cards (customer_id, balance) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, depositCard.getCustomer_id());
            preparedStatement.setDouble(2, depositCard.getBalance());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                System.out.println("Deposit card added successfully.");
            } else {
                System.out.println("Failed to add deposit card.");
            }
        }
    }

    public void addCreditCardToExistingAccount(Integer customerId, Double balance) throws SQLException {
        CreditCard creditCard = new CreditCard(customerId, balance);

        String sql = "INSERT INTO deposit_cards (customer_id, balance) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, creditCard.getCustomer_id());
            preparedStatement.setDouble(2, creditCard.getBalance());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                System.out.println("Deposit card added successfully.");
            } else {
                System.out.println("Failed to add deposit card.");
            }
        }
    }

//        int rowsInserted = preparedStatement.executeUpdate();
//        System.out.println(rowsInserted + " row(s) inserted.");
}





