package com.shaktipumps.shakti_rms.aryabata.classes;

import java.util.ArrayList;

public class SensorMode_Data {
    private static ArrayList<String> _defaultStrings;
    private static ArrayList<SensorMode_Data> _defaults;
    private int id;
    private String sensor_Name;

    public SensorMode_Data(int id, String sensor_Name) {
        this.id = id;
        this.sensor_Name = sensor_Name;
    }

    private static ArrayList<SensorMode_Data> Defaults() {
        if (_defaults == null) {
            _defaults = new ArrayList<>();
            _defaults.add(new SensorMode_Data(0, "No Sensors"));
            _defaults.add(new SensorMode_Data(1, "Sensor 1"));
            _defaults.add(new SensorMode_Data(2, "Sensor 2"));
            _defaults.add(new SensorMode_Data(3, "Sensor 3"));
            _defaults.add(new SensorMode_Data(4, "Sensor 4"));
            _defaults.add(new SensorMode_Data(5, "Sensor 5"));
            _defaults.add(new SensorMode_Data(6, "Sensor 6"));
            _defaults.add(new SensorMode_Data(7, "Sensor 7"));
            _defaults.add(new SensorMode_Data(8, "Sensor 8"));
            _defaults.add(new SensorMode_Data(9, "Sensor 9"));
            _defaults.add(new SensorMode_Data(10, "Sensor 10"));
            _defaults.add(new SensorMode_Data(11, "Sensor 11"));
            _defaults.add(new SensorMode_Data(12, "Sensor 12"));
            _defaults.add(new SensorMode_Data(13, "Sensor 13"));
            _defaults.add(new SensorMode_Data(14, "Sensor 14"));
            _defaults.add(new SensorMode_Data(15, "Sensor 15"));
        }
        return _defaults;
    }

    public static ArrayList<String> DefaultStrings() {
        try {

            if (_defaultStrings == null) {
                _defaultStrings = new ArrayList<>();
                ArrayList<SensorMode_Data> tmp = Defaults();
                for (SensorMode_Data each : tmp) {
                    _defaultStrings.add(each.getSensor_Name());
                }
            }
            return _defaultStrings;
        } catch (Exception ex) {
            return null;
        }
    }

    public static SensorMode_Data GetMatchingRecordForTheName(String name) {
        for (SensorMode_Data each : Defaults()) {
            if (each.getSensor_Name().equals(name)) {
                return each;
            }
        }
        return null;
    }

    public static SensorMode_Data GetMatchingRecordForTheId(int id) {
        for (SensorMode_Data each : Defaults()) {
            if (each.getId() == id) {
                return each;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public String getSensor_Name() {
        return sensor_Name;
    }

    public void setSensor_Name(String p_sensor_Name) {
        sensor_Name = p_sensor_Name;
    }
}
