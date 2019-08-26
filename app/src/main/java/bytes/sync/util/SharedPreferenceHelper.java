package bytes.sync.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {

    private static final String SHARED_PREF = "SmartNotificationSharedPref";
    private static final String SMART_NOTIFICATION_PREF = "SMART_NOTIFICATION_PREF";
    private static final String OTP_TTS = "OTP_TTS";
    private static final String DEVICE_LOCKED = "DEVICE_LOCKED";

    private static SharedPreferenceHelper instance = new SharedPreferenceHelper();
    private static SharedPreferences sharedPreferences;
    private SharedPreferenceHelper() {}

    public static SharedPreferenceHelper getInstance(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return instance;
    }

    public void setSmartNotificationEnabled(boolean isSmartNotificationEnabled) {
        sharedPreferences.edit().putBoolean(SMART_NOTIFICATION_PREF, isSmartNotificationEnabled).apply();
    }

    public boolean getSmartNotificationEnabled() {
        return sharedPreferences.getBoolean(SMART_NOTIFICATION_PREF, true);
    }

    public void setOtpTTS(boolean isOtpTTSEnabled) {
        sharedPreferences.edit().putBoolean(OTP_TTS, isOtpTTSEnabled).apply();
    }

    public boolean getOtpTTS() {
        return sharedPreferences.getBoolean(OTP_TTS, false);
    }

    public void setTTSDeviceUnlocked(boolean isDeviceUnlockedTTS) {
        sharedPreferences.edit().putBoolean(DEVICE_LOCKED, isDeviceUnlockedTTS).apply();
    }

    public boolean getTTSDeviceUnlocked() {
        return sharedPreferences.getBoolean(DEVICE_LOCKED, false);
    }

}
