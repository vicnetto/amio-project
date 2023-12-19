package xyz.vicnetto.amio_project.notification;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xyz.vicnetto.amio_project.GlobalConstant;
import xyz.vicnetto.amio_project.R;
import xyz.vicnetto.amio_project.mail.MailService;
import xyz.vicnetto.amio_project.sensor.SensorDataHolder;
import xyz.vicnetto.amio_project.sensor.SensorRequest;

public class NotificationService extends Service {

    int notification_id = 1;

    Pair<Integer, Integer> notificationTime = new Pair<>(19, 23);

    private final Timer timer = new Timer(true);

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        // Create the used notification channel.
        createNotificationChannel();

        final Handler handler = new Handler();
        TimerTask task = new TimerTask() {
            public void run() {
                handler.post(() -> {
                    updateNotificationTime();
                    // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NotificationService.this);

                    if (MailService.checkTime(notificationTime.first, notificationTime.second)) {
                        if (SensorDataHolder.getInstance().data != null) {
                            verifyIfAnySensorChangedState();
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 5 * 1000);
    }

    /**
     * Make a request to the API and verify if any sensor was turned on.
     */
    private void verifyIfAnySensorChangedState() {
        Call call = SensorRequest.getSensorInformation();

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // Not connected to VPN, should do nothing.
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                List<Boolean> oldSensorState = new ArrayList<>();

                // Add true if sensor is on, false otherwise.
                for (int i = 0; i < GlobalConstant.QUANTITY_OF_SENSORS; i++) {
                    float oldSensorValue = SensorDataHolder.getInstance().data.get(i).value;

                    oldSensorState.add(oldSensorValue >= GlobalConstant.SENSOR_THRESHOLD);
                }

                // Treat the new response and save it.
                SensorRequest.treatJSONResponseAndUpdateUI(response);

                // Verify if all the sensors that were off are still off.
                for (int i = 0; i < GlobalConstant.QUANTITY_OF_SENSORS; i++) {
                    SensorDataHolder.SensorInformation currentNewSensor =
                            SensorDataHolder.getInstance().data.get(i);

                    // In case the sensor is now on, should send a notification.
                    if (!oldSensorState.get(i) && currentNewSensor.value >= GlobalConstant.SENSOR_THRESHOLD) {
                        String title = currentNewSensor.mote + " is now on!";
                        String description = "The new value is " + currentNewSensor.value;
                        sendNotification(notification_id++, title, description);
                    }
                }
            }
        });
    }

    /**
     * Send a notification to the android phone.
     *
     * @param id          -> Id of the notification, should be unique.
     * @param title       -> Title of the notification.
     * @param description -> Description of the notification.
     */
    private void sendNotification(int id, String title, String description) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, GlobalConstant.NOTIFICATION_CHANNEL_NAME)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // In case use has done the rights to send notifications, it will send.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
            notificationManager.notify(id, builder.build());
        }

    }

    /**
     * Create a notification channel to send information about the application.
     */
    @SuppressLint("ObsoleteSdkInt")
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(GlobalConstant.NOTIFICATION_CHANNEL_NAME,
                    GlobalConstant.NOTIFICATION_CHANNEL_NAME, importance);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void updateNotificationTime() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Integer start = Integer.parseInt(preferences.getString("start_time_s", "19"));
        Integer end = Integer.parseInt(preferences.getString("end_time_s", "23"));

        notificationTime = new Pair<>(start, end);
    }
}