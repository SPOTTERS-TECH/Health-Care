package com.caregiver.healthcare;

public class Vitals {
    private String temperature;
    private String pulse;
    private String respiration;
    private String bloodpressure;
    private String bloodoximetry;
    private String pain;
    private String bloodsugar;
    private String date;
    private String time;

    public Vitals(String temperature, String pulse, String respiration, String bloodpressure, String bloodoximetry, String pain, String bloodsugar, String date, String time) {
        this.temperature = temperature;
        this.pulse = pulse;
        this.respiration = respiration;
        this.bloodpressure = bloodpressure;
        this.bloodoximetry = bloodoximetry;
        this.pain = pain;
        this.bloodsugar = bloodsugar;
        this.date = date;
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getRespiration() {
        return respiration;
    }

    public void setRespiration(String respiration) {
        this.respiration = respiration;
    }

    public String getBloodpressure() {
        return bloodpressure;
    }

    public void setBloodpressure(String bloodpressure) {
        this.bloodpressure = bloodpressure;
    }

    public String getBloodoximetry() {
        return bloodoximetry;
    }

    public void setBloodoximetry(String bloodoximetry) {
        this.bloodoximetry = bloodoximetry;
    }

    public String getPain() {
        return pain;
    }

    public void setPain(String pain) {
        this.pain = pain;
    }

    public String getBloodsugar() {
        return bloodsugar;
    }

    public void setBloodsugar(String bloodsugar) {
        this.bloodsugar = bloodsugar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
