package xyz.vicnetto.amio_project.broadcast;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import xyz.vicnetto.amio_project.MainActivity;
import xyz.vicnetto.amio_project.notification.NotificationService;

public class BroadcastReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // In case of boot, the application will be executed.
        Intent activityIntent = new Intent(context, NotificationService.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(activityIntent);
    }
}
