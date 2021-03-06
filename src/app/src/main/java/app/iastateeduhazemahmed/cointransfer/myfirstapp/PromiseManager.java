package app.iastateeduhazemahmed.cointransfer.myfirstapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PromiseManager extends AppCompatActivity {

    private static final String REGISTER_URL = "http://proj-309-vc-5.cs.iastate.edu/PromiseCheck.php";
    private EditText editTextUsername;
    private EditText editTextPassword;
    Button button, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promisemanager);

        getSupportActionBar().setTitle("Check for existing promise");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTextUsername = (EditText) findViewById(R.id.promiseID);
        editTextPassword = (EditText) findViewById(R.id.userID);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PromiseManager.this, Home.class);
                startActivity(i);
            }
        });
        button = (Button) findViewById(R.id.checkBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == button){
                    CheckForPromise();
                }
            }
        });
    }




    // Take the user ID and password to check if he/she is registered on the system or not.
    private void CheckForPromise() {
        String PID = editTextUsername.getText().toString();
        Log.d("PID",PID);
        String username = editTextPassword.getText().toString().trim().toLowerCase();
        Log.d("username", username);
        check(PID,username);
    }
    // Check the username and password against the data base.
    private void check(String PID, String username) {
        String urlSuffix = "?PID="+PID+"&user="+username;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PromiseManager.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();


                if(s.equals("You are eligible to participate in the chat.") || s == "You are eligible to participate in the chat.")
                {
                    try {
                        String s1 = editTextUsername.getText().toString();
                        Intent i1 = new Intent(PromiseManager.this, ChatRoom.class);
                        i1.putExtra("arg", s1);
                        startActivity(i1);
                    }
                    catch (Exception e)
                    {
                        Log.d("exception123", e.toString());
                        Toast.makeText(getApplicationContext(),"Check your Internet connection",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                }

            }

            // Sends an HTTP request to the server.
            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Check your Internet connection",Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }

    //Useless
    @Override
    public void onBackPressed() {
        // do nothing.
    }

}