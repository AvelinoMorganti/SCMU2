package DAO;

import classes.Account;
import classes.State;
import classes.hashPassword;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author avelino
 */
public class DAOAccount {

    /*
     Recebe um nome e se existir um usuario com este nome no banco, 
     retorna um objeto Account vinculado a ele
     */
    public Account getAccount(String name) {

        Account account = null;

        try {

            MySQLConnector conn = new MySQLConnector();
            conn.connect();

            PreparedStatement getUser
                    = conn.getConnection().prepareStatement("SELECT "
                            + "idaccount, "
                            + "username, "
                            + "password, "
                            + "salt, "
                            + "locked, "
                            + "idstate "
                            + "FROM account "
                            + "WHERE username = ?");

            getUser.setString(1, name);

            ResultSet data = getUser.executeQuery();

            long tmpIdaccount;
            String tmpUsername;
            String tmpPassword;
            String tmpSalt;
            boolean tmpLocked;
            long tmpProperties;

            if (data == null) {
                return null;
            }

            if (data.next() != false) {
                tmpIdaccount = data.getLong(1);
                tmpUsername = data.getString(2);
                tmpPassword = data.getString(3);
                tmpSalt = data.getString(4);
                tmpLocked = data.getBoolean(5);
                tmpProperties = data.getLong(6);

                conn.closeConnection();

                account = new Account(tmpIdaccount, tmpUsername, tmpPassword, tmpSalt, tmpLocked, tmpProperties);
            }

        } catch (ClassNotFoundException | SQLException ex) {
        }
        return account;
    }

    /*    
     Recebe um objeto Account, salva no banco.
     */
    public boolean createAccount(Account account, int idProperties) {
        if (account != null) {
            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();
                PreparedStatement insert = conn.getConnection().prepareStatement("INSERT INTO account VALUES(?,?,?,?,?,?)");

                insert.setInt(1, 0);
                insert.setString(2, account.getUsername());
                insert.setString(3, account.getPassword());
                insert.setString(4, account.getSalt());
                insert.setString(5, "0");
                insert.setInt(6, idProperties);

                insert.executeUpdate();
                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
            }
        }
        return false;
    }

    public boolean createAccount(Account account, State state, long id) {
        if (account != null) {
            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();
                PreparedStatement insert = conn.getConnection().prepareStatement("INSERT INTO account VALUES(?,?,?,?,?,?)");

                insert.setInt(1, 0);
                insert.setString(2, account.getUsername());
                insert.setString(3, account.getPassword());
                insert.setString(4, account.getSalt());
                insert.setString(5, "0");

                insert.setLong(6, state.getId());
                insert.executeUpdate();

                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
            }
        }
        return false;
    }

    /*    
     Recebe um objeto Account, atualiza no banco.
     */
    public boolean updateAccount(String username, int idState) {
        if (idState != 0) {
            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();

                PreparedStatement update
                        = conn.getConnection().prepareStatement("UPDATE account "
                                + "SET idstate = ? "
                                + "WHERE username = ?");

                update.setInt(1, idState);
                update.setString(2, username);
                update.executeUpdate();
                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
            }
        }
        return false;
    }
    /*    
     Recebe um objeto Account, atualiza no banco.
     */

    public boolean updateAccount(Account account) {
        if (account != null) {
            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();

                PreparedStatement update
                        = conn.getConnection().prepareStatement("UPDATE account "
                                + "SET password = ?, salt = ? "
                                + "WHERE username = ?");

                update.setString(1, account.getPassword());
                update.setString(2, account.getSalt());
                update.setString(3, account.getUsername());
                update.executeUpdate();
                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
            }
        }
        return false;
    }

    /*
     Recebe uma String nome e apaga os dados vinculado 
     a uma conta com este nome, se essa conta existir.
     */
    public boolean deleteAccount(String name) {
        if ((name != null) && (!name.equals(""))) {
            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();

                String sql = "DELETE FROM `account` WHERE `username` = '" + name + "'";
                conn.getStatement().execute(sql);

                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
                //Logger.getLogger(MySQLAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public Account login(String name, String pwd) {

        Account acc = getAccount(name);

        try {
            MySQLConnector conn = new MySQLConnector();
            conn.connect();

            PreparedStatement getUser
                    = conn.getConnection().
                    prepareStatement("SELECT password, salt, locked "
                            + "FROM account WHERE username = ?");

            ResultSet data = getUser.executeQuery();

            if (data == null || !data.next()) {
                return null;
            }

            String securePassword = data.getString(1);
            String salt = data.getString(2);
            boolean locked = data.getBoolean(3);

            conn.closeConnection();

            String newSecurePassword = hashPassword.getSHA512(pwd + salt);

            if (!newSecurePassword.equals(securePassword)) {
                return null;
            }

            acc.setLoggedIn(true);
            acc.setLocked(locked);

        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
        return acc;
    }

    public ArrayList<Account> getAllAccounts() {

        ArrayList<Account> allAccounts = new ArrayList<Account>();

        try {
            MySQLConnector conn = new MySQLConnector();
            conn.connect();

            String sql = "SELECT * FROM account";
            conn.getStatement().execute(sql);
            ResultSet data = conn.getStatement().getResultSet();

            if (data == null) {
                return null;
            }

            while (data.next()) {
                String username = data.getString("username");
                String password = data.getString("password");
                String salt = data.getString("salt");
                boolean tmp = (data.getInt("locked") != 0);

                allAccounts.add(new Account(username, password, salt, tmp));

            }
            conn.closeConnection();

        } catch (ClassNotFoundException | SQLException ex) {
        }
        return allAccounts;
    }

    public boolean lockAccount(String username, String locked) {
        if (username != null && username != "") {

            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();

                PreparedStatement update
                        = conn.getConnection().prepareStatement("UPDATE account "
                                + "SET locked = ?"
                                + "WHERE username = ?");

                update.setString(1, locked);
                update.setString(2, username);
                update.executeUpdate();
                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
            }
        }
        return false;
    }
}
