package banking;

import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.*;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    private static final SQLiteDataSource dataSource = new SQLiteDataSource();
    public static void main(String[] args) {
        String url ="jdbc:sqlite:" + args[1];
        //String url = "jdbc:sqlite:card.s3db";
        dataSource.setUrl(url);
        try (Connection con = dataSource.getConnection()) {
           // Statement creation
            try (Statement statement = con.createStatement()) {
                // Statement execution
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER," +
                        "number TEXT NOT NULL," +
                        "pin TEXT NOT NULL," +
                        "balance INTEGER DEFAULT 0)");
                StartMenu.start(statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       System.out.println("Bye!");
    }

    public static int getBalance(String cardNumber, Statement statement) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT balance " +
                " FROM card " +
                " WHERE number = '" + cardNumber + "'");
        result.next();
        return result.getInt("balance");
    }

    public static void changeBalance(String cardNumber, int amount, Statement statement) throws SQLException {
        String balanceString;
        if (amount > 0) {
            balanceString = "balance + " + amount;
        } else {
            balanceString = "balance - " + (-amount);
        }
        statement.executeUpdate("UPDATE card SET balance = balance + " + balanceString +
                " WHERE number = '" + cardNumber + "'");

    }


}
