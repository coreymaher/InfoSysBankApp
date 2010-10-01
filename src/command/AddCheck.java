package command;

import java.util.Scanner;

import model.Check;
import model.CheckingAccount;

public class AddCheck extends Command {

	public AddCheck() {
		super("Check", "Add a check transaction");
	}

	@Override
	public boolean run() {
		CheckingAccount fromAccount = CommandUtil.askCheckingAccount("From Account");
		CheckingAccount toAccount = CommandUtil.askCheckingAccount("To Account");
		Scanner in = new Scanner(System.in);
		System.out.println("Amount:");
		double amount = in.nextDouble();

		Check newCheck = new Check();
		newCheck.setAccount(fromAccount);
		newCheck.setOtherAccount(toAccount);
		newCheck.setAmount(amount);
		newCheck.save();
		
		fromAccount.addBalance(-1 * amount);
		fromAccount.save();
		
		toAccount.addBalance(amount);
		toAccount.save();

		return true;
	}

}
