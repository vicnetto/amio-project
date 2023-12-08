package xyz.vicnetto.amio_project.sensor;

import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xyz.vicnetto.amio_project.MainActivity;
import xyz.vicnetto.amio_project.ui.MainView;

public class SensorRequest {

    private MainActivity mainActivity;

    public static final String BASE_URL = "http://iotlab.telecomnancy.eu:8080/iotlab/rest/data/1/light1/last";

    OkHttpClient client = new OkHttpClient();

    /**
     * Constructor of SensorRequest
     *
     * @param mainActivity -> The context is needed to use the Toast library, to make popup's.
     */
    public SensorRequest(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * This function make a new request to the URL, getting the status of the sensors.
     * Successful request: UI will be updated.
     * Failure request: A popup will be shown saying about the error.
     *
     * @param mainView -> Contains all elements of the UI.
     */
    public void printSensorInformation(MainView mainView) {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();

        // After the call specification, it will make the call.
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            public void onResponse(@NonNull Call call, @NonNull Response response)
                    throws IOException {
                if (response.isSuccessful()) {
                    Log.d("MainActivity", "Success! Code: " + response.code());

                    if (response.body() != null) {
                        // If response body is not null, it will map the JSON to a class.
                        ObjectMapper objectMapper = new ObjectMapper();
                        JSONSensorData JSONSensorData = objectMapper.readValue(response.body().string(), JSONSensorData.class);

                        // After converting from JSON to a class, now the UI can be updated.
                        mainView.updateViewAccordingToData(JSONSensorData);
                    }
                }
            }

            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // Failure on the process.
                // TODO: toast with error message.
                Log.d("MainActivity", "Failure!");
            }
        });
    }
}
