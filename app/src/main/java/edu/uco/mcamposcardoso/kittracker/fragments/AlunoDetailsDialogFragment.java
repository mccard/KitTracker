package edu.uco.mcamposcardoso.kittracker.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

import edu.uco.mcamposcardoso.kittracker.R;
import edu.uco.mcamposcardoso.kittracker.types.ApiResponse;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlunoDetailsDialogFragment.AlunoConfirmationListener} interface
 * to handle interaction events.
 * Use the {@link AlunoDetailsDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlunoDetailsDialogFragment extends DialogFragment {
    private static final String ARG_PARAM1 = "param1";

    private static String matricula;
    private static String password;
    View view;
    private boolean destroyed = false;
    Button btnAlunoConfirmation;
    ProgressDialog progressDialog;
    MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

    private AlunoConfirmationListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param matricula Parameter 1.
     * @return A new instance of fragment AlunoDetailsDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlunoDetailsDialogFragment newInstance(String matricula, String password) {
        AlunoDetailsDialogFragment fragment = new AlunoDetailsDialogFragment();
        setMatricula(matricula);
        setPassword(password);
        return fragment;
    }

    public AlunoDetailsDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mListener = (AlunoConfirmationListener) getActivity(); //getActivity()
            // returns the Activity this fragment is currently associated with.
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement ScannerListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_aluno_details_dialog, container, false);

        getDialog().setCanceledOnTouchOutside(false);

        btnAlunoConfirmation = (Button) view.findViewById(R.id.btnAlunoConfirmation);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnAlunoConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpRequestTask().execute();
            }
        });
    }
    public String getMatricula() {
        return matricula;
    }

    public static void setMatricula(String value) {
        matricula = value;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        AlunoDetailsDialogFragment.password = password;
    }

    public interface AlunoConfirmationListener {
        public void onAlunoConfirmation();
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, ApiResponse> {

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
            body.add("matricula", getMatricula());
            body.add("password", getPassword());
        }

        @Override
        protected ApiResponse doInBackground(Void... params) {
            try {
                final String url = "https://odontokits.herokuapp.com/authenticate_aluno";
                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                // Alternative to the implementation below
                  ResponseEntity<ApiResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, ApiResponse.class);
                 return response.getBody();
                //return restTemplate.postForObject(url, request, ApiResponse.class);

            } catch (ResourceAccessException e) {
                dismissProgressDialog();
                Log.e("CAUSA", e.getClass().toString(), e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Sem conexão com a internet.", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (HttpClientErrorException e) {
                dismissProgressDialog();
                Log.e("CAUSA2", e.getClass().toString(), e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Credenciais inválidas!", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (HttpServerErrorException e) {
                dismissProgressDialog();
                Log.e("CAUSA3", e.getClass().toString(), e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Serviço indisponível!", Toast.LENGTH_LONG).show();
                    }
                });
            }


            return null;
        }

        @Override
        protected void onPostExecute(ApiResponse response) {
            Log.d("response", response.getStatus());
            if (response != null) {
                dismissProgressDialog();
                Toast.makeText(getActivity(), "Autenticação do Aluno: " + response.getStatus(), Toast.LENGTH_SHORT).show();
                mListener.onAlunoConfirmation();
                dismiss();
            }
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        this.destroyed = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        this.destroyed = true;
    }

    private void showLoadingProgressDialog() {
        this.showProgressDialog("Autenticando...");
    }

    private void showProgressDialog(CharSequence message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }

        progressDialog.setMessage(message);
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && !destroyed) {
            progressDialog.dismiss();
        }
    }

}