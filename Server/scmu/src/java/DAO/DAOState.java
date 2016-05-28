package DAO;

import classes.State;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            float harmfulGases = 0;
            float luminosity = 0;

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
                harmfulGases = data.getFloat(7);
                luminosity = data.getFloat(8);

                conn.closeConnection();

                p = new State(id, tmpLamp, tmpAlarm, tmpSMSNotifications, tmpLatitude, tmpLongitude, harmfulGases, luminosity);
            }

        } catch (ClassNotFoundException | SQLException ex) {
        }
        return p;
    }

    public boolean insertState(State state) {
        if (state != null) {
            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();
                PreparedStatement insert = conn.getConnection().prepareStatement("INSERT INTO state VALUES(?,?,?,?,?,?,?,?)");

                insert.setInt(1, 0);
                //insert.setBoolean(2, "" + ((properties.isLamp()) ? 1 : 0));
                insert.setBoolean(2, state.isLamp());
                insert.setBoolean(3, state.isAlarm());
                insert.setBoolean(4, state.isSmsNotifications());
                insert.setString(5, state.getLatitude());
                insert.setString(6, state.getLongitude());
                insert.setDouble(7, state.getHarmfulGases());
                insert.setDouble(8, state.getLuminosity());
                insert.executeUpdate();

                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
            }
        }
        return false;
    }

    public boolean updateState(State state) {
        if (state != null) {
            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();
                /*UPDATE state SET lamp = 'true', alarm = 'true', smsNotifications = 'true', latitude = "39.000", longitude = "39.000",  harmfulGases = '59.000', luminosity = '59.000' WHERE idstate = '93';*/

                PreparedStatement update
                        = conn.getConnection().prepareStatement("UPDATE state "
                                + "SET"
                                + " lamp = ?,"
                                + " alarm = ?, "
                                + " smsNotifications = ?, "
                                + " latitude = ?, "
                                + " longitude = ?, "
                                + " harmfulGases = ?, "
                                + " luminosity = ? "
                                + "WHERE idstate = ?");

                update.setBoolean(1, state.isLamp());
                update.setBoolean(2, state.isAlarm());
                update.setBoolean(3, state.isSmsNotifications());
                update.setString(4, state.getLatitude());
                update.setString(5, state.getLongitude());
                update.setFloat(6, state.getHarmfulGases());
                update.setFloat(7, state.getLuminosity());
                update.setLong(8, state.getId());
                update.executeUpdate();
                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
            }
        }
        return false;
    }
}
