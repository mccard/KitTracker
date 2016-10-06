package edu.uco.mcamposcardoso.kittracker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.android.BeepManager;
import com.welcu.android.zxingfragmentlib.BarCodeScannerFragment;

public class SampleFragment extends BarCodeScannerFragment {

    int i;
    private static final long DELAY = 2000; // 2 seconds

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final BeepManager beepManager = new BeepManager(getActivity());
        beepManager.updatePrefs();

        i = 0;

        this.setmCallBack(new IResultCallback() {
            private long lastTimestamp = 0;
            @Override
            public void result(Result lastResult) {
                if((System.currentTimeMillis() - lastTimestamp) < DELAY) { // 2s delay
                Log.d("Delay applied", String.valueOf(System.currentTimeMillis())
                        + ", " + String.valueOf(lastTimestamp) + ", " + DELAY);

                    return;
                }

                beepManager.playBeepSoundAndVibrate();

                Toast.makeText(getActivity(), i++ + " Scan: " + lastResult.toString(), Toast.LENGTH_SHORT).show();

                lastTimestamp = System.currentTimeMillis();
            }
        });
    }

    public SampleFragment() {

    }

    @Override
    public int getRequestedCameraId() {
        return -1; // set to 1 to use the front camera (won't work if the device doesn't have one, it is up to you to handle this method ;)
    }
}