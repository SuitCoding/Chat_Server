package tk.playerforcehd.chat.config.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import tk.playerforcehd.chat.annotation.type.Getter;
import tk.playerforcehd.chat.annotation.type.Setter;
import tk.playerforcehd.chat.exception.manager.DatabaseErrorManager;
import tk.playerforcehd.chat.start.Main;

public class MySQL {

	/**
	 * The DatabaseErrorManager handles every exception which get thrown by the MySQL classes
	 */
	public DatabaseErrorManager databaseErrorManager;
	
	/**
	 * The created instance of this class;
	 */
	protected static MySQL mysqlInstance;
	
	/**
	 * The address of the MySQL Server, default is 127.0.0.1 (localhost)
	 */
	private String address;
	
	/**
	 * The port of the MySQL Server, default is 3306
	 */
	private String port;
	
	/**
	 * The Database where the server saves his data
	 */
	private String database;
	
	/**
	 * The MySQL user which is used to login, default is root
	 */
	private String user;
	
	/**
	 * The password of the MySQL Database
	 */
	private String password;
	
	/**
	 * The instance of the MySQL Connection, needed to communicate with the MySQL Database
	 */
	protected Connection connection;
	
	public MySQL() {
		mysqlInstance = this;
		databaseErrorManager = new DatabaseErrorManager(Main.getMainInstance());
		readProperties();
	}

	/**
	 * Reads the server.properties file and set the connection data
	 */
	private void readProperties(){
		this.address = Main.getMainInstance().getServer_properties().get("mysql_ip").toString();
		this.port = Main.getMainInstance().getServer_properties().get("mysql_port").toString();
		this.database = Main.getMainInstance().getServer_properties().get("mysql_database").toString();
		this.user = Main.getMainInstance().getServer_properties().get("mysql_user").toString(); 
		this.password = Main.getMainInstance().getServer_properties().get("mysql_password").toString(); 
	}

	/**
	 * Connects to the database with the given fields
	 */
	public void connect(){
		if(!isConnected()){
			try {
				Main.getMainInstance().getLogger().info("Connecting to the database...");
				connection = DriverManager.getConnection("jdbc:mysql://" + address + ":" + port + "/" + database,user,password);
				Main.getMainInstance().getLogger().info("Succesfully Connected to the database!");
			} catch (SQLException e) {
				Main.getMainInstance().getLogger().severe("Can't connect to the database, the server will Shutdown NOW!");
				Main.getMainInstance().getLogger().severe("Read the Stacktrace to see what has went wrong!");
				databaseErrorManager.throwException(e, false);
			}
		}
	}
	
	/**
	 * Closing the connection to the Database if a connection is open
	 */
	public void disconnect(){
		if(isConnected()){
			try {
				Main.getMainInstance().getLogger().info("Disconnecting from the database...");
				connection.close();
				Main.getMainInstance().getLogger().info("Succesfully Disconnected from the database");
			} catch (SQLException e) {
				databaseErrorManager.throwException(e, false);
			}
		}
		
	}
	
	/**
	 * Reconnect to the server with the given Database settings
	 */
	public void reconnect(){
		if(isConnected()){
			try {
				Main.getMainInstance().getLogger().info("Reconnecting...");
				if(isConnected()){
					Main.getMainInstance().getLogger().info("Server is connected to a database, closing connection...");
					connection.close();
					Main.getMainInstance().getLogger().info("The connection has been succesfully closed!");
				}
				Main.getMainInstance().getLogger().info("Connecting to the Database...");
				connection = DriverManager.getConnection("jdbc:mysql://" + address + ":" + port + "/" + database,user,password);
				Main.getMainInstance().getLogger().info("Connected to the database!");
				Main.getMainInstance().getLogger().info("Succesfully reconnected to the database");
			} catch (SQLException e){
				Main.getMainInstance().getLogger().severe("Can't connect to the database, the server will Shutdown NOW!");
				Main.getMainInstance().getLogger().severe("Read the Stacktrace to see what has went wrong!");
				databaseErrorManager.throwException(e, false);
			}
		}
	}
	
	/**
	 * Checks if the connection stands
	 * @return true if it is connected -> false if not
	 */
	public boolean isConnected(){
		return (connection == null ? false : true);
	}
	
	/**
	 * Creates a ResultSet with the given String and returns it
	 * @param qry the querry which returns the result
	 * @return the ResultSet of the querry Command
	 */
	@Getter
	public ResultSet getResult(String qry){
		if(isConnected()){
			try{
				return connection.createStatement().executeQuery(qry);
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Getter
	public String getAddress() {
		return address;
	}

	@Setter
	public void setAddress(String address) {
		this.address = address;
	}

	@Getter
	public String getPort() {
		return port;
	}

	@Setter
	public void setPort(String port) {
		this.port = port;
	}

	@Getter
	public String getDatabase() {
		return database;
	}

	@Setter
	public void setDatabase(String database) {
		this.database = database;
	}

	@Getter
	public String getUser() {
		return user;
	}

	@Setter
	public void setUser(String user) {
		this.user = user;
	}

	@Getter
	public String getPassword() {
		return password;
	}

	@Setter
	public void setPassword(String password) {
		this.password = password;
	}

	@Getter
	public Connection getConnection() {
		return connection;
	}

	@Getter
	public static MySQL getMysqlInstance() {
		return mysqlInstance;
	}

	@Getter
	public DatabaseErrorManager getDatabaseErrorManager() {
		return databaseErrorManager;
	}

}
