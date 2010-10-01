package command;

import java.util.Scanner;

import model.CheckingAccount;
import model.Transfer;

public class AddTransfer extends Command {

	public AddTransfer() {
		super("Transfer", "Add a transfer transaction");
	}

	@Override
	public boolean run() {
		CheckingAccount fromAccount = CommandUtil.askCheckingAccount("From Account");
		CheckingAccount toAccount = CommandUtil.askCheckingAccount("To Account");
		Scanner in = new Scanner(System.in);
		System.out.println("Amount:");
		double amount = in.nextDouble();

		Transfer newTransfer = new Transfer();
		newTransfer.setAccount(fromAccount);
		newTransfer.setOtherAccount(toAccount);
		newTransfer.setAmount(amount);
		newTransfer.save();
		
		fromAccount.addBalance(-1 * amount);
		fromAccount.save();
		
		toAccount.addBalance(amount);
		toAccount.save();

		return true;
	}

}
