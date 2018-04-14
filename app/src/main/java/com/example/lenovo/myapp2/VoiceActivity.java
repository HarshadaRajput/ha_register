package com.example.lenovo.myapp2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class VoiceActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean recordOn = false;

    private TextView resultTEXT;
    private AppCompatImageView recordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_activity_layout);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        resultTEXT = (TextView) findViewById(R.id.TVresult2);

        recordButton = (AppCompatImageView) findViewById(R.id.imageButton);
        recordButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        HomePreferences.initialize(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.voice_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                HomePreferences.save("is_login", "0");
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
        return true;
    }


    private void toggleRecord() {
        if (recordOn) {
            recordOn = false;
            recordButton.setImageResource(R.drawable.ic_recording_off);
        } else {
            recordOn = true;
            recordButton.setImageResource(R.drawable.ic_recording_on);
        }
    }

    public void promptSpeechInput() {
        if (recordOn) {
            Toast.makeText(VoiceActivity.this, "Already Recording....", Toast.LENGTH_LONG).show();
            return;
        }
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "SAY SOMETHING!");

        try {
            startActivityForResult(i, 100);
            toggleRecord();
        } catch (ActivityNotFoundException a) {
            Toast.makeText(VoiceActivity.this, "Sorry! your device doesn't support speech language", Toast.LENGTH_LONG).show();
        }
    }

    private String output(String word) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Word: ");
        stringBuilder.append(word);
        stringBuilder.append("\n");
        //      stringBuilder.append("Code Word: ");
        //     stringBuilder.append(MakeCode.getCode(word));
//        stringBuilder.append("\n");
//        stringBuilder.append("Command Code: ");
        int code = Dictionary.getCode(MakeCode.getCode(word));
        //  stringBuilder.append(code);
        stringBuilder.append("\n");
        if (code <= 0) {
            stringBuilder.append("Invalid Voice Command");
        } else {
            executeCommand(code);
            stringBuilder.append("Valid Voice Command");
        }
        return stringBuilder.toString();
    }

    private void executeCommand(int code) {
        RequestBody requestBody = new FormBody.Builder()
                .add("action", "" + code)
                .add("userid", HomePreferences.get("user_id"))
                .build();
        new CallOperation().execute(requestBody);
    }

    private class CallOperation extends AsyncTask<RequestBody, Void, Integer> {
        String errorMessage = "Something went wrong";

        @Override
        protected Integer doInBackground(RequestBody... requestBodies) {
            int result = 11;
            try {
                String response = MakeCall.post("op.php", requestBodies[0], VoiceActivity.class.getSimpleName());
                //{"status":"1","action":2,"message":"All Off"}
                if (response != null) {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("status")) {
                        result = jsonObject.getInt("status");
                        if (result == 1) {
                            errorMessage = jsonObject.getString("message");
                        } else {
                            errorMessage = jsonObject.getString("message");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer == 1) {
                SnackResponse.success(errorMessage, VoiceActivity.this);
            } else {
                SnackResponse.failed(errorMessage, VoiceActivity.this);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        super.onActivityResult(requestCode, resultCode, i);
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK && i != null) {
                    ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.e(VoiceActivity.class.getSimpleName(), "result: " + result);
                    resultTEXT.setText(output(result.get(0)));
                    toggleRecord();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton:
                promptSpeechInput();
                break;
        }
    }
}


