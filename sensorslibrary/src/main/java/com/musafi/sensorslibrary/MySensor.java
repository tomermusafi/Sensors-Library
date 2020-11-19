package com.musafi.sensorslibrary;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import org.jetbrains.annotations.NotNull;


public class MySensor {
    /**
     *sensor listener thar provide the sensors value(magnet, proximity and light)
     */
    public interface CallBack_MySensors{
        /**
         *provide the magnet sensor value(0 - 360)
         * @param magnet
         */
        void getMagnetDirection(float magnet);

        /**
         *provide the proximity sensor value(0.0 or 8.0)
         * @param proximity
         */
        void getProximity(float proximity);

        /**
         *provide the light sensor value
         * @param light
         */
        void getLight(float light);
    }

    //sensors
    private  SensorManager sensorManager;
    private  Sensor accelerometerSensor, magneticSensor, lightSensor, proximitySensor;


    //sensors values
    private float light = 0.0f;
    private static float magnet = 0.0f;
    private static float proximity = 0.8f;

    private  float[] mGravity;
    private  float[] mGeomagnetic;
    private  float rotation;

    private CallBack_MySensors callBack_mySensors;

    public MySensor(@NotNull CallBack_MySensors callBack_mySensors, @NotNull Context context){
        if(callBack_mySensors == null || context == null){
            throw new RuntimeException("One of the parameters you entered is null");
        }
        this.callBack_mySensors = callBack_mySensors;
        initialSensors(context);
    }

    private  SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            findDirection(event);
            proximity(event);
            lightCheck(event);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    };

    private  void findDirection(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;

        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];

            if (SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic)) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                float azimut = orientation[0];
                rotation = azimut * 360 / (2 * 3.14159f);
                if(rotation < 0){
                    rotation = 360 + rotation;
                }
                magnet = rotation;
                callBack_mySensors.getMagnetDirection(magnet);
            }
        }
    }

    private void proximity(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            Log.d("prpr", "proximity: " + event.values[0]);
            proximity =event.values[0];
            callBack_mySensors.getProximity(proximity);
        }
    }

    private void lightCheck(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            light = event.values[0];
        }
        callBack_mySensors.getLight(light);
    }

    private  void initialSensors(Context context){
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor= sensorManager .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticSensor= sensorManager .getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        lightSensor= sensorManager .getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor= sensorManager .getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    /**
     * Registers a SensorEventListener for the given sensors
     */
    public  void registerSensors(){
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(sensorEventListener, magneticSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(sensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(sensorEventListener, proximitySensor, SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * Unregisters a SensorEventListener
     */
    public  void unregisterSensors(){
        sensorManager.unregisterListener(sensorEventListener);
    }

}
