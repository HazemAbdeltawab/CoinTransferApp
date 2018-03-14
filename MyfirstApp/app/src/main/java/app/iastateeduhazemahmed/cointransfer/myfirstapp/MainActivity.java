package app.iastateeduhazemahmed.cointransfer.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    private static final String REGISTER_URL = "http://proj-309-vc-5.cs.iastate.edu/login.php";
    private EditText editTextUsername;
    private EditText editTextPassword;
    Button button, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_shiwei);
        editTextUsername = (EditText) findViewById(R.id.emailField);
        editTextPassword = (EditText) findViewById(R.id.passwordField);
        Log.d("bla", "welcome to the app");
        button = (Button) findViewById(R.id.loginBtn);
        button2 = (Button) findViewById(R.id.signBtn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Sign.class);
                startActivity(i);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == button){
                    registerUser();
                }
            }
        });
    }
// Take the user ID and password to check if he/she is registered on the system or not.
    private void registerUser() {
        String username = editTextUsername.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();
        register(username,password);
    }
// Check the username and password against the data base.
    private void register(String username, String password) {
        String urlSuffix = "?U="+username+"&P="+password;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();


                if(s.equals("Data Matched")||s=="Data Matched")
                {
                    try {
                        String s1 = editTextUsername.getText().toString();
                        Intent i1 = new Intent(MainActivity.this, Home.class);
                        i1.putExtra("arg", s1);
                        startActivity(i1);
                    }
                    catch (Exception e)
                    {
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

