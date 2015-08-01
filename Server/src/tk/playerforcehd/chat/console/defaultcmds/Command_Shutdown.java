package tk.playerforcehd.chat.console.defaultcmds;

import tk.playerforcehd.chat.console.command.AbstractCommand;
import tk.playerforcehd.chat.start.Main;

public class Command_Shutdown extends AbstractCommand {

	public Command_Shutdown(String name) {
		super(name);
	}

	@Override
	public void execute(String command, String[] args) {
		Main.getMainInstance().shutdown();
	}

}
