package command;

import java.util.ArrayList;
import java.util.Scanner;

import model.Check;
import model.CheckingAccount;

public class Checks extends Command {

	public Checks() {
		super("Checks", "List checks for a specific account in a period of time");
	}

	@Override
	public boolean run() {
		CheckingAccount selectedAccount = CommandUtil.askCheckingAccount();
		
		if (selectedAccount != null) {
			System.out.println("Number of days:");
			Scanner in = new Scanner(System.in);
			String input = in.next();
			int days = Integer.parseInt(input);
			
			ArrayList<Check> checks = Check.query("timestamp > date_sub(CURDATE(), INTERVAL" + days + " DAY");
			for (Check check : checks) {
				System.out.println(check.getTransactionID());
			}
		}
		return true;
	}

}
