package dido.auntaccount.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("users")
public class User extends Model {

    public static final String USER_ID_COLUMN = "userId";
    public static final String USER_NAME_COLUMN = "userName";
    public static final String FIRST_NAME_COLUMN = "firstName";
    public static final String LAST_NAME_COLUMN = "lastName";
    public static final String PASSWORD_COLUMN = "password";
    public static final String IS_DELETED_COLUMN = "isDeleted";

    public User setUserId(Long id) {
        set(USER_ID_COLUMN, id);
        return this;
    };

    public User setUserName(String userName) {
        set(USER_NAME_COLUMN, userName);
        return this;
    };

    public User setFirstName(String firstName) {
        set(FIRST_NAME_COLUMN, firstName);
        return this;
    };

    public User setLastName(String lastName) {
        set(LAST_NAME_COLUMN, lastName);
        return this;
    };

    public User setPassword(String password) {
        set(PASSWORD_COLUMN, password);
        return this;
    };

    public User setIsDeleted(boolean isDeleted) {
        set(IS_DELETED_COLUMN, isDeleted);
        return this;
    };

}
