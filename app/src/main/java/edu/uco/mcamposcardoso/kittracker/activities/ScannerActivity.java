package edu.uco.mcamposcardoso.kittracker.activities;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.uco.mcamposcardoso.kittracker.R;
import edu.uco.mcamposcardoso.kittracker.fragments.AlunoDetailsDialogFragment;
import edu.uco.mcamposcardoso.kittracker.fragments.SampleFragment;
import edu.uco.mcamposcardoso.kittracker.types.ScanInformation;

public class ScannerActivity extends FragmentActivity implements SampleFragment.ScannerListener, AlunoDetailsDialogFragment.AlunoConfirmationListener {

    SampleFragment mScannerFragment;
    TextView txtNomeAluno, txtTelefone, txtCurso, txtPeriodo, txtNomeItem;
    Button btnEntrada, btnSaida;
    DialogFragment alunoDetailsDialogFragment;
    AlertDialog alertDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

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
                mScannerFragment.setFeed_type("Saida");
            }
        });
    }

    @Override
    public void onItemScan(String scanResult) {
        if(StringToScanInformation(scanResult) != null) {
            showDialog(StringToScanInformation(scanResult));
        }
        else{
            return;
        }

    }

    @Override
    public void onAlunoConfirmation(String kit) {
        mScannerFragment.registerScan(kit);
    }

    private void showDialog(ScanInformation scan) {
        DialogFragment currentDialog = (DialogFragment) getFragmentManager().findFragmentByTag("aluno_dialog");
        if(currentDialog != null){
            currentDialog.dismissAllowingStateLoss();
        }

        alunoDetailsDialogFragment = AlunoDetailsDialogFragment.newInstance(scan);
        alunoDetailsDialogFragment.show(getFragmentManager(), "aluno_dialog");
    }

    public ScanInformation StringToScanInformation(String data){

        //  "2012939548;17";
        ScanInformation scanInformation = null;

        try {

            String parts[] = data.split(";");
            String matricula = parts[0];
            String kit = parts[1];


            scanInformation = new ScanInformation(matricula.substring(0, matricula.length()),
                    kit.substring(0, kit.length()));
        }
        catch(Exception e){
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Erro na etiqueta");
            alertDialog.setMessage("Tem certeza que este é um código de etiqueta?");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
          //  Toast.makeText(this, "Tem certeza que este é um código de etiqueta?", Toast.LENGTH_LONG).show();
        }

        return scanInformation;
    }
}