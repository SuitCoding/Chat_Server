package tk.playerforcehd.chat.exception.manager;

import tk.playerforcehd.chat.annotation.type.Getter;
import tk.playerforcehd.chat.exception.BootLoaderException;
import tk.playerforcehd.chat.start.Main;

/**
 * When a error happens by booting the server, this manager will handle the stacktrace
 */
public class BootErrorManager implements IErrorManager {

	private Main mainInstance;
	
	public BootErrorManager(Main mainInstance) {
		this.mainInstance = mainInstance;
	}

	@Override
	public void throwException(Exception exception, boolean isCritical) {
		mainInstance.getLogger().warning("A error has been throwed by booting the server!");
		mainInstance.getLogger().warning("------------------------------------------------------------");
		mainInstance.getLogger().warning("Error from the main instance or the bootloader:");
		mainInstance.getLogger().warning(Main.getMainInstance().convertExceptionToString(exception));
		mainInstance.getLogger().warning("------------------------------------------------------------");
		mainInstance.getLogger().warning("The thrown BootLoaderException (Pointing to this)");
		try {
			throw new BootLoaderException("Anything is has going wrong by booting the server...");
		} catch (BootLoaderException e) {
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
