package edu.uco.mcamposcardoso.kittracker.activities;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.uco.mcamposcardoso.kittracker.R;
import edu.uco.mcamposcardoso.kittracker.fragments.AlunoDetailsDialogFragment;
import edu.uco.mcamposcardoso.kittracker.fragments.ManuallyRegisterKitFragment;
import edu.uco.mcamposcardoso.kittracker.fragments.SampleFragment;
import edu.uco.mcamposcardoso.kittracker.interfaces.AlunoConfirmationListener;
import edu.uco.mcamposcardoso.kittracker.interfaces.ManualRegistrationListener;
import edu.uco.mcamposcardoso.kittracker.types.ScanInformation;

public class ScannerActivity extends FragmentActivity implements SampleFragment.ScannerListener, AlunoConfirmationListener, ManualRegistrationListener {

    SampleFragment mScannerFragment;
    Boolean multipleRegistration = false;
    Button btnEntrada, btnSaida, btnCadastarKit, btnMultipleRegistration;
    DialogFragment alunoDetailsDialogFragment, manuallyRegisterKitDialogFragment;
    AlertDialog alertDialog;

    ArrayList<ScanInformation> scanInfoArray = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        btnEntrada = (Button) this.findViewById(R.id.btnEntrada);
        btnSaida = (Button) this.findViewById(R.id.btnSaida);
        btnCadastarKit = (Button) this.findViewById(R.id.btnKitCadastroManual);
        btnMultipleRegistration = (Button) this.findViewById(R.id.btnActivateMultipleRegistration);

        FragmentManager manager = getSupportFragmentManager();
        mScannerFragment = (SampleFragment) manager.findFragmentById(R.id.scanner_fragment);

        scanInfoArray.clear();

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

        btnCadastarKit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(scanInfoArray, "register");
            }
        });

        btnMultipleRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleRegistration = !multipleRegistration;
                if(multipleRegistration){
                    scanInfoArray.clear();
                    deactivate_multiple_registration();
                }
                else{
                    activate_multiple_registration();
                    if(!scanInfoArray.isEmpty()) {
                        showDialog(scanInfoArray, "aluno");
                    }
                }
            }
        });
    }

    private void activate_multiple_registration() {
        btnMultipleRegistration.setTextColor(ColorStateList.valueOf(Color.BLACK));
        btnMultipleRegistration.setBackgroundResource(android.R.drawable.btn_default);
        btnMultipleRegistration.setText("Iniciar Cadastro Múltiplo");
    }

    private void deactivate_multiple_registration() {
        btnMultipleRegistration.setTextColor(ColorStateList.valueOf(Color.WHITE));
        btnMultipleRegistration.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_state_off));
        btnMultipleRegistration.setText("Finalizar Cadastro Múltiplo");
    }

    @Override
    public void onItemScan(String scanResult) {
        if(StringToScanInformation(scanResult) != null) {
            if (multipleRegistration == true) {
                add_to_scan_array(StringToScanInformation(scanResult));
            }
            else{
                scanInfoArray.clear();
                add_to_scan_array(StringToScanInformation(scanResult));
                showDialog(scanInfoArray, "aluno");
            }
        }
        else{
            return;
        }
    }

    private void add_to_scan_array(ScanInformation scanResult) {
        if (scanInfoArray.isEmpty()) {
            scanInfoArray.add(scanResult);
        }
        else{
            //Toast.makeText(this, scanInfoArray.get(scanInfoArray.size() - 1).getFeed_type() + ";" + scanResult.getFeed_type(), Toast.LENGTH_LONG).show();
            if (scanInfoArray.get(scanInfoArray.size() - 1).getMatricula().equals(scanResult.getMatricula())){
                scanInfoArray.add(scanResult);
            }
            else{
                alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Erro");
                alertDialog.setMessage("Kit não pertence ao mesmo aluno!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }
    }

    @Override
    public void onAlunoConfirmation(String matricula) {
        int i;
     //   Toast.makeText(this, "scanInfoArray.size(): " + scanInfoArray.size(), Toast.LENGTH_LONG).show();
        for(i = 0; i < scanInfoArray.size(); i++) {
            mScannerFragment.registerScan(scanInfoArray.get(i).getKit(), matricula, scanInfoArray.get(i).getFeed_type());
        }
        scanInfoArray.clear();
    }

    @Override
    public void onManualRegistration(String scanResult) {
        if(StringToScanInformation(scanResult) != null) {
            if (multipleRegistration == true) {
                add_to_scan_array(StringToScanInformation(scanResult));
            }
            else{
                scanInfoArray.clear();
                add_to_scan_array(StringToScanInformation(scanResult));
                showDialog(scanInfoArray, "aluno");
            }
            //Toast.makeText(this, StringToScanInformation(scanResult).getMatricula() + ";" + StringToScanInformation(scanResult).getKit(), Toast.LENGTH_LONG).show();
        }
        else{
            return;
        }
    }

    private void showDialog(ArrayList<ScanInformation> scanInfoArray, String type) {
        DialogFragment currentDialog = (DialogFragment) getFragmentManager().findFragmentByTag("current_dialog");
        if(currentDialog != null){
            //return;
            currentDialog.dismissAllowingStateLoss();
        }

        if(type == "aluno") {
            alunoDetailsDialogFragment = AlunoDetailsDialogFragment.newInstance(scanInfoArray);
            alunoDetailsDialogFragment.show(getFragmentManager(), "current_dialog");
        }
        else {
            manuallyRegisterKitDialogFragment = ManuallyRegisterKitFragment.newInstance();
            manuallyRegisterKitDialogFragment.show(getFragmentManager(), "current_dialog");
        }
    }

    public ScanInformation StringToScanInformation(String data){

        //  "2012939548;17";
        ScanInformation scanInformation = null;

        try {

            String parts[] = data.split(";");
            String matricula = parts[0];
            String kit = parts[1];


            scanInformation = new ScanInformation(matricula.substring(0, matricula.length()),
                    kit.substring(0, kit.length()), mScannerFragment.getFeed_type());
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