package edu.uco.mcamposcardoso.kittracker.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.welcu.android.zxingfragmentlib.BarCodeScannerFragment;

import edu.uco.mcamposcardoso.kittracker.R;
import edu.uco.mcamposcardoso.kittracker.fragments.SampleFragment;

public class ScannerActivity extends FragmentActivity implements SampleFragment.ScannerListener {

    LinearLayout layoutContent;
    BarCodeScannerFragment mScannerFragment;
    TextView txtNomeAluno, txtTelefone, txtCurso, txtPeriodo, txtNomeItem;
    ToggleButton btnEntrada, btnSaida;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        txtNomeAluno = (TextView) this.findViewById(R.id.txtNomeAluno);
        txtTelefone = (TextView) this.findViewById(R.id.txtTelefone);
        txtCurso = (TextView) this.findViewById(R.id.txtCurso);
        txtPeriodo = (TextView) this.findViewById(R.id.txtPeriodo);
        txtNomeItem = (TextView) this.findViewById(R.id.txtNomeItem);

        btnEntrada = (ToggleButton) this.findViewById(R.id.btnEntrada);
        btnSaida = (ToggleButton) this.findViewById(R.id.btnSaida);

        btnEntrada.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btnSaida.setChecked(false);
                }
            }
        });

        btnSaida.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    btnEntrada.setChecked(false);
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        mScannerFragment = (BarCodeScannerFragment) fm.findFragmentById(R.id.scanner_fragment);
    }

    @Override
    public void onItemScan(String nomeAluno, String telefone, String curso, String periodo, String nomeItem) {
        txtNomeAluno.setText(nomeAluno);
        txtCurso.setText(curso);
        txtNomeItem.setText(nomeItem);
        txtPeriodo.setText(periodo);
        txtTelefone.setText(telefone);
    }
}