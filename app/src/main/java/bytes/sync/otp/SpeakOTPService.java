package bytes.sync.otp;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import bytes.sync.util.Constants;
import bytes.sync.util.OTPFormatter;
import bytes.sync.util.SharedPreferenceHelper;

public class SpeakOTPService extends Service implements TextToSpeech.OnInitListener {

    private static final String TAG = SpeakOTPService.class.getName();

    private TextToSpeech textToSpeech;
    private String formattedOTP;
    private KeyguardManager keyguardManager;


    public SpeakOTPService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        textToSpeech = new TextToSpeech(getApplicationContext(), this);
        keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (SharedPreferenceHelper.getInstance(getApplicationContext()).getTTSDeviceUnlocked()) {
            if(keyguardManager != null && keyguardManager.isDeviceLocked()) {
                Log.d(TAG, "Device is locked - do not speak");
                return super.onStartCommand(intent, flags, startId);
            }
        }
        String otp = intent.getStringExtra(Constants.OTP_EXTRA);
        formattedOTP = OTPFormatter.addSpace(otp, 2);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onInit(int status) {
        Log.d(TAG, "TTS initialized - status code: " + status);

        if(status == TextToSpeech.SUCCESS) {
            textToSpeech.speak(formattedOTP, TextToSpeech.QUEUE_FLUSH, null, "OTP_UTTERANCE_ID");
            Log.d(TAG, "TTS activated");
        }
    }
}
