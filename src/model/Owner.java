package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Bank.DatabaseManager;

public class Owner {
	
	private int ownerID;
	private String name;
	
	private boolean nameChanged = false;
	
	public void setOwnerID( int newOwnerID ) {
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
	
	public boolean isNameChanged() {
		return nameChanged;
	}
	
	public static ArrayList<Owner> query(String where) {
		ArrayList<Owner> owners = new ArrayList<Owner>();
		try {
			Connection connection = DatabaseManager.getInstance().getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT id, name FROM owner";
			if (where != null) {
				query += " WHERE " + where;
			}
			statement.executeQuery(query);
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				Owner o = new Owner();
				o.setOwnerID( rs.getInt("id"));
				o.setName(rs.getString("name"));
				owners.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return owners;
	}

}
