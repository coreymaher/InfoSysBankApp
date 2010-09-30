package command;

import java.util.ArrayList;

import model.CheckingAccount;
import model.Owner;

public class List extends Command {

	public List() {
		super("List", "Lists information about all accounts");
	}

	@Override
	public boolean run() {
		ArrayList<CheckingAccount> accounts = CheckingAccount.query();
		for (CheckingAccount account : accounts) {
			System.out.println("Account: " + account.getAccountID());
			System.out.println("\tBalance: " + account.getFormattedBalance());
			System.out.println("\tOwner(s):");
			for (Owner owner : account.getOwners()) {
				System.out.println("\t\t" + owner.getName());
			}
		}

		return true;
	}

}
