package com.example.fortran.androidthingspir;

import android.os.Build;

/**
 * Created by fortran on 6/5/17.
 */

@SuppressWarnings("WeakerAccess")
public class BoardDefaults {
    private static final String DEVICE_EDISON_ARDUINO = "edison_arduino";
    private static final String DEVICE_EDISON = "edison";
    private static final String DEVICE_JOULE = "joule";
    private static final String DEVICE_RPI3 = "rpi3";
    private static final String DEVICE_IMX6UL_PICO = "imx6ul_pico";
    private static final String DEVICE_IMX6UL_VVDN = "imx6ul_iopb";
    private static final String DEVICE_IMX7D_PICO = "imx7d_pico";
    private static String sBoardVariant = "";

    public static String getGpioLamp() {
        switch (getBoartVersion()) {
            case DEVICE_EDISON_ARDUINO:
                return "IO13";
            case DEVICE_EDISON:
                return "GP45";
            case DEVICE_JOULE:
                return "J6_25";
            case DEVICE_RPI3:
                return "BCM6";
            case DEVICE_IMX6UL_PICO:
                return "GPIO4_IO20";
            case DEVICE_IMX6UL_VVDN:
                return "GPIO3_IO06";
            case DEVICE_IMX7D_PICO:
                return "GPIO_34";
            default:
                throw new IllegalArgumentException("Unknown device: " + Build.DEVICE);
        }
    }

    public static String getGPIOForPIR() {
        switch (getBoartVersion()) {
            case DEVICE_EDISON_ARDUINO:
                return "IO12";
            case DEVICE_EDISON:
                return "GP44";
            case DEVICE_JOULE:
                return "J7_71";
            case DEVICE_RPI3:
                return "BCM21";
            case DEVICE_IMX6UL_PICO:
                return "GPIO4_IO20";
            case DEVICE_IMX6UL_VVDN:
                return "GPIO3_IO01";
            case DEVICE_IMX7D_PICO:
                return "GPIO_174";
            default:
                throw new IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE);
        }
    }

    private static String getBoartVersion() {

        if(!sBoardVariant.isEmpty()) {
            return sBoardVariant;
        }

        sBoardVariant = Build.DEVICE;

        return sBoardVariant;
    }







}
