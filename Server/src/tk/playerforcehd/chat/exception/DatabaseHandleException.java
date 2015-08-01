package tk.playerforcehd.chat.exception;

public class DatabaseHandleException extends Exception {

	private static final long serialVersionUID = -8659441764745053992L;

	public DatabaseHandleException() {}

	public DatabaseHandleException(String arg0) {
		super(arg0);
	}

	public DatabaseHandleException(Throwable arg0) {
		super(arg0);
	}

	public DatabaseHandleException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DatabaseHandleException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
