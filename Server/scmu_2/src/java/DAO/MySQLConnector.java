package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnector {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String BD = "whaves_scmu2";

    
     private static final String BD_IP = "whaves.com"; //Ou 184.107.114.5
     private static final String URL = "jdbc:mysql://" + BD_IP + "/" + BD;
     private static final String USERNAME = "whaves_scmu2";
     private static final String PASSWORD = "timbernerslee2016";
     /*
    private static final String BD_IP = "localhost";
    private static final String URL = "jdbc:mysql://" + BD_IP + "/" + BD;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "multivack23";*/

    private Connection connection = null;
    private Statement statement;
    private ResultSet resultSet = null;

    public MySQLConnector() {
    }

    public Connection connect() throws ClassNotFoundException, SQLException {

        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            this.statement = this.getConnection().createStatement();
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        return connection;
    }

    public void closeConnection() throws SQLException {

        try {
            this.getConnection().close();
            this.connection = null;
            this.resultSet = null;
            this.statement = null;
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
}
