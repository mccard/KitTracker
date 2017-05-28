package edu.uco.mcamposcardoso.kittracker.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.android.BeepManager;
import com.welcu.android.zxingfragmentlib.BarCodeScannerFragment;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import edu.uco.mcamposcardoso.kittracker.types.UserToken;


public class SampleFragment extends BarCodeScannerFragment {

    private ScannerListener mListener = null;
    private static final long DELAY = 2000; // 2 seconds
    int scan_count;

    public interface ScannerListener {
        public void onItemScan(String nomeAluno, String telefone, String curso, String periodo, String nomeItem);
    }


    // ***************************************
    // Activity methods
    // ***************************************

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
                if((System.currentTimeMillis() - lastTimestamp) < DELAY) { // 2s delay (Se a diferença do scaneamento atual pro ´ultimo escaneamento for menor do que 2 segundos => aplica deplay (return)
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

    public SampleFragment() {

    }

    @Override
    public int getRequestedCameraId() {
        return -1; // set to 1 to use the front camera (won't work if the device doesn't have one, it is up to you to handle this method ;)
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, UserToken> {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();

            body.add("email", "its.matheus3@gmail.com");
            body.add("password", "123123");

        }

        @Override
        protected UserToken doInBackground(Void... params) {
            try {
                final String url = "https://odontokits.herokuapp.com/authenticate_user";
                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                // Alternative to the implementation below
                //  ResponseEntity<UserToken> response = restTemplate.exchange(url, HttpMethod.POST, request, UserToken.class);
                // return response.getBody();
                return restTemplate.postForObject(url, request, UserToken.class);

            } catch (ResourceAccessException e) {
                dismissProgressDialog();
                Log.e("CAUSA", e.getClass().toString(), e);
                getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getActivity(), "Sem conexão com a internet.", Toast.LENGTH_LONG).show();
                                                }
                                            });
                return null;
            }

            //return null;
        }

        @Override
        protected void onPostExecute(UserToken userToken) {
            if(userToken != null) {
                dismissProgressDialog();
                Toast.makeText(getActivity(), userToken.getAuth_token(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}