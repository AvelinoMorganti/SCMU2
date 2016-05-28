package com.whaves.scmu;

public class Account {

    private long idAccount;
    private String username;
    private String password;
    private String salt;
    private boolean loggedIn;
    private boolean locked;
    private long idProperties;

    /*
     Create Account
     */
    public Account(String username, String password) {
        this.username = username;
        this.salt = hashPassword.getRandomString(20);
        this.password = hashPassword.getSHA512(password + this.salt);
        this.locked = false;
        this.loggedIn = false;
    }

    /*
     Login a user
     */
    public Account(String username, String passwordHasheada, String salt, boolean locked) {
        this.username = username;
        this.password = passwordHasheada;
        this.salt = salt;
        this.locked = locked;
        this.loggedIn = false;
    }

    public Account(long idAccount, String username, String password, String salt, boolean locked, long idProperties) {
        this.idAccount = idAccount;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.locked = locked;
        this.idProperties = idProperties;
    }



    public Account() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(long idAccount) {
        this.idAccount = idAccount;
    }

    public long getIdProperties() {
        return idProperties;
    }

    public void setIdProperties(long idProperties) {
        this.idProperties = idProperties;
    }
}
