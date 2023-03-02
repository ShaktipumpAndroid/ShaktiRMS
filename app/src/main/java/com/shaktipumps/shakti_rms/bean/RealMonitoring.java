package com.shaktipumps.shakti_rms.bean;


public class RealMonitoring {

  String   MPName = " ",
           Unit   = " ",
           Value  = " ";
   int     Index  = 0;
   float     Divisible  = 0;


    public String getMPName() {
        return MPName;
    }

    public void setMPName(String MPName) {
        this.MPName = MPName;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public int getIndex() {
        return Index;
    }

    public void setIndex( int index) {
        Index = index;
    }

    public float getDivisible() {
        return Divisible;
    }

    public void setDivisible( float divisible) {
       Divisible = divisible;
    }
}
