package com.musafi.y4s1t1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.musafi.sensorslibrary.MySensor;

public class MainActivity extends AppCompatActivity {

    TextView my_magnet, my_proximity, my_light;
    MySensor mySensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my_magnet = findViewById(R.id.main_LBL_magnet);
        my_proximity = findViewById(R.id.main_LBL_proximity);
        my_light = findViewById(R.id.main_LBL_light);

        mySensors = new MySensor(callBack_MySensors, this);

    }


    MySensor.CallBack_MySensors callBack_MySensors = new MySensor.CallBack_MySensors() {
        @Override
        public void getMagnetDirection(float magnet) {
            my_magnet.setText(""+(int)magnet);
        }

        @Override
        public void getProximity(float proximity) {
            my_proximity.setText(""+proximity);
        }

        @Override
        public void getLight(float light) {
            my_light.setText(""+light);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mySensors.registerSensors();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mySensors.unregisterSensors();
    }
}