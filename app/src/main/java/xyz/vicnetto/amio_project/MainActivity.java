package xyz.vicnetto.amio_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.vicnetto.amio_project.mail.JavaMailAPI;
import xyz.vicnetto.amio_project.sensor.SensorRequest;
import xyz.vicnetto.amio_project.setting.SettingsActivity;
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
        // Associate button with setting activity.
        associateConfigurationButton();

        // Load main service
        Intent mainService = new Intent(this, MainService.class);
        startService(mainService);


        // Send mail service
        sendMail("armand.bouveron@telecomnancy.net");

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
     * Associate the configuration button with setting activities
     */
    private void associateConfigurationButton(){
        this.mainView.getConfiguration().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * This function allow us to send a mail with two given address TODO: compete this function and associate with a button
     * @param addrDst the e-mail address that we want to send to
     */
    private void sendMail(String addrDst){

        JavaMailAPI javaMailAPI = new JavaMailAPI(this, addrDst , "test", "testttttttt");

        javaMailAPI.execute();
        Log.d("mail2","send mail....2");

    }
}