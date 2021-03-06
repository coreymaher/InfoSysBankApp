package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bank.DatabaseManager;

public class Check extends Transaction {
	
	public Check() {
		
	}
	
	public Check(ResultSet resultSet) throws SQLException {
		super(resultSet);
		otherAccount = new CheckingAccount();
		otherAccount.setAccountID(resultSet.getInt("otherAccountID"));
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
			ArrayList<Check> loadedChecks = Check.query("transactionID = " + transactionID);
			Check loadedCheck = loadedChecks.get(0);
			account = loadedCheck.account;
			otherAccount = loadedCheck.otherAccount;
			amount = loadedCheck.amount;
			timestamp = loadedCheck.timestamp;
			setIsLoaded();
		}
	}

	@Override
	protected void insert() {
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "INSERT INTO Transactions (transactionTypeID, accountID, otherAccountID, amount) " +
					"VALUES(" + TransactionType.CHECK.getTransactionTypeID() + ", " + account.getAccountID() + ", " + otherAccount.getAccountID() + ", " + amount + ")";
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
	
	public static ArrayList<Check> query() {
		return query(null);
	}
	
	public static ArrayList<Check> query(String where) {
		ArrayList<Check> checks = new ArrayList<Check>();
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT transactionID, accountID, otherAccountID, amount, timestamp FROM Transactions " +
					"WHERE TransactionTypeID = " + TransactionType.CHECK.getTransactionTypeID();
			if (where == null) {
				query += " AND ( " + where + " )";
			}
			statement.executeQuery(query);
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				Check c = new Check(rs);
				checks.add(c);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checks;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("From Account: " + account.getAccountID()).append("\n");
		result.append("To Account: " + otherAccount.getAccountID()).append("\n");
		result.append(super.toString());

		return result.toString();
	}

}
