package xyz.vicnetto.amio_project.mail;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import java.time.DayOfWeek;
import java.time.LocalDate;

import android.widget.Toast;

import androidx.preference.PreferenceManager;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

import xyz.vicnetto.amio_project.GlobalConstant;
import xyz.vicnetto.amio_project.R;
import xyz.vicnetto.amio_project.sensor.SensorDataHolder;
import xyz.vicnetto.amio_project.setting.SettingsFragment;

public class MailService extends Service {

    private Timer timer;
    private SettingsFragment settingsFragment;
    public MailService() {
    }

    public void onCreate(){
        Log.d("MailService","Hello, mail service lancé");
        //this.verifyLights();
        sendMail();
    }


    public void onDestroy(){
        this.timer.cancel();
        Log.d("MailService","Hello, mail service arrêté");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Plan the verification of values of our lights
     */
    public void verifyLights(){
        this.timer= new Timer();
        final Handler handler = new Handler();
        TimerTask task = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        // to execute the action of verification TODO

                    }
                });
            }
        };
        timer.schedule(task, 0, GlobalConstant.SENSOR_UPDATE_INTERVAL_IN_MINUTES * 60 * 1000);
    }


    /**
     * This function allow us to send a mail with two given address TODO: compete this function and associate with a button
     */
    public void sendMail(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String addrDst = preferences.getString("addressMail", "wenjia.tang@telecomnancy.net");

        String message = preferences.getString("message", "Attention, une nouvelle lumière allumée!");

        if (addrDst != null && message!= null){
            JavaMailAPI javaMailAPI = new JavaMailAPI(this, addrDst , "Lumière à TN", message);
            javaMailAPI.execute();

            Log.d("mail2","send mail....2");
        }
        else {
            Log.d("mail1","send mail....echec");
        }


    }


}