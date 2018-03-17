package com.example.lenovo.myapp2;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private EditText Userid;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button Register;
    private Button start_wifi;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Userid = (EditText) findViewById(R.id.Userid);
        Password = (EditText) findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);
        Login.setOnClickListener((View.OnClickListener) this);
        Register = (Button) findViewById(R.id.btnregister);
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
    private class Call extends AsyncTask<RequestBody, Void, String> {
        @Override
        protected String doInBackground(RequestBody... requestBodies) {
            try {
                return MakeCall.post("http://192.168.8.151/login.php", requestBodies[0], Register.class.getSimpleName());
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("login");
                JSONObject object = jsonArray.getJSONObject(0);
                String id = object.getString("user_id");
                String name = object.getString("name");
                Toast.makeText(getApplicationContext(), "Name: " + name, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void login() {
        EditText userid = (EditText) findViewById(R.id.Userid);
        EditText password = (EditText) findViewById(R.id.etPassword);

        String Userid = userid.getText().toString();
        String Password = password.getText().toString();


        RequestBody requestBody = new FormBody.Builder()
                .add("Userid", Userid)
                .add("Password", Password)
                .build();
        new Call().execute(requestBody);
    }

    @Override
    public void onBackPressed() {

    }



    public void btnWifi_onClick(View view){
        Intent intentWifi;
        intentWifi =  new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intentWifi);
    }
    public void btnregister_onClick(View view){
        Intent intentr;
        intentr = new Intent(MainActivity.this, Register.class);
        startActivity(intentr);
    }
}

