package command;

import java.util.Scanner;

import model.CheckingAccount;
import model.Overdraft;

public class AddOverdraft extends Command {

	public AddOverdraft() {
		super("Overdraft", "Add an overdraft fee transaction");
	}

	@Override
	public boolean run() {
		CheckingAccount account = CommandUtil.askCheckingAccount();
		Scanner in = new Scanner(System.in);
		System.out.println("Amount:");
		double amount = in.nextDouble();

		Overdraft newOverdraft = new Overdraft();
		newOverdraft.setAccount(account);
		newOverdraft.setAmount(amount);
		newOverdraft.save();
		
		account.addBalance(-1 * amount);
		account.save();

		return true;
	}

}
