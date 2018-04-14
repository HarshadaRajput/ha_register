package com.example.lenovo.myapp2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class RegisterActivity extends AppCompatActivity {

    private String TAG = RegisterActivity.class.getSimpleName();

    private EditText userNameBox, firstNameBox, lastNameBox, emailBox;
    private TextInputEditText passwordBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        userNameBox = (EditText) findViewById(R.id.userid);
        firstNameBox = (EditText) findViewById(R.id.firstname);
        lastNameBox = (EditText) findViewById(R.id.lastname);
        emailBox = (EditText) findViewById(R.id.email);
        passwordBox = (TextInputEditText) findViewById(R.id.passwd);

        Button registerButton = (Button) findViewById(R.id.btnregister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        userNameBox.setText("punit");
        emailBox.setText("shuklapunit9@gmail.com");
        firstNameBox.setText("punit");
        lastNameBox.setText("shukla");
        passwordBox.setText("123456789");
    }

    private class Call extends AsyncTask<RequestBody, Void, Integer> {
        private ProgressDialog pDialog;
        private String errorMessage = "Something went wrong!";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Creating user..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Integer doInBackground(RequestBody... requestBodies) {
            try {
                String response = MakeCall.post("re.php", requestBodies[0], RegisterActivity.class.getSimpleName());
                //{"register":[{"message":"Register Successfully.","status":1}]}
                if (response != null) {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("register")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("register");
                        Log.e(TAG, "" + jsonArray);
                        if (jsonArray.length() > 0) {
                            JSONObject object = jsonArray.getJSONObject(0);
                            Log.e(TAG, "" + object);
                            if (object.has("status")) {
                                int status = object.getInt("status");
                                Log.e(TAG, "" + status);
                                if (status == 1) {
                                    errorMessage = getApplicationContext().getResources().getString(R.string.successful);
                                } else {
                                    errorMessage = object.getString("message");
                                }
                                return status;
                            }
                        }
                        return 11;
                    } else {
                        return 11;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return 12;
            }
            return 12;
        }

        @Override
        protected void onPostExecute(Integer response) {
            super.onPostExecute(response);
            if (pDialog != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.dismiss();
                    }
                }, 3000);
            }
            if (response == 1) {
                SnackResponse.success(errorMessage, RegisterActivity.this);
                // thread to add delay of 1sec before going to next screen
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent nextIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(nextIntent, ActivityTransition.moveToNextAnimation(getApplicationContext()));
                        finish();
                    }
                }, 3000);
            } else {
                if (errorMessage.trim().length() <= 0) {
                    errorMessage = GetResponses_.getStatusMessage(response, getApplicationContext());
                }
                SnackResponse.failed(errorMessage, RegisterActivity.this);
            }
        }
    }

    /**
     * Creating product
     */
    private void register() {
        String user_name = userNameBox.getText().toString();
        String first_name = firstNameBox.getText().toString();
        String last_name = lastNameBox.getText().toString();
        String email = emailBox.getText().toString();
        String password = passwordBox.getText().toString();

        RequestBody requestBody = new FormBody.Builder()
                .add("username", user_name)
                .add("firstname", first_name)
                .add("lastname", last_name)
                .add("email", email)
                .add("password", password)
                .build();
        new Call().execute(requestBody);
    }
}



