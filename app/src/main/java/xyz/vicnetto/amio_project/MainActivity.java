package xyz.vicnetto.amio_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import xyz.vicnetto.amio_project.sensor.SensorRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorRequest sensorRequest = new SensorRequest();
        try {
            sensorRequest.printSensorInformation();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}