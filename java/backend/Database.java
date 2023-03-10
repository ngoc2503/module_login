package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {
	private static String DB_URL = "jdbc:mysql://localhost:3306/";
	private static String USER_NAME = "root";
	private static String PASSWORD = "ngoc25031993";
	private static String DATABASE_NAME = "asm3database";
	
	
	public static Connection getConnection() {
	    String url = DB_URL + DATABASE_NAME;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, USER_NAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	// Add new user into database asm3database.
	public static int addCustomer(User user) {
		int ret = 0;
		String sql = "INSERT INTO users (userid, firstname, lastname, salt, pass, otpsecret) VALUE(?,?,?,?,?,?)";
		try(PreparedStatement prepared =getConnection().prepareStatement(sql)) {
			prepared.setString(1, user.getUserid());
			prepared.setString(2, user.getFistname());
			prepared.setString(3, user.getLastname());
			prepared.setString(4, user.getSalt());
			prepared.setString(5, user.getPassword());
			prepared.setString(6, user.getOptsecret());
			ret = prepared.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	// update user when user login success
	public static int updateUser(String userid, int countLoggin) {
		int isUpdate = 0;
		
		String sqlString = "UPDATE users SET faillogin = ? WHERE userid = ?";
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement(sqlString)) {
			statement.setInt(1, countLoggin);
			statement.setString(2, userid);
			isUpdate = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isUpdate;
	}
	// set status user
	public static int setStatusUser(String userid, int flag) {
		int isUpdate = 0;
		
		String sqlString = "UPDATE users SET isLocked = ? WHERE userid = ?";
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement(sqlString)) {
			statement.setInt(1, flag);
			statement.setString(2, userid);
			isUpdate = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isUpdate;
	}
	
	// set security
	public static int setLabelSecurity(String userid, int label) {
		int isSuccess = 0;
		String sql = "UPDATE users SET label = ? WHERE userid = ?";
		Connection connection = getConnection();
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, label);
			statement.setString(2, userid);
			isSuccess = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}
	// find user 
	public static User getUser(String userid) {
		User user = null;
		String sql = "SELECT * FROM users WHERE userid = ?";
		try(PreparedStatement statement = 
				getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
			statement.setString(1, userid);
			ResultSet resultset = statement.executeQuery();
			if(resultset.first()) {
				user = new User();
				user.setUserid(resultset.getString("userid"));
				user.setFistname(resultset.getString("firstname"));
				user.setLastname(resultset.getString("lastname"));
				user.setSalt(resultset.getString("salt"));
				user.setPassword(resultset.getString("pass"));
				user.setOptsecret(resultset.getString("otpsecret"));
				user.setFaillogin(resultset.getInt("faillogin"));
				user.setLabel(resultset.getInt("label"));
				if(resultset.getInt("isLocked") == 0) user.setIslooked(false);
				else user.setIslooked(true);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	
	// Delete Element
	public void deleteCustomer() {
		
	}
	// get List customer
	public void getListCustomer() {
		
	}
	
	// add a new into news
	public static int addNews(News news) {
		
		int ret = 0;
		String sql = "INSERT INTO news (userid, content,userDay,label) VALUE (?,?,?,?)";
		try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
			statement.setString(1, news.getUserid());
			statement.setString(2, news.getText());
			statement.setDate(3, news.getDate());
			statement.setInt(4, news.getLabel());
			ret = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return ret;
	}
	
	// get list news with label
	
	public static ArrayList<News> getListNew(int label) {
		ArrayList<News> listNews = new ArrayList();
		String sql = "select * from news where label <= ?";
		
		try (PreparedStatement statement = 
				getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
			statement.setInt(1, label);
			ResultSet rSet = statement.executeQuery();
			if(rSet.first()) {
				do {
					News news =  new News();
					news.setId(rSet.getInt("id"));
					news.setUserid(rSet.getString("userid"));
					news.setText(rSet.getString("content"));
					news.setDate(rSet.getDate("userday"));
					news.setLabel(rSet.getInt("label"));
					listNews.add(news);
				} while (rSet.next());

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listNews;
	}
}
