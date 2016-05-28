/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import DAO.DAOAccount;
import DAO.DAOState;
import com.google.gson.Gson;
import javax.servlet.http.Cookie;

/**
 *
 * @author Avelino
 */
public class Auth {

    private Account user;

    public Auth() {
        user = null;
    }

    public Account getUser() {
        return user;
    }

    public boolean loginCookie(Cookie cookies[]) {
        String username = "";
        String hashPassword = "";

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                }
                if (cookie.getName().equals("password")) {
                    hashPassword = cookie.getValue();
                }
            }

            if (username.equals("") || hashPassword.equals("")) {
                return false;
            }

            if ((username != null) && (hashPassword != null)) {
                return loginHashPassword(username, hashPassword);
            }
        }
        return false;
    }

    public boolean loginHashPassword(String username, String hashPassword) {
        DAOAccount daoAccount = new DAOAccount();
        user = daoAccount.getAccount(username);

        if (user != null) {
            if (user.getUsername().equals(username)
                    && (user.getPassword().equals(hashPassword))) {
                return true;
            }
        }
        return false;
    }

    public boolean loginString(String username, String password) {

        DAOAccount daoAccount = new DAOAccount();
        user = daoAccount.getAccount(username);

        if (user != null) {
            if (user.getUsername().equals(username)
                    && (user.getPassword().equals(
                            hashPassword.getSHA512(
                                    password + user.getSalt())))) {
                return true;
            }
        }
        return false;
    }
}
