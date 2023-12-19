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

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

import xyz.vicnetto.amio_project.GlobalConstant;
import xyz.vicnetto.amio_project.R;
import xyz.vicnetto.amio_project.sensor.SensorDataHolder;
import xyz.vicnetto.amio_project.setting.SettingsFragment;

public class MailService extends Service{

    private Timer timer;
    private int startTimeS = 23;
    private int endTimeS =6;
    private int startTimeW =19;
    private int endTimeW = 23;
    private String addrDst;
    private String message;
    private SettingsFragment settingsFragment;
    public MailService() {
    }

    public void onCreate(){
        Log.d("MailService","Hello, mail service lancé");
        updateSetting();
        this.verifyLights();
        //sendMail();
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

                        updateSetting();
                        // to execute the action of verification TODO
                        if (checkDay()== true){
                            if (checkTime(startTimeS, endTimeS)) {
                                if (SensorDataHolder.getInstance().data != null) {
                                    sendMail();
                                }
                            }
                        }
                        else {
                            if (checkTime(startTimeW, endTimeW)) {
                                if (SensorDataHolder.getInstance().data != null) {
                                    sendMail();
                                }
                            }
                        }


                    }
                });
            }
        };
        timer.schedule(task, 0, GlobalConstant.SENSOR_UPDATE_INTERVAL_IN_MINUTES * 1000* 60);
    }


    /**
     * This function allow us to send a mail with two given address TODO: compete this function and associate with a button
     */
    public void sendMail(){

        if (addrDst != null && message!= null){
            JavaMailAPI javaMailAPI = new JavaMailAPI(this, addrDst , "Lumière à TN", message);
            javaMailAPI.execute();

            Log.d("mail2","send mail....2");
        }
        else {
            Log.d("mail1","send mail....echec");
        }


    }

    /**
     * To check if today is a workday
     * @return
     */
    private boolean checkDay(){
        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        if (dayOfWeek.getValue() >= DayOfWeek.MONDAY.getValue() &&
                dayOfWeek.getValue() <= DayOfWeek.FRIDAY.getValue()){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean checkTime(int startTime, int endTime) {
        if (endTime > startTime) {
            return LocalTime.now().isAfter(LocalTime.of(startTime, 0)) &&
                    LocalTime.now().isBefore(LocalTime.of(startTime, 0));
        } else {
            return LocalTime.now().isAfter(LocalTime.of(startTime, 0)) ||
                    LocalTime.now().isBefore(LocalTime.of(endTime, 0));
        }
    }

    private void updateSetting() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.addrDst = preferences.getString("addressMail", "wenjia.tang@telecomnancy.net");
        this.message = preferences.getString("message", "Attention, une nouvelle lumière allumée!");
        this.startTimeS = Integer.parseInt(preferences.getString("start_time_s","19")) ;
        this.endTimeS = Integer.parseInt(preferences.getString("end_time_s","23"));
        this.startTimeW = Integer.parseInt(preferences.getString("start_time_w","23"));
        this.endTimeW = Integer.parseInt(preferences.getString("end_time_w","6"));
    }


}