package xyz.vicnetto.amio_project.broadcast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import xyz.vicnetto.amio_project.notification.NotificationService;

public class BroadcastReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

        if (preferences.getBoolean("startup", false)) {
            // In case of boot, the application will be executed.
            Intent activityIntent = new Intent(context, NotificationService.class);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(activityIntent);
        }
    }
}
