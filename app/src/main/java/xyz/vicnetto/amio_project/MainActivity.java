package xyz.vicnetto.amio_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import xyz.vicnetto.amio_project.mail.MailTool;
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

        // Load main service
        Intent mainService = new Intent(this, MainService.class);
        startService(mainService);

        //getSupportFragmentManager().beginTransaction()
        //.replace(R.id.setting_container, new SettingsFragment())
        //        .commit();

        // Send mail service
        //sendMail(" "," ");

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

    /**
     * This function allow us to send a mail with two given address TODO: compete this function and associate with a button
     * @param addrDst the e-mail address that we want to send to
     * @param addrCC the e-mail address that we want to add to CC
     */
    private void sendMail(String addrDst, String addrCC){
        MailTool mailTool = new MailTool();
        Intent mailService = mailTool.sendMailIntent(addrDst,addrCC);

        try {
            startActivity(Intent.createChooser(mailService, "Send mail..."));
            finish();
            Log.i("Mail service", "Finished sending email...");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}