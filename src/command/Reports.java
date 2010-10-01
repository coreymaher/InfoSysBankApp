package command;

public class Reports extends CommandList {

	public Reports() {
		super("Reports", "Generate reports");
		
		addCommand(new List());
		addCommand(new GreaterBalance());
		addCommand(new Checks());
		addCommand(new Transactions());
		addCommand(new Back());
	}

}
