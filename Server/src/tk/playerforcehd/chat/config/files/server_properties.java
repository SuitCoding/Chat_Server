package tk.playerforcehd.chat.config.files;

import tk.playerforcehd.chat.config.properties.AbstractPropertie;
import tk.playerforcehd.chat.start.Main;

public class server_properties extends AbstractPropertie {

	public server_properties(String fileName) {
		super(fileName);
	}

	@Override
	public void addDefaults() {
		write("server_ip", "127.0.0.1");
		write("server_port", "25569");
		write("mysql_ip", "127.0.0.1");
		write("mysql_port", "3306");
		write("mysql_database", Main.getMainInstance().getProgrammName());
		write("mysql_user", "root");
		write("mysql_password", "password");
		save("Configure here the connection settings between MySQL, or the server side things!");
	}

}
