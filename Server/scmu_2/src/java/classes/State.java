/*
 * To change this license header, choose License Headers in Project State.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author avelino
 */
public class State {
//{"alarmSensor":,"luminosity":35.509266,"harmfulGases":4.985344,"temperature":28.627930}
    private long id;
    private boolean lamp;
    private boolean alarm;
    private boolean alarmSensor;
    private boolean smsNotifications;
    private float harmfulGases;
    private float luminosity;
    private float temperature;
    private String latitude;
    private String longitude;
    //private Date date;
    //private Date time;
    private String d;
    private String t;

    public State() {
    }

    public State(long id, boolean lamp, boolean alarm, boolean alarmSensor, boolean smsNotifications, float harmfulGases, float luminosity, float temperature, String latitude, String longitude, String d, String t) {
        this.id = id;
        this.lamp = lamp;
        this.alarm = alarm;
        this.alarmSensor = alarmSensor;
        this.smsNotifications = smsNotifications;
        this.harmfulGases = harmfulGases;
        this.luminosity = luminosity;
        this.temperature = temperature;
        this.latitude = latitude;
        this.longitude = longitude;
        this.d = d;
        this.t = t;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isLamp() {
        return lamp;
    }

    public void setLamp(boolean lamp) {
        this.lamp = lamp;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public boolean isAlarmSensor() {
        return alarmSensor;
    }

    public void setAlarmSensor(boolean alarmSensor) {
        this.alarmSensor = alarmSensor;
    }

    public boolean isSmsNotifications() {
        return smsNotifications;
    }

    public void setSmsNotifications(boolean smsNotifications) {
        this.smsNotifications = smsNotifications;
    }

    public float getHarmfulGases() {
        return harmfulGases;
    }

    public void setHarmfulGases(float harmfulGases) {
        this.harmfulGases = harmfulGases;
    }

    public float getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(float luminosity) {
        this.luminosity = luminosity;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

}
