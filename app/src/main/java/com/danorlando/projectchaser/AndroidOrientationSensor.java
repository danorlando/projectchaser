package com.danorlando.projectchaser;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by danorlando on 10/22/14.
 */
public class AndroidOrientationSensor extends Activity {

    TextView textviewOrientation;
    OrientationEventListener myOrientationEventListener;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);
        textviewOrientation = (TextView)findViewById(R.id.textorientation);

        myOrientationEventListener
                = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL){

            @Override
            public void onOrientationChanged(int arg0) {
                // TODO Auto-generated method stub
                textviewOrientation.setText("Orientation: " + String.valueOf(arg0));
            }};

        if (myOrientationEventListener.canDetectOrientation()){
            Toast.makeText(this, "Can DetectOrientation", Toast.LENGTH_LONG).show();
            myOrientationEventListener.enable();
        }
        else{
            Toast.makeText(this, "Can't DetectOrientation", Toast.LENGTH_LONG).show();
            finish();
        }


    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        myOrientationEventListener.disable();
    }

}
