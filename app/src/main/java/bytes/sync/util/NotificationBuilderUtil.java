package bytes.sync.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import bytes.sync.otp.CopyToClipboardReceiver;
import bytes.sync.otp.MainActivity;
import bytes.sync.otp.R;


public class NotificationBuilderUtil {

    private static final String TAG = NotificationBuilderUtil.class.getName();

    private static final String packageName = "bytes.sync.otp";
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "Glance";
    private static final String NOTIFICATION_TITLE = "Glance";
    private static final String CONTENT_TEXT = "OTP Found";


    public void showNotification(Context context, String otp, String sender) {
        RemoteViews collapsedView = new RemoteViews(packageName, R.layout.notification_collapsed);
        RemoteViews expandedView = new RemoteViews(packageName, R.layout.notification_expanded);

        Intent clickIntent = new Intent(context, MainActivity.class);
        PendingIntent clickPendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0);

        Intent copyIntent = new Intent(context, CopyToClipboardReceiver.class);
        copyIntent.setAction("bytes.sync.otp:COPY_ACTION");
        copyIntent.putExtra("OTP", otp);
        PendingIntent copyPendingIntent = PendingIntent.getBroadcast(context, 0, copyIntent, 0);

        //Set otp
        String formattedOtp = getFormattedOTP(otp);
        Log.d(TAG, "Formatted OTP: " + formattedOtp);
        collapsedView.setTextViewText(R.id.otp_textView, formattedOtp);
        expandedView.setTextViewText(R.id.otp_textView, formattedOtp);

        //Set timestamp
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a", Locale.getDefault());
        String time = simpleDateFormat.format(calendar.getTime());
        collapsedView.setTextViewText(R.id.timestamp_textView, time);
        expandedView.setTextViewText(R.id.timestamp_textView, time);

        //Set sender in the big notification style
        expandedView.setTextViewText(R.id.sender_textView, "OTP from " + sender);

        //Set copy intent
        expandedView.setOnClickPendingIntent(R.id.copy_imageView, copyPendingIntent);
        collapsedView.setOnClickPendingIntent(R.id.copy_imageView, copyPendingIntent);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                .setCustomHeadsUpContentView(collapsedView)
                .setAutoCancel(true)
                .setContentIntent(clickPendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }


    private String getFormattedOTP(String otp) {
        if(otp.length() > 6) {
            //Add single space
            return OTPFormatter.addSpace(otp, 1);
        } else {
            //Add 2 spaces
            return OTPFormatter.addSpace(otp, 2);
        }
    }

}
