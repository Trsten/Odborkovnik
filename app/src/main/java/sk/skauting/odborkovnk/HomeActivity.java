package sk.skauting.odborkovnk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sk.skauting.odborkovnk.Model.User;

public class HomeActivity extends AppCompatActivity {

    private String email;

    private RecyclerView mRecyclerChallenges;

    private Toolbar toolbar;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference refDatabase;

    private String userID;

    private ListView mLiestWiev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.RegToolbar);
        setSupportActionBar(toolbar);

        mRecyclerChallenges = (RecyclerView) findViewById(R.id.recycleViewHome);

        FloatingActionButton fab = findViewById(R.id.fabAddChallenge);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listOfChallenges = new Intent(HomeActivity.this, ChallengeActivity.class);
                listOfChallenges.putExtra("user", email);
                startActivity(listOfChallenges);
            }
        });

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        refDatabase = firebaseDatabase.getReference("users");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        refDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for ( DataSnapshot ds : dataSnapshot.getChildren() ) {
                    User user = ds.getValue(User.class);
                    if ( user.getEmail().equals(email) ) {
                        String str = user.getEmail();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){
            int id = item.getItemId();
            if (id == R.id.logout) {
                finish();
            } else if (id == R.id.home) {
                Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.finished) {
                Toast.makeText(getApplicationContext(), "finished", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.actual) {
                Toast.makeText(getApplicationContext(), "actual", Toast.LENGTH_SHORT).show();
            }
            return true;
        }



}
