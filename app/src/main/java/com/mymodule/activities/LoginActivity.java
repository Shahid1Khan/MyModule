package com.mymodule.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mymodule.R;
import com.mymodule.models.MyDetail;
import com.mymodule.mysharedprefrence.MyPrefData;
import com.mymodule.utils.CheckNetwork;
import com.mymodule.utils.MyDialog;
import com.mymodule.utils.MyValidation;
import com.mymodule.utils.Urls;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout inputEmail, inputPassword;
    EditText edtEmail, edtPassword;
    String s = "";
    LinearLayout mainLayout;

    Button btnLogin, btnForgotPassword, btnRegistration;

    public static LoginActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        init();

        // new ToolbarOperation(this).setupToolbar("Login",true);

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().equals("")) {
                    inputEmail.setError("Enter Email");
                } else if (!new MyValidation().checkEmail(s.toString())) {
                    inputEmail.setError("Enter Valid Email");
                } else {
                    inputEmail.setErrorEnabled(false);
                    inputEmail.setError(null);
                }
                // inputEmail.setError(s.toString().equals("") ? "Enter Email" : null);
            }
        });
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    inputPassword.setError("Enter Password");
                } else {
                    inputPassword.setErrorEnabled(false);
                    inputPassword.setError(null);
                }
            }
        });
    }

    private void init() {
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        inputEmail = (TextInputLayout) findViewById(R.id.inputEmail);
        inputPassword = (TextInputLayout) findViewById(R.id.inputPassword);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnForgotPassword = (Button) findViewById(R.id.btnForgotPassword);
        btnRegistration = (Button) findViewById(R.id.btnRegistration);
        btnLogin.setOnClickListener(this);
        btnRegistration.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (email.equals("")) {
                    inputEmail.setError("Enter Email");
                } else if (!new MyValidation().checkEmail(email)) {
                    inputEmail.setError("Enter Valid Email");
                } else if (password.equals("")) {
                    inputPassword.setError("Enter Password");
                } else {
                    if (new CheckNetwork().isConnected(LoginActivity.this)) {
                        UserLogin(email, password);
                    } else {
                        new MyDialog(LoginActivity.this).getNoInternetDialog().show();
                    }
                }
                break;
            case R.id.btnForgotPassword:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
            case R.id.btnRegistration:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
        }
    }

    public void UserLogin(String... s) {
        final Dialog progress = new MyDialog(this).getProgressDialog();
        progress.show();

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("Email", s[0])
                .addFormDataPart("Password", s[1])
                .build();

        final Request request = new Request.Builder().url(Urls.LoginUrl)
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody).build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("Failure", "" + e);
                String response = getString(R.string.networkError);
                handleResponse(response, progress);
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                if (res.isSuccessful()) {
                    String response = Html.fromHtml(res.body().string()).toString();
                    Log.v("ResponsePostSuccess", response);
                    handleResponse(response, progress);
                }
            }
        });
    }

    public void handleResponse(final String response, final Dialog progress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (response.equalsIgnoreCase(getString(R.string.networkError))) {
                        new MyDialog(LoginActivity.this).getNoInternetDialog().show();
                    } else {
                        try {
                            JSONObject json = new JSONObject(response);
                            Snackbar.make(mainLayout, json.getString("Message"), Snackbar.LENGTH_LONG).show();
                            if (json.getBoolean("Success")) {
                                getMyDetail(json.getString("UserId"), json.getString("Token"));
                                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                finish();
                            }
                        } catch (Exception e) {
                            if (progress.isShowing())
                                progress.dismiss();
                            Log.v("ParsingException", "" + e);
                        }
                    }
                    if (progress.isShowing())
                        progress.dismiss();
                } catch (Exception e) {
                    if (progress.isShowing())
                        progress.dismiss();
                    Log.v("Exception", "" + e);
                }
            }
        });
    }


    public void getMyDetail(final String... s) {
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("Token", s[1])
                .addFormDataPart("UserId", s[0])
                .build();

        final Request request = new Request.Builder().url(Urls.getProfile)
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody).build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("Failure", "" + e);
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                if (res.isSuccessful()) {
                    String response = Html.fromHtml(res.body().string()).toString();
                    Log.v("ResponsePostSuccess", response);
                    // handleResponse(response, progress);
                    MyDetail detail = new MyDetail(
                            s[0], "", "", "", "", "", s[1]);

                    new MyPrefData(LoginActivity.this).setMyDetails(detail);

                }
            }
        });
    }

}
