package command;

import java.util.Scanner;

import model.CheckingAccount;
import model.WireTransfer;

public class AddWireTransfer extends Command {

	public AddWireTransfer() {
		super("Wire Transfer", "Add a wire transfer fee transaction");
	}

	@Override
	public boolean run() {
		CheckingAccount account = CommandUtil.askCheckingAccount();
		Scanner in = new Scanner(System.in);
		System.out.println("Amount:");
		double amount = in.nextDouble();

		WireTransfer newWireTransfer = new WireTransfer();
		newWireTransfer.setAccount(account);
		newWireTransfer.setAmount(amount);
		newWireTransfer.save();
		
		account.addBalance(-1 * amount);
		account.save();

		return true;
	}

}
