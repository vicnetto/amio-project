package xyz.vicnetto.amio_project.ui;

import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import xyz.vicnetto.amio_project.R;
import xyz.vicnetto.amio_project.sensor.JSONSensorData;
import xyz.vicnetto.amio_project.sensor.SensorRequest;

public class MainView {

    private static final int QUANTITY_OF_SENSORS = 4;
    private static final int SENSOR_THRESHOLD = 250;

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

    /**
     * Associate the function to print all sensor information to the update button.
     *
     * @param sensorRequest -> Class that contains the function to make the request and print information.
     */
    public void configureUpdateButton(SensorRequest sensorRequest) {
        update.setOnClickListener(view -> sensorRequest.printSensorInformation(this));
    }

    /**
     * Update the UI according to data.
     *
     * @param JSONSensorData -> JSON returned from the request.
     */
    public void updateViewAccordingToData(JSONSensorData JSONSensorData) {
        for (int i = 0; i < QUANTITY_OF_SENSORS; i++) {
            xyz.vicnetto.amio_project.sensor.JSONSensorData.JSONSensor currentSensorInformation = JSONSensorData.data.get(i);
            SensorView currentSensorView = sensorView.get(i);

            // Update the name of the sensor.
            currentSensorView.getName().setText(String.valueOf(currentSensorInformation.mote));

            // In case the sensor value is more than a specific threshold, led is on.
            if (currentSensorInformation.value > SENSOR_THRESHOLD)
                currentSensorView.getLed().setImageResource(R.color.led_on);
            else
                currentSensorView.getLed().setImageResource(R.color.led_off);

            // Update the value of the sensor.
            currentSensorView.getValue().setText(String.valueOf(currentSensorInformation.value));
        }

        // Format the date to a more readable value, and then update the UI with the current time.
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
