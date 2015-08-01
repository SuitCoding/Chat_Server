package tk.playerforcehd.chat.start;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import tk.playerforcehd.chat.annotation.type.Getter;
import tk.playerforcehd.chat.annotation.type.Setter;
import tk.playerforcehd.chat.config.files.server_properties;
import tk.playerforcehd.chat.config.mysql.MySQL;
import tk.playerforcehd.chat.connection.MainServerSocket;
import tk.playerforcehd.chat.connection.client.ClientConnection;
import tk.playerforcehd.chat.console.ConsoleCommandHandler;
import tk.playerforcehd.chat.exception.manager.BootErrorManager;

public class Main {

	/**
	 * The instance of the costumized Logger
	 */
	public Logger logger;
	
	/**
	 * Logs the console output into a file
	 */
	public FileLogger fileLogger;
	
	/**
	 * The main instance of this Server
	 */
	private static Main mainInstance;
		
	/**
	 * The name of this server
	 */
	private String programmName = "Chat";
	
	/**
	 * The server version
	 */
	private String programmVersion = "0.0.1 - Dev";
	
	/**
	 * Needed by the shutdown(); method
	 */
	private boolean isBooted = false;
	
	/**
	 * The location where the server has been started
	 */
	private static File mainDataDir;
	
	/**
	 * The location where the logs are saved
	 */
	public File logDataDir;
	
	/**
	 * The location where the plugins are saved;
	 */
	private File pluginDataDir;
	
	/**
	 * The main config file of the server, the important MySQL Connection can configured in it
	 */
	private server_properties server_properties;
	
	/**
	 * When a exception get throwed by starting the server, this manager handles the exception, so they look nicer and cleaner
	 */
	private BootErrorManager tmp_bootErrorManager;
	
	/**
	 * The instance of the MySQL class, contains a connection to the MySQL Database, the database is used for nearly everything
	 */
	public MySQL mySQL;
	
	/**
	 * Registers a consoleCommandHandler, with this commands can executed in the console
	 */
	public ConsoleCommandHandler consoleCommandHandler;
	
	/**
	 * A collection of all connected clients
	 */
	public Collection<ClientConnection> clientConnections;
	
	/**
	 * The main Socket which recieves every client
	 */
	public MainServerSocket mainServerSocket;
	
	/**
	 * Get called by the start of this server
	 */
	public Main() {
		mainInstance = this;
		
		//Setting a temporary standard logger
		setLogger(Logger.getLogger("["+programmName+"]"));
		
		tmp_bootErrorManager = new BootErrorManager(getMainInstance());
		
		setMainDataDir();
		setLogDataDir();
		setPluginDataDir();
		
		customizeLogger();
		fileLogger = new FileLogger(this);
		logger.info(programmName + " server Version " + programmVersion + " is booting");
		
		getLogger().info("Loading Server Properties Configuration...");
		loadServerProperties();
		getLogger().info("Done!");
		
		getLogger().info("Connecting to the MySQL Database...");
		mySQL = new MySQL();
		mySQL.connect();
		if(mySQL.isConnected()){
			getLogger().info("Connected to the Database!");
		} else {
			getLogger().severe("Failed to connect to the Database!");
			getLogger().severe("The Server will shutingdown NOW!");
			shutdown();
		}
		
		clientConnections = new ArrayList<ClientConnection>();
		
		mainServerSocket = new MainServerSocket();
		
		consoleCommandHandler = new ConsoleCommandHandler();
	}

	/**
	 * Get called by starting the server (main method)
	 * @param args To start the programm with custom arguments
	 */
	public static void main(String[] args) {
		new Main();
	}

	/**
	 * The first thing which happens is the customization of the logger, so it looks nicer.
	 */
	private void customizeLogger(){
		LogManager manager = LogManager.getLogManager();
		try {
			manager.readConfiguration(getClass().getResourceAsStream("/logger.properties"));
		} catch (SecurityException | IOException e) {
			throwError(e, true);
		}
		setLogger(Logger.getLogger("["+programmName+"]"));
	}

	protected void throwError(Exception exception, boolean isCritical){
		tmp_bootErrorManager.throwException(exception, isCritical);
	}
	
	/**
	 * Softly stops the server...
	 */
	public void shutdown(){
		getLogger().info("Shutingdown server...");
		getLogger().info("Thank you and Good Bye!");
		if(isBooted){
			mySQL.disconnect();
			mainServerSocket.shutdownServerThread();
			System.exit(-1);
		} else {
			System.exit(-1);
		}
	}
	
	@Getter
	public Logger getLogger() {
		return logger;
	}

	@Setter
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Getter
	public String getProgrammName() {
		return programmName;
	}

	@Getter
	public String getProgrammVersion() {
		return programmVersion;
	}

	@Getter
	public static Main getMainInstance() {
		return Main.mainInstance;
	}
	
	@Getter
	private boolean isBooted() {
		return isBooted;
	}

	@Setter
	private void setBooted(boolean isBooted) {
		this.isBooted = isBooted;
	}

	/**
	 * Returns the location where the server has been started, so it can used to manage the server files in the current folder.
	 * @return a string with the server path
	 * @throws URISyntaxException when the path a bad syntax have, this get thrown (should not happen)
	 * @throws UnsupportedEncodingException get thrown when the encoding fails (like a wrong encoder has been given)
	 */
	@Getter
	public String getJarLocation() throws URISyntaxException, UnsupportedEncodingException{
		CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
		File jarFile;
		if (codeSource.getLocation() != null) {
			jarFile = new File(codeSource.getLocation().toURI());
		} else {
			String path = Main.class.getResource(Main.class.getSimpleName() + ".class").getPath();
		    String jarFilePath = path.substring(path.indexOf(":") + 1, path.indexOf("!"));
		    jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
		    jarFile = new File(jarFilePath);
		}
		return jarFile.getParentFile().getAbsolutePath();	
	}
	
	/**
	 * Initializes the mainDataDir, so it's easier to handle files in the main location
	 */
	@Setter
	private void setMainDataDir(){
		try {
			Main.mainDataDir = new File(getJarLocation());
		} catch (UnsupportedEncodingException | URISyntaxException e) {
			throwError(e, true);
		}
	}
	
	@Getter
	public static File getMainDataDir() {
		return mainDataDir;
	}

	/**
	 * Initializes the logDataDir, so it's easier to handle files in the logs folder
	 */
	@Setter
	private void setLogDataDir(){
		try {
			this.logDataDir = new File(getJarLocation() + "/logs");
		} catch (UnsupportedEncodingException | URISyntaxException e) {
			throwError(e, true);
		}
		if(!this.logDataDir.exists()){
			this.logDataDir.mkdir();
		}
	}

	@Getter
	public File getLogDataDir() {
		return logDataDir;
	}
	
	/**
	 * Initializes the pluginDataDir, so it's easier to handle files in the plugins folder
	 */
	@Setter
	private void setPluginDataDir(){
		this.pluginDataDir = new File(getMainDataDir().getPath() + "/plugins");
		if(!this.pluginDataDir.exists()){
			this.pluginDataDir.mkdir();
		}
	}

	@Getter
	public File getPluginDataDir() {
		return pluginDataDir;
	}

	@Getter
	protected FileLogger getFileLogger() {
		return fileLogger;
	}
	
	/**
	 * Converts a Exception to a String, so it can written into the log
	 * @param exception the exception which gte converted into a String
	 * @return the string which can written into a file
	 */
	public String convertExceptionToString(Exception exception){
		StringWriter sw = new StringWriter();
		exception.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
	
	/**
	 * Load the server.properties file or creates it when it's not exist
	 */
	private void loadServerProperties(){
		this.server_properties = new server_properties("server.properties");
	}

	@Getter
	public server_properties getServer_properties() {
		return server_properties;
	}

	@Setter
	protected void setServer_properties(server_properties server_properties) {
		this.server_properties = server_properties;
	}

	@Getter
	public MySQL getMySQL() {
		return mySQL;
	}

	@Setter
	protected void setMySQL(MySQL mySQL) {
		this.mySQL = mySQL;
	}

	@Getter
	public MainServerSocket getMainServerSocket() {
		return mainServerSocket;
	}

	@Getter
	public ConsoleCommandHandler getConsoleCommandHandler() {
		return consoleCommandHandler;
	}
	
	/**
	 * Register a new Client connection
	 * @param clientConnection the client connection to register
	 */
	public void addClientConnection(ClientConnection clientConnection){
		this.clientConnections.add(clientConnection);
	}
	
	/**
	 * Removes a registred Client connection from the client list
	 * @param clientConnection the connection to remove
	 */
	public void removeClientConnection(ClientConnection clientConnection){
		this.clientConnections.remove(clientConnection);
	}
}
