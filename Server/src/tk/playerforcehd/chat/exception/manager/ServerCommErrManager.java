package tk.playerforcehd.chat.exception.manager;

import tk.playerforcehd.chat.annotation.type.Getter;
import tk.playerforcehd.chat.exception.ServerCommunicationException;
import tk.playerforcehd.chat.start.Main;

/**
 * When a error happens by starting the communication with sockets, this manager will handle the stacktrace
 */
public class ServerCommErrManager implements IErrorManager {

	private Main mainInstance;
	
	public ServerCommErrManager(Main mainInstance) {
		this.mainInstance = mainInstance;
	}

	@Override
	public void throwException(Exception exception, boolean isCritical) {
		mainInstance.getLogger().warning("A error has been throwed by doing anything with sockets!");
		mainInstance.getLogger().warning("------------------------------------------------------------");
		mainInstance.getLogger().warning("Error from the main instance or the bootloader:");
		mainInstance.getLogger().warning(Main.getMainInstance().convertExceptionToString(exception));
		mainInstance.getLogger().warning("------------------------------------------------------------");
		mainInstance.getLogger().warning("The thrown ServerCommunicationException (Pointing to this)");
		try {
			throw new ServerCommunicationException("Anything is has going wrong by doing anything with sockets...");
		} catch (ServerCommunicationException e) {
			mainInstance.getLogger().warning(Main.getMainInstance().convertExceptionToString(e));
		}
		mainInstance.getLogger().warning("------------------------------------------------------------");
		if(isCritical){
			mainInstance.shutdown();
		}
	}

	@Getter
	private Main getMainInstance() {
		return mainInstance;
	}
	
}
