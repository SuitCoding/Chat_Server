package tk.playerforcehd.chat.exception;

/**
 * This exception get thrown when anything fails on loading the server
 */
public class BootLoaderException extends Exception {

	private static final long serialVersionUID = 1L;

	public BootLoaderException() {
	}

	public BootLoaderException(String arg0) {
		super(arg0);
	}

	public BootLoaderException(Throwable arg0) {
		super(arg0);
	}

	public BootLoaderException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BootLoaderException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
