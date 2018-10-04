package com.example.anjie.sensorapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.anjie.sensorapp.Model.AccData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Date;

public class SensorDataActivity
        extends AppCompatActivity implements SensorEventListener{
    private SensorManager mSensorManger;

    //Individual light and proximity sensor
    //private Sensor mSensorProximity;
    //private Sensor mSensorLight;
    private Sensor mSensorACC;
    //private Sensor mSensorHeart;

    //TextViews to display current sensor values
    private TextView mTextSensorLight;
    private TextView mTextSensorACC_X;
    private TextView mTextSensorACC_Y;
    private TextView mTextSensorACC_Z;
    private TextView mTextTimestamp;

    private AccData mAccData;
    //DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    //private TextView mTextSensorProximity;
    //private TextView mTextSensorHeart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);

        mSensorManger = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //get an instance of the sensor manager from system service and assign it to the variable

        mTextSensorACC_X = (TextView) findViewById(R.id.label_acc_x);
        mTextSensorACC_Y = (TextView) findViewById(R.id.label_acc_y);
        mTextSensorACC_Z = (TextView) findViewById(R.id.label_acc_z);
        mTextTimestamp = (TextView) findViewById(R.id.timestamp_text);
        //mTextSensorProximity = (TextView) findViewById(R.id.label_proximity);
        //mTextSensorHeart = (TextView) findViewById(R.id.label_heart);

       // mSensorLight = mSensorManger.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorACC = mSensorManger.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //mSensorGRV = mSensorManger.getDefaultSensor(Sensor.TYPE_GRAVITY);
        //mSensorProximity = mSensorManger.getDefaultSensor(Sensor.TYPE_PROXIMITY);
       //mSensorHeart = mSensorManger.getDefaultSensor(Sensor.TYPE_HEART_BEAT);

        String sensor_error = getResources().getString(R.string.error_no_sensor);

        //Test for the existence of two sensors
       // if(mSensorLight == null){
         //   mTextSensorLight.setText(sensor_error);
        //}
        if(mSensorACC == null){
            mTextSensorACC_X.setText(sensor_error);
            mTextSensorACC_Y.setText(sensor_error);
            mTextSensorACC_Z.setText(sensor_error);
        }
        //if(mSensorProximity == null){
         //   mTextSensorProximity.setText(sensor_error);
        //}
        mAccData = new AccData();
        Button testButton = findViewById(R.id.test_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference().child("test").child(mAccData.getTime()+"");
                ref.child("uid").setValue(123);
                ref.child("x").setValue(mAccData.getX());
                ref.child("y").setValue(mAccData.getY());
                ref.child("z").setValue(mAccData.getZ());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //The delay constant indicates how quickly new data is reported from the sensor
        //if(mSensorProximity != null){
        //mSensorManger.registerListener(this, mSensorProximity,
        //            SensorManager.SENSOR_DELAY_NORMAL);
        //}
        mSensorManger.registerListener(this, mSensorACC,
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //A single call unregistered all the registered listeners.
        // Unregistering the sensor listeners in the onStop() method prevents
        //the device from using power when the app is not visible.
        mSensorManger.unregisterListener(this);
    }

    //Called when new sensor data is available
    @Override
    public void onSensorChanged(SensorEvent Event) {
        //int sensorType = sensorEvent.sensor.getType();
        long timestamp = (new Date()).getTime() + (Event.timestamp - System.nanoTime()) / 1000000L;

        float currentValueX = Event.values[0];
        float currentValueY = Event.values[1];
        float currentValueZ = Event.values[2];

        mAccData.setX(currentValueX);
        mAccData.setY(currentValueY);
        mAccData.setZ(currentValueZ);
        mAccData.setTime(timestamp);
       // switch (sensorType){
            //Event came from the light sensor
         //   case Sensor.TYPE_LIGHT:
                //Handle light sensor
           //     mTextSensorLight.setText(getResources().getString(
                 //       R.string.light_sensor_text, currentValueX
             //   ));
               // break;
            //case Sensor.TYPE_ACCELEROMETER:
                mTextSensorACC_X.setText(getResources().getString(
                        R.string.acc_sensor_text_X, currentValueX
                ));
               mTextSensorACC_Y.setText(getResources().getString(
                        R.string.acc_sensor_text_Y, currentValueY
               ));
               mTextSensorACC_Z.setText(getResources().getString(
                        R.string.acc_sensor_text_Z, currentValueZ
                ));


              // String AccKey = Long.toString(timestamp);
              // database.child("Accelerator").child(AccKey).setValue(currentValueX);

            //case Sensor.TYPE_PROXIMITY:
            //    mTextSensorProximity.setText(getResources().getString(
            //            R.string.proximity_text,currentValue
            //    ));

            //default:
                //do nothing
       // }

        mTextTimestamp.setText(getResources().getString(
                R.string.timestamp, timestamp
        ));

    }

    //Called if the sensor's accuracy changes
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
