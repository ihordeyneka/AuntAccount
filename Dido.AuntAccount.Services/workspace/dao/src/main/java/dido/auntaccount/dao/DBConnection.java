package dido.auntaccount.dao;

import org.javalite.activejdbc.Base;

/**
 * Created by orysiadeyneka on 19.03.16.
 */
public class DBConnection {

    public static void openConnection() {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/sys", "root", "password");
    }

    public static void closeConnection() {
        Base.close();
    }

}
