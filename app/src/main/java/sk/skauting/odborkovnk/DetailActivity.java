package sk.skauting.odborkovnk;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import sk.skauting.odborkovnk.Model.ChallengeTask;
import sk.skauting.odborkovnk.View.RecycleViewChallengesAdapter;
import sk.skauting.odborkovnk.View.RecycleViewTasksAdapter;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    private CircleImageView img;
    private String imgUrl;

    private Toolbar toolbar;

    private TextView textViewTitle;
    private String title;

    private ArrayList<Boolean> mCompleted;
    private ArrayList<String> mTasks;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference refDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d(TAG,"onCreated: started");


        final Intent intent = getIntent();
        imgUrl = intent.getStringExtra("imgUrl");

        title = intent.getStringExtra("title");
        textViewTitle = (TextView) findViewById(R.id.txtDetailTitle);
        textViewTitle.setText(title);

        mCompleted = new ArrayList<>();
        boolean[] completed = intent.getBooleanArrayExtra("completed");
        for ( int i = 0 ; i < completed.length; i++ ) {
            mCompleted.add(completed[i]);
        }

        mTasks = new ArrayList<>();
        mTasks = intent.getStringArrayListExtra("task");

        img = (CircleImageView) findViewById(R.id.imgDetailChallenge);
        Glide.with(this)
                .asBitmap()
                .load(imgUrl)
                .into(img);

        toolbar = findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycleViewDetail);
        final RecycleViewTasksAdapter adapter = new RecycleViewTasksAdapter(this,mTasks,mCompleted);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabSaveChanges);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] result = new boolean[mCompleted.size()];
                int i = 0;
                for ( Boolean bl : mCompleted ) {
                    result[i] = bl;
                    i++;
                }

                adapter.getStateOfComplete();
                Intent resultIntend = new Intent();
                resultIntend.putExtra("result",result);
                resultIntend.putExtra("title",title);
                resultIntend.putExtra("position",intent.getIntExtra("position",0));

                setResult(RESULT_OK,resultIntend);

                firebaseDatabase = FirebaseDatabase.getInstance();
                String pathToChallenge = intent.getStringExtra("path");
                refDatabase = firebaseDatabase.getReference(pathToChallenge);

                ArrayList<String> str = intent.getStringArrayListExtra("taskPath");

                for (int j = 0 ; j < str.size(); j++) {
                    String key = str.get(j);

                    String value = mCompleted.get(j) ? "true" : "false";

                    ChallengeTask chTsk = new ChallengeTask( value , mTasks.get(j));
                    Map<String,Object> values = chTsk.toMap();

                    Map<String,Object> childUpdates = new HashMap<>();
                    childUpdates.put("/tasks/" + key ,values);
                    refDatabase.updateChildren(childUpdates);
                }
                finish();
            }
        });

    }
}

