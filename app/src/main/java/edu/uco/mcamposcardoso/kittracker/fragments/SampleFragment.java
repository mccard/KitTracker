package edu.uco.mcamposcardoso.kittracker.fragments;

import android.annotation.TargetApi;
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
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import edu.uco.mcamposcardoso.kittracker.types.FeedResponse;


public class SampleFragment extends BarCodeScannerFragment {

    private ScannerListener mListener = null;
    private static final long DELAY = 2000; // 2 seconds
    int scan_count;

    public interface ScannerListener {
        public void onItemScan(String nomeAluno, String telefone, String curso, String periodo, String nomeItem);
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

                new HttpRequestTask().execute();

                // Space reserved to call mListener.OnItemScan() in order to trigger the activity associated with this fragment

                Toast.makeText(getActivity(), scan_count++ + " Scan: " + lastResult.toString(), Toast.LENGTH_SHORT).show();

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

    private class HttpRequestTask extends AsyncTask<Void, Void, FeedResponse> {

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
            body.add("tipo", getFeed_type());
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected FeedResponse doInBackground(Void... params) {
            try {
                final String url = "https://odontokits.herokuapp.com/kits/16/feeds.json";
                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();

                final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo" +
                        "xLCJleHAiOjE0OTY1MjA0OTV9.bC4Ty0h7aOCPRaIVuWjWLHjGWPFfnm3ciFNm0rsY2Ns";

                HttpAuthentication http = new HttpAuthentication() {
                    @Override
                    public String getHeaderValue() {
                        return token;
                    }
                };
                http.getHeaderValue();

                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                headers.setAuthorization(http);
                HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

                    restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                // Alternative to the implementation below
                ResponseEntity<FeedResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, FeedResponse.class);
                return response.getBody();
                // return restTemplate.postForObject(url, request, UserToken.class);
            } catch (ResourceAccessException e) {
                dismissProgressDialog();
                Log.e("CAUSA", e.getClass().toString(), e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Sem conexão com a internet.", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (HttpClientErrorException e){
                dismissProgressDialog();
                Log.e("CAUSA2", e.getClass().toString(), e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Token Inválido!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }


        @Override
        protected void onPostExecute(FeedResponse response) {
            if(response != null) {
                dismissProgressDialog();
                Toast.makeText(getActivity(), response.getStatus(), Toast.LENGTH_LONG).show();
            }
        }
    }

}