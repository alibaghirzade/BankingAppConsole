import Service.CustomerService;
import Service.impl.CreditCardServiceImpl;
import Service.impl.CustomerServiceImpl;
import Service.impl.DepositCardServiceImpl;
import entity.CreditCard;
import entity.Customer;
import entity.DepositCard;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {

    public Main() throws SQLException, ClassNotFoundException {
    }

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/bank_app_console";
        String username = "postgres";
        String password = "admin";
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    static CustomerServiceImpl customerService;

    static {
        try {
            customerService = new CustomerServiceImpl(createConnection());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static DepositCardServiceImpl depositCardService;

    static {
        try {
            depositCardService = new DepositCardServiceImpl(createConnection());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    static CreditCardServiceImpl creditCardService;

    static {
        try {
            creditCardService = new CreditCardServiceImpl(createConnection());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException, ParseException {
        Scanner sc = new Scanner(System.in);
        int input;

        while (true) {
            System.out.println("--------Welcome to 'Baku Bank'. Do you have any account before?--------" +
                    "\n1. Of course, I already have an account." +
                    "\n2. No, I want to be a member of the best bank in Azerbaijan." +
                    "\n3. Bye, bye :(" +
                    "\n\nPlease press '1' if you have an account or '2' to register. For exit press '3'");
            input = sc.nextInt();
            sc.nextLine();

            if (input == 1) {
                System.out.println("Give us your ID number to identify your account ");
                int id = sc.nextInt();
                sc.nextLine();
                System.out.println(customerService.getCustomerById(id));
                while (true) {
                    System.out.println("What do you want to do in your account? " +
                            "\n1.Show my infos" +
                            "\n2.Add Deposit Card" +
                            "\n3.Add Credit Card" +
                            "\n4.Exit");
                    int input4 = sc.nextInt();
                    sc.nextLine();
                    if (input4 == 1) {
                        System.out.println(customerService.getCustomerWithCardsById(1));
                        break;
                    } else if (input4 == 2) {
                        System.out.println("Enter your balance: ");
                        Double balance = sc.nextDouble();
                        sc.nextLine();
                        customerService.addDepositCardToExistingAccount(id, balance);
                    } else if (input4 == 3) {
                        System.out.println("Enter your balance: ");
                        Double balance = sc.nextDouble();
                        sc.nextLine();
                        customerService.addCreditCardToExistingAccount(id, balance);
                    } else if (input4 == 4){
                        System.exit(0);
                    }
                    else {
                        System.out.println("Invalid input. Please enter a valid option (1, 2, or 3).");
                    }

                }
            } else if (input == 2) {
                System.out.println("Share your information with us to become a user of our bank!" +
                        "\nEnter a name: ");
                String name = sc.nextLine();

                System.out.println("Enter a surname: ");
                String surname = sc.nextLine();

                System.out.print("Enter a birth date (yyyy-MM-dd): ");
                String dateStr = sc.next();
                sc.nextLine();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate;
                parsedDate = sdf.parse(dateStr);
                Date sqlDate = new Date(parsedDate.getTime());

                Customer c = customerService.insertCustomer(new Customer(name, surname, sqlDate));

                System.out.println("Your account has been successfully created!" +
                        "\nWould you like to add a card to your account?" +
                        "\n1.Yes, add" +
                        "\n2.No, exit" +
                        "\n\nPlease press '1' if you want or press '2' to exit");
                int input2 = sc.nextInt();
                sc.nextLine();

                while (true) {
                    if (input2 == 1) {
                        while (true) {
                            System.out.println("Which type of card do you want?" +
                                    "\n1.Deposit Card" +
                                    "\n2.Credit Card");
                            int input3 = sc.nextInt();
                            sc.nextLine();
                            if (input3 == 1) {
                                System.out.println("Enter your balance: ");
                                Double balance = sc.nextDouble();
                                sc.nextLine();

                                DepositCard depositCard = depositCardService.addingDepositCard(new DepositCard(c.getId(), balance));
                                System.exit(0);
                            } else if (input3 == 2) {
                                System.out.println("Enter Your Balance: ");
                                Double balance = sc.nextDouble();
                                sc.nextLine();

                                CreditCard creditCard = creditCardService.addingCreditCard(new CreditCard(c.getId(), balance));
                                System.exit(0);
                            } else {
                                System.out.println("Invalid input. Please enter a valid option (1, 2, or 3).");
                            }
                        }


                    } else if (input2 == 2) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a valid option (1, 2, or 3).");
                    }
                }

            } else if (input == 3) {
                System.out.println("Thank you for using 'Baku Bank'. Have a great day!");
                System.exit(0);
            } else {
                System.out.println("Invalid input. Please enter a valid option (1, 2, or 3).");
            }
        }
    }
}
