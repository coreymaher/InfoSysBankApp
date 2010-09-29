package Bank;
import java.util.ArrayList;
import java.util.Scanner;

import model.CheckingAccount;
import model.Owner;


public class Bank {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) {
		boolean running = true;
		Scanner in = new Scanner(System.in);
		while (running) {
			String input = in.nextLine();
			if (input.equals("exit")) {
				running = false;
			} else if (input.equals("list")) {
				//ArrayList<CheckingAccount> accounts;
				CheckingAccount acct = new CheckingAccount();
				ArrayList<Owner> owners = acct.getOwners();
			}
		}
	}

}
