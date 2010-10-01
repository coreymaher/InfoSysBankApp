package command;

import java.util.ArrayList;
import java.util.Scanner;

import model.Check;
import model.CheckingAccount;
import model.Deposit;
import model.Transaction;

public class Transactions extends Command {

	public Transactions() {
		super("Transactions", "List of transactions for the specified account during the specified time period");
	}

	@Override
	public boolean run() {
		CheckingAccount account = CommandUtil.askCheckingAccount();
		Scanner in = new Scanner(System.in);
		System.out.println("Number of days:");
		int days = in.nextInt();
		
		ArrayList<Deposit> deposits = new ArrayList<Deposit>();
		ArrayList<Check> checks = new ArrayList<Check>();
		for (Transaction transaction : account.getTransactions("timestamp > date_sub(CURDATE(), INTERVAL " + days + " DAY)")) {
			if (transaction instanceof Deposit) {
				deposits.add((Deposit) transaction);
			} else if (transaction instanceof Check) {
				checks.add((Check) transaction);
			}
		}
		
		System.out.println("Deposits:");
		for (Deposit deposit : deposits) {
			System.out.println(deposit);
		}
		
		System.out.println("Checks:");
		for (Check check : checks) {
			System.out.println(check);
		}
		
		return true;
	}

}
