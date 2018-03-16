package com.example.lenovo.myapp2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import okhttp3.FormBody;
import okhttp3.RequestBody;



public class Register extends AppCompatActivity {

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    private EditText Userid;
    private EditText Firstname;
    private EditText Lastname;
    private EditText Email;
    private EditText Passwd;


    private Button Register;

    private static String url_create_product = "https://api.androidhive.info/android_connect/create_product.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Userid = (EditText) findViewById(R.id.userid);
        Firstname = (EditText) findViewById(R.id.firstname);
        Lastname = (EditText) findViewById(R.id.lastname);
        Email = (EditText) findViewById(R.id.email);
        Passwd = (EditText) findViewById(R.id.passwd);
        Register = (Button) findViewById(R.id.btnregister);


        // button click event
        Register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewUser() {
                    @Override
                    protected String doInBackground(String... strings) {
                        return null;
                    }
                }.execute();
            }
        });
    }

    private class Call extends AsyncTask<RequestBody, Void, String> {
        @Override
        protected String doInBackground(RequestBody... requestBodies) {
            try {
                MakeCall.post("http://192.168.88.2/batu/register.php", requestBodies[0], Register.class.getSimpleName());
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }
    }

    /**
     * Background Async Task to Create new product
     */
    abstract class CreateNewUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

    /**
     * Creating product
     */
    protected void doInBackground(String... args) {
        String userid = Userid.getText().toString();
        String firstname = Firstname.getText().toString();
        String lastname = Lastname.getText().toString();
        String email = Email.getText().toString();
        String passwd = Passwd.getText().toString();

        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", passwd)
                .add("firstname", firstname)
                .add("lastname", lastname)
                .add("userid", userid)


                .build();
        new Call().execute(requestBody);


    }
}



            /*// Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Userid", userid));
            params.add(new BasicNameValuePair("Firstname", firstname));
            params.add(new BasicNameValuePair("Lastname", lastname));
            params.add(new BasicNameValuePair("Email", email));
            params.add(new BasicNameValuePair("Passwd", passwd));


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = ((Object) jsonParser).makeHttpRequest(url_create_product, "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), Register.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
       /* protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
    }
}*/






