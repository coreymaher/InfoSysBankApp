package bank;
import java.util.Scanner;

import command.Command;
import command.Exit;
import command.List;


public class Bank {
	
	private static Command commandList[] = { new List(), new Exit() };

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) {
		boolean running = true;
		Scanner in = new Scanner(System.in);
		while (running) {
			PrintMenu();
			String input = in.nextLine();
			for (Command command : commandList) {
				if(command.equalsCommand(input)) {
					running = command.run();
				}
			}
		}
	}
	
	public static void PrintMenu() {
		System.out.println("Main Menu:");
		for (Command command : commandList) {
			System.out.println(command.getText());
		}
	}

}
