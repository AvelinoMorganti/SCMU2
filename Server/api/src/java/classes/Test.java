/*
 * To change this license header, choose License Headers in Project State.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import DAO.DAOAccount;
import DAO.DAOState;

import java.sql.SQLException;

/**
 *
 * @author avelino
 */
public class Test {

    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        DAOAccount auth = new DAOAccount();
        
        
        //Herve
        DAOState daoP = new DAOState();
        Account a[] = new Account[2];
         a[0] = new Account("herve", "scmu_2016");
         a[1] = new Account("carmen", "scmu_2016");
        State p[] = new State[2];
        p[0] = new State(0, true, true, true, "", "", 26.453, 82.0);
        p[1] = new State(0, true, true, true, "", "", 26.453, 82.0);
                
         daoP.insertState(p[0]);
         daoP.insertState(p[1]);
         int id = daoP.getLastRegisterState();
         auth.createAccount(a[0], id);
          id = daoP.getLastRegisterState();
         auth.createAccount(a[1], id);
        
       /*  DAOState daoP = new DAOState();

         Account a[] = new Account[10];
         a[0] = new Account("avelino", "123");
         a[1] = new Account("test", "123");
         a[2] = new Account("root", "123");
         a[3] = new Account("nuno", "123");
         a[4] = new Account("miguel", "123");
         a[5] = new Account("douglas", "123");
         a[6] = new Account("jose", "123");
         a[7] = new Account("rafael", "123");
         a[8] = new Account("ines", "123");
         a[9] = new Account("patricia", "123");

         State p[] = new State[10];
         long s = 1;
         for (int i = 0; i < 10; i++, s++) {

         boolean b1 = false, b2 = false, b3 = false;

         if (((int) (Math.random() * 2)) == 1) {
         b1 = true;
         }
         if (((int) (Math.random() * 2)) == 1) {
         b2 = true;
         }
         if (((int) (Math.random() * 2)) == 1) {
         b3 = true;
         }
         p[i] = new State(-1, b1, b2, b3, "-31", "8", (Math.random() * 100), (Math.random() * 100));

         daoP.insertProperties(p[i]);
         int id = daoP.lastRegisterProperties();
         auth.createAccount(a[i], id);
         System.out.println(new Gson().toJson(p[i]) + "\n");
         System.out.println(new Gson().toJson(a[i]) + "\n");
         }*/

         //Properties pOk = daoP.getPropertiesByID(3);
         //System.out.println(new Gson().toJson(pOk).toString());
         //Properties p = new State(0, true, true, true, "-1", "1", (Math.random() * 100), (Math.random() * 100));
         //daoP.insertProperties(p);
         //Properties pOk = daoP.getPropertiesByID(300);
         //auth.createAccount(c, pOk);
        
         //DAO.DAOUsers dao = new DAO.DAOUsers();
         //ArrayList<Account> all = dao.getAllAccounts();
         //all.add(Account a...);
         

      /*  Account account = null;
         //MySQLAuthenticator my = new MySQLAuthenticator();
         account = auth.getAccount("avelino");

         System.out.println("\n\n" + new Gson().toJson(account).toString());
       */
        
        /*State a = loadUserFromJSONGson("");
        System.out.println(
                "\nid = " + a.getId()
                + "\nlamp = " + a.isLamp()
                + "\nalarm = " + a.isAlarm()
                + "\nsms = " + a.isSms_notifications()
                + "\nlatitude = " + a.getLatitude()
                + "\nlongitude = " + a.getLongitude()
                + "\nharm = " + a.getHarmfulGases()
                + "\nluminosity = " + a.getLuminosity());*/

    }

    /*public static State loadUserFromJSONGson(String jsonString) {
        jsonString = "{\"id\":114,\"lamp\":true,\"alarm\":false,\"smsNotifications\":true,\"latitude\":\"-31\",\"longitude\":\"8\",\"harmfulGases\":15.9943,\"luminosity\":50.4646}";
        Gson gson = new Gson();
        State user = gson.fromJson(jsonString, State.class);
        return user;
    }
*/
}
