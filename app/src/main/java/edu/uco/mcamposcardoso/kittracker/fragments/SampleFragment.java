package edu.uco.mcamposcardoso.kittracker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.android.BeepManager;
import com.welcu.android.zxingfragmentlib.BarCodeScannerFragment;

public class SampleFragment extends BarCodeScannerFragment {

    private ScannerListener mListener = null;
    private static final long DELAY = 2000; // 2 seconds
    int scan_count;

    public interface ScannerListener {
        public void onItemScan(String nomeAluno, String telefone, String curso, String periodo, String nomeItem);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final BeepManager beepManager = new BeepManager(getActivity());
        beepManager.updatePrefs();

        scan_count = 0;

        this.setmCallBack(new IResultCallback() {
            private long lastTimestamp = 0;
            @Override
            public void result(Result lastResult) {
                if((System.currentTimeMillis() - lastTimestamp) < DELAY) { // 2s delay (Se a diferença do scaneamento atual pro ´ultimo escaneamento for menor do que 2 segundos => aplica deplay (return)
                    Log.d("Delay applied", String.valueOf(System.currentTimeMillis())
                        + ", " + String.valueOf(lastTimestamp) + ", " + DELAY);
                    return;
                }

                beepManager.playBeepSoundAndVibrate();

                // Space reserved to call mListener.OnItemScan() in order to trigger the activity associated with this fragment

                Toast.makeText(getActivity(), scan_count++ + " Scan: " + lastResult.toString(), Toast.LENGTH_SHORT).show();

                lastTimestamp = System.currentTimeMillis();
            }
        });

        try {
            mListener = (ScannerListener) getActivity(); //getActivity()
            // returns the Activity this fragment is currently associated with.
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement ScannerListener");
        }
    }

    public SampleFragment() {

    }

    @Override
    public int getRequestedCameraId() {
        return -1; // set to 1 to use the front camera (won't work if the device doesn't have one, it is up to you to handle this method ;)
    }
}