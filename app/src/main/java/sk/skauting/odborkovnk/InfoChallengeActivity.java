package sk.skauting.odborkovnk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import sk.skauting.odborkovnk.Model.ChallengeTask;
import sk.skauting.odborkovnk.View.RecycleViewTasksAdapter;

public class InfoChallengeActivity extends AppCompatActivity {

    private static final String TAG = "ActivityInfoChallenge";

    private CircleImageView img;
    private String imgUrl;

    private Toolbar toolbar;

    private TextView textViewTitle;
    private String title;
    private ArrayList<String> mTasks;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        intent = getIntent();
        imgUrl = intent.getStringExtra("imgUrl");

        title = intent.getStringExtra("title");
        textViewTitle = (TextView) findViewById(R.id.txtDetailTitle);
        textViewTitle.setText(title);

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

        mTasks = intent.getStringArrayListExtra("tasks");

        RecyclerView recyclerView = findViewById(R.id.recycleViewDetail);
        final RecycleViewTasksAdapter adapter = new RecycleViewTasksAdapter(this,mTasks );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabAddChallenge);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key = intent.getStringExtra("key");
                intent.putExtra("keyChallenge", key);
                setResult(RESULT_OK,intent);

                finish();
                }
        });

    }

}
