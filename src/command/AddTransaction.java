package command;

public class AddTransaction extends CommandList {

	public AddTransaction() {
		super("Add Transaction", "Add transactions");
		
		addCommand(new AddDeposit());
		addCommand(new AddCheck());
		addCommand(new Back());
	}

}
