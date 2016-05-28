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

    private long id;
    private boolean lamp;
    private boolean alarm;
    private boolean smsNotifications;
    private String latitude;
    private String longitude;
    private double harmfulGases;
    private double luminosity;

    public State() {
    }

    public State(long id, 
            boolean lamp, 
            boolean alarm, 
            boolean smsNotifications, 
            String latitude, 
            String longitude, 
            double harmfulGases, 
            double luminosity) {
        this.id = id;
        this.lamp = lamp;
        this.alarm = alarm;
        this.smsNotifications = smsNotifications;
        this.latitude = latitude;
        this.longitude = longitude;
        this.harmfulGases = harmfulGases;
        this.luminosity = luminosity;
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

    public boolean isSmsNotifications() {
        return smsNotifications;
    }

    public void setSmsNotifications(boolean smsNotifications) {
        this.smsNotifications = smsNotifications;
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

    public double getHarmfulGases() {
        return harmfulGases;
    }

    public void setHarmfulGases(double harmfulGases) {
        this.harmfulGases = harmfulGases;
    }

    public double getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(double luminosity) {
        this.luminosity = luminosity;
    }

}
