package bytes.sync.otp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import static bytes.sync.otp.App.CHANNEL_ID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final int SMS_PERMISSION_CODE = 111;

    Button button;

    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification(view);
            }
        });

        notificationManager = NotificationManagerCompat.from(this);

    }

    @Override
    public void onResume() {
        super.onResume();

        if(!isSmsPermissionGranted()) {
            requestReadAndSendSmsPermission();
        }
    }


    public boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request runtime SMS permission
     */
    private void requestReadAndSendSmsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, SMS_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == SMS_PERMISSION_CODE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // SMS related task you need to do.
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }


    public void showNotification(View v) {
        RemoteViews collapsedView = new RemoteViews(getPackageName(),
                R.layout.notification_collapsed);
        RemoteViews expandedView = new RemoteViews(getPackageName(),
                R.layout.notification_expanded);

        Intent clickIntent = new Intent(this, MainActivity.class);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this,
                0, clickIntent, 0);

        //collapsedView.setTextViewText(R.id.text_view_collapsed_1, "Hello World!");

        //expandedView.setImageViewResource(R.id.image_view_expanded, R.drawable.ic_launcher_foreground);
        //expandedView.setOnClickPendingIntent(R.id.image_view_expanded, clickPendingIntent);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                .setCustomHeadsUpContentView(collapsedView)
                //.setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .build();

        notificationManager.notify(1, notification);
    }

}
