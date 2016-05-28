/*
 * To change this license header, choose License Headers in Project State.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import DAO.DAOAccount;
import DAO.DAOSchedule;
import DAO.DAOState;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author avelino
 */
public class Test {

    public static void main(String args[]) throws ClassNotFoundException, SQLException, ParseException {

        DAOState daoState = new DAOState();
        DAOSchedule daoSchedule = new DAOSchedule();
        String g = "1sh19aGtAU";//context.getRecoveringTriggerKey().getGroup();
        String result = daoSchedule.getScheduleByGroup(g);
        System.out.println(result);
        
        State state = new Gson().fromJson(result, State.class);
        daoState.updateState(state);
        
        
        
        
        /* 
         Date a = WUtil.formataData("2016-05-25");
         System.out.println("Date: "+a.getDate());
         System.out.println("Day: "+a.getDay());
         System.out.println("Month: "+a.getMonth());
         System.out.println("Year: "+(a.getYear()+1900));
         System.out.println("Second: "+a.getSeconds());
         System.out.println("Minute: "+a.getMinutes());
         System.out.println("Hour: "+a.getHours());
         System.out.println("Time: "+a.getTime());
         */
        /*
         Schedule s = new Schedule("2016-05-25", "05:05:05");
         //s.setSchedule("0", "51", "4", "25", "5", "2016");
         System.out.println("passou");
         */
        /*
         DAOAccount daoA = new DAOAccount();
         DAOState daoP = new DAOState();

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

         for (int i = 0; i < 10; i++) {

         boolean b1 = false, b2 = false, b3 = false, b4 = false;

         if (((int) (Math.random() * 2)) == 1) {
         b1 = true;
         }
         if (((int) (Math.random() * 2)) == 1) {
         b2 = true;
         }
         if (((int) (Math.random() * 2)) == 1) {
         b3 = true;
         }
         if (((int) (Math.random() * 2)) == 1) {
         b4 = true;
         }
         p[i] = new State(-1,
         b1, b2, b3, b4,
         (float) (Math.random() * 100),
         (float) (Math.random() * 100),
         (float) (Math.random() * 100),
         "-31", "8","2016-06-23", "00:00:00");

         daoP.insertState(p[i]);
         int id = daoP.getLastRegisterState();
         daoA.createAccount(a[i], id);
         //System.out.println(new Gson().toJson(p[i]) + "\n");
         //System.out.println(new Gson().toJson(a[i]) + "\n");
         }*/
    }
}
