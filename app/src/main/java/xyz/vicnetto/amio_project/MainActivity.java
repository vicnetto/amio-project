package xyz.vicnetto.amio_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xyz.vicnetto.amio_project.sensor.SensorRequest;
import xyz.vicnetto.amio_project.ui.MainView;
import xyz.vicnetto.amio_project.ui.SensorView;

public class MainActivity extends AppCompatActivity {

    private MainView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load all elements from the UI.
        loadViewElements();

        // Associate button with sensor update.
        mainView.configureUpdateButton(new SensorRequest(this));
    }

    /**
     * Load all elements from the UI. These elements are stored in the MainView class.
     */
    private void loadViewElements() {
        List<SensorView> sensors = new ArrayList<>();
        sensors.add(new SensorView(findViewById(R.id.name_1), findViewById(R.id.led_1), findViewById(R.id.value_1)));
        sensors.add(new SensorView(findViewById(R.id.name_2), findViewById(R.id.led_2), findViewById(R.id.value_2)));
        sensors.add(new SensorView(findViewById(R.id.name_3), findViewById(R.id.led_3), findViewById(R.id.value_3)));
        sensors.add(new SensorView(findViewById(R.id.name_4), findViewById(R.id.led_4), findViewById(R.id.value_4)));

        TextView time = findViewById(R.id.time);

        Button update = findViewById(R.id.update);
        Button configuration = findViewById(R.id.configuration);

        mainView = new MainView(sensors, time, update, configuration);
    }
}