package com.mymodule.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mymodule.R;
import com.mymodule.models.MyDetail;
import com.mymodule.mysharedprefrence.MyPrefData;
import com.mymodule.utils.CheckNetwork;
import com.mymodule.utils.MyDialog;
import com.mymodule.utils.MyValidation;
import com.mymodule.utils.Urls;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout inputUserName, inputEmail, inputPassword, inputDOB, inputNumber;
    EditText edtUserName, edtEmail, edtPassword, edtDOB, edtNumber;
    CheckBox chkTearms;

    LinearLayout mainLayout;

    Button btnRegistration;
    String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }

    private void init() {
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        inputUserName = (TextInputLayout) findViewById(R.id.inputUserName);
        inputEmail = (TextInputLayout) findViewById(R.id.inputEmail);
        inputPassword = (TextInputLayout) findViewById(R.id.inputPassword);
        inputDOB = (TextInputLayout) findViewById(R.id.inputDOB);
        inputNumber = (TextInputLayout) findViewById(R.id.inputNumber);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtDOB = (EditText) findViewById(R.id.edtDOB);
        edtNumber = (EditText) findViewById(R.id.edtNumber);
        chkTearms = (CheckBox) findViewById(R.id.chkTearms);
        btnRegistration = (Button) findViewById(R.id.btnRegistration);

        edtUserName.addTextChangedListener(new MyTextWatcher(inputUserName, edtUserName));
        edtEmail.addTextChangedListener(new MyTextWatcher(inputEmail, edtEmail));
        edtPassword.addTextChangedListener(new MyTextWatcher(inputPassword, edtPassword));
        edtDOB.addTextChangedListener(new MyTextWatcher(inputDOB, edtDOB));
        edtNumber.addTextChangedListener(new MyTextWatcher(inputNumber, edtNumber));

        edtDOB.setOnClickListener(this);
        btnRegistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtDOB:
                final Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                int diff = now.get(Calendar.YEAR) - year;
                                if (diff > 15) {
                                    int month = monthOfYear + 1;
                                    date = (month < 10 ? "0" + month : month) + "-" + dayOfMonth + "-" + year;
                                    edtDOB.setText(date);
                                } else {
                                    Toast.makeText(RegistrationActivity.this, "Age must be grater than 15", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },

                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
                dpd.setMaxDate(now);
                dpd.setAccentColor(ContextCompat.getColor(RegistrationActivity.this, R.color.colorPrimary));
                break;
            case R.id.btnRegistration:
                String name = "", email = "", password = "", dob = "", number = "";

                name = edtUserName.getText().toString();
                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();
                dob = edtDOB.getText().toString();
                number = edtNumber.getText().toString();


                if (name.equals("")) {
                    inputUserName.setError("Enter Name");
                } else if (!new MyValidation().checkName(name)) {
                    inputUserName.setError("Enter Valid Name");
                } else if (email.equals("")) {
                    inputEmail.setError("Enter Email");
                } else if (!new MyValidation().checkEmail(email)) {
                    inputEmail.setError("Enter Valid Email");
                } else if (password.equals("")) {
                    inputPassword.setError("Enter Password");
                } else if (!new MyValidation().checkPassword(password)) {
                    inputPassword.setError("Enter Valid Password");
                } else if (dob.equals("")) {
                    inputDOB.setError("Select Dob");
                } else if (number.equals("")) {
                    inputNumber.setError("Enter Mobile No");
                } else if (!new MyValidation().checkMobile(number)) {
                    inputNumber.setError("Enter Valid Mobile No");
                } else if (!chkTearms.isChecked()) {
                    Toast.makeText(RegistrationActivity.this, "Accept Tearms and Condition", Toast.LENGTH_SHORT).show();
                } else {
                    if (new CheckNetwork().isConnected(RegistrationActivity.this)) {
                        UserRegistration(name, email, password, dob, number);
                    } else {
                        new MyDialog(RegistrationActivity.this).getNoInternetDialog().show();
                    }
                }
                break;
        }
    }

    class MyTextWatcher implements TextWatcher {
        TextInputLayout inputLayout;
        EditText editText;

        public MyTextWatcher(TextInputLayout inputLayout, EditText editText) {
            this.inputLayout = inputLayout;
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().equals("")) {
                switch (editText.getId()) {
                    case R.id.edtUserName:
                        inputLayout.setError("Enter Name");
                        break;
                    case R.id.edtEmail:
                        inputLayout.setError("Enter Email");
                        break;
                    case R.id.edtPassword:
                        inputLayout.setError("Enter Password");
                        break;
                    case R.id.edtDOB:
                        inputLayout.setError("Select Date of birth");
                        break;
                    case R.id.edtNumber:
                        inputLayout.setError("Enter Contact No");
                        break;
                }
            } else {
                if (editText.getId() == R.id.edtEmail) {
                    if (!new MyValidation().checkEmail(s.toString())) {
                        inputLayout.setError("Enter Valid Email");
                    } else {
                        inputLayout.setErrorEnabled(false);
                        inputLayout.setError(null);
                    }
                } else  if (editText.getId() == R.id.edtPassword) {
                    if (!isValidPassword(s.toString())) {
                        inputLayout.setError("Enter Valid Password");
                    } else {
                        inputLayout.setErrorEnabled(false);
                        inputLayout.setError(null);
                    }
                }else {
                    inputLayout.setErrorEnabled(false);
                    inputLayout.setError(null);
                }
            }
        }
    }


    public void UserRegistration(final String... s) {
        final Dialog progress = new MyDialog(this).getProgressDialog();
        progress.show();

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("Username", s[0])
                .addFormDataPart("Email", s[1])
                .addFormDataPart("Password", s[2])
                .addFormDataPart("DateOfBirth", s[3])
                .addFormDataPart("phonenumber", s[4])
                .addFormDataPart("IsTerm", "1")
                .build();

        final Request request = new Request.Builder().url(Urls.RegistrationUrl)
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody).build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("Failure", "" + e);
                String response = getString(R.string.networkError);
                handleResponse(response, progress,s);
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                if (res.isSuccessful()) {
                    String response = Html.fromHtml(res.body().string()).toString();
                    Log.v("ResponsePostSuccess", response);
                    handleResponse(response, progress,s);
                }
            }
        });
    }

    public void handleResponse(final String response, final Dialog progress, final String... myDetail) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (response.equalsIgnoreCase(getString(R.string.networkError))) {
                        new MyDialog(RegistrationActivity.this).getNoInternetDialog().show();
                    } else {
                        try {
                            JSONObject json = new JSONObject(response);
                            Snackbar.make(mainLayout, json.getString("Message"), Snackbar.LENGTH_LONG).show();
                            if (json.getBoolean("Success")) {

//                                MyDetail detail = new MyDetail(json.getString("UserId"), myDetail[0], myDetail[1],
//                                        myDetail[2], myDetail[3], myDetail[4]);
//                                new MyPrefData(RegistrationActivity.this).setMyDetails(detail);

                              //  startActivity(new Intent(RegistrationActivity.this, DashboardActivity.class));
                               // LoginActivity.activity.finish();
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



    //*****************************************************************
    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
