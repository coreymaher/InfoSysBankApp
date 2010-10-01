package command;

public class AddTransaction extends CommandList {

	public AddTransaction() {
		super("Add Transaction", "Add transactions");
		
		addCommand(new AddDeposit());
		addCommand(new AddCheck());
		addCommand(new AddTransfer());
		addCommand(new AddInterest());
		addCommand(new AddCheckPrinting());
		addCommand(new AddOverdraft());
		addCommand(new AddWireTransfer());
		addCommand(new Back());
	}

}
