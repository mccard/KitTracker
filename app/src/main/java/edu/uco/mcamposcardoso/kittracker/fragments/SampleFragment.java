package edu.uco.mcamposcardoso.kittracker.fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

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


public class SampleFragment extends BarCodeScannerFragment {

    private ScannerListener mListener = null;
    private static final long DELAY = 2000; // 2 seconds
    int scan_count;
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

        final BeepManager beepManager = new BeepManager(getActivity());
        beepManager.updatePrefs();

        scan_count = 0;

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

    public void registerScan(String kit_id, String matricula){
        //Toast.makeText(getActivity()," KIT_ID: " + Integer.parseInt(kit_id), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity()," MATRICULA: " + matricula, Toast.LENGTH_SHORT).show();
        body.add("kit_type_id", kit_id);
        body.add("matricula", matricula);
        new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, ApiResponse> {

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
            body.add("tipo", getFeed_type());
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected ApiResponse doInBackground(Void... params) {
            try {
                final String url = "https://odontokits.herokuapp.com/feeds.json";
                restTemplate = new RestTemplate();

                headers = new HttpHeaders();

                http = new HttpAuthentication() {
                    @Override
                    public String getHeaderValue() {
                        return CurrentUser.getInstance().getToken();
                    }
                };

                http.getHeaderValue();

                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                headers.setAuthorization(http);
                request = new HttpEntity<>(body, headers);

                    restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                // Alternative to the implementation below
                response = restTemplate.exchange(url, HttpMethod.POST, request, ApiResponse.class);
                return response.getBody();
                //return restTemplate.postForObject(url, request, ApiResponse.class);
            } catch (ResourceAccessException e) {
                dismissProgressDialog();
                Log.e("CAUSA", e.getClass().toString(), e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Erro no cadastro");
                        alertDialog.setMessage("Sem conexão com a internet!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                 //       Toast.makeText(getActivity(), "Sem conexão com a internet.", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (HttpServerErrorException e) {
                dismissProgressDialog();
                Log.e("CAUSA3", e.getClass().toString(), e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Erro no cadastro");
                        alertDialog.setMessage("Kit não existente para o aluno!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        //       Toast.makeText(getActivity(), "Sem conexão com a internet.", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (HttpClientErrorException e){
                dismissProgressDialog();
                Log.e("CAUSA2", e.getClass().toString(), e);
                Log.e("CAUSA2", e.getStatusCode().toString(), e);
                final String statusCode = e.getStatusCode().toString();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Log.d("token", CurrentUser.getInstance().getToken());
                        if(statusCode.equals("400")){
                            alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle("Erro no cadastro");
                            alertDialog.setMessage("O aparelho já realizou " + getFeed_type());
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                       //     Toast.makeText(getActivity(), "O aparelho já realizou " + getFeed_type(), Toast.LENGTH_LONG).show();
                        }
                        else if(statusCode.equals("401"))
                        {
                            alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle("Erro no cadastro");
                            alertDialog.setMessage("Token expirado!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                         //   Toast.makeText(getActivity(), "Login expirado!", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle("Erro no cadastro");
                            alertDialog.setMessage("Um erro inesperado ocorreu!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        //    Toast.makeText(getActivity(), "Existe algum problema na etiqueta!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            return null;
        }


        @Override
        protected void onPostExecute(ApiResponse response) {
            if(response != null) {
                dismissProgressDialog();
                alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Cadastro bem sucedido");
                alertDialog.setMessage("Cadastro de Movimentação de Kit: " + response.getStatus());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
          //      Toast.makeText(getActivity(), "Cadastro de Movimentação de Kit: " + response.getStatus(), Toast.LENGTH_LONG).show();
            }
        }
    }

}