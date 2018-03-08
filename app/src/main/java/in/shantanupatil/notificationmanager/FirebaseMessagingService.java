package in.shantanupatil.notificationmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import in.shantanupatil.notificationmanager.Events.RetriveEvents.EventItemsList;
import in.shantanupatil.notificationmanager.Staff.RetriveStaff.NoticeActivity;
import in.shantanupatil.notificationmanager.TandP.RetriveInformation.TNP;

/**
 * Created by Shantanu on 1/31/2018.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();

        Log.d("MessageServiceTags", "" + title);

        try {
            if (title.startsWith("TNP")) {
                Log.d("MessageServiceTags", "TNP");
                Intent intent = new Intent(this, TNP.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setDefaults(Notification.DEFAULT_LIGHTS|Notification.BADGE_ICON_LARGE|Notification.DEFAULT_VIBRATE|Notification.FLAG_FOREGROUND_SERVICE|Notification.DEFAULT_SOUND)
                        .setBadgeIconType(R.drawable.icon)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setContentIntent(pi);

                int notiID = (int) System.currentTimeMillis();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(notiID, mBuilder.build());
            } else if (title.startsWith("Event")) {
                Log.d("MessageServiceTags", "Event");
                PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, EventItemsList.class), PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icon)
                        .setDefaults(Notification.DEFAULT_LIGHTS|Notification.BADGE_ICON_LARGE|Notification.DEFAULT_VIBRATE|Notification.FLAG_FOREGROUND_SERVICE|Notification.DEFAULT_SOUND)
                        .setBadgeIconType(R.drawable.icon)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setContentIntent(pi);

                int notiID = (int) System.currentTimeMillis();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(notiID, mBuilder.build());
            } else if (title.startsWith("Notice")) {
                Log.d("MessageServiceTags", "Notice");
                Intent intent = new Intent(this, NoticeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setDefaults(Notification.DEFAULT_LIGHTS|Notification.BADGE_ICON_LARGE|Notification.DEFAULT_VIBRATE|Notification.FLAG_FOREGROUND_SERVICE|Notification.DEFAULT_SOUND)
                        .setBadgeIconType(R.drawable.icon)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setContentIntent(pi);

                int notiID = (int) System.currentTimeMillis();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(notiID, mBuilder.build());
            }
        } catch (Exception e) {

         }

    }
}
