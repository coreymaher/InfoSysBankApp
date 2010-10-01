package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;

import bank.DatabaseManager;


public class CheckingAccount extends Model {
	
	private static final NumberFormat format = NumberFormat.getCurrencyInstance();

	private int accountID;
	private double balance;
	
	private boolean balanceChanged = false;
	private boolean ownersChanged = false;
	
	private ArrayList<Owner> owners;
	private ArrayList<Transaction> transactions;
	
	public int getAccountID() {
		return accountID;
	}
	
	public void setAccountID(int newAccountID) {
		accountID = newAccountID;
	}
	
	public double getBalance() {
		loadData();
		return balance;
	}
	
	public String getFormattedBalance() {
		loadData();
		return format.format(balance);
	}
	
	public void setBalance(double newBalance) {
		balanceChanged = true;
		balance = newBalance;
	}
	
	public void addBalance(double additionalBalance) {
		loadData();
		balanceChanged = true;
		balance += additionalBalance;
	}
	
	public boolean isChanged() {
		return balanceChanged;
	}
	
	public ArrayList<Owner> getOwners() {
		if (owners == null ) {
			owners = Owner.findByAccount(this);
		}
		return owners;
	}
	
	public void addOwner(Owner owner) {
		if (owners == null ) {
			owners = Owner.findByAccount(this);
		}
		ownersChanged = true;
		owners.add(owner);
	}
	
	public ArrayList<Transaction> getTransactions(String andWhere) {
		return Transaction.findByAccount(this, andWhere);
	}
	
	public ArrayList<Transaction> getTransactions() {
		if (transactions == null) {
			transactions = Transaction.findByAccount(this);
		}
		return transactions;
	}
	
	protected void loadData() {
		if (isLoaded()) {
			return;
		} else {
			ArrayList<CheckingAccount> loadedAccounts = CheckingAccount.query("accountID = " + accountID);
			CheckingAccount loadedAccount = loadedAccounts.get(0);
			balance = loadedAccount.balance;
			setIsLoaded();
		}
	}

	public static ArrayList<CheckingAccount> query() {
		return query(null);
	}
	
	public static ArrayList<CheckingAccount> query(String where) {
		ArrayList<CheckingAccount> checkingAccounts = new ArrayList<CheckingAccount>();
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT accountID, balance FROM CheckingAccounts";
			if (where != null) {
				query += " WHERE " + where;
			}
			statement.executeQuery(query);
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				CheckingAccount a = new CheckingAccount();
				a.setIsLoaded();
				a.setAccountID( rs.getInt("accountID"));
				a.setBalance(rs.getDouble("balance"));
				checkingAccounts.add(a);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkingAccounts;
	}
	
	protected void insert() {
		if (!isChanged()) {
			return;
		}

		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "INSERT INTO CheckingAccounts (balance) VALUES(" + balance + ")";
			statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.first()) {
				accountID = rs.getInt(1);
			}
			rs.close();
			statement.close();			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (ownersChanged) {
			updateOwnerRelations();
		}
	}
	
	protected void update() {
		if (!isChanged()) {
			return;
		}

		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "UPDATE CheckingAccounts SET balance = " + balance + " WHERE accountID = " + accountID;
			statement.executeUpdate(query);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (ownersChanged) {
			updateOwnerRelations();
		}
	}
	
	private void updateOwnerRelations() {
		for (Owner owner : owners) {
			try {
				Connection connection = DatabaseManager.getInstance().getConnection();
				Statement statement = connection.createStatement();
				String query = "INSERT IGNORE INTO Owners_CheckingAccounts (checkingAccountID, ownerID) VALUES(" + accountID + ", " + owner.getOwnerID() + ")";
				statement.executeUpdate(query);
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
