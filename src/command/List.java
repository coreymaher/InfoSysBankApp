package command;

import java.text.NumberFormat;
import java.util.ArrayList;

import model.CheckingAccount;
import model.Owner;

public class List extends Command {

	private static final NumberFormat format = NumberFormat.getCurrencyInstance();

	public List() {
		super("List", "Lists information about all accounts");
	}

	@Override
	public boolean run() {
		ArrayList<CheckingAccount> accounts = CheckingAccount.query();
		for (CheckingAccount account : accounts) {
			System.out.println("Account: " + account.getAccountID());
			System.out.println("\tBalance: " + format.format(account.getBalance()));
			System.out.println("\tOwner(s):");
			for (Owner owner : account.getOwners()) {
				System.out.println("\t\t" + owner.getName());
			}
		}

		return true;
	}

}
