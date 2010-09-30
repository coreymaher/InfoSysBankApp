package command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import model.CheckingAccount;
import model.Owner;

public class AddAccount extends Command {

	public AddAccount() {
		super("Add Account", "Adds accounts to the bank");
	}

	@Override
	public boolean run() {
		ArrayList<Owner> ownerCache = Owner.query();
		HashMap<Integer, Owner> ownerList = new HashMap<Integer, Owner>();
		ArrayList<Owner> owners = new ArrayList<Owner>();
		boolean running = true;
		System.out.println("Creating checking account");
		Scanner in = new Scanner(System.in);
		while (running) {
			System.out.print("Owners: ");
			System.out.print("[ ");
			for (Owner owner : owners) {
				System.out.print(owner.getName() + " ");
			}
			System.out.println("]");
			System.out.println("0. Done");
			System.out.println("1. Add New");
			System.out.println("2. Cancel");
			System.out.println("----------");
			int id = 3;
			ownerList = new HashMap<Integer, Owner>();
			for (Owner owner : ownerCache) {
				int curID = id++;
				String ownerID = (owner.getOwnerID() == 0) ? "New" : Integer.toString(owner.getOwnerID());
				System.out.println(curID + ". " + owner.getName() + "(" + ownerID + ")");
				ownerList.put(curID, owner);
			}
			
			try {
				String input = in.nextLine();
				int option = Integer.parseInt(input);
				switch(option) {
				case 0:
					if (owners.size() == 0) {
						break;
					}
					CheckingAccount checkingAccount = new CheckingAccount();
					checkingAccount.setBalance(0.0);
					for (Owner owner : owners) {
						if (owner.isChanged()) {
							owner.save();
						}
						checkingAccount.addOwner(owner);
					}
					checkingAccount.save();
					running = false;
					break;
				case 1:
					Owner newOwner = addUser();
					ownerCache.add(newOwner);
					owners.add(newOwner);
					break;
				case 2:
					running = false;
					break;
					default:
						Owner selectedOwner = ownerList.get(option);
						if (selectedOwner != null) {
							if (owners.contains(selectedOwner)) {
								owners.remove(selectedOwner);
							} else {
								owners.add(selectedOwner);
							}
						}
				}
			} catch (NumberFormatException e) {
				
			}
		}
		return true;
	}
	
	private Owner addUser() {
		Scanner in = new Scanner(System.in);
		boolean running = true;
		String name = "";
		while (running) {
			System.out.println("Name:");
			name = in.next();
			running = (name.length() == 0);
		}
		Owner newOwner = new Owner();
		newOwner.setName(name);
		return newOwner;
	}

}
