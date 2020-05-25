package sk.skauting.odborkovnk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ProgressBar;

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
import sk.skauting.odborkovnk.View.RecycleViewSelectChallenges;

public class ChallengeActivity extends AppCompatActivity {

    private static final String TAG = "ChallengeActivity";

    private Toolbar toolbar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference refDatabase;

    private ArrayList<String> mImageUrls;
    private ArrayList<String> mKeys;
    private ArrayList<String> mNumberOfTasks;
    private ArrayList<String> mTitle;
    private ArrayList<ArrayList<String >> mTasks;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        toolbar = findViewById(R.id.selChellengeToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mImageUrls = new ArrayList<>();
        mKeys = new ArrayList<>();
        mNumberOfTasks = new ArrayList<>();
        mTitle = new ArrayList<>();
        mTasks = new ArrayList<ArrayList<String>>();

        progressBar = (ProgressBar) findViewById(R.id.loadingDataSelectPb);

        firebaseDatabase = FirebaseDatabase.getInstance();
        refDatabase = firebaseDatabase.getReference("challenges");

        progressBar.setVisibility(View.VISIBLE);

        loadChallenges();

        RecyclerView recyclerView = findViewById(R.id.recycleViewSelect);
        RecycleViewSelectChallenges adapter = new RecycleViewSelectChallenges(this,mImageUrls, mKeys,mNumberOfTasks,mTitle,mTasks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadChallenges() {
        refDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Challenge challenge = ds.getValue(Challenge.class);
                    mImageUrls.add(challenge.getImageUrl());
                    mKeys.add(ds.getKey());
                    mNumberOfTasks.add( "number of tasks: " + challenge.ngetNumerOfTask());
                    mTitle.add(challenge.getTitle());

                    ArrayList<String> localTasks = new ArrayList<>();
                    Map<String, ChallengeTask> tasks = challenge.getTasks();
                    for (Map.Entry<String, ChallengeTask> task : tasks.entrySet()) {
                        localTasks.add(task.getValue().getTask());
                    }
                    mTasks.add(localTasks);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "prekreslujem : onCreate");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == 1 ) {
            if ( resultCode == RESULT_OK ) {
                if ( data != null) {
                    String key = data.getStringExtra("keyChallenge");
                    Intent intent = new Intent();
                    intent.putExtra("keyChallenge",key);

                    setResult(RESULT_OK,intent);

                    finish();
                }
            }
        }

    }
}
