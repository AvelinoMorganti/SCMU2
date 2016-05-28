package DAO;

import classes.State;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author avelino
 */
public class DAOState {

    public int getLastRegisterState() {

        String sql = "SELECT * FROM state WHERE idstate IN (SELECT MAX(idstate) FROM state)";
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

    public State getStateByID(long id) {

        State p = null;

        try {

            MySQLConnector conn = new MySQLConnector();
            conn.connect();

            PreparedStatement getUser
                    = conn.getConnection().prepareStatement("SELECT "
                            + "idstate, "
                            + "lamp, "
                            + "alarm, "
                            + "smsNotifications, "
                            + "latitude, "
                            + "longitude, "
                            + "harmfulGases, "
                            + "luminosity "
                            + "FROM state WHERE idstate = ?");

            getUser.setLong(1, id);

            ResultSet data = getUser.executeQuery();

            long tmpId;
            boolean tmpLamp = false;
            boolean tmpAlarm = false;
            boolean tmpSMSNotifications = false;
            String tmpLatitude = "0.0";
            String tmpLongitude = "0.0";
            double harmfulGases = 0;
            double luminosity = 0;

            if (data == null) {
                return null;
            }

            if (data.next() != false) {
                tmpId = data.getLong(1);
                tmpLamp = (data.getInt(2) != 0);
                tmpAlarm = (data.getInt(3) != 0);
                tmpSMSNotifications = (data.getInt(4) != 0);
                tmpLatitude = data.getString(5);
                tmpLongitude = data.getString(6);
                harmfulGases = data.getDouble(7);
                luminosity = data.getDouble(8);

                conn.closeConnection();

                p = new State(id, tmpLamp, tmpAlarm, tmpSMSNotifications, tmpLatitude, tmpLongitude, harmfulGases, luminosity);
            }

        } catch (ClassNotFoundException | SQLException ex) {
        }
        return p;
    }

    public boolean insertState(State properties) {
        if (properties != null) {
            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();
                PreparedStatement insert = conn.getConnection().prepareStatement("INSERT INTO state VALUES(?,?,?,?,?,?,?,?)");

                insert.setInt(1, 0);
                //insert.setBoolean(2, "" + ((properties.isLamp()) ? 1 : 0));
                insert.setBoolean(2, properties.isLamp());
                insert.setBoolean(3, properties.isAlarm());
                insert.setBoolean(4, properties.isSmsNotifications());
                insert.setString(5, properties.getLatitude());
                insert.setString(6, properties.getLongitude());
                insert.setDouble(7, properties.getHarmfulGases());
                insert.setDouble(8, properties.getLuminosity());
                insert.executeUpdate();

                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
            }
        }
        return false;
    }
}
