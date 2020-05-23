package sk.skauting.odborkovnk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListOfChallenges extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textViewTitle;

    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference refDatabase;
    private List<TaskChallenge> challenges;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_challenges);

        toolbar = (Toolbar) findViewById(R.id.SelToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //textViewTitle = (TextView) findViewById(R.id.SelTextViewTitle);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        refDatabase = database.getReference("odborky/Botanik");
        challenges = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.SelRecycleView);
        new FireBaseDatabaseHelper().readTaskChelenges(new FireBaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<TaskChallenge> taskChallenges, List<String> keys) {
                findViewById(R.id.loading_task_pb).setVisibility(View.GONE);
                new RecyclerView_Config().setConfig(mRecyclerView,ListOfChallenges.this,
                        taskChallenges,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

    }

    public void readChallenges() {
        refDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                challenges.clear();
                List<String> keys = new ArrayList<>();
                for ( DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    TaskChallenge taskChallenge = keyNode.getValue(TaskChallenge.class);
                    challenges.add(taskChallenge);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListOfChallenges.this, "Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
