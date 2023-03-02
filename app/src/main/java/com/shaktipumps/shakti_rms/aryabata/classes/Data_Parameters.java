package com.shaktipumps.shakti_rms.aryabata.classes;

import com.shaktipumps.shakti_rms.aryabata.adapters.Utils;

import java.util.BitSet;



public class Data_Parameters {
    private int statusFlags;
    private int faultFlags1;
    private int faultFlags2;
    private float temperature;
    private float flow;
    private float activeEnergy;
    private float reactiveEnergy;
    private float rmsVoltageA;
    private float rmsVoltageB;
    private float rmsVoltageC;
    private float frequency;
    private float rmsCurrentA;
    private float rmsCurrentB;
    private float rmsCurrentC;
    private float activePower;
    private float reactivePower;
    private float apparentPower;
    private float powerFactor;
    private String currentDateTime;
    private boolean starterRunning;
    private boolean fault;
    private boolean autoMode;
    private boolean inputVoltageLow;
    private boolean inputVoltageHigh;
    private boolean overCurrent;
    private boolean inputFrequencyLow;
    private boolean overLoad;
    private boolean inputFrequencyHigh;
    private boolean starterIdError;
    private boolean dryRunFault;
    private boolean sensorFault;
    private boolean temperatureFault;

    private boolean ambientTemperatureHigh;
    private boolean earthFault;
    private boolean currentUnBalance;
    private boolean CurrenLimit;

    private boolean waterEmpty;
    private boolean tooManyStarts;
    private boolean rtcError;
    private boolean voltageUnBalance;


    public static Data_Parameters ParseObject(byte[] bytes) {
        try {
            int index = 0;
            if (bytes[index++] != 0x03) return null;
            if (bytes[index++] != 0x40) return null;
            Data_Parameters parameters = new Data_Parameters();

            parameters.setStatusFlags(Utils.ToInt16(bytes, index));
            index = index + 2;

            parameters.setFaultFlags1(Utils.ToInt16(bytes, index));
            index = index + 2;

            parameters.setFaultFlags2(Utils.ToInt16(bytes, index));
            index = index + 2;

            parameters.setTemperature(Utils.DivideBy(Utils.ToInt16Reverse(bytes, index), 10));
            index = index + 2;

            parameters.setFlow(Utils.ToInt16Reverse(bytes, index));
            index = index + 2;

            parameters.setActiveEnergy(Utils.ToFloat(bytes, index));
            index = index + 4;

            parameters.setReactiveEnergy(Utils.ToFloat(bytes, index));
            index = index + 4;

            parameters.setRmsVoltageA(Utils.ToFloat(bytes, index));
            index = index + 4;

            parameters.setRmsVoltageB(Utils.ToFloat(bytes, index));
            index = index + 4;

            parameters.setRmsVoltageC(Utils.ToFloat(bytes, index));
            index = index + 4;

            parameters.setFrequency(Utils.ToFloat(bytes, index));
            index = index + 4;

            parameters.setRmsCurrentA(Utils.ToFloat(bytes, index));
            index = index + 4;

            parameters.setRmsCurrentB(Utils.ToFloat(bytes, index));
            index = index + 4;

            parameters.setRmsCurrentC(Utils.ToFloat(bytes, index));
            index = index + 4;

            parameters.setActivePower(Utils.ToFloat(bytes, index));
            index = index + 4;

            parameters.setReactivePower(Utils.ToFloat(bytes, index));
            index = index + 4;

            parameters.setApparentPower(Utils.ToFloat(bytes, index));
            index = index + 4;

            parameters.setPowerFactor(Utils.ToFloat(bytes, index));
            index = index + 4;

            int obtainedCRC = Utils.ToInt16Reverse(bytes, index);
            int calculatedCRC = Utils.CalculateChecksum(bytes);
            //if(obtainedCRC != calculatedCRC) return null;

            byte[] statusBytes = Utils.Int2ByteArray2(parameters.getStatusFlags());
            BitSet statusBits = BitSet.valueOf(statusBytes);

            int sIndex = 0;
            parameters.setStarterRunning(statusBits.get(sIndex++));
            parameters.setFault(statusBits.get(sIndex++));
            parameters.setAutoMode(statusBits.get(sIndex));

            byte[] faultBytes1 = Utils.Int2ByteArray2(parameters.getFaultFlags1());
            BitSet faultBits1 = BitSet.valueOf(faultBytes1);
            int fIndex1 = 0;
            parameters.setInputVoltageLow(faultBits1.get(fIndex1++));
            parameters.setInputVoltageHigh(faultBits1.get(fIndex1++));
            parameters.setOverCurrent(faultBits1.get(fIndex1++));
            parameters.setInputFrequencyLow(faultBits1.get(fIndex1++));
            parameters.setOverLoad(faultBits1.get(fIndex1++));
            parameters.setInputFrequencyHigh(faultBits1.get(fIndex1++));
            parameters.setStarterIdError(faultBits1.get(fIndex1++));
            parameters.setDryRunFault(faultBits1.get(fIndex1++));
            parameters.setSensorFault(faultBits1.get(fIndex1++));
            parameters.setTemperatureFault(faultBits1.get(fIndex1++));

            parameters.setAmbientTemperatureHigh(faultBits1.get(fIndex1++));
            parameters.setEarthFault(faultBits1.get(fIndex1++));
            parameters.setWaterEmpty(faultBits1.get(fIndex1++));
            parameters.setTooManyStarts(faultBits1.get(fIndex1++));
            parameters.setRtcError(faultBits1.get(fIndex1++));
            parameters.setVoltageUnBalance(faultBits1.get(fIndex1));

            byte[] faultBytes2 = Utils.Int2ByteArray2(parameters.getFaultFlags2());
            BitSet faultBits2 = BitSet.valueOf(faultBytes2);
            int fIndex2 = 0;
            parameters.setCurrentUnBalance(faultBits2.get(fIndex2++));
            parameters.setCurrenLimit(faultBits2.get(fIndex2));

            return parameters;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isAmbientTemperatureHigh() {
        return ambientTemperatureHigh;
    }

    public void setAmbientTemperatureHigh(boolean ambientTemperatureHigh) {
        this.ambientTemperatureHigh = ambientTemperatureHigh;
    }

    public boolean isEarthFault() {
        return earthFault;
    }

    public void setEarthFault(boolean earthFault) {
        this.earthFault = earthFault;
    }

    public boolean isWaterEmpty() {
        return waterEmpty;
    }

    public void setWaterEmpty(boolean waterEmpty) {
        this.waterEmpty = waterEmpty;
    }

    public boolean isTooManyStarts() {
        return tooManyStarts;
    }

    public void setTooManyStarts(boolean tooManyStarts) {
        this.tooManyStarts = tooManyStarts;
    }

    public boolean isRtcError() {
        return rtcError;
    }

    public void setRtcError(boolean rtcError) {
        this.rtcError = rtcError;
    }

    public boolean isVoltageUnBalance() {
        return voltageUnBalance;
    }

    public void setVoltageUnBalance(boolean voltageUnBalance) {
        this.voltageUnBalance = voltageUnBalance;
    }

    public boolean isStarterRunning() {
        return starterRunning;
    }

    public void setStarterRunning(boolean starterRunning) {
        this.starterRunning = starterRunning;
    }

    public boolean isFault() {
        return fault;
    }

    public void setFault(boolean fault) {
        this.fault = fault;
    }

    public boolean isAutoMode() {
        return autoMode;
    }

    public void setAutoMode(boolean autoMode) {
        this.autoMode = autoMode;
    }

    public boolean isInputVoltageLow() {
        return inputVoltageLow;
    }

    public void setInputVoltageLow(boolean inputVoltageLow) {
        this.inputVoltageLow = inputVoltageLow;
    }

    public boolean isInputVoltageHigh() {
        return inputVoltageHigh;
    }

    public void setInputVoltageHigh(boolean inputVoltageHigh) {
        this.inputVoltageHigh = inputVoltageHigh;
    }

    public boolean isOverCurrent() {
        return overCurrent;
    }

    public void setOverCurrent(boolean overCurrent) {
        this.overCurrent = overCurrent;
    }

    public boolean isInputFrequencyLow() {
        return inputFrequencyLow;
    }

    public void setInputFrequencyLow(boolean inputFrequencyLow) {
        this.inputFrequencyLow = inputFrequencyLow;
    }

    public boolean isOverLoad() {
        return overLoad;
    }

    public void setOverLoad(boolean overLoad) {
        this.overLoad = overLoad;
    }

    public boolean isInputFrequencyHigh() {
        return inputFrequencyHigh;
    }

    public void setInputFrequencyHigh(boolean inputFrequencyHigh) {
        this.inputFrequencyHigh = inputFrequencyHigh;
    }

    public boolean isStarterIdError() {
        return starterIdError;
    }

    public void setStarterIdError(boolean starterIdError) {
        this.starterIdError = starterIdError;
    }

    public boolean isDryRunFault() {
        return dryRunFault;
    }

    public void setDryRunFault(boolean dryRunFault) {
        this.dryRunFault = dryRunFault;
    }

    public boolean isSensorFault() {
        return sensorFault;
    }

    public void setSensorFault(boolean sensorFault) {
        this.sensorFault = sensorFault;
    }

    public int getStatusFlags() {
        return statusFlags;
    }

    public void setStatusFlags(int statusFlags) {
        this.statusFlags = statusFlags;
    }

    public int getFaultFlags1() {
        return faultFlags1;
    }

    public void setFaultFlags1(int faultFlags1) {
        this.faultFlags1 = faultFlags1;
    }

    public int getFaultFlags2() {
        return faultFlags2;
    }

    public void setFaultFlags2(int faultFlags2) {
        this.faultFlags2 = faultFlags2;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getFlow() {
        return flow;
    }

    public void setFlow(float flow) {
        this.flow = flow;
    }

    public float getActiveEnergy() {
        return activeEnergy;
    }

    public void setActiveEnergy(float activeEnergy) {
        this.activeEnergy = activeEnergy;
    }

    public float getReactiveEnergy() {
        return reactiveEnergy;
    }

    public void setReactiveEnergy(float reactiveEnergy) {
        this.reactiveEnergy = reactiveEnergy;
    }

    public float getRmsVoltageA() {
        return rmsVoltageA;
    }

    public void setRmsVoltageA(float rmsVoltageA) {
        this.rmsVoltageA = rmsVoltageA;
    }

    public float getRmsVoltageB() {
        return rmsVoltageB;
    }

    public void setRmsVoltageB(float rmsVoltageB) {
        this.rmsVoltageB = rmsVoltageB;
    }

    public float getRmsVoltageC() {
        return rmsVoltageC;
    }

    public void setRmsVoltageC(float rmsVoltageC) {
        this.rmsVoltageC = rmsVoltageC;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public float getRmsCurrentA() {
        return rmsCurrentA;
    }

    public void setRmsCurrentA(float rmsCurrentA) {
        this.rmsCurrentA = rmsCurrentA;
    }

    public float getRmsCurrentB() {
        return rmsCurrentB;
    }

    public void setRmsCurrentB(float rmsCurrentB) {
        this.rmsCurrentB = rmsCurrentB;
    }

    public float getRmsCurrentC() {
        return rmsCurrentC;
    }

    public void setRmsCurrentC(float rmsCurrentC) {
        this.rmsCurrentC = rmsCurrentC;
    }

    public float getActivePower() {
        return activePower;
    }

    public void setActivePower(float activePower) {
        this.activePower = activePower;
    }

    public float getReactivePower() {
        return reactivePower;
    }

    public void setReactivePower(float reactivePower) {
        this.reactivePower = reactivePower;
    }

    public float getApparentPower() {
        return apparentPower;
    }

    public void setApparentPower(float apparentPower) {
        this.apparentPower = apparentPower;
    }

    public float getPowerFactor() {
        return powerFactor;
    }

    public void setPowerFactor(float powerFactor) {
        this.powerFactor = powerFactor;
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(String currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public boolean isTemperatureFault() {
        return temperatureFault;
    }

    public void setTemperatureFault(boolean temperatureFault) {
        this.temperatureFault = temperatureFault;
    }

    public boolean isCurrentUnBalance() {
        return currentUnBalance;
    }

    public void setCurrentUnBalance(boolean currentUnBalance) {
        this.currentUnBalance = currentUnBalance;
    }

    public boolean isCurrenLimit() {
        return CurrenLimit;
    }

    public void setCurrenLimit(boolean CurrenLimit) {
        this.CurrenLimit = CurrenLimit;
    }
}
