package com.danorlando.projectchaser;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by danorlando on 10/20/14.
 */
public abstract class SingleFragmentActivity extends FragmentActivity implements Orientation.Listener {
    protected abstract Fragment createFragment();

    private SensorManager mSensorManager;
    Sensor accelerometer;
    Sensor magnetometer;
    CharSequence text;
    private Orientation mOrientation;
    OrientationEventListener orientationEventListener;
    TextView textviewOrientation;


    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
     /*   mOrientation = new Orientation((SensorManager) getSystemService(Activity.SENSOR_SERVICE),
                getWindow().getWindowManager());
        setContentView(getLayoutResId(Surface.ROTATION_0));
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        } */
     //   textviewOrientation = (TextView)findViewById(R.id.textorientation);

      /*  orientationEventListener
                = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL){
            @Override
            public void onOrientationChanged(int arg0) {
                if (orientationEventListener.canDetectOrientation()){
                    Toast.makeText(getApplicationContext(), "Can DetectOrientation", Toast.LENGTH_LONG).show();
                    orientationEventListener.enable();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Can't DetectOrientation", Toast.LENGTH_LONG).show();
                    finish();
                }
                mOrientation = new Orientation((SensorManager) getSystemService(Activity.SENSOR_SERVICE),
                        getWindow().getWindowManager());
                mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
                accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                int rotation = getWindowManager().getDefaultDisplay().getRotation();
                setContentView(getLayoutResId());
                FragmentManager manager = getSupportFragmentManager();
                Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
                Fragment fragmentList = manager.findFragmentById(R.id.listContainer);
                Fragment fragmentDetail = manager.findFragmentById(R.id.detailFragmentContainer);


                switch (rotation) {
                    case Surface.ROTATION_0:
                        Log.d("*************", "ORIENTATION IS PORTRAIT");
                        if (fragment == null) {
                            fragment = createFragment();
                            manager.beginTransaction()
                                    .add(R.id.fragmentContainer, fragment)
                                    .commit();
                        }
                        text = "SCREEN_ORIENTATION_PORTRAIT, ROTATION 0";
                        break;
                    case Surface.ROTATION_90:
                        Log.d("*************", "ORIENTATION IS LANDSCAPE");
                        if (fragmentList == null) {
                            fragmentList = createFragment();
                            manager.beginTransaction()
                                    .add(R.id.listContainer, fragmentList)
                                    .commit();
                        }
                        if (fragmentDetail == null) {
                            fragmentDetail = createFragment();
                            manager.beginTransaction()
                                    .add(R.id.detailFragmentContainer, fragmentDetail)
                                    .commit();
                        }
                        text = "SCREEN_ORIENTATION_LANDSCAPE";
                        break;
                    case Surface.ROTATION_180:
                        Log.d("*************", "ORIENTATION IS REVERSE PORTRAIT");
                        if (fragment == null) {
                            fragment = createFragment();
                            manager.beginTransaction()
                                    .add(R.id.fragmentContainer, fragment)
                                    .commit();
                        }
                        text = "SCREEN_ORIENTATION_REVERSE_PORTRAIT";
                        break;
                    case Surface.ROTATION_270:
                        Log.d("*************", "ORIENTATION IS REVERSE LANDSCAPE");
                        if (fragmentList == null) {
                            fragmentList = createFragment();
                            manager.beginTransaction()
                                    .add(R.id.listContainer, fragmentList)
                                    .commit();
                        }
                        if (fragmentDetail == null) {
                            fragmentDetail = createFragment();
                            manager.beginTransaction()
                                    .add(R.id.detailFragmentContainer, fragmentDetail)
                                    .commit();
                        }
                        text = "SCREEN_ORIENTATION_REVERSE_LANDSCAPE";
                        break;
                    default:
                        Log.d("*************", "ORIENTATION IS DEFAULT");
                        if (fragment == null) {
                            fragment = createFragment();
                            manager.beginTransaction()
                                    .add(R.id.fragmentContainer, fragment)
                                    .commit();
                        }
                        text = "SCREEN_ORIENTATION_PORTRAIT, DEFAULT";
                        break;
                }
            }}; */
    }

    @Override
    protected void onResume() {
        super.onResume();
    //    mOrientation.startListening(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    //    mOrientation.stopListening();
    }

    @Override
    public void onOrientationChanged(float pitch, float roll) {
        Toast toast = Toast.makeText(getApplicationContext(), text,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 10, 0);
        toast.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
     /*   mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        setContentView(getLayoutResId());
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        Fragment fragmentList = manager.findFragmentById(R.id.listContainer);
        Fragment fragmentDetail = manager.findFragmentById(R.id.detailFragmentContainer);
        switch (rotation) {
            case Surface.ROTATION_0:
                Log.d("*************", "ORIENTATION IS PORTRAIT");
                if (fragment == null) {
                    fragment = createFragment();
                    manager.beginTransaction()
                            .add(R.id.fragmentContainer, fragment)
                            .commit();
                }
                text = "SCREEN_ORIENTATION_PORTRAIT";
                break;
            case Surface.ROTATION_90:
                Log.d("*************", "ORIENTATION IS LANDSCAPE");
                if (fragmentList == null) {
                    fragmentList = createFragment();
                    manager.beginTransaction()
                            .add(R.id.listContainer, fragmentList)
                            .commit();
                }
                if (fragmentDetail == null) {
                    fragmentDetail = createFragment();
                    manager.beginTransaction()
                            .add(R.id.detailFragmentContainer, fragmentDetail)
                            .commit();
                }
                text = "SCREEN_ORIENTATION_LANDSCAPE";
                break;
            case Surface.ROTATION_180:
                Log.d("*************", "ORIENTATION IS REVERSE PORTRAIT");
                if (fragment == null) {
                    fragment = createFragment();
                    manager.beginTransaction()
                            .add(R.id.fragmentContainer, fragment)
                            .commit();
                }
                text = "SCREEN_ORIENTATION_REVERSE_PORTRAIT";
                break;
            case Surface.ROTATION_270:
                Log.d("*************", "ORIENTATION IS REVERSE LANDSCAPE");
                if (fragmentList == null) {
                    fragmentList = createFragment();
                    manager.beginTransaction()
                            .add(R.id.listContainer, fragmentList)
                            .commit();
                }
                if (fragmentDetail == null) {
                    fragmentDetail = createFragment();
                    manager.beginTransaction()
                            .add(R.id.detailFragmentContainer, fragmentDetail)
                            .commit();
                }
                text = "SCREEN_ORIENTATION_REVERSE_LANDSCAPE";
                break;
            default:
                Log.d("*************", "ORIENTATION IS DEFAULT");
                if (fragment == null) {
                    fragment = createFragment();
                    manager.beginTransaction()
                            .add(R.id.fragmentContainer, fragment)
                            .commit();
                }
                text = "SCREEN_ORIENTATION_PORTRAIT, DEFAULT";
                break;
        }
        Toast toast = Toast.makeText(getApplicationContext(), text,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 10, 0);
        toast.show();
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
        orientationEventListener.enable();*/
    }

  /*  @Override
    protected void onDestroy() {
        super.onDestroy();
        orientationEventListener.disable();
    } */

}
