/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import classes.State;
import com.google.gson.Gson;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Avelino
 */
public class DAOSchedule {

    /*
     Buscar registro mais antigo (ou data atual)    
     */
    public int getLastRegisterSchedule() {

        String sql = "SELECT * FROM schedule WHERE idschedule IN (SELECT MAX(idschedule) FROM schedule)";
        int count = 0;
        try {

            MySQLConnector conn = new MySQLConnector();
            conn.connect();

            PreparedStatement getUser
                    = conn.getConnection().prepareStatement(sql);
            ResultSet data = getUser.executeQuery();

            if (data.next() != false) {
                count = data.getInt(1);
                conn.closeConnection();
            }

        } catch (ClassNotFoundException | SQLException ex) {
        }

        return count;
    }

    public String getScheduleByID(long id) {
        try {

            MySQLConnector conn = new MySQLConnector();
            conn.connect();

            PreparedStatement getSchedule
                    = conn.getConnection().prepareStatement("SELECT "
                            + "idschedule, "
                            + "json, "
                            + "namegroup "
                            + "FROM schedule WHERE idschedule = ?");

            getSchedule.setLong(1, id);

            ResultSet data = getSchedule.executeQuery();

            long tmpId;
            String json = "";
            String group = "";

            if (data == null) {
                return null;
            }

            if (data.next() != false) {
                tmpId = data.getLong(1);
                json = data.getString(2);
                group = data.getString(3);
                conn.closeConnection();
                return json;
            }

        } catch (ClassNotFoundException | SQLException ex) {
        }
        return null;
    }
    
    public String getScheduleByGroup(String namegroup) {
        try {

            MySQLConnector conn = new MySQLConnector();
            conn.connect();

            PreparedStatement getSchedule
                    = conn.getConnection().prepareStatement("SELECT "
                            + "idschedule, "
                            + "json, "
                            + "namegroup "
                            + "FROM schedule WHERE namegroup like ?");

            getSchedule.setString(1, namegroup);

            ResultSet data = getSchedule.executeQuery();

            long tmpId;
            String json = "";
            
            if (data == null) {
                return null;
            }

            if (data.next() != false) {
                tmpId = data.getLong(1);
                json = data.getString(2);
                namegroup = data.getString(3);
                conn.closeConnection();
                return json;
            }

        } catch (ClassNotFoundException | SQLException ex) {
        }
        return null;
    }

    public boolean insertSchedule(String schedule, String namegroup) {
        if (schedule != null) {
            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();
                PreparedStatement insert = conn.getConnection().prepareStatement("INSERT INTO schedule VALUES(?,?,?)");

                insert.setInt(1, 0);
                insert.setString(2, schedule);
                insert.setString(3, namegroup);
                insert.executeUpdate();
                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
            }
        }
        return false;
    }

    public boolean updateSchedule(String schedule, String namegroup, long idschedule) {
        if (schedule != null) {
            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();

                PreparedStatement update
                        = conn.getConnection().prepareStatement("UPDATE schedule "
                                + "SET"
                                + " json = ?, "
                                + "namegroup = ? "
                                + "WHERE idschedule = ?");

                update.setString(1, schedule);
                update.setString(2, namegroup);
                update.setLong(3, idschedule);

                update.executeUpdate();
                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
                System.err.println(ex.getMessage());
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        return false;
    }

}
