package com.shaktipumps.shakti_rms.aryabata.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Utils {

    public static final UUID MyUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static Integer Polynomial = 0xA001;
    public static String regexForOnly1DigitAfterDecimal = "^\\d{1,3}+(\\.\\d)?$";
    //public static String adminAppURL = "http://vtrace.in/arya_rest/";
    //public static String adminAppURL = "http://122.175.43.240/arya_rest/";
    public static String adminAppURL = "https://solar10.shaktisolarrms.com/Home/";

    //below combination leads to time out of 15 seconds
    public static final int MAX_RETRY_COUNT = 1500;
    public static final int RETRY_WAIT_TIME = 10;
    //


    public static byte[] Trim(byte[] bytes) {
        int i = bytes.length - 1;
        while (i >= 0 && bytes[i] == 0) {
            --i;
        }
        return Arrays.copyOf(bytes, i + 1);
    }

    public static void ShowProgressDialog(final Activity activity, final ProgressDialog pd, final String msg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.setMessage(msg);
                pd.show();
            }
        });
    }

    public static void HideProgressDialog(final Activity activity, final ProgressDialog pd) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.hide();
            }
        });
    }


    private static boolean IsNetworkConnected(Context current) {
        ConnectivityManager cm = (ConnectivityManager) current.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        }
        return false;
    }

    private static boolean IsInternetAvailable() {
        try {
            return new CheckNetwork().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    public static boolean IsConnectedNHasInternet(Context current) {
        return IsNetworkConnected(current) && IsInternetAvailable();
    }

    public static String BytesToString(byte[] b) {
        try {
            return new String(b, "US-ASCII");
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }

    public static short ToInt16(byte[] data, int offset) {
        return (short) (data[offset] & 0xFF | (data[offset + 1] & 0xFF) << 8);
    }

    public static short ToInt16Reverse(byte[] data, int offset) {
        return ByteBuffer.wrap(new byte[]{data[offset], data[offset + 1]}).order(ByteOrder.BIG_ENDIAN).getShort();
    }

    public static float MultiplyBy(int intValue, int multiplyBy) {
        return (intValue * multiplyBy);
    }

    public static float DivideBy(int intValue, int divideBy) {
        return (intValue / divideBy);
    }

    private static int ToInt32(byte[] data, int offset) {
        return (data[offset] & 0xFF) | ((data[offset + 1] & 0xFF) << 8)
                | ((data[offset + 2] & 0xFF) << 16)
                | ((data[offset + 3] & 0xFF) << 24);
    }

    public static float ToFloat(byte[] data, int offset) {
        return Float.intBitsToFloat(ToInt32(data, offset));
    }

    public static byte[] Float2ByteArray(float value) {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }

    public static byte[] Int2ByteArray(int value) {
        return ByteBuffer.allocate(2).putInt(value).array();
    }

    public static byte[] Int2ByteArray2(int value) {
        byte highByte = (byte) (value & 0xFF);
        byte lowByte = (byte) ((value >> 8) & 0xFF);
        return new byte[]{lowByte, highByte};
    }

    public static int Calculated_CRC(byte[] bytes) {
        Integer ModCRC = 0xFFFF;
        Integer reg;
        for (int j = 0; j < bytes.length -2; j++) {
            ModCRC = ModCRC ^ bytes[j];
            for (int count = 0; count < 8; count++) {
                reg = (ModCRC & 0x0001);
                ModCRC >>= 1;
                if (reg >= 1) ModCRC ^= Polynomial;
            }
        }
        return ModCRC;
    }

    public static int Crc16(byte[] bytes) {
        byte value = 0;
        byte temp;
        for (byte i = 0; i < bytes.length - 2; ++i) {
            value = bytes[i];
            temp = i;
            for (byte j = 0; j < 8; ++j) {
                if (((value ^ temp) & 0x0001) != 0) {
                    value = (byte) ((value >> 1) ^ Polynomial);
                } else {
                    value >>= 1;
                }
                temp >>= 1;
            }
        }
        return value;
    }

    public static Integer CalculateChecksum(byte[] pData) {

        int reg;
        byte j;
        Integer ModCRC = 0xFFFF;

        for (j = 0; j < pData.length - 2; j++) {
            ModCRC = ModCRC ^ pData[j];
            for (int count = 0; count < 8; count++) {
                reg = (ModCRC & 0x0001);
                ModCRC >>= 1;
                if (reg >= 1) ModCRC ^= Polynomial;
            }
        }
        return ModCRC;
    }
}
