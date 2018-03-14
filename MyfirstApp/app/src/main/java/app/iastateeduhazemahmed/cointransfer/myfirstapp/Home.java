package app.iastateeduhazemahmed.cointransfer.myfirstapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class Home extends AppCompatActivity {
    private


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button button, button2, button3, button4;
        //Set title
        getSupportActionBar().setTitle("Home");
        //Button Used for finding your friends
        button = (Button) findViewById(R.id.friendsBtn);
        //Button Used to check eligibility for enter the chatroom
        button2 = (Button) findViewById(R.id.checkpromisesBtn);
        //Button used to delete a friend
        button3 = (Button) findViewById(R.id.deletefrndBtn);
        //Button used for Adding a friend.
        button4 = (Button) findViewById(R.id.button3);

        //Button used for Adding a friend.
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, AddFriends.class);
                startActivity(i);
            }
        });
        //Button used to delete a friend
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, deleteFriends.class);
                startActivity(i);
            }
        });
        //Button Used to check eligibility for enter the chatroom
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, PromiseManager.class);
                startActivity(i);
            }
        });
        //Button Used for finding your friends
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Friends.class);
                startActivity(i);
            }
        });
    }


}