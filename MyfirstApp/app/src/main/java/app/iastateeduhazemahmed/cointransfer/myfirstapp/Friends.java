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

public class Friends extends AppCompatActivity {

    private static final String REGISTER_URL = "http://proj-309-vc-5.cs.iastate.edu/list_friends.php";
    private EditText editTextUsername;
    private EditText editText2;
    Button button, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        getSupportActionBar().setTitle("Find friends");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTextUsername = (EditText) findViewById(R.id.UsernameField);
        editText2 = (EditText) findViewById(R.id.editText2);

        button = (Button) findViewById(R.id.checkFriends);
        button2 = (Button) findViewById(R.id.retrnHome);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Friends.this, Home.class);
                startActivity(i);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == button){
                    CheckForFriends();
                }
            }
        });
    }




    // Take the user ID and password to check if he/she is registered on the system or not.
    private void CheckForFriends() {
        String username = editTextUsername.getText().toString().trim().toLowerCase();
        check(username);
    }
    // Check the username and password against the data base.
    private void check(String username) {
        String urlSuffix = "?Username_1="+username;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Friends.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                String friends[] = s.split("<br>");
                String n = "";
                for(int i = 0; i< friends.length; i++) {
                    if (i == friends.length - 1) {
                        n += friends[i] + " ";
                    } else {
                        n += friends[i] + ", ";
                    }
                }
                // print the friends' names on the screen.
                editText2.setText(n);

                if(s.equals("Data Matched")||s=="Data Matched")
                {
                    try {
                        String s1 = editTextUsername.getText().toString();
                        Intent i1 = new Intent(Friends.this, Home.class);
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
