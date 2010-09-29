package command;

public class Exit extends Command {

	public Exit() {
		super("Exit", "Exits the application");
	}

	@Override
	public boolean run() {
		return false;
	}

}
