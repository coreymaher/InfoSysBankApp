package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import bank.DatabaseManager;

public abstract class Transaction extends Model {
	
	private static final NumberFormat format = NumberFormat.getCurrencyInstance();
	private static final DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.LONG, SimpleDateFormat.LONG);
	
	protected int transactionID;
	protected Date timestamp;
	protected CheckingAccount account;
	protected CheckingAccount otherAccount;
	protected double amount;
	
	public Transaction() {
		
	}
	
	public Transaction(ResultSet resultSet) throws SQLException {
		transactionID = resultSet.getInt("transactionID");
		timestamp = resultSet.getTimestamp("timestamp");
		amount = resultSet.getDouble("amount");
		
		account = new CheckingAccount();
		account.setAccountID(resultSet.getInt("accountID"));
		
		setIsLoaded();
	}

	public int getTransactionID() {
		return transactionID;
	}
	
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public CheckingAccount getAccount() {
		return account;
	}
	
	public void setAccount(CheckingAccount account) {
		this.account = account;
	}
	
	public CheckingAccount getOtherAccount() {
		return otherAccount;
	}
	
	public void setOtherAccount(CheckingAccount otherAccount) {
		this.otherAccount = otherAccount;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public static ArrayList<Transaction> findByAccount(CheckingAccount account) {
		return findByAccount(account, null);
	}
	
	public static ArrayList<Transaction> findByAccount(CheckingAccount account, String andWhere) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT transactionID, transactionTypeID, accountID, otherAccountID, amount, timestamp FROM Transactions " +
					"WHERE (accountID = " + account.getAccountID() + " OR otherAccountID = " + account.getAccountID() + ")";
			if (andWhere != null) {
				query += " AND ( " + andWhere + " )"; 
			}
			statement.executeQuery(query);
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				int transactionTypeID = rs.getInt("transactionTypeID");
				if (TransactionType.DEPOSIT.getTransactionTypeID() == transactionTypeID) {
					transactions.add(new Deposit(rs));
				} else if (TransactionType.CHECK.getTransactionTypeID() == transactionTypeID) {
					transactions.add(new Check(rs));
				} else if (TransactionType.TRANSFER.getTransactionTypeID() == transactionTypeID) {
					transactions.add(new Transfer(rs));
				} else if (TransactionType.INTEREST.getTransactionTypeID() == transactionTypeID) {
					transactions.add(new Interest(rs));
				} else if (TransactionType.CHECKPRINTING.getTransactionTypeID() == transactionTypeID) {
					transactions.add(new CheckPrinting(rs));
				} else if (TransactionType.OVERDRAFT.getTransactionTypeID() == transactionTypeID) {
					transactions.add(new Overdraft(rs));
				} else if (TransactionType.WIRETRANSFER.getTransactionTypeID() == transactionTypeID) {
					transactions.add(new WireTransfer(rs));
				}
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return transactions;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("Amount: " + format.format(amount)).append("\n");
		result.append("Date: " + dateFormat.format(timestamp)).append("\n");
		
		return result.toString();
	}

}
