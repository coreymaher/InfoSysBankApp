package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import bank.DatabaseManager;

public enum TransactionType {

	DEPOSIT,
	CHECK,
	TRANSFER,
	INTEREST,
	CHECKPRINTING,
	OVERDRAFT,
	WIRETRANSFER;

	private int transactionTypeID = -1;

	public int getTransactionTypeID() {
		if (transactionTypeID == -1) {
			LoadCache();
		}
		return transactionTypeID;
	}
	
	public static void LoadCache() {
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT transactionID, name FROM TransactionTypes";
			statement.executeQuery(query);
			ResultSet rs = statement.getResultSet();
			ArrayList<TransactionType> transactionTypes = new ArrayList<TransactionType>(Arrays.asList(TransactionType.values()));
			while (rs.next()) {
				int transactionID = rs.getInt("transactionID");
				String name = rs.getString("name").replaceAll(" ", "").toUpperCase();
				for (TransactionType transactionType : transactionTypes) {
					if (name.equals(transactionType.name())) {
						transactionType.transactionTypeID = transactionID;
						transactionTypes.remove(transactionType);
						break;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
