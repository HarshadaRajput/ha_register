package com.example.lenovo.myapp2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userIdBox, passwordBox;
    private TextView Info, registerLink;
    private Button loginButton;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userIdBox = (EditText) findViewById(R.id.Userid);
        passwordBox = (EditText) findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        loginButton = (Button) findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(this);
        registerLink = (TextView) findViewById(R.id.btnregister);
    }

    @Override
    protected void onStart() {
        super.onStart();
        userIdBox.setText("girish");
        passwordBox.setText("123456");

        //Shared preferences to save data
        HomePreferences.initialize(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnregister:
                Intent next = new Intent(this, Register.class);
                startActivity(next);
                break;
            case R.id.btnLogin:
                login();
                break;
        }
    }

    // Info.setText("No. Of Attempts Remaining : 5");

    /* Login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            validate(Userid.getText().toString(),Password.getText().toString());
        }
    });
}
private void  validate(String Userid,String userPassword) {
    if ((Userid.equals("Harshada")) && (userPassword.equals("1234"))) {
        Intent intent = new Intent(MainActivity.this, Second2Activity.class);
        startActivity(intent);
    } else {
        if ((Userid.equals("Karuna")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, Second2Activity.class);
            startActivity(intent);
        } else {
            if ((Userid.equals("Dipika")) && (userPassword.equals("1234"))) {
                Intent intent = new Intent(MainActivity.this, Second2Activity.class);
                startActivity(intent);
            } else {
                if ((Userid.equals("Punit")) && (userPassword.equals("1234"))) {
                    Intent intent = new Intent(MainActivity.this, Second2Activity.class);
                    startActivity(intent);
                }
                else {
                    counter--;

                    Info.setText("No. Of Attempts Remaining :  " + String.valueOf(counter));

                    if (counter == 0) {
                        Login.setEnabled(false);
                    }
                }
            }
        }
    }
}
*/

    /*
    * 1: success
    * 2: failed
    * 11: network error
    * 12: internal error
    * */
    private class Call extends AsyncTask<RequestBody, Void, Integer> {
        String errorMessage = "";

        @Override
        protected Integer doInBackground(RequestBody... requestBodies) {
            try {
                //http://10.4.0.152/ha/login.php?user_name=girish12345&password=Test@1234
                //{"login":[{"userid":"2","name":"Girish Mane","email":"girish@gmail.com","message":"Login Successfully.","status":1}]}
                //{"login":[{"message":"Wrong user name password.","status":2}]}
                String response = MakeCall.post("lo.php", requestBodies[0], Register.class.getSimpleName());
                if (response != null) {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("login")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("login");
                        if (jsonArray.length() > 0) {
                            JSONObject object = jsonArray.getJSONObject(0);
                            if (object.has("status")) {
                                int status = object.getInt("status");
                                if (status == 1) {
                                    HomePreferences.save("user_id", object.getString("userid"));
                                    HomePreferences.save("name", object.getString("name"));
                                    HomePreferences.save("email", object.getString("email"));
                                    HomePreferences.save("is_login", "1");

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
            if (response == 1) {
                SnackResponse.success(errorMessage, MainActivity.this);
                // thread to add delay of 1sec before going to next screen
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent nextIntent = new Intent(getApplicationContext(), Second2Activity.class);
                        startActivity(nextIntent, ActivityTransition.moveToNextAnimation(getApplicationContext()));
                    }
                }, 1000);

            } else {
                if (errorMessage.trim().length() <= 0) {
                    errorMessage = GetResponses_.getStatusMessage(response, getApplicationContext());
                }
                SnackResponse.failed(errorMessage, MainActivity.this);
            }
        }
    }

    private void login() {
        EditText userNameBox = (EditText) findViewById(R.id.Userid);
        EditText passwordBox = (EditText) findViewById(R.id.etPassword);

        String user_name = userNameBox.getText().toString().trim();
        String password = passwordBox.getText().toString().trim();


        RequestBody requestBody = new FormBody.Builder()
                .add("username", user_name)
                .add("password", password)
                .build();
        new Call().execute(requestBody);
    }

    @Override
    public void onBackPressed() {

    }


    public void btnWifi_onClick(View view) {
        Intent intentWifi;
        intentWifi = new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intentWifi);
    }

    public void btnregister_onClick(View view) {
        Intent intentr;
        intentr = new Intent(MainActivity.this, Register.class);
        startActivity(intentr);
    }
}

