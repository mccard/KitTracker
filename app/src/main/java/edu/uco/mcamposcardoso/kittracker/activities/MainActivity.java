package edu.uco.mcamposcardoso.kittracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.uco.mcamposcardoso.kittracker.R;

public class MainActivity extends AppCompatActivity {

    private Button btnShowLog, btnScannerFragmentTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShowLog = (Button) this.findViewById(R.id.btnShowLog);
        btnScannerFragmentTest = (Button) this.findViewById(R.id.btnScannerFragment);

        btnShowLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), LogActivity.class);

                startActivity(intent);
            }
        });

        btnScannerFragmentTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), ScannerActivity.class);

                startActivity(intent);
            }
        });
    }
}
