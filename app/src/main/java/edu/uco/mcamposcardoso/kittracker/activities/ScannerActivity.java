package edu.uco.mcamposcardoso.kittracker.activities;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.uco.mcamposcardoso.kittracker.R;
import edu.uco.mcamposcardoso.kittracker.fragments.SampleFragment;

public class ScannerActivity extends FragmentActivity implements SampleFragment.ScannerListener {

    SampleFragment mScannerFragment;
    TextView txtNomeAluno, txtTelefone, txtCurso, txtPeriodo, txtNomeItem;
    Button btnEntrada, btnSaida;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

      //  txtNomeAluno = (TextView) this.findViewById(R.id.txtNomeAluno);
        txtTelefone = (TextView) this.findViewById(R.id.txtTelefone);
        txtCurso = (TextView) this.findViewById(R.id.txtCurso);
        txtPeriodo = (TextView) this.findViewById(R.id.txtPeriodo);
     //   txtNomeItem = (TextView) this.findViewById(R.id.txtNomeItem);
        btnEntrada = (Button) this.findViewById(R.id.btnEntrada);
        btnSaida = (Button) this.findViewById(R.id.btnSaida);

        FragmentManager manager = getSupportFragmentManager();
        mScannerFragment = (SampleFragment) manager.findFragmentById(R.id.scanner_fragment);

        btnEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaida.setBackgroundResource(android.R.drawable.btn_default);
                btnSaida.setTextColor(ColorStateList.valueOf(Color.BLACK));
                btnEntrada.setTextColor(ColorStateList.valueOf(Color.WHITE));
                btnEntrada.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_state_on));
                mScannerFragment.setFeed_type("Entrada");
            }
        });

        btnSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEntrada.setBackgroundResource(android.R.drawable.btn_default);
                btnEntrada.setTextColor(ColorStateList.valueOf(Color.BLACK));
                btnSaida.setTextColor(ColorStateList.valueOf(Color.WHITE));
                btnSaida.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_state_off));
                mScannerFragment.setFeed_type("Saída");
            }
        });
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