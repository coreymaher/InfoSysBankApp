package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bank.DatabaseManager;

public class Overdraft extends Transaction {
	
	public Overdraft() {

	}
	
	public Overdraft(ResultSet resultSet) throws SQLException {
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
			ArrayList<Overdraft> loadedOverdrafts = Overdraft.query("transactionID = " + transactionID);
			Overdraft loadedOverdraft = loadedOverdrafts.get(0);
			account = loadedOverdraft.account;
			amount = loadedOverdraft.amount;
			timestamp = loadedOverdraft.timestamp;
			setIsLoaded();
		}
	}

	@Override
	protected void insert() {
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "INSERT INTO Transactions (transactionTypeID, accountID, amount) " +
					"VALUES(" + TransactionType.OVERDRAFT.getTransactionTypeID() + ", " + account.getAccountID() + ", " + amount + ")";
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
	
	public static ArrayList<Overdraft> query() {
		return query(null);
	}
	
	public static ArrayList<Overdraft> query(String where) {
		ArrayList<Overdraft> overdrafts = new ArrayList<Overdraft>();
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT transactionID, accountID, amount, timestamp FROM Transactions " +
					"WHERE TransactionTypeID = " + TransactionType.OVERDRAFT.getTransactionTypeID();
			if (where == null) {
				query += " AND ( " + where + " )";
			}
			statement.executeQuery(query);
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				Overdraft o = new Overdraft(rs);
				overdrafts.add(o);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return overdrafts;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Account: " + account.getAccountID()).append("\n");
		result.append(super.toString());

		return result.toString();
	}

}
