package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bank.DatabaseManager;


public class CheckingAccount {

	private int accountID;
	private double balance;
	
	private boolean balanceChanged = false;
	
	private ArrayList<Owner> owners;
	
	public int getAccountID() {
		return accountID;
	}
	
	public void setAccountID(int newAccountID) {
		accountID = newAccountID;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(Double newBalance) {
		balanceChanged = true;
		balance = newBalance;
	}
	
	public void addBalance(Double additionalBalance) {
		balanceChanged = true;
		balance += additionalBalance;
	}
	
	public ArrayList<Owner> getOwners() {
		if (owners == null ) {
			owners = Owner.findByAccount(this);
		}
		return owners;
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
	
}
