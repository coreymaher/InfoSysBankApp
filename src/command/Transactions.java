package command;

import java.util.ArrayList;
import java.util.Scanner;

import model.Check;
import model.CheckPrinting;
import model.CheckingAccount;
import model.Deposit;
import model.Interest;
import model.Overdraft;
import model.Transaction;
import model.Transfer;
import model.WireTransfer;

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
		ArrayList<Transfer> transfers = new ArrayList<Transfer>();
		ArrayList<Interest> interests = new ArrayList<Interest>();
		ArrayList<CheckPrinting> checkPrintings = new ArrayList<CheckPrinting>();
		ArrayList<Overdraft> overdrafts = new ArrayList<Overdraft>();
		ArrayList<WireTransfer> wireTransfers = new ArrayList<WireTransfer>();
		for (Transaction transaction : account.getTransactions("timestamp > date_sub(CURDATE(), INTERVAL " + days + " DAY)")) {
			if (transaction instanceof Deposit) {
				deposits.add((Deposit) transaction);
			} else if (transaction instanceof Check) {
				checks.add((Check) transaction);
			} else if (transaction instanceof Transfer) {
				transfers.add((Transfer) transaction);
			} else if (transaction instanceof Interest) {
				interests.add((Interest) transaction);
			} else if (transaction instanceof CheckPrinting) {
				checkPrintings.add((CheckPrinting) transaction);
			} else if (transaction instanceof Overdraft) {
				overdrafts.add((Overdraft) transaction);
			} else if (transaction instanceof WireTransfer) {
				wireTransfers.add((WireTransfer) transaction);
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
		
		System.out.println("Transfers:");
		for (Transfer transfer : transfers) {
			System.out.println(transfer);
		}
		
		System.out.println("Interest Payments:");
		for (Interest interest : interests) {
			System.out.println(interest);
		}
		
		System.out.println("Fees:");
		System.out.println("Check Printings:");
		for (CheckPrinting checkPrinting : checkPrintings) {
			System.out.println(checkPrinting);
		}
		
		System.out.println("Overdrafts:");
		for (Overdraft overdraft : overdrafts) {
			System.out.println(overdraft);
		}
		
		System.out.println("Wire Transfer:");
		for (WireTransfer wireTransfer : wireTransfers) {
			System.out.println(wireTransfer);
		}
		
		return true;
	}

}
