package command;

public class Back extends Command {

	public Back() {
		super("Back", "Return to the previous menu");
	}

	@Override
	public boolean run() {
		return false;
	}

}
