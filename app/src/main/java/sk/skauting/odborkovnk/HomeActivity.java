package sk.skauting.odborkovnk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference refDatabase;
    private String email;

    private TextView textViewFullname;
    private TextView textViewSUnit;
    private TextView textViewSNickName;
    private TextView textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               createOdborka();
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

    private void createOdborka() {

    }

}
