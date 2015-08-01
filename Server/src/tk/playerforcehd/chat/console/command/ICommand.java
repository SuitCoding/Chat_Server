package tk.playerforcehd.chat.console.command;

public interface ICommand {

	/**
	 * Get executed when a user runs this command in the Server Console
	 * @param command the command which get executed
	 * @param args the arguments of the command
	 */
	public void execute(String command, String[] args);
	
}
