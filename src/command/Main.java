package command;

public class Main extends CommandList {
	
	public Main() {
		super("Main", "");

		addCommand(new AddAccount());
		addCommand(new Reports());
		addCommand(new Exit());
	}

}
