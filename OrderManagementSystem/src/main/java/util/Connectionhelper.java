package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Connectionhelper {

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		ResourceBundle rb = ResourceBundle.getBundle("db");
		String user = rb.getString("user");
		String pwd = rb.getString("password");
		String driver = rb.getString("driver");
		String url = rb.getString("url");
		
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(url, user, pwd);
		return connection;
	}

}
