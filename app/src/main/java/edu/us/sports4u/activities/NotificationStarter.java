package edu.us.sports4u.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import edu.us.sports4u.R;

/**
 * Created by shybovycha on 30/08/15.
 */
public class NotificationStarter extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent sIntent)
    {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, MainTabActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // this is it, we'll build the notification!
        // in the addAction method, if you don't want any icon, just set the first param to 0
        Notification mNotification = new Notification.Builder(context)

                .setContentTitle("New Post!")
                .setContentText("Here's an awesome update for you!")
                .setSmallIcon(R.drawable.running)
                .setContentIntent(pIntent)
                .setSound(soundUri)

                .addAction(R.drawable.running, "View", pIntent)
                .addAction(0, "Remind", pIntent)

                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // If you want to hide the notification after it was selected, do the code below
        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, mNotification);
    }
}
