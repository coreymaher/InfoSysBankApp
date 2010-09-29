package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Bank.DatabaseManager;

public class CheckingAccount {

	private int accountID;
	private double balance;
	
	private ArrayList<Owner> owners;
	
	public int getAccountID() {
		return accountID;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(Double newBalance) {
		balance = newBalance;
	}
	
	public void addBalance(Double additionalBalance) {
		balance += additionalBalance;
	}
	
	public ArrayList<Owner> getOwners() {
		if (owners == null ) {
			owners = Owner.query("accountID=" + accountID);
		}
		return owners;
	}
	
}
