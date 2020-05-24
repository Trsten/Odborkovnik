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
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    private CircleImageView img;
    private String imgUrl;

    private Toolbar toolbar;

    private TextView textViewTitle;
    private String title;

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

        img = (CircleImageView) findViewById(R.id.imgDetailChallenge);
        Glide.with(this)
                .asBitmap()
                .load(imgUrl)
                .into(img);

        toolbar = findViewById(R.id.RegToolbarDetail);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

}
