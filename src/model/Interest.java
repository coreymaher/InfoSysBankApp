package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bank.DatabaseManager;

public class Interest extends Transaction {
	
	public Interest() {

	}
	
	public Interest(ResultSet resultSet) throws SQLException {
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
			ArrayList<Interest> loadedInterestPayments = Interest.query("transactionID = " + transactionID);
			Interest loadedInterestPayment = loadedInterestPayments.get(0);
			account = loadedInterestPayment.account;
			amount = loadedInterestPayment.amount;
			timestamp = loadedInterestPayment.timestamp;
			setIsLoaded();
		}
	}

	@Override
	protected void insert() {
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "INSERT INTO Transactions (transactionTypeID, accountID, amount) " +
					"VALUES(" + TransactionType.INTEREST.getTransactionTypeID() + ", " + account.getAccountID() + ", " + amount + ")";
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
	
	public static ArrayList<Interest> query() {
		return query(null);
	}
	
	public static ArrayList<Interest> query(String where) {
		ArrayList<Interest> interests = new ArrayList<Interest>();
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT transactionID, accountID, amount, timestamp FROM Transactions " +
					"WHERE TransactionTypeID = " + TransactionType.INTEREST.getTransactionTypeID();
			if (where == null) {
				query += " AND ( " + where + " )";
			}
			statement.executeQuery(query);
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				Interest i = new Interest(rs);
				interests.add(i);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return interests;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Account: " + account.getAccountID()).append("\n");
		result.append(super.toString());

		return result.toString();
	}

}
