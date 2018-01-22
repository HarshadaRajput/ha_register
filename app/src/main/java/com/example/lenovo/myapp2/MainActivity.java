package com.example.lenovo.myapp2;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button start_wifi;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView)findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);

        Info.setText("No. Of Attempts Remaining : 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });
    }
    private void  validate(String userName,String userPassword) {
        if ((userName.equals("Harshada")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, Second2Activity.class);
            startActivity(intent);
        } else {
            if ((userName.equals("Karuna")) && (userPassword.equals("1234"))) {
                Intent intent = new Intent(MainActivity.this, Second2Activity.class);
                startActivity(intent);
            } else {
                if ((userName.equals("Dipika")) && (userPassword.equals("1234"))) {
                    Intent intent = new Intent(MainActivity.this, Second2Activity.class);
                    startActivity(intent);
                } else {
                    if ((userName.equals("Punit")) && (userPassword.equals("1234"))) {
                        Intent intent = new Intent(MainActivity.this, Second2Activity.class);
                        startActivity(intent);
                    } else {
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
    public void btnWifi_onClick(View view){
        Intent intentWifi;
        intentWifi =  new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intentWifi);
    }
}

