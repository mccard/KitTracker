package edu.uco.mcamposcardoso.kittracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btnScannerMode, btnShowLog;
    IntentIntegrator integrator;
    String FILENAME = "log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScannerMode = (Button) this.findViewById(R.id.btnScannerMode);
        btnShowLog = (Button) this.findViewById(R.id.btnShowLog);

        final Activity activity = this;

        integrator = new IntentIntegrator(activity);

        btnScannerMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan(integrator);
            }
        });

        btnShowLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), LogActivity.class);

                startActivity(intent);
            }
        });
    }

    public void scan(IntentIntegrator integrator){
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                //get current date time with Date()
                Date date = new Date();

                    writeData(readSavedData() + "!" + result.getContents()); //next -> add date

                scan(integrator);
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void writeData ( String data ) {
        try {
            FileOutputStream fOut = openFileOutput ( "settings.dat" , MODE_WORLD_READABLE ) ;
            OutputStreamWriter osw = new OutputStreamWriter ( fOut ) ;
            osw.write(data) ;
            osw.flush() ;
            osw.close() ;
        } catch ( Exception e ) {
            e.printStackTrace ( ) ;
        }
    }

    public String readSavedData ( ) {
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = openFileInput("settings.dat") ;
            InputStreamReader isr = new InputStreamReader ( fIn ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                datax.append(readString);
                readString = buffreader.readLine ( ) ;
            }

            isr.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }
        return datax.toString() ;
    }
}
