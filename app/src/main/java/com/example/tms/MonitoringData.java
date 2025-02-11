package com.example.tms;

public class MonitoringData {
    private String pressure;
    private String temperature;
    private String moisture;
    private String pollution;
    private String skintype;

    public MonitoringData() {
        // Default constructor required for Firebase
    }

    public MonitoringData(String pressure, String temperature, String moisture, String pollution, String skintype) {
        this.pressure = pressure;
        this.temperature = temperature;
        this.moisture = moisture;
        this.pollution = pollution;
        this.skintype = skintype;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getMoisture() {
        return moisture;
    }

    public void setMoisture(String moisture) {
        this.moisture = moisture;
    }

    public String getPollution() {
        return pollution;
    }

    public void setPollution(String pollution) {
        this.pollution = pollution;
    }

    public String getSkintype() {
        return skintype;
    }

    public void setSkintype(String skintype) {
        this.skintype = skintype;
    }
}
