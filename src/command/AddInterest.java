package command;

import java.util.Scanner;

import model.CheckingAccount;
import model.Interest;

public class AddInterest extends Command {

	public AddInterest() {
		super("Interest Payment", "Add a interest payment transaction");
	}

	@Override
	public boolean run() {
		CheckingAccount account = CommandUtil.askCheckingAccount();
		Scanner in = new Scanner(System.in);
		System.out.println("Amount:");
		double amount = in.nextDouble();

		Interest newDeposit = new Interest();
		newDeposit.setAccount(account);
		newDeposit.setAmount(amount);
		newDeposit.save();
		
		account.addBalance(amount);
		account.save();

		return true;
	}

}
