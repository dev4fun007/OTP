package bytes.sync.otp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import bytes.sync.util.Constants;
import bytes.sync.util.NotificationBuilderUtil;
import bytes.sync.util.OtpExtractor;
import bytes.sync.util.SharedPreferenceHelper;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = SmsReceiver.class.getName();
    private final SmsManager sms = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {

        if(!SharedPreferenceHelper.getInstance(context).getSmartNotificationEnabled())
            return;

        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                String sender = "";
                StringBuilder sms = new StringBuilder();
                for (Object o : pdus) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) o);
                    String senderNum = currentMessage.getDisplayOriginatingAddress();
                    sms.append(currentMessage.getDisplayMessageBody());
                    sender = senderNum;
                    Log.d(TAG, "senderNum: " + senderNum + "; message: " + sms);

                }

                String otp = OtpExtractor.extractOTP(sms.toString());
                NotificationBuilderUtil notificationBuilderUtil = new NotificationBuilderUtil();
                notificationBuilderUtil.showNotification(context, otp, sender);
                Log.d(TAG,"OTP: " + otp);

                if(SharedPreferenceHelper.getInstance(context).getOtpTTS()) {
                    Intent speakIntent = new Intent(context, SpeakOTPService.class);
                    speakIntent.putExtra(Constants.OTP_EXTRA, otp);
                    context.startService(speakIntent);
                }
            }
        }

    }
}
