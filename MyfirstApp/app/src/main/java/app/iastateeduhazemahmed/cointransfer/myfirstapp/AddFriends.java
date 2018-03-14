package app.iastateeduhazemahmed.cointransfer.myfirstapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddFriends extends AppCompatActivity {
    // declaration of variables.
    private static final String REGISTER_URL = "http://proj-309-vc-5.cs.iastate.edu/add_friend.php";
    private EditText editTextUsername;
    private EditText editTextUsername2;
    Button button, button2;

    // Function start up.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);
        editTextUsername = (EditText) findViewById(R.id.usernameaddFriend);
        editTextUsername2 = (EditText) findViewById(R.id.FriendAddFriend);
        button = (Button) findViewById(R.id.addfrndBtn);
        getSupportActionBar().setTitle("Add Friends");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        button2 = (Button) findViewById(R.id.retrnhomeadfrndBtn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddFriends.this, Home.class);
                startActivity(i);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( view == button)
                {
                    registerUser();
                }
            }
        });
    }

    private void registerUser() {
        String username = editTextUsername.getText().toString().trim().toLowerCase();
        String friend = editTextUsername2.getText().toString().trim().toLowerCase();
        register(username,friend);
    }
    // Check the username and password against the data base.
    private void register(String username, String friend) {
        String urlSuffix = "?Username_1="+username+"&Username_2="+friend;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddFriends.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();


                if(s.equals("Successfully added friend!")||s=="Successfully added friend!")
                {
                    try {
                        String s1 = editTextUsername.getText().toString();
                        Toast.makeText(getApplicationContext(),"Friend successfuly added, go back to your home page now", Toast.LENGTH_SHORT).show();
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