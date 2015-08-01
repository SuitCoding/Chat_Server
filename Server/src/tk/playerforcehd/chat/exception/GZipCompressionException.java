package tk.playerforcehd.chat.exception;

/**
 * Get thrown when anything went wrong by compressing or decompressing a gzip file
 */
public class GZipCompressionException extends Exception {

	private static final long serialVersionUID = 1L;

	public GZipCompressionException() {
	}

	public GZipCompressionException(String arg0) {
		super(arg0);
	}

	public GZipCompressionException(Throwable arg0) {
		super(arg0);
	}

	public GZipCompressionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public GZipCompressionException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
