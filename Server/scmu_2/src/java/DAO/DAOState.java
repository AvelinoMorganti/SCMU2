package DAO;

import classes.State;
import com.google.gson.Gson;
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
                            + "alarmSensor, "
                            + "smsNotifications, "
                            + "harmfulGases, "
                            + "luminosity, "
                            + "temperature, "
                            + "latitude, "
                            + "longitude, "
                            + "date, "
                            + "time "
                            + "FROM state WHERE idstate = ?");

            getUser.setLong(1, id);

            ResultSet data = getUser.executeQuery();

            long tmpId;
            boolean tmpLamp = false;
            boolean tmpAlarm = false;
            boolean tmpAlarmSensor = false;
            boolean tmpSMSNotifications = false;
            float harmfulGases = 0;
            float luminosity = 0;
            float temperature = 0;
            String tmpLatitude = "0.0";
            String tmpLongitude = "0.0";
            String d = "1993-06-23";
            String t = "00:00:00";

            if (data == null) {
                return null;
            }

            if (data.next() != false) {
                tmpId = data.getLong(1);
                tmpLamp = (data.getInt(2) != 0);
                tmpAlarm = (data.getInt(3) != 0);
                tmpAlarmSensor = (data.getInt(4) != 0);
                tmpSMSNotifications = (data.getInt(5) != 0);
                harmfulGases = data.getFloat(6);
                luminosity = data.getFloat(7);
                temperature = data.getFloat(8);
                tmpLatitude = data.getString(9);
                tmpLongitude = data.getString(10);
                d = data.getString(11);
                t = data.getString(12);

                conn.closeConnection();

                p = new State(id,
                        tmpLamp, tmpAlarm, tmpAlarmSensor, tmpSMSNotifications,
                        harmfulGases, luminosity, temperature,
                        tmpLatitude, tmpLongitude, d, t);
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
                PreparedStatement insert = conn.getConnection().prepareStatement("INSERT INTO state VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");

                insert.setInt(1, 0);
                //insert.setBoolean(2, "" + ((properties.isLamp()) ? 1 : 0));
                insert.setBoolean(2, state.isLamp());
                insert.setBoolean(3, state.isAlarm());
                insert.setBoolean(4, state.isAlarmSensor());
                insert.setBoolean(5, state.isSmsNotifications());
                insert.setDouble(6, state.getHarmfulGases());
                insert.setDouble(7, state.getLuminosity());
                insert.setDouble(8, state.getTemperature());
                insert.setString(9, state.getLatitude());
                insert.setString(10, state.getLongitude());
                insert.setString(11, state.getD());
                insert.setString(12, state.getT());
                insert.executeUpdate();

                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
            }
        }
        return false;
    }

    public boolean updateState(State state) {
        System.out.println("Entrou no update");
        //System.err.println("State data = " + state.getD());

        /* String a = new Gson().toJson(state);
         System.err.println(a);*/
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
                                + " alarmSensor = ?, "
                                + " smsNotifications = ?, "
                                + " harmfulGases = ?, "
                                + " luminosity = ?, "
                                + " temperature = ?, "
                                + " latitude = ?, "
                                + " longitude = ?, "
                                + " date = ?, "
                                + " time = ? "
                                + "WHERE idstate = ?");

                update.setBoolean(1, state.isLamp());
                update.setBoolean(2, state.isAlarm());
                update.setBoolean(3, state.isAlarmSensor());
                update.setBoolean(4, state.isSmsNotifications());
                update.setFloat(5, state.getHarmfulGases());
                update.setFloat(6, state.getLuminosity());
                update.setFloat(7, state.getTemperature());
                update.setString(8, state.getLatitude());
                update.setString(9, state.getLongitude());
                update.setString(10, state.getD());
                update.setString(11, state.getT());
                update.setLong(12, state.getId());

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
