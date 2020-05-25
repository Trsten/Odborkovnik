package sk.skauting.odborkovnk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import sk.skauting.odborkovnk.View.RecycleViewChallengesAdapter;

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
    private ArrayList<String> mChallengePath = new ArrayList<>();
    private ArrayList<ArrayList<String>> mTaskPath = new ArrayList<>();
    private String mPath;

    private RecycleViewChallengesAdapter adapter;

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
                startActivityForResult(listOfChallenges,2);
            }
        });

        mPath = "users/";
        mChallengePath = new ArrayList<>();

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        refDatabase = firebaseDatabase.getReference("users");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        progressBar.setVisibility(View.VISIBLE);

        loadChallenges();

        final RecyclerView recyclerView = findViewById(R.id.recycleViewHome);
        adapter = new RecycleViewChallengesAdapter(this,mImagesNames,mTitles,mNumbersOfTasks,mTasks,mChallengePath,mTaskPath);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                refDatabase = firebaseDatabase.getReference(mChallengePath.get(position));
                refDatabase.removeValue();
                adapter.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        helper.attachToRecyclerView(recyclerView);
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
                    mImagesNames.clear();
                    mTitles.clear();
                    mNumbersOfTasks.clear();
                    mChallengePath.clear();
                    mTasks.clear();
                    mTaskPath.clear();
                    mPath = "users/";
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        if (user.getEmail().equals(email)) {
                            mPath += ds.getKey();
                            Map<String, Challenge> challenges = user.getChallenges();
                            for (Map.Entry<String, Challenge> challenge : challenges.entrySet()) {
                                mImagesNames.add(challenge.getValue().getImageUrl());
                                mTitles.add(challenge.getValue().getTitle());
                                mNumbersOfTasks.add(setTaskText(challenge.getValue()));
                                mChallengePath.add(mPath + "/challenges/" + challenge.getKey());

                                ArrayList<ChallengeTask> mArray = new ArrayList<>();
                                ArrayList<String> mArKey = new ArrayList<>();
                                Map<String, ChallengeTask> tasks = challenge.getValue().getTasks();
                                for (Map.Entry<String, ChallengeTask> task : tasks.entrySet()) {

                                    mArray.add(task.getValue());
                                    mArKey.add(task.getKey());
                                }
                            mTasks.add(mArray);
                            mTaskPath.add(mArKey);
                            }
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            Log.d(TAG,"prekreslujem : onCreate");
        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<Boolean> newCompleted = new ArrayList<>();
        if ( requestCode == 1 ) {
            if ( resultCode == RESULT_OK ) {
                if ( data != null) {
                    int completedTasks = 0;
                    boolean[] completed = data.getBooleanArrayExtra("result");
                    for ( int i = 0 ; i < completed.length; i++ ) {
                        newCompleted.add(completed[i]);
                        completedTasks += ( completed[i] ? 1 : 0 );
                    }
                    int position = data.getIntExtra("position",0);

                    String nmbTask = "";
                    if ( newCompleted.size() == completedTasks ) {
                        nmbTask = "successfully completed";
                    } else {
                        nmbTask = "completed " + completedTasks + "/" + newCompleted.size();
                    }
                    ArrayList<String> newNumbersOfTasks = new ArrayList<String>();

                    for ( String s : this.mNumbersOfTasks) {
                        newNumbersOfTasks.add(s);
                    }
                    newNumbersOfTasks.set(position,nmbTask);
                    adapter.update(newNumbersOfTasks);
                }
            }
        } else if ( requestCode == 2) {
            if ( resultCode == RESULT_OK ) {
                final String keyChallenge = data.getStringExtra("keyChallenge");
                refDatabase = firebaseDatabase.getReference("challenges" );
                refDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if ( ds.getKey().equals(keyChallenge) ) {
                                Challenge ch = ds.getValue(Challenge.class);
                                refDatabase = firebaseDatabase.getReference(mPath+ "/challenges");
                                String keyId = refDatabase.push().getKey();
                                refDatabase.child(keyId).setValue(ch);
                                adapter.insertData(ch,setTaskText(ch),mPath + "/challenges/" + ds.getKey() );
                                adapter.notifyDataSetChanged();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }

    }

}
