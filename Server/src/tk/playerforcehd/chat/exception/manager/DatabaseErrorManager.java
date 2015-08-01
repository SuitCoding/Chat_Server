package tk.playerforcehd.chat.exception.manager;

import tk.playerforcehd.chat.annotation.type.Getter;
import tk.playerforcehd.chat.exception.DatabaseHandleException;
import tk.playerforcehd.chat.start.Main;

/**
 * When a error happens by handle anything with the Database, this manager will handle the stacktrace
 */
public class DatabaseErrorManager implements IErrorManager {

	private Main mainInstance;
	
	public DatabaseErrorManager(Main mainInstance) {
		this.mainInstance = mainInstance;
	}

	@Override
	public void throwException(Exception exception, boolean isCritical) {
		mainInstance.getLogger().warning("A error has been throwed by handle anything with the Database!");
		mainInstance.getLogger().warning("------------------------------------------------------------");
		mainInstance.getLogger().warning("Error from the class which throwed this exception:");
		mainInstance.getLogger().warning(Main.getMainInstance().convertExceptionToString(exception));
		mainInstance.getLogger().warning("------------------------------------------------------------");
		mainInstance.getLogger().warning("The thrown DatabaseHandleException (Pointing to this)");
		try {
			throw new DatabaseHandleException("Anything is has going wrong by handle anything with the Database...");
		} catch (DatabaseHandleException e) {
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
