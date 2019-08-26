package bytes.sync.otp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.widget.CompoundButton;

import bytes.sync.util.SharedPreferenceHelper;

public class HomeActivity extends AppCompatActivity {

    SwitchCompat smartNotificationSwitch;
    SwitchCompat otpTTSSwitch;
    SwitchCompat ttsDeviceUnlocked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        smartNotificationSwitch = findViewById(R.id.smartNotificationSwitch);
        smartNotificationSwitch.setChecked(SharedPreferenceHelper.getInstance(this).getSmartNotificationEnabled());
        smartNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferenceHelper.getInstance(HomeActivity.this).setSmartNotificationEnabled(b);
            }
        });

        otpTTSSwitch = findViewById(R.id.otpTTSSwitch);
        otpTTSSwitch.setChecked(SharedPreferenceHelper.getInstance(this).getOtpTTS());
        otpTTSSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferenceHelper.getInstance(HomeActivity.this).setOtpTTS(b);
            }
        });

        ttsDeviceUnlocked = findViewById(R.id.ttsDeviceUnlocked_switch);
        ttsDeviceUnlocked.setChecked(SharedPreferenceHelper.getInstance(this).getTTSDeviceUnlocked());
        ttsDeviceUnlocked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferenceHelper.getInstance(HomeActivity.this).setTTSDeviceUnlocked(b);
            }
        });
    }
}
