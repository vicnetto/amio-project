package xyz.vicnetto.amio_project.sensor;

import android.util.Log;
import android.widget.Toast;

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

    private final MainActivity mainActivity;

    public static final String BASE_URL = "http://iotlab.telecomnancy.eu:8080/iotlab/rest/data/1/light1/last";

    /**
     * Constructor of SensorRequest
     *
     * @param mainActivity -> The context is needed to use the Toast library, to make popup's.
     */
    public SensorRequest(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * To get the sensor information, we should call this static function. To treat the result:
     * call.enqueue(new Callback() {...})
     *
     * In this case, we will have two functions inside the Callback. On is in case of success and the
     * other one in case it fails.
     *
     * @return
     */
    public static Call getSensorInformation() {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();

        return new OkHttpClient().newCall(request);
    }

    /**
     * This function make a new request to the URL, getting the status of the sensors.
     * Successful request: UI will be updated.
     * Failure request: A popup will be shown saying about the error.
     *
     * @param mainView -> Contains all elements of the UI.
     */
    public void printSensorInformation(MainView mainView) {
        // After the call specification, it will make the call.
        Call call = getSensorInformation();
        Toast.makeText(mainActivity, "Request sent!", Toast.LENGTH_SHORT).show();

        call.enqueue(new Callback() {
            public void onResponse(@NonNull Call call, @NonNull Response response)
                    throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    // Convert JSON data to class and update the UI.
                    convertDataAndUpdateUI(mainView, response);
                }
            }

            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // Failure on the process, informing the user using Toast.
                failedToSendRequest();
            }
        });
    }

    /**
     * In case the request was a success, this function converts the JSON to a class and then
     * updates the UI.
     *
     * @param mainView -> The main view with all the UI components.
     * @param response -> The JSON response from the request.
     * @throws IOException -> ObjectMapper requests a IOException.
     */
    private void convertDataAndUpdateUI(MainView mainView, Response response) throws IOException {
        // If response body is not null, it will map the JSON to a class.
        ObjectMapper objectMapper = new ObjectMapper();
        JSONSensorData JSONSensorData = objectMapper.readValue(response.body().string(), JSONSensorData.class);

        // After converting from JSON to a class, now the UI can be updated.
        mainView.updateViewAccordingToData(JSONSensorData);
    }

    /**
     * In case it wasn't possible to make the request, a error message will be shown with Toast.
     */
    private void failedToSendRequest() {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(mainActivity,
                        "Request failed! Are you connected to TN EDUROAM or VPN?"
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
