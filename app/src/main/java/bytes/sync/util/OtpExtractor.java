package bytes.sync.util;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtpExtractor {

    private static final String TAG = OtpExtractor.class.getName();
    private static final String OTP_PATTERN = "([0-9]{4,8})";
    private static final Pattern pattern = Pattern.compile(OTP_PATTERN);

    public static String extractOTP(String msg) {
        String otp = "";

        Matcher matcher = pattern.matcher(msg);
        if(matcher.find()) {
            if (matcher.groupCount() > 0) {
                Log.d(TAG, "Pattern matched with group count: " + matcher.groupCount());
                otp = matcher.group(0);
            }
        }

        return otp;
    }

}
