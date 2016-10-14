package edu.uco.mcamposcardoso.kittracker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.android.BeepManager;
import com.welcu.android.zxingfragmentlib.BarCodeScannerFragment;

import java.util.ArrayList;

import edu.uco.mcamposcardoso.kittracker.types.Aluno;
import edu.uco.mcamposcardoso.kittracker.types.Item;

public class SampleFragment extends BarCodeScannerFragment {

    private ScannerListener mListener = null;
    int i;
    private static final long DELAY = 2000; // 2 seconds
    Aluno aluno1, aluno2;
    ArrayList<Aluno> alunos = new ArrayList();
    ArrayList<Item> items = new ArrayList();
    Item item1, item2;

    public interface ScannerListener {
        public void onItemScan(String nomeAluno);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aluno1 = new Aluno("Matheus C. Cardoso", "2012939548", "Engenharia de Software", "8", "98817-9424");
        aluno2 = new Aluno("Bruno C. Cardoso", "2013968305", "Odontologia", "2", "98817-9424");

        item1 = new Item("Caixa de prótese", "20160001", "2012939548");
        item2 = new Item("Caixa de aparelho", "20160002", "2014939548");

        alunos.add(aluno1);
        alunos.add(aluno2);

        items.add(item1);
        items.add(item2);

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

                for(i = 0; i < items.size(); i++) {

                    if(items.get(i).getNumeroItem().toString() == lastResult.toString()){ //se

                    }
                }

                mListener.onItemScan("RRRRRRRRonaldo!!");

                Toast.makeText(getActivity(), i++ + " Scan: " + lastResult.toString(), Toast.LENGTH_SHORT).show();

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