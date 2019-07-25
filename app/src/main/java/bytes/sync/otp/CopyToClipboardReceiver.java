package bytes.sync.otp;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class CopyToClipboardReceiver extends BroadcastReceiver {

    private static final String TAG = CopyToClipboardReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {

        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        String otp = intent.getStringExtra("OTP");
        if(clipboardManager != null) {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("otp", otp));
            Toast.makeText(context, R.string.otpCopiedToast, Toast.LENGTH_LONG).show();
            Log.d(TAG, "OTP copied: " + otp);
        }

    }
}
