package dido.auntaccount.dao;

import org.javalite.activejdbc.Base;

import dido.auntaccount.entities.User;

public class App {

	public App() {

	}

	/*
	 * public static void main(String[] args) { createUser(); }
	 */

	public String getFirstUser() {
		Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/sys", "root", "password");
		String user = User.where("UserId = 1").get(0).getString("FirstName");
		Base.close();
		return user;
	}

	public static User
	createUser() {
		Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/sys", "root", "password");
		User user = new User();
		user.set("userId", "1");
		user.set("userName", "Name");
		user.set("firstName", "Name1");
		user.set("lastName", "Name2");
		user.set("password", "Pas");
		user.set("isDeleted", false);
		user.saveIt();
		Base.close();
		return user;
	}

}
