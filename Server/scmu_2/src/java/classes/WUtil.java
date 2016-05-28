/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Avelino
 */
public class WUtil {

    public static String retornaDataAtualSt() {
        SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = new Date();
        return data.format(dt);
    }

    public static Date retornaDataAtualDt() {
        SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = new Date();
        return dt;
    }

    public static String retornaHoraAtualSt() {
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
        Date hr = new Date();
        return hora.format(hr);
    }

    public static Date retornaHoraAtualDt() {
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
        Date hr = new Date();
        return hr;
    }

    public static Date formataData(String dataR) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = (Date) formatter.parse(dataR);
        return dt;
    }
    public static Date formataHora(String horaR) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date hr = (Date) formatter.parse(horaR);
        return hr;
    }

    public static String formataData(Date dataR) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(dataR);
    }

    public static String formataHora(Date horaR) throws ParseException {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(horaR);
    }
}
