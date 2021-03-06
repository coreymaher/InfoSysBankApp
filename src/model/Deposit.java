package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bank.DatabaseManager;

public class Deposit extends Transaction {
	
	public Deposit() {

	}
	
	public Deposit(ResultSet resultSet) throws SQLException {
		super(resultSet);
	}

	@Override
	public boolean isChanged() {
		return false;
	}
	
	@Override
	protected void loadData() {
		if (isLoaded()) {
			return;
		} else {
			ArrayList<Deposit> loadedDeposits = Deposit.query("transactionID = " + transactionID);
			Deposit loadedDeposit = loadedDeposits.get(0);
			account = loadedDeposit.account;
			amount = loadedDeposit.amount;
			timestamp = loadedDeposit.timestamp;
			setIsLoaded();
		}
	}

	@Override
	protected void insert() {
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "INSERT INTO Transactions (transactionTypeID, accountID, amount) " +
					"VALUES(" + TransactionType.DEPOSIT.getTransactionTypeID() + ", " + account.getAccountID() + ", " + amount + ")";
			statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.first()) {
				transactionID = rs.getInt(1);
			}
			rs.close();
			statement.close();			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void update() {

	}
	
	public static ArrayList<Deposit> query() {
		return query(null);
	}
	
	public static ArrayList<Deposit> query(String where) {
		ArrayList<Deposit> deposits = new ArrayList<Deposit>();
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT transactionID, accountID, amount, timestamp FROM Transactions " +
					"WHERE TransactionTypeID = " + TransactionType.DEPOSIT.getTransactionTypeID();
			if (where == null) {
				query += " AND ( " + where + " )";
			}
			statement.executeQuery(query);
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				Deposit d = new Deposit(rs);
				deposits.add(d);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deposits;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Account: " + account.getAccountID()).append("\n");
		result.append(super.toString());

		return result.toString();
	}

}
