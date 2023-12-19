package xyz.vicnetto.amio_project;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import xyz.vicnetto.amio_project.broadcast.BroadcastReceiver;
import xyz.vicnetto.amio_project.mail.MailService;
import xyz.vicnetto.amio_project.notification.NotificationService;
import xyz.vicnetto.amio_project.setting.SettingsActivity;
import xyz.vicnetto.amio_project.ui.MainView;
import xyz.vicnetto.amio_project.ui.SensorView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load all elements from the UI.
        loadViewElements();

        // Load setting Fragment


        // Associate button with sensor update.
        MainView.getInstance().configureUpdateButton();

        // Associate button with setting activity.
        associateConfigurationButton();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotificationPermissions();
        }
        Intent intent = new Intent(this, NotificationService.class);
        super.startService(intent);

        Intent mailService = new Intent(this, MailService.class);
        startService(mailService);

        BroadcastReceiver broadcast = new BroadcastReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BOOT_COMPLETED);
        int receiverFlags = ContextCompat.RECEIVER_EXPORTED;
        ContextCompat.registerReceiver(this, broadcast, filter, receiverFlags);
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
        MainView.getInstance().setSensorShow(sensors);

        TextView time = findViewById(R.id.time);
        MainView.getInstance().setTime(time);

        Button update = findViewById(R.id.update);
        MainView.getInstance().setUpdate(update);

        Button configuration = findViewById(R.id.configuration);
        MainView.getInstance().setConfiguration(configuration);
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void checkNotificationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            // Random number, just to verify if the result was good.
            boolean isPostPermitted = ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.POST_NOTIFICATIONS);

            if (!isPostPermitted) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }

    /**
     * Associate the configuration button with setting activities
     */
    private void associateConfigurationButton(){
        MainView.getInstance().getConfiguration().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

}