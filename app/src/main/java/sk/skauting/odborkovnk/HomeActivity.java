package sk.skauting.odborkovnk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import sk.skauting.odborkovnk.Model.Challenge;
import sk.skauting.odborkovnk.Model.ChallengeTask;
import sk.skauting.odborkovnk.Model.User;
import sk.skauting.odborkovnk.View.RecycleViewListChallengesAdapter;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private String email;

    private Toolbar toolbar;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference refDatabase;

    private String userID;

    private ProgressBar progressBar;

    private ArrayList<String> mImagesNames = new ArrayList<>();
    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mNumbersOfTasks = new ArrayList<>();
    private ArrayList<ArrayList<ChallengeTask>> mTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.RegToolbar);
        setSupportActionBar(toolbar);
        progressBar = findViewById(R.id. loadingDatapb);

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

        progressBar.setVisibility(View.VISIBLE);

        loadChallenges();

        RecyclerView recyclerView = findViewById(R.id.recycleViewHome);
        RecycleViewListChallengesAdapter adapter = new RecycleViewListChallengesAdapter(this,mImagesNames,mTitles,mNumbersOfTasks,mTasks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

        private String setTaskText(Challenge challenge) {
            if ( challenge.ngetNumerOfTask() != challenge.numberOfCompleted() ) {
                return "completed " +  String.valueOf(challenge.numberOfCompleted()) +
                        "/" + String.valueOf(challenge.ngetNumerOfTask());
            } else {
                return "successfully completed";
            }
        }

        private void loadChallenges() {
            refDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        if (user.getEmail().equals(email)) {

                            Map<String, Challenge> challenges = user.getChallenges();
                            for (Map.Entry<String, Challenge> challenge : challenges.entrySet()) {
                                mImagesNames.add(challenge.getValue().getImgFileName());
                                mTitles.add(challenge.getValue().getTitle());
                                mNumbersOfTasks.add(setTaskText(challenge.getValue()));

                                ArrayList<ChallengeTask> mArray = new ArrayList<>();
                                Map<String, ChallengeTask> tasks = challenge.getValue().getTasks();
                                for (Map.Entry<String, ChallengeTask> task : tasks.entrySet()) {
                                    mArray.add(task.getValue());
                                }
                            mTasks.add(0,mArray);
                            }
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
}
