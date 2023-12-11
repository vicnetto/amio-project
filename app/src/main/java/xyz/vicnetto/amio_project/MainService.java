package xyz.vicnetto.amio_project;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainService extends Service {

    private Timer timer;
    public MainService() {
    }

    public void onCreate(){
        Log.d("MainService","Hello, service lancé");
        this.verifyLights();
    }

    public void onDestroy(){
        this.timer.cancel();
        Log.d("MainService","Hello, service stoppé");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Plan the verification of values of our lights
     */
    private void verifyLights(){
        this.timer= new Timer();
        final Handler handler = new Handler();
        TimerTask task = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        // to execute the action of verification TODO
                        Toast.makeText(MainService.this, "verify!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        timer.schedule(task, 0, 5000);
    }
}