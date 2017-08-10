package edu.uco.mcamposcardoso.kittracker.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import edu.uco.mcamposcardoso.kittracker.R;
import edu.uco.mcamposcardoso.kittracker.types.CurrentUser;
import edu.uco.mcamposcardoso.kittracker.types.UserToken;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    private boolean destroyed = false;
    Button btnLogin;
    EditText edtEmail, edtPassword;
    MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        //  Development purposes

        body.add("email", "its.matheus3@gmail.com");
        body.add("password", "123123");


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //       body.add("email", edtEmail.getText().toString());
            //       body.add("password", edtPassword.getText().toString());
            new HttpRequestTask().execute();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.destroyed = true;
        dismissProgressDialog();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, UserToken> {

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Sem conexão com a internet.", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (HttpClientErrorException e) {
                dismissProgressDialog();
                Log.e("CAUSA2", e.getClass().toString(), e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Credenciais inválidas!", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (HttpServerErrorException e) {
                dismissProgressDialog();
                Log.e("CAUSA3", e.getClass().toString(), e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Serviço indisponível!", Toast.LENGTH_LONG).show();
                    }
                });
            }


            return null;
        }

        @Override
        protected void onPostExecute(UserToken userToken) {
            if (userToken != null) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), userToken.getAuth_token(), Toast.LENGTH_SHORT).show();
                CurrentUser.getInstance().setToken(userToken.getAuth_token());
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void showLoadingProgressDialog() {
        this.showProgressDialog("Realizando login...");
    }

    private void showProgressDialog(CharSequence message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
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
