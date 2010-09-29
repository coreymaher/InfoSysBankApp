package command;

public abstract class Command {

	private static int commandIDS = 1;

	protected final String name;
	protected final String help;
	protected final int id;

	public Command(String name, String help) {
		this.name = name;
		this.help = help;
		this.id = commandIDS++;
	}

	public String getText() {
		return id + ". " + name + " - " + help;
	}

	public boolean equalsCommand(String input) {
		return (input.equals(name) || input.equals(Integer.toString(id)));
	}

	public abstract boolean run();

}
