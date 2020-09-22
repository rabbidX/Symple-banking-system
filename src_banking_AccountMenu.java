package banking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountMenu {
    static void start(String cardNumber, Statement statement) throws SQLException {
        System.out.println("You have successfully logged in!");
        System.out.println();
        boolean doStart = false;
        while (true) {
            System.out.println(new StringBuilder().append("1. Balance\n")
                    .append("2. Add income\n")
                    .append("3. Do transfer\n")
                    .append("4. Close account\n")
                    .append("5. Log out\n")
                    .append("0. Exit").toString());
            int action = Main.scanner.nextInt();
            System.out.println();
            if (action == 1) {
                System.out.println("Balance: " + Main.getBalance(cardNumber, statement));
            } else if (action == 2) {
                addIncome(cardNumber, statement);
            } else if (action == 3) {
                doTransfer(cardNumber, statement);
            } else if (action == 4) {
                closeAccount(cardNumber, statement);
                doStart = true;
                break;
            } else if (action == 5) {
                doStart = true;
                break;
            } else {
                break;
            }
        }
        if (doStart) {
            StartMenu.start(statement);
        }
    }
    public static void addIncome(String cardNumber, Statement statement) throws SQLException {
        System.out.println("Enter income:");
        int income = Main.scanner.nextInt();
        Main.changeBalance(cardNumber, income, statement);
        System.out.println("Income was added!");

    }

    public static void doTransfer(String cardNumber, Statement statement) throws SQLException {
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        int recipient = Main.scanner.nextInt();
        String strRecipient = String.valueOf(recipient);
        int controlSum = Account.controlSum(strRecipient.substring(0, strRecipient.length() - 1));
        if (!String.valueOf(controlSum).equals(strRecipient.substring(strRecipient.length() - 1, 1))) {
            System.out.println("Probably you made mistake in the card number. Please try again!");
            return;
        }
        ResultSet resultRecipient = statement.executeQuery("SELECT number" +
                " FROM card" +
                " WHERE number = '" + strRecipient + "'");
        if (!resultRecipient.next()) {
            System.out.println("Such a card does not exist");
            return;
        }
        System.out.println("Enter how much money you want to transfer:");
        int amount = Main.scanner.nextInt();
        if (amount > Main.getBalance(cardNumber, statement)) {
            System.out.println("Not enough money!");
            return;
        }
        Main.changeBalance(cardNumber, -amount, statement);
        Main.changeBalance(strRecipient, amount, statement);

    }

    public static void closeAccount(String cardNumber, Statement statement) throws SQLException {
        statement.executeUpdate("DELETE FROM card " +
                " WHERE number = '" + cardNumber + "'");

    }
}
