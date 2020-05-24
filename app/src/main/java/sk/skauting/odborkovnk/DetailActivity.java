package sk.skauting.odborkovnk;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d(TAG,"onCreated: started");


        Intent intent = getIntent();
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
        RecycleViewTasksAdapter adapter = new RecycleViewTasksAdapter(this,mTasks,mCompleted);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
