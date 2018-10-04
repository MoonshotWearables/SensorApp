package com.example.anjie.sensorapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager mSensorManger;
    private Button btn_data;
    private Button logout_btn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_data = (Button) findViewById(R.id.btn_data);
        logout_btn = (Button) findViewById(R.id.logout_btn);
        mAuth = FirebaseAuth.getInstance();

        btn_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToDataPage();
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                sendToLogin();
            }
        });

        mSensorManger = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //get an instance of the sensor manager from system service and assign it to the variable

        List<Sensor> sensorList =
                mSensorManger.getSensorList(Sensor.TYPE_ALL);

        StringBuilder sensorText = new StringBuilder();

        for(Sensor currentSensor:sensorList){
            sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
        }

        TextView sensorTextView = (TextView) findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);
    }

    private void sendToDataPage(){
        Intent setupIntent = new Intent(MainActivity.this, SensorDataActivity.class);
        startActivity(setupIntent);
    }

    private void sendToLogin(){
        Intent setupIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(setupIntent);
    }
}
