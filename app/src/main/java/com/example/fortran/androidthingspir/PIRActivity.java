package com.example.fortran.androidthingspir;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

/**
 * Created by fortran on 6/5/17.
 */
public class PIRActivity extends AppCompatActivity {
    private static final String TAG = PIRActivity.class.getName();

    private Gpio fPirGpio;
    private Gpio fLampGpio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Starting PIRActivity");

        PeripheralManagerService peripheralService = new PeripheralManagerService();

        try {

            //Create GPIO connection for PIR sensor.
            fPirGpio = peripheralService.openGpio(BoardDefaults.getGPIOForPIR());

            //Configure as an input.
            fPirGpio.setDirection(Gpio.DIRECTION_IN);

            //Enable edge trigger
            fPirGpio.setEdgeTriggerType(Gpio.EDGE_BOTH);

            //Register an event callback
            fPirGpio.registerGpioCallback(fSetLampCallback);

            //Create GPIO connection for Lamp.
            fLampGpio = peripheralService.openGpio(BoardDefaults.getGpioLamp());

            //Configure as an output.
            fLampGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "Starting onDestroy");

        if(fLampGpio != null) {
            try {
                fLampGpio.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing Lamp driver", e);
            } finally {
                fLampGpio = null;
            }
        }

        if (fPirGpio != null) {
            fPirGpio.unregisterGpioCallback(fSetLampCallback);

            try {
                fPirGpio.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing PIR driver", e);
            } finally {
                fPirGpio = null;
            }
        }
    }

    /**
     *  Update the value of the Lapm output.
     * @param state
     */
    private void switchLamp(final boolean state) {
        try {
            fLampGpio.setValue(state);
        } catch (IOException e) {
            Log.e(TAG, "Error updating GPIO value", e);
        }
    }

    /**
     * Register an event callback.
     */
    private GpioCallback fSetLampCallback = new GpioCallback() {
        @Override
        public boolean onGpioEdge(Gpio gpio) {
            try {
                Log.d(TAG, "GPIO callback -->" + gpio.getValue());
                switchLamp(gpio.getValue());
            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
            }

            return true;
        }

        @Override
        public void onGpioError(Gpio gpio, int error) {
            Log.w(TAG, gpio + ": Error event " + error);
        }

    };

}
