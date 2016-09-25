package edu.uco.mcamposcardoso.kittracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LogActivity extends AppCompatActivity {

    ListView lstLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        lstLog = (ListView) this.findViewById(R.id.listLog);

        try {
            String qrcode = readSavedData().substring(1);

            System.out.println("oq tem no arquivo: " + qrcode);

            // o arquivo se parece com isso aqui
            //"!NOM:Matheus C. Cardoso;TEL:84988179424;CUR:Odontologia;PER:7;ITM:Caixa com protese!" +
            //  "NOM:Bruno C. Cardoso;TEL:84988179424;CUR:Odontologia;PER:9;ITM:Caixa com pano";

            List<Log> listLog = new ArrayList<Log>();

            //divide tudo em varios registros (records)
            String records[] = qrcode.split("!");

            // pra cada um, transforma no tipo Log e adiciona na listLog
            for (int i = 0; i < records.length; i++) {
                listLog.add(StringToLog(records[i]));
            }

            lstLog.setAdapter(new RowAdapter(getBaseContext(), listLog));

        }catch (Exception e){
            Toast.makeText(this, "Não há registros!", Toast.LENGTH_LONG).show();
        }

    }

    public Log StringToLog(String data){

        String parts[] = data.split(";");
        String nome = parts[0];
        String telefone = parts[1];
        String curso = parts[2];
        String periodo = parts[3];
        String item = parts[4];

        Log log = new Log(nome.substring(4, nome.length()), telefone.substring(4, telefone.length()),
                          curso.substring(4, curso.length()), periodo.substring(4, periodo.length()),
                          item.substring(4, item.length()));

        return log;
    }

    public String readSavedData ( ) {
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = openFileInput ( "settings.dat" ) ;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
