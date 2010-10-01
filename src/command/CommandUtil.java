package command;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

import model.CheckingAccount;

public class CommandUtil {
	
	public static CheckingAccount askCheckingAccount() {
		return askCheckingAccount("Account");
	}

	public static CheckingAccount askCheckingAccount(String prompt) {
		ArrayList<CheckingAccount> accountList = CheckingAccount.query();
		TreeMap<Integer, CheckingAccount> accounts = new TreeMap<Integer, CheckingAccount>();
		CheckingAccount selectedAccount = null;
		int id = 2;
		for (CheckingAccount account : accountList) {
			accounts.put(id++, account);
		}
		Scanner in = new Scanner(System.in);
		boolean running = true;
		while (running) {
			System.out.println(prompt + ":");
			System.out.println("1. Cancel");
			System.out.println("---------");
			for (Entry<Integer, CheckingAccount> entry : accounts.entrySet()) {
				System.out.println(entry.getKey() + ". " + entry.getValue().getAccountID());
			}
			
			String input = in.next();
			try {
				int option = Integer.parseInt(input);
				if (option == 1) {
					running = false;
				} else {
					if (accounts.containsKey(option)) {
						selectedAccount = accounts.get(option);
						break;
					}
				}
			} catch (NumberFormatException e) {
				
			}
		}
		
		return selectedAccount;
	}


}
