package banking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StartMenu {
    static void start(Statement statement) throws SQLException {
        System.out.println(new StringBuilder().append("1. Create an account\n")
                .append("2. Log into account\n")
                .append("0. Exit").toString());
        int action = Main.scanner.nextInt();
        System.out.println();
        if (action == 1) {
            createAnAccount(statement);
        } else if (action == 2) {
            logIntoAccount(statement);
        }
    }
    private static void createAnAccount(Statement statement) throws SQLException {
        Account account = new Account();
        System.out.println(new StringBuilder()
                .append("Your card has been created\n")
                .append("Your card number:\n")
                .append(account.getCardNumber())
                .append("\nYour card PIN:\n")
                .append(account.getPIN()).toString());
        statement.executeUpdate("INSERT INTO card (number, pin) VALUES " +
                "(" + account.getCardNumber() + ", " + account.getPIN() + ")");
        System.out.println();
        start(statement);
    }

    private static void logIntoAccount(Statement statement) throws SQLException{
        System.out.println("Enter your card number:");
        String cardNumber = Main.scanner.next();
        System.out.println("Enter your PIN:\n");
        String PIN = Main.scanner.next();

        ResultSet result = statement.executeQuery("SELECT * " +
                "FROM card " +
                "WHERE number = '" + cardNumber + "'" +
                " AND pin = '" + PIN + "'");
        if (result.next()) {
            AccountMenu.start(cardNumber, statement);
        } else {
            System.out.println("Wrong card number or PIN!");
            System.out.println();
            start(statement);
        }
    }

}
