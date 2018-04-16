package com.example.lenovo.myapp2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameBox, passwordBox, serverBox;
    private TextView Info, registerLink;
    private Button loginButton;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);

        serverBox = (EditText) findViewById(R.id.server_address);
        usernameBox = (EditText) findViewById(R.id.user_name);
        passwordBox = (EditText) findViewById(R.id.user_password);

        Info = (TextView) findViewById(R.id.tvInfo);
        loginButton = (Button) findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(this);
        registerLink = (TextView) findViewById(R.id.btnregister);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Shared preferences to save data
        HomePreferences.initialize(getApplicationContext());
        serverBox.setText("http://Your Device IP/");
        usernameBox.setText("");//girish
        passwordBox.setText("");//123456789
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkWifi();
        registerReceiver(WifiReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
    }

    protected void onPause() {
        super.onPause();
        unregisterReceiver(WifiReceiver);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                login();
                break;
        }
    }

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
                String response = MakeCall.post("lo.php", requestBodies[0], RegisterActivity.class.getSimpleName());
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
                HomePreferences.save("is_login", "1");
                SnackResponse.success(errorMessage, LoginActivity.this);
                // thread to add delay of 1sec before going to next screen
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent nextIntent = new Intent(getApplicationContext(), VoiceActivity.class);
                        startActivity(nextIntent, ActivityTransition.moveToNextAnimation(getApplicationContext()));
                        finish();
                    }
                }, 1000);

            } else {
                if (errorMessage.trim().length() <= 0) {
                    errorMessage = GetResponses_.getStatusMessage(response, getApplicationContext());
                }
                SnackResponse.failed(errorMessage, LoginActivity.this);
            }
        }
    }

    private void login() {
        String serverAddress = serverBox.getText().toString().trim();
        if (serverAddress.length() <= 0) {
            serverBox.setError("Enter Server Address");
            return;
        }
        HomePreferences.save("server", serverAddress);

        String user_name = usernameBox.getText().toString().trim();
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

    private BroadcastReceiver WifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int WifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);

            switch (WifiState) {
                case WifiManager.WIFI_STATE_ENABLED:
                    checkWifi();
                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    checkWifi();
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN: {
                }
                break;

            }
        }
    };

    private void checkWifi() {
        WifiManager wifi = (WifiManager) this.getApplicationContext().getSystemService(Context
                .WIFI_SERVICE);
        assert wifi != null;
        if (!wifi.isWifiEnabled()) {
            findViewById(R.id.login_wifi_warning_layout).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.login_wifi_warning_layout).setVisibility(View.GONE);
        }
    }

    public void btnWifi_onClick(View view) {
        Intent intentWifi;
        intentWifi = new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intentWifi);
    }

    public void btnregister_onClick(View view) {
        String serverAddress = serverBox.getText().toString().trim();
        if (serverAddress.length() <= 0) {
            serverBox.setError("Enter Server Address");
            return;
        }
        HomePreferences.save("server", serverAddress);

        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}

