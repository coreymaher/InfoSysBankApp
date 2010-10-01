package command;

import java.util.Scanner;

import model.CheckPrinting;
import model.CheckingAccount;

public class AddCheckPrinting extends Command {

	public AddCheckPrinting() {
		super("Check Printing", "Add a check printing fee transaction");
	}

	@Override
	public boolean run() {
		CheckingAccount account = CommandUtil.askCheckingAccount();
		Scanner in = new Scanner(System.in);
		System.out.println("Amount:");
		double amount = in.nextDouble();

		CheckPrinting newCheckPrinting = new CheckPrinting();
		newCheckPrinting.setAccount(account);
		newCheckPrinting.setAmount(amount);
		newCheckPrinting.save();
		
		account.addBalance(-1 * amount);
		account.save();

		return true;
	}

}
