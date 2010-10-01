package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bank.DatabaseManager;

public class WireTransfer extends Transaction {
	
	public WireTransfer() {

	}
	
	public WireTransfer(ResultSet resultSet) throws SQLException {
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
			ArrayList<WireTransfer> loadedWireTransfers = WireTransfer.query("transactionID = " + transactionID);
			WireTransfer loadedWireTransfer = loadedWireTransfers.get(0);
			account = loadedWireTransfer.account;
			amount = loadedWireTransfer.amount;
			timestamp = loadedWireTransfer.timestamp;
			setIsLoaded();
		}
	}

	@Override
	protected void insert() {
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "INSERT INTO Transactions (transactionTypeID, accountID, amount) " +
					"VALUES(" + TransactionType.WIRETRANSFER.getTransactionTypeID() + ", " + account.getAccountID() + ", " + amount + ")";
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
	
	public static ArrayList<WireTransfer> query() {
		return query(null);
	}
	
	public static ArrayList<WireTransfer> query(String where) {
		ArrayList<WireTransfer> wireTransfers = new ArrayList<WireTransfer>();
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT transactionID, accountID, amount, timestamp FROM Transactions " +
					"WHERE TransactionTypeID = " + TransactionType.WIRETRANSFER.getTransactionTypeID();
			if (where == null) {
				query += " AND ( " + where + " )";
			}
			statement.executeQuery(query);
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				WireTransfer wt = new WireTransfer(rs);
				wireTransfers.add(wt);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wireTransfers;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Account: " + account.getAccountID()).append("\n");
		result.append(super.toString());

		return result.toString();
	}

}
