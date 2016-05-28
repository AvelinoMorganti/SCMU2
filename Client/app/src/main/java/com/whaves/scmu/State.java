/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whaves.scmu;

/**
 *
 * @author avelino
 */
public class State {

    private long id;
    private boolean lamp;
    private boolean alarm;
    private boolean alarmSensors;
    private boolean smsNotifications;
    private double harmfulGases;
    private double luminosity;
    private double temperature;
    private String latitude;
    private String longitude;
    private String d;
    private String t;

    public State() {
    }

    public State(long id,
                 boolean lamp, boolean alarm, boolean alarmSensors, boolean smsNotifications,
                 double harmfulGases, double luminosity, double temperature,
                 String latitude, String longitude, String d, String t) {
        this.id = id;
        this.lamp = lamp;
        this.alarm = alarm;
        this.alarmSensors = alarmSensors;
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

    public boolean isAlarmSensors() {
        return alarmSensors;
    }

    public void setAlarmSensors(boolean alarmSensors) {
        this.alarmSensors = alarmSensors;
    }

    public boolean isSmsNotifications() {
        return smsNotifications;
    }

    public void setSmsNotifications(boolean smsNotifications) {
        this.smsNotifications = smsNotifications;
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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
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
