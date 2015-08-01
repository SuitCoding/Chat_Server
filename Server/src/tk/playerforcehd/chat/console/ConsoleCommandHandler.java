package tk.playerforcehd.chat.console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import tk.playerforcehd.chat.annotation.type.Getter;
import tk.playerforcehd.chat.console.command.AbstractCommand;
import tk.playerforcehd.chat.console.defaultcmds.Command_Shutdown;

public class ConsoleCommandHandler {

	private static ConsoleCommandHandler consoleCommandHandler;
	
	/**
	 * The thread which is listening to the console input
	 */
	private Thread thread;
	
	/**
	 * A list of every registred Command
	 */
	protected Collection<AbstractCommand> commandList;
	
	public ConsoleCommandHandler() {
		consoleCommandHandler = this;
		this.commandList = new ArrayList<AbstractCommand>();
		this.thread = new Thread(new CommandListenerThread());
		this.thread.setName("ServerConsoleInputThread");
		this.thread.start();
		registerDefaultCommands();
	}

	/**
	 * Registers a new command for the console
	 * @param abstractCommand the command which get exxecuted
	 */
	public void registerCommand(AbstractCommand abstractCommand){
		commandList.add(abstractCommand);
	}
	
	@Getter
	public Collection<AbstractCommand> getCommands() {
		return commandList;
	}

	@Getter
	protected static ConsoleCommandHandler getConsoleCommandHandler() {
		return ConsoleCommandHandler.consoleCommandHandler;
	}

	@Getter
	public Thread getThread() {
		return thread;
	}

	/**
	 * Registers the default server commands
	 */
	private void registerDefaultCommands(){
		registerCommand(new Command_Shutdown("shutdown"));
	}
	
	/**
	 * This class listens to every input in the console
	 */
	private class CommandListenerThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				@SuppressWarnings("resource")
				Scanner scanner = new Scanner(System.in);
				String messages = scanner.next();
				String[] splitetCommand = messages.split(" ");
				String command = splitetCommand[0];
				messages.replace(command, "");
				for(AbstractCommand abstractCommand : ConsoleCommandHandler.getConsoleCommandHandler().getCommands()){
					if(abstractCommand.getName().equalsIgnoreCase(command)){
						abstractCommand.execute(command, messages.split(" "));
					}
				}
			}
		}
		
	}
}
