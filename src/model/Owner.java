package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bank.DatabaseManager;


public class Owner {
	
	private boolean loaded = false;

	private int ownerID;
	private String name;
	
	private boolean nameChanged = false;
	
	public void setOwnerID(int newOwnerID) {
		ownerID = newOwnerID;
	}
	
	public int getOwnerID() {
		return ownerID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName( String newName ) {
		nameChanged = true;
		name = newName;
	}
	
	public boolean isChanged() {
		return nameChanged;
	}
	
	public static ArrayList<Owner> query() {
		return query(null);
	}
	
	public static ArrayList<Owner> query(String where) {
		ArrayList<Owner> owners = new ArrayList<Owner>();
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT ownerID, name FROM Owners";
			if (where != null) {
				query += " WHERE " + where;
			}
			statement.executeQuery(query);
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				Owner o = new Owner();
				o.loaded = true;
				o.ownerID = rs.getInt("ownerID");
				o.name = rs.getString("name");
				owners.add(o);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return owners;
	}
	
	public static ArrayList<Owner> findByAccount(CheckingAccount account) {
		ArrayList<Owner> owners = new ArrayList<Owner>();
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT Owners.ownerID, Owners.name FROM Owners,Owners_CheckingAccounts " +
					"WHERE Owners_CheckingAccounts.ownerID = Owners.ownerID AND " +
					"Owners_CheckingAccounts.checkingAccountID = " + account.getAccountID();
			statement.executeQuery(query);
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				Owner o = new Owner();
				o.loaded = true;
				o.ownerID = rs.getInt("ownerID");
				o.name = rs.getString("name");
				owners.add(o);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return owners;
	}
	
	public void save() {
		if (loaded) {
			update();
		} else {
			insert();
		}
	}
	
	private void insert() {
		if (!isChanged()) {
			return;
		}

		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "INSERT INTO Owners (name) VALUES('" + name + "')";
			statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.first()) {
				ownerID = rs.getInt(1);
			}
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void update() {
		if (!isChanged()) {
			return;
		}

		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "UPDATE Owners SET name = '" + name + "' WHERE ownerID = " + ownerID;
			statement.executeUpdate(query);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
