package edu.uco.mcamposcardoso.kittracker.fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.android.BeepManager;
import com.welcu.android.zxingfragmentlib.BarCodeScannerFragment;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import edu.uco.mcamposcardoso.kittracker.types.ApiResponse;
import edu.uco.mcamposcardoso.kittracker.types.CurrentUser;
import edu.uco.mcamposcardoso.kittracker.types.KitTypeArray;

public class SampleFragment extends BarCodeScannerFragment {

    private ScannerListener mListener = null;
    private static final long DELAY = 2000; // 2 seconds
    int scan_count;
    KitTypeArray ktp = new KitTypeArray();
    private String alert_text;
    ResponseEntity<ApiResponse> response;
    HttpEntity<MultiValueMap<String, Object>> request;
    HttpHeaders headers;
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    HttpAuthentication http;
    RestTemplate restTemplate;
    AlertDialog alertDialog;

    public interface ScannerListener {
        public void onItemScan(String scanResult);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alertDialog = new AlertDialog.Builder(getActivity()).create();

        final BeepManager beepManager = new BeepManager(getActivity());
        beepManager.updatePrefs();

        scan_count = 0;
        alert_text = "";

        this.setmCallBack(new IResultCallback() {
            private long lastTimestamp = 0;

            @Override
            public void result(Result lastResult) {
                if ((System.currentTimeMillis() - lastTimestamp) < DELAY) { // 2s delay (Se a diferença do scaneamento atual pro ´ultimo escaneamento for menor do que 2 segundos => aplica deplay (return)
                    Log.d("Delay applied", String.valueOf(System.currentTimeMillis())
                            + ", " + String.valueOf(lastTimestamp) + ", " + DELAY);
                    return;
                }

                beepManager.playBeepSoundAndVibrate();

                mListener.onItemScan(lastResult.toString());

                Log.d("Scanned code", lastResult.toString());

                //Toast.makeText(getActivity(), scan_count++ + " Scan: " + lastResult.toString(), Toast.LENGTH_SHORT).show();

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

    public void registerScan(String kit_id, String matricula, String feedType){
        new HttpRequestTask().execute(feedType, kit_id, matricula);
    }

    private class HttpRequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(final String... params) {
            try {
                final String url = "https://odontokits.herokuapp.com/feeds.json";
                restTemplate = new RestTemplate();

                body.clear();
                body.add("tipo", params[0]);
                body.add("kit_type_id", params[1]);
                body.add("matricula", params[2]);

                headers = new HttpHeaders();

                http = new HttpAuthentication() {
                    @Override
                    public String getHeaderValue() {
                        return CurrentUser.getInstance().getToken();
                    }
                };

                Log.d("bodyk", body.getFirst("kit_type_id") + ";" + body.getFirst("matricula")
                        + ";" + body.getFirst("tipo") + ";" + body.size() + ";" + params[0]);

                http.getHeaderValue();

                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                headers.setAuthorization(http);
                request = new HttpEntity<>(body, headers);

                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                // Alternative to the implementation below
                response = restTemplate.exchange(url, HttpMethod.POST, request, ApiResponse.class);
                return params[1];
                //return restTemplate.postForObject(url, request, ApiResponse.class);
            } catch (ResourceAccessException e) {
                dismissProgressDialog();
            //    Log.e("CAUSA", e.getClass().toString(), e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            alert_text = alert_text + "- Kit " + ktp.id_to_name.getFirst(params[1]) +
                                    ": Sem conexão com a internet!\n";
                        }
                        catch (ArrayIndexOutOfBoundsException e){
                            alert_text = alert_text + "- Kit **Não Cadastrado**" + ": Sem conexão com a internet!\n";
                        }
                        if(alertDialog.isShowing()){
                            alertDialog.setMessage(alert_text);
                        }
                        else {
                            alertDialog.setTitle("Cadastro de Movimentação");
                            alertDialog.setMessage(alert_text);
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            alert_text = "";
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.setCancelable(false);
                            alertDialog.show();
                        }
                    }
                });
            }
            catch (HttpServerErrorException e) {
                dismissProgressDialog();
           //     Log.e("CAUSA3", e.getClass().toString(), e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            alert_text = alert_text + "- Kit " + ktp.id_to_name.getFirst((params[1])) +
                                    ": Kit não existente para o aluno!\n";
                        }
                        catch (ArrayIndexOutOfBoundsException e){
                            alert_text = alert_text + "- Kit **Não Cadastrado**" + ": Kit não existente para o aluno!\n";
                        }
                        if(alertDialog.isShowing()){
                            alertDialog.setMessage(alert_text);
                        }
                        else {
                            alertDialog.setTitle("Cadastro de Movimentação");
                            alertDialog.setMessage(alert_text);
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            alert_text = "";
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.setCancelable(false);
                            alertDialog.show();
                        }
                    }
                });
            }
            catch (HttpClientErrorException e){
                dismissProgressDialog();
          //      Log.e("CAUSA2", e.getClass().toString(), e);
           //     Log.e("CAUSA2", e.getStatusCode().toString(), e);
                final String statusCode = e.getStatusCode().toString();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Log.d("token", CurrentUser.getInstance().getToken());
                        if(statusCode.equals("400")){
                            try {
                                //Toast.makeText(getActivity(), "ktp.id_to_name.getFirst(Integer.getInteger(params[1])): " + ktp.id_to_name.getFirst(params[1]), Toast.LENGTH_LONG).show();
                                alert_text = alert_text + "- Kit " + ktp.id_to_name.getFirst(params[1]) +
                                        ": O aparelho já realizou " + params[0] + "\n";
                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                alert_text = alert_text + "- Kit **Não Cadastrado**" + ": O aparelho já realizou " + params[0] + ".\n";
                            }
                            if(alertDialog.isShowing()){
                                alertDialog.setMessage(alert_text);
                            }
                            else {
                                alertDialog.setTitle("Cadastro de Movimentação");
                                alertDialog.setMessage(alert_text);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                alert_text = "";
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.setCancelable(false);
                                alertDialog.show();
                            }
                       //     Toast.makeText(getActivity(), "O aparelho já realizou " + getFeed_type(), Toast.LENGTH_LONG).show();
                        }
                        else if(statusCode.equals("401"))
                        {
                            try {
                                alert_text = alert_text + "- Kit " + ktp.id_to_name.getFirst(params[1]) +
                                        ": Token expirado!\n";
                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                alert_text = alert_text + "- Kit **Não Cadastrado**" + ": Token expirado!\n";
                            }
                            if(alertDialog.isShowing()){
                                alertDialog.setMessage(alert_text);
                            }
                            else {
                                alertDialog.setTitle("Cadastro de Movimentação");
                                alertDialog.setMessage(alert_text);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                alert_text = "";
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.setCancelable(false);
                                alertDialog.show();
                            }
                         //   Toast.makeText(getActivity(), "Login expirado!", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            try {
                                alert_text = alert_text + "- Kit " + ktp.id_to_name.getFirst((params[1])) +
                                        ": Um erro inesperado aconteceu!\n";
                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                alert_text = alert_text + "- Kit **Não Cadastrado**" + ": Um erro inesperado aconteceu!\n";
                            }
                            if(alertDialog.isShowing()){
                                alertDialog.setMessage(alert_text);
                            }
                            else {
                                alertDialog.setTitle("Cadastro de Movimentação");
                                alertDialog.setMessage(alert_text);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                alert_text = "";
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.setCancelable(false);
                                alertDialog.show();
                            }
                        //    Toast.makeText(getActivity(), "Existe algum problema na etiqueta!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(String kit_id) {
            if(kit_id != null) {
                dismissProgressDialog();
                try {
                    alert_text = alert_text + "- Kit " + ktp.id_to_name.getFirst((kit_id)) +  ": sucesso.\n";
                }
                catch (ArrayIndexOutOfBoundsException e){
                    alert_text = alert_text + "- Kit **Não Cadastrado**: sucesso." + "\n";
                }
                catch (NumberFormatException e){
                    alert_text = alert_text + "- Kit **Não Cadastrado**: sucesso." + "\n";
                }
                if(alertDialog.isShowing()){
                    alertDialog.setMessage(alert_text);
                }
                else {
                    alertDialog.setTitle("Cadastro de Movimentação");
                    alertDialog.setMessage(alert_text);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    alert_text = "";
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
          //      Toast.makeText(getActivity(), "Cadastro de Movimentação de Kit: " + response.getStatus(), Toast.LENGTH_LONG).show();
            }
        }
    }

}