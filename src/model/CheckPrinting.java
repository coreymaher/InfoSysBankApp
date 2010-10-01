package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bank.DatabaseManager;

public class CheckPrinting extends Transaction {
	
	public CheckPrinting() {

	}
	
	public CheckPrinting(ResultSet resultSet) throws SQLException {
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
			ArrayList<CheckPrinting> loadedCheckPrintings = CheckPrinting.query("transactionID = " + transactionID);
			CheckPrinting loadedCheckPrinting = loadedCheckPrintings.get(0);
			account = loadedCheckPrinting.account;
			amount = loadedCheckPrinting.amount;
			timestamp = loadedCheckPrinting.timestamp;
			setIsLoaded();
		}
	}

	@Override
	protected void insert() {
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "INSERT INTO Transactions (transactionTypeID, accountID, amount) " +
					"VALUES(" + TransactionType.CHECKPRINTING.getTransactionTypeID() + ", " + account.getAccountID() + ", " + amount + ")";
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
	
	public static ArrayList<CheckPrinting> query() {
		return query(null);
	}
	
	public static ArrayList<CheckPrinting> query(String where) {
		ArrayList<CheckPrinting> checkPrintings = new ArrayList<CheckPrinting>();
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT transactionID, accountID, amount, timestamp FROM Transactions " +
					"WHERE TransactionTypeID = " + TransactionType.CHECKPRINTING.getTransactionTypeID();
			if (where == null) {
				query += " AND ( " + where + " )";
			}
			statement.executeQuery(query);
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				CheckPrinting cp = new CheckPrinting(rs);
				checkPrintings.add(cp);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkPrintings;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Account: " + account.getAccountID()).append("\n");
		result.append(super.toString());

		return result.toString();
	}

}
