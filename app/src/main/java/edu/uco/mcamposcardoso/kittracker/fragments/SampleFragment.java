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
    private static final long DELAY = 2000; // 2 seconds
    Aluno aluno1, aluno2;
    int k, uy;
    ArrayList<Aluno> alunos = new ArrayList();
    ArrayList<Item> items = new ArrayList();
    Item item1, item2;

    public interface ScannerListener {
        public void onItemScan(String nomeAluno, String telefone, String curso, String periodo, String nomeItem);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aluno1 = new Aluno("Ronaldo C. Cardoso", "2012939548", "Engenharia de Software", "8", "98817-9424");
        aluno2 = new Aluno("Bruno C. Cardoso", "2013968305", "Odontologia", "2", "98817-9424");

        item1 = new Item("Caixa de prótese", "20160001", "2012939548");
        item2 = new Item("Caixa de aparelho", "20160002", "2013968305");

        alunos.add(aluno1);
        alunos.add(aluno2);

        items.add(item1);
        items.add(item2);

        final BeepManager beepManager = new BeepManager(getActivity());
        beepManager.updatePrefs();

        k = 0;
        uy = 0;

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

                for(int i = 0; i < items.size(); i++) {
                    Log.d("calmae1", items.get(i).getNumeroItem().toString() + " é igual a " + lastResult.toString() + "???");

                    if(items.get(i).getNumeroItem().toString().equals(lastResult.toString())){ //se no array items tem algum item com o numero scaneado
                        Log.d("calmae2", "olha eu aqui");
                        for(int j = 0; j < alunos.size(); j++) {
                            if(alunos.get(j).getMatriculaAluno().equals(items.get(i).getMatriculaAluno())){
                                mListener.onItemScan(alunos.get(j).getNomeAluno(), alunos.get(j).getTelefone(),
                                        alunos.get(j).getCurso(), alunos.get(j).getPeriodo(), items.get(i).getNomeItem());
                            }
                        }
                    }
                }

               // mListener.onItemScan("RRRRRRRRonaldo!!");

                Toast.makeText(getActivity(), k++ + " Scan: " + lastResult.toString(), Toast.LENGTH_SHORT).show();

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