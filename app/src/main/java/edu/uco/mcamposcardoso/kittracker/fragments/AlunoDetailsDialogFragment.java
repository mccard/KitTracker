package edu.uco.mcamposcardoso.kittracker.fragments;

import android.app.AlertDialog;
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
import android.widget.EditText;

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
import edu.uco.mcamposcardoso.kittracker.interfaces.AlunoConfirmationListener;
import edu.uco.mcamposcardoso.kittracker.types.ApiResponse;
import edu.uco.mcamposcardoso.kittracker.types.ScanInformation;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlunoConfirmationListener} interface
 * to handle interaction events.
 * Use the {@link AlunoDetailsDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlunoDetailsDialogFragment extends DialogFragment {

    public static ScanInformation getScan() {
        return scan;
    }

    public static void setScan(ScanInformation scan) {
        AlunoDetailsDialogFragment.scan = scan;
    }

    private static ScanInformation scan;
    private static String matricula;
    private static String password;
    View view;
    private boolean destroyed = false;
    Button btnAlunoConfirmation;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
    EditText edtAlunoMatricula, edtAlunoPassword;

    private AlunoConfirmationListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param scan ScanInformation.
     * @return A new instance of fragment AlunoDetailsDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlunoDetailsDialogFragment newInstance(ScanInformation scan) {
        AlunoDetailsDialogFragment fragment = new AlunoDetailsDialogFragment();
        setScan(scan);
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
                    + " must implement AlunoConfirmationListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("Insira as credenciais");

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_aluno_details_dialog, container, false);

        getDialog().setCanceledOnTouchOutside(false);

        edtAlunoMatricula = (EditText) view.findViewById(R.id.edtAlunoMatricula);
        edtAlunoPassword = (EditText) view.findViewById(R.id.edtAlunoPassword);

        edtAlunoMatricula.setText(scan.getMatricula());

        if (!scan.getMatricula().isEmpty()){
            edtAlunoPassword.requestFocus();
        }

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

    private class HttpRequestTask extends AsyncTask<Void, Void, ApiResponse> {

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
            body.add("matricula", edtAlunoMatricula.getText().toString());
            body.add("password", edtAlunoPassword.getText().toString());
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
                        alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Erro na autenticação");
                        alertDialog.setMessage("Sem conexão com a internet!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                      //  Toast.makeText(getActivity(), "Sem conexão com a internet.", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (HttpClientErrorException e) {
                dismissProgressDialog();
                Log.e("CAUSA2", e.getClass().toString(), e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Erro na autenticação");
                        alertDialog.setMessage("Credenciais inválidas!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                      //  Toast.makeText(getActivity(), "Credenciais inválidas!", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (HttpServerErrorException e) {
                dismissProgressDialog();
                Log.e("CAUSA3", e.getClass().toString(), e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Erro na autenticação");
                        alertDialog.setMessage("Serviço indisponível!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
               //         Toast.makeText(getActivity(), "Serviço indisponível!", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (NullPointerException e) {
                dismissProgressDialog();
                Log.e("CAUSA4", e.getClass().toString(), e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Erro na autenticação");
                        alertDialog.setMessage("Um erro ocorreu. Tente novamente.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                      //  Toast.makeText(getActivity(), "Um erro inesperado occoreu, tente novamente!", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(ApiResponse response) {
//            Log.d("response", response.getStatus());
            if (response != null && response.getStatus().equals("success")) {
                dismissProgressDialog();
         //       Toast.makeText(getActivity(), "Autenticação do Aluno: " + response.getStatus(), Toast.LENGTH_SHORT).show();
                mListener.onAlunoConfirmation(scan.getKit(), edtAlunoMatricula.getText().toString());
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
