package tk.playerforcehd.chat.console.command;

import tk.playerforcehd.chat.annotation.type.Getter;

public abstract class AbstractCommand implements ICommand {

	private String name;
	
	public AbstractCommand(String name) {
		this.name = name;
	}

	/**
	 * Get executed when a user runs this command in the Server Console
	 * @param command the command which get executed
	 * @param args the arguments of the command
	 */
	abstract public void execute(String command, String[] args);

	@Getter
	public String getName() {
		return name;
	}

}
