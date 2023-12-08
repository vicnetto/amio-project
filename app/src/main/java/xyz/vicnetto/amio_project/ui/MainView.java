package xyz.vicnetto.amio_project.ui;

import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import xyz.vicnetto.amio_project.R;
import xyz.vicnetto.amio_project.sensor.JSONSensorData;
import xyz.vicnetto.amio_project.sensor.SensorRequest;

public class MainView {

    private static final int QUANTITY_OF_SENSORS = 4;

    private List<SensorView> sensorView;

    private TextView time;

    private Button update;

    private Button configuration;

    public MainView(List<SensorView> sensorView, TextView time, Button update, Button configuration) {
        this.sensorView = sensorView;
        this.time = time;
        this.update = update;
        this.configuration = configuration;
    }

    public void configureUpdateButton(SensorRequest sensorRequest) {
        update.setOnClickListener(view -> sensorRequest.printSensorInformation(this));
    }

    public void updateViewAccordingToData(JSONSensorData JSONSensorData) {
        for (int i = 0; i < QUANTITY_OF_SENSORS; i++) {
            xyz.vicnetto.amio_project.sensor.JSONSensorData.JSONSensor currentSensorInformation = JSONSensorData.data.get(i);
            SensorView currentSensorView = sensorView.get(i);

            currentSensorView.getName().setText(String.valueOf(currentSensorInformation.mote));

            if (currentSensorInformation.value > 250)
                currentSensorView.getLed().setImageResource(R.color.led_on);

            currentSensorView.getValue().setText(String.valueOf(currentSensorInformation.value));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        time.setText(formatter.format(LocalDateTime.now()));
    }

    public List<SensorView> getSensorShow() {
        return sensorView;
    }

    public void setSensorShow(List<SensorView> sensorView) {
        this.sensorView = sensorView;
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public Button getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Button configuration) {
        this.configuration = configuration;
    }

}
