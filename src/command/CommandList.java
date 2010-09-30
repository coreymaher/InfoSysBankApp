package command;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class CommandList extends Command {
	
	private int currentIndex = 1;
	private ArrayList<Command> commandList;

	public CommandList(String name, String help) {
		super(name, help);

		commandList = new ArrayList<Command>();
	}
	
	public void addCommand(Command command) {
		command.id = currentIndex++;
		commandList.add(command);
	}

	@Override
	public boolean run() {
		boolean running = true;
		Scanner in = new Scanner(System.in);
		while (running) {
			printMenu();
			String input = in.nextLine();
			for (Command command : commandList) {
				if(command.equalsCommand(input)) {
					running = command.run();
				}
			}
		}
		return true;
	}
	
	public void printMenu() {
		System.out.println(name + " Menu:");
		for (Command command : commandList) {
			System.out.println(command.getText());
		}
	}

}
