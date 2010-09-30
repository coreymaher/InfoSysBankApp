package command;

public abstract class Command {

	protected final String name;
	protected final String help;
	protected int id;

	public Command(String name, String help) {
		this.name = name;
		this.help = help;
	}

	public String getText() {
		return id + ". " + name + " - " + help;
	}

	public boolean equalsCommand(String input) {
		return (input.equals(name) || input.equals(Integer.toString(id)));
	}

	public abstract boolean run();

}
