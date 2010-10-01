package command;

import java.util.Scanner;

import model.CheckingAccount;
import model.Deposit;

public class AddDeposit extends Command {

	public AddDeposit() {
		super("Deposit", "Add a deposit transaction");
	}

	@Override
	public boolean run() {
		CheckingAccount account = CommandUtil.askCheckingAccount();
		Scanner in = new Scanner(System.in);
		System.out.println("Amount:");
		double amount = in.nextDouble();

		Deposit newDeposit = new Deposit();
		newDeposit.setAccount(account);
		newDeposit.setAmount(amount);
		newDeposit.save();
		
		account.addBalance(amount);
		account.save();

		return true;
	}

}
