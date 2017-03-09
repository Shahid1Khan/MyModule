package com.mymodule.activities;

import android.app.Dialog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mymodule.R;
import com.mymodule.utils.CheckNetwork;
import com.mymodule.utils.MyDialog;
import com.mymodule.utils.MyValidation;
import com.mymodule.utils.Urls;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputLayout inputEmail;
    EditText edtEmail;

    LinearLayout mainLayout;
    Button btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        inputEmail = (TextInputLayout) findViewById(R.id.inputEmail);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnResetPassword = (Button) findViewById(R.id.btnResetPassword);


        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().equals("")){
                    inputEmail.setError("Enter Email");
                }else if (!new MyValidation().checkEmail(s.toString())){
                    inputEmail.setError("Enter Valid Email");
                }else {
                    inputEmail.setErrorEnabled(false);
                    inputEmail.setError(null);
                }
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                if (email.equals("")) {
                    inputEmail.setError("Enter Email");
                }else {
                    if (new CheckNetwork().isConnected(ForgotPasswordActivity.this)){
                        ForgotPassword(email);
                    }else {
                        new MyDialog(ForgotPasswordActivity.this).getNoInternetDialog().show();
                    }
                }
            }
        });
    }

    public void ForgotPassword(String... s) {
        final Dialog progress = new MyDialog(this).getProgressDialog();
        progress.show();
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("Email", s[0])
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
                        new MyDialog(ForgotPasswordActivity.this).getNoInternetDialog().show();
                    } else {
                        try {
                            JSONObject json = new JSONObject(response);
                            Snackbar.make(mainLayout, json.getString("Message"), Snackbar.LENGTH_LONG).show();
                            if (json.getBoolean("Success")) {

                            }
                        } catch (Exception e) {
                            Log.v("ParsingException", "" + e);
                        }
                    }
                    if (progress.isShowing())
                        progress.dismiss();
                } catch (Exception e) {
                    Log.v("Exception", "" + e);
                }
            }
        });
    }


}
