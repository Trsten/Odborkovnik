package sk.skauting.odborkovnk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference refDatabase;
    private String email;

    private TextView textViewFullname;
    private TextView textViewSUnit;
    private TextView textViewSNickName;
    private TextView textViewEmail;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.RegToolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fabAddChallenge);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listOfChallenges = new Intent(HomeActivity.this, ListOfChallenges.class);
                listOfChallenges.putExtra("user",email);
                startActivity(listOfChallenges);
            }
        });

        textViewFullname = (TextView) findViewById(R.id.textViewFullname);
        textViewSUnit = (TextView) findViewById(R.id.textViewSUnit);
        textViewSNickName = (TextView) findViewById(R.id.textViewSNickname);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        refDatabase = database.getReference("users");

        refDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("email").getValue().equals(email)) {
                        textViewFullname.setText(ds.child("fullname").getValue(String.class));
                        textViewSNickName.setText(ds.child("scoutNickname").getValue(String.class));
                        textViewSUnit.setText(ds.child("scoutUnit").getValue(String.class));
                        textViewEmail.setText(email);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            finish();
        } else if(id == R.id.home) {
            Toast.makeText(getApplicationContext(),"home", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.finished) {
            Toast.makeText(getApplicationContext(),"finished", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.actual) {
            Toast.makeText(getApplicationContext(),"actual", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
