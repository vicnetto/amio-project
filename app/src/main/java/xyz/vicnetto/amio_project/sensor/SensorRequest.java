package xyz.vicnetto.amio_project.sensor;

import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SensorRequest {

    public static final MediaType JSON = MediaType.get("application/json");

    public static final String BASE_URL = "http://iotlab.telecomnancy.eu:8080/iotlab/rest/data/1/light1/last";

    OkHttpClient client = new OkHttpClient();

    public void printSensorInformation() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            public void onResponse(@NonNull Call call, @NonNull Response response)
                    throws IOException {
                Log.d("MainActivity", "Success! Code: " + response.code());

                ObjectMapper objectMapper = new ObjectMapper();
                SensorInformation sensorInformation = objectMapper.readValue(response.body().string(), SensorInformation.class);
            }

            public void onFailure(Call call, IOException e) {
                Log.d("MainActivity", "Failure!");
            }
        });
    }
}
