package command;

import java.util.ArrayList;
import java.util.Scanner;

import model.CheckingAccount;

public class GreaterBalance extends Command {

	public GreaterBalance() {
		super("Balance", "List accounts with a balance greater than the given amount");
	}

	@Override
	public boolean run() {
		Scanner in = new Scanner(System.in);
		System.out.println("Amount:");
		
		double amount = in.nextDouble();
		ArrayList<CheckingAccount> accounts = CheckingAccount.query("balance > " + amount);
		
		for (CheckingAccount account : accounts) {
			System.out.println("Account: " + account.getAccountID() + "\tBalance: " + account.getFormattedBalance());
		}
		return true;
	}

}
