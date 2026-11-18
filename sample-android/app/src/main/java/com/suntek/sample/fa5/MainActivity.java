package com.suntek.sample.fa5;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.uniwin.system.manager.UniwinManager;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UniwinManager manager = UniwinManager.getInstance(getApplicationContext());
        String model = manager != null ? manager.getDeviceModel() : "N/A";
        String fw = manager != null ? manager.getFirmwareVersion() : "N/A";
        String sn = manager != null ? manager.getSerialNumber() : "N/A";
        TextView view = new TextView(this);
        view.setText("SUNTEK FA5 Ready\nModel: " + model + "\nFW: " + fw + "\nSN: " + sn);
        setContentView(view);
    }
}
