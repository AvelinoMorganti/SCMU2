package classes;

import DAO.DAOAccount;
import DAO.DAOState;
import com.google.gson.Gson;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class WSockets extends Thread {

    private final Socket connection;

    public WSockets(Socket conexao) {
        this.connection = conexao;
        /*System.out.println(
         //connection.getInetAddress().getHostName()
         "\n" + connection.getInetAddress().getAddress().toString()
         + "\n" + connection.getInetAddress().getHostAddress().toString()
         //+ "\n" + connection.getInetAddress().getCanonicalHostName()
         );*/
    }

    @Override
    public void run() {
        boolean authenticated = false;
        String username = "";
        String password = "";
        String command = "";
        Scanner in = null;
        PrintStream out = null;
        try {
            in = new Scanner(connection.getInputStream());
            out = new PrintStream(connection.getOutputStream());
            DAOAccount daoAccount = new DAOAccount();
            DAOState daoState = new DAOState();
            Account user = null;
            classes.State state = null;

            command = in.nextLine();

            String authentication[];

            authentication = command.split(";");

            for (int i = 0; i < authentication.length; i++) {
                System.out.println(authentication[i]);
            }

            if (authentication[0].equals("$CONNECT")) {
                /**
                 * TODO informar IP
                 *
                 */
                //username = in.nextLine();
                //password = in.nextLine();
                username = authentication[1];
                password = authentication[2];

                Auth auth = new Auth();
                authenticated = auth.loginString(username, password);

                user = daoAccount.getAccount(username);

                if (!authenticated || user == null) {
                    out.println("401");
                    connection.close();
                }
                if (authenticated) {
                    out.println("200 OK");
                }
            } else {
                out.println("401");
                connection.close();
            }

            while (in.hasNext()) {
                command = in.nextLine();

                if (command == null || command.equals("")) {
                    authenticated = false;
                    connection.close();
                    break;
                    //throw new Exception("invalid data");
                }

                if (command.equals("$END;")) {
                    authenticated = false;
                    out.println("END CONNECTION");
                    out.close();
                    connection.close();
                    break;
                    //System.exit(0);
                }

                if (authenticated) {
                    if (command.equals("$GET;")) {

                        //TODO boolean error and return http error
                        state = daoState.getStateByID(user.getIdState());
                        /*
                        out.println("#" + state.isLamp() + ";"
                                + state.isAlarm() + ";");*/
                        String json = new Gson().toJson(state);
                        out.println(json);
                        continue;
                    }

                    if (command.contains("$PUT;")) {
                        String post[];
                        post = command.split(";");                     
                        
                        //{"alarmSensor":,"luminosity":35.509266,"harmfulGases":4.985344,"temperature":28.627930}
                        Gson gson = new Gson();
                        state = daoState.getStateByID(user.getIdState());

                        classes.State state_new = null;

                        state_new = gson.fromJson(post[1], classes.State.class);
                        //id
                        state_new.setId(user.getIdState());
                        //lamp
                        state_new.setLamp(state.isLamp());
                        //alarm
                        state_new.setAlarm(state.isAlarm());
                        //alarmSensor
                        //state_new.setAlarmSensor(state.isAlarmSensor());
                        //smsNotifications
                        state_new.setSmsNotifications(state.isSmsNotifications());
                        //latitude
                        state_new.setLatitude(state.getLatitude());
                        //longitude
                        state_new.setLongitude(state.getLongitude());
                        //d
                        state_new.setD(state.getD());
                        //t
                        state_new.setT(state.getT());
                        
                        if (daoState.updateState(state_new)) {
                            out.println("201 Created");
                        } else {
                            out.println("500 Internal Server Error");
                        }
                        continue;
                    }

                    if (command.equals("$POST;")) {
                        out.println("100 Continue");
                        String dataToSave = in.nextLine();

                        Gson gson = new Gson();
                        state = daoState.getStateByID(user.getIdState());

                        classes.State state_new = null;

                        state_new = gson.fromJson(dataToSave, classes.State.class);
                        //id
                        state_new.setId(user.getIdState());
                        //lamp
                        state_new.setLamp(state.isLamp());
                        //alarm
                        state_new.setAlarm(state.isAlarm());
                        //alarmSensor
                        //state_new.setAlarmSensor(state.isAlarmSensor());
                        //smsNotifications
                        state_new.setSmsNotifications(state.isSmsNotifications());
                        //latitude
                        state_new.setLatitude(state.getLatitude());
                        //longitude
                        state_new.setLongitude(state.getLongitude());
                        //d
                        state_new.setD(state.getD());
                        //t
                        state_new.setT(state.getT());

                        if (daoState.updateState(state_new)) {
                            out.println("201 Created");
                        } else {
                            out.println("500 Internal Server Error");
                        }
                        continue;
                    }

                    if (command.equals("$CHANGE_PASSWORD;")) {
                        out.println("501 Not Implemented");
                        continue;
                    }

                    if (command.equals("$HI;")) {
                        out.println("HEY!");
                        continue;
                    }

                }
                authenticated = false;

                connection.close();
                break;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                authenticated = false;
                username = "";
                password = "";
                command = "";
                if (out != null) {
                    out.println("[ERROR-TRY-CATCH] $END_CONNECTION;");
                    out.close();
                }
                connection.close();
                System.out.println("Conexao fechada");
                //System.exit(0);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
