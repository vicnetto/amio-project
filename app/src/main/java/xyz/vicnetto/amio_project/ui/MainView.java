package xyz.vicnetto.amio_project.ui;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import xyz.vicnetto.amio_project.R;
import xyz.vicnetto.amio_project.sensor.SensorDataHolder;
import xyz.vicnetto.amio_project.sensor.SensorRequest;

public class MainView {

    private static final int QUANTITY_OF_SENSORS = 4;
    private static final int SENSOR_THRESHOLD = 250;
    private static final int BUTTON_DEACTIVATION_TIME_IN_SECONDS = 5;

    private List<SensorView> sensorView;

    private TextView time;

    private Button update;

    private Button configuration;

    @SuppressLint("StaticFieldLeak")
    private static final MainView MAIN_VIEW = new MainView();

    // Get singleton with all MainView information.
    public static MainView getInstance() {
        return MAIN_VIEW;
    }

    /**
     * Make the configuration of the Update button. This button will call a function to make a request
     * and update the UI. Also, the button will be deactivated during a certain time to avoid spam.
     */
    public void configureUpdateButton() {
        // When button is clicked, disable to button (to avoid spam) and make a request.
        update.setOnClickListener((view) -> {
            update.setEnabled(false);
            SensorRequest.getAndUpdateSensorInformation();

            // After BUTTON_DEACTIVATION_TIME_IN_SECONDS, the button should be able to be clicked again.
            new Handler().postDelayed(() -> {
                update.setEnabled(true);
            },BUTTON_DEACTIVATION_TIME_IN_SECONDS * 1000);
        });
    }

    /**
     * Update the UI according to data.
     *
     * @param SensorDataHolder -> JSON returned from the request.
     */
    public void updateViewAccordingToData(SensorDataHolder SensorDataHolder) {
        for (int i = 0; i < QUANTITY_OF_SENSORS; i++) {
            xyz.vicnetto.amio_project.sensor.SensorDataHolder.SensorInformation currentSensorInformation = SensorDataHolder.data.get(i);
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

    public void setSensorShow(List<SensorView> sensorView) {
        this.sensorView = sensorView;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public void setConfiguration(Button configuration) {
        this.configuration = configuration;
    }

    public Button getConfiguration() {
        return configuration;
    }
}
