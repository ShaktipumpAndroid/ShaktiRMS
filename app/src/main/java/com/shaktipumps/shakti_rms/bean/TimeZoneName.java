package com.shaktipumps.shakti_rms.bean;

/**
 * Created by shakti on 12-Apr-19.
 */
public class TimeZoneName {
    String  timezone_id,
            timezone_long,
            timezone_short,
            timezone_city;


    public String getTimezone_id() {
        return timezone_id;
    }

    public void setTimezone_id(String timezone_id) {
        this.timezone_id = timezone_id;
    }



    public String getTimezone_city() {
        return timezone_city;
    }

    public void setTimezone_city(String timezone_city) {
        this.timezone_city = timezone_city;
    }

    public String getTimezone_long() {
        return timezone_long;
    }

    public void setTimezone_long(String timezone_long) {
        this.timezone_long = timezone_long;
    }

    public String getTimezone_short() {
        return timezone_short;
    }

    public void setTimezone_short(String timezone_short) {
        this.timezone_short = timezone_short;
    }
}
