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


public class Sign extends AppCompatActivity {
    private static final String REGISTER_URL = "http://proj-309-vc-5.cs.iastate.edu/create_user.php";
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextConfirmPassword = (EditText) findViewById(R.id.confirmpassword);
        editTextFirstName = (EditText) findViewById(R.id.firstname);
        editTextLastName = (EditText) findViewById(R.id.lastname);
        editTextEmail = (EditText) findViewById(R.id.emailaddress);
        button = (Button) findViewById(R.id.signupBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == button){
                    registerUser();
//                    String email = editTextEmail.getText().toString();
//                    String[] newEmail = email.split("@");
//                    email = newEmail[0] + "%40" + newEmail[1];
//                Log.d("email",email);
                }
            }
        });
    }
    // Take the user ID and password to check if he/she is registered on the system or not.
    private void registerUser() {

        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmpassword = editTextConfirmPassword.getText().toString().trim();
        String firstname = editTextFirstName.getText().toString();
        String lastname  = editTextLastName.getText().toString();
        String email = editTextEmail.getText().toString();
        String[] newEmail = email.split("@");
        email = newEmail[0] + "%40" + newEmail[1];
        if(password.equals(confirmpassword)  || password == confirmpassword) {
            register(username, password, firstname, lastname, email);
        }else{
            Toast.makeText(getApplicationContext(),"password doesn't match",Toast.LENGTH_SHORT).show();
        }
    }
    // Check the username and password against the data base.
    private void register(String username, String password, String firstname, String lastname, String email) {
        String urlSuffix = "?First_Name="+firstname+"&Last_Name="+lastname+"&Email="+email + "&Username=" + username+ "&Password=" + password;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Sign.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();


                if(s.equals("Successfully created user!")|| s == "Successfully created user!")
                {
                    try {
                        String s1 = editTextUsername.getText().toString();
                        Intent i1 = new Intent(Sign.this, MainActivity.class);
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