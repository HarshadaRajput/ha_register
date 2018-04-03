package com.example.lenovo.myapp2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import okhttp3.FormBody;
import okhttp3.RequestBody;



public class RegisterActivity extends AppCompatActivity {

    private String TAG = RegisterActivity.class.getSimpleName();


    private EditText Userid;
    private EditText Firstname;
    private EditText Lastname;
    private EditText Email;
    private EditText Passwd;

    //Widget name should start with small caps if two words then second word first letter capital
    private Button registerButton;

    /*private static String url_create_product = "https://api.androidhive.info/android_connect/create_product.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Userid = (EditText) findViewById(R.id.userid);
        Firstname = (EditText) findViewById(R.id.firstname);
        Lastname = (EditText) findViewById(R.id.lastname);
        Email = (EditText) findViewById(R.id.email);
        Passwd = (EditText) findViewById(R.id.passwd);
        registerButton = (Button) findViewById(R.id.btnregister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private class Call extends AsyncTask<RequestBody, Void, String> {


        private ProgressDialog pDialog;

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
        protected String doInBackground(RequestBody... requestBodies) {
            try {
                String response = MakeCall.post("re.php", requestBodies[0], RegisterActivity.class.getSimpleName());
                Log.e(TAG, "res" + response);
            } catch (Exception e) {

                e.printStackTrace();

            }
            return null;
        }

       @Override
       protected void onPostExecute(String file_url) {
           // dismiss the dialog once done
           pDialog.dismiss();


       }


      /* protected void onPostExecute(String file_url,Integer response) {
           pDialog.dismiss();
           super.onPostExecute(String.valueOf(response));


           String errorMessage = null;
           if (response == 1) {
               SnackResponse.success(errorMessage, RegisterActivity.this);
               // thread to add delay of 1sec before going to next screen
           } else {
               if (errorMessage.trim().length() <= 0) {
                   errorMessage = GetResponses_.getStatusMessage(response, getApplicationContext());
               }
               SnackResponse.failed(errorMessage, RegisterActivity.this);
           }
       }*/
    }



        /**
         * Background Async Task to Create new product
         */
        //abstract class CreateNewUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
       /* @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }
*/

        /**
         * Creating product
         */
        private void register() {
            String userid = Userid.getText().toString();
            String firstname = Firstname.getText().toString();
            String lastname = Lastname.getText().toString();
            String email = Email.getText().toString();
            String passwd = Passwd.getText().toString();

            RequestBody requestBody = new FormBody.Builder()
                    .add("username", userid)
                    .add("firstname", firstname)
                    .add("email", email)
                    .add("lastname", lastname)
                    .add("password", passwd)


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
                    Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
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






