package sk.skauting.odborkovnk.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import sk.skauting.odborkovnk.InfoChallengeActivity;
import sk.skauting.odborkovnk.R;

public class RecycleViewSelectChallenges extends RecyclerView.Adapter<RecycleViewSelectChallenges.ViewHolder> {
    private static final String TAG = "RecycleViewSelectCh";

    private ArrayList<String> mImageUrls;
    private ArrayList<String> mKeys;
    private ArrayList<String> mNumberOfTasks;
    private ArrayList<String> mTitles;
    private ArrayList<ArrayList<String>> mTasks;
    private Context mContex;

    public RecycleViewSelectChallenges(Context mContext, ArrayList<String> mImageUrls, ArrayList<String> mKeys,
                                       ArrayList<String> mNumberOfTasks, ArrayList<String> mTitles, ArrayList<ArrayList<String>> mTasks) {
        this.mImageUrls = mImageUrls;
        this.mKeys = mKeys;
        this.mNumberOfTasks = mNumberOfTasks;
        this.mTitles = mTitles;
        this.mContex = mContext;
        this.mTasks = mTasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        Log.d(TAG,"onBindViewHolder : called.");
        Glide.with(mContex)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.img);
        holder.title.setText(mTitles.get(position));
        holder.numberOfTasks.setText(mNumberOfTasks.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> task = new ArrayList<>();
                task = mTasks.get(position);
                Intent actvityInfo = new Intent(mContex, InfoChallengeActivity.class);
                actvityInfo.putExtra("imgUrl",mImageUrls.get(position));
                actvityInfo.putExtra("title", mTitles.get(position));
                actvityInfo.putExtra("key",mKeys.get(position));
                actvityInfo.putStringArrayListExtra("tasks",task);
                if (mContex instanceof Activity) {
                    ((Activity) mContex  ).startActivityForResult(actvityInfo,1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView img;
        TextView title;
        TextView numberOfTasks;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgChListItem);
            title = itemView.findViewById(R.id.titleChListItem);
            numberOfTasks = itemView.findViewById(R.id.numberOfTaskChListItem);
        }
    }

    private boolean alearedyHaveChallenge() {
        //TODO: compare challenges which i have
        return false;
    }
}
