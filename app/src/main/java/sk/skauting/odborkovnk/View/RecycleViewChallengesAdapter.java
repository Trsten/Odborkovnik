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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import sk.skauting.odborkovnk.DetailActivity;
import sk.skauting.odborkovnk.Model.ChallengeTask;
import sk.skauting.odborkovnk.R;

public class RecycleViewChallengesAdapter extends RecyclerView.Adapter<RecycleViewChallengesAdapter.ViewHolder> {

    private static final String TAG = "RecycleViewLCAdapter";

    private ArrayList<String> mImagesNames = new ArrayList<>();
    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mNumbersOfTasks = new ArrayList<>();
    private ArrayList<ArrayList<ChallengeTask>> mTasks = new ArrayList<>();
    private ArrayList<ArrayList<String>> mTaskPath = new ArrayList<>();
    private ArrayList<String> mChallengePath = new ArrayList<>();
    private Context context;

    public RecycleViewChallengesAdapter(Context context, ArrayList<String> mImagesNames, ArrayList<String> mTitles,ArrayList<String> mNumbersOfTasks,
                                        ArrayList<ArrayList<ChallengeTask>>  mTasks,ArrayList<String> mChallengePath,ArrayList<ArrayList<String>> mTaskPath ) {
        this.mImagesNames = mImagesNames;
        this.mTitles = mTitles;
        this.mNumbersOfTasks = mNumbersOfTasks;
        this.mTasks = mTasks;
        this.context = context;
        this.mChallengePath = mChallengePath;
        this.mTaskPath = mTaskPath;
    }

    public void update(List<String> newList)
    {
        ChalDiffUtilCallBack diffUtilCallBack = new ChalDiffUtilCallBack(this.mNumbersOfTasks,newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallBack);

        this.mNumbersOfTasks.clear();
        this.mNumbersOfTasks.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder : called.");
        for ( String asd : mNumbersOfTasks ) {
            Log.d(TAG, "onBindHolder : values " + asd);
        }
        Glide.with(context)
                .asBitmap()
                .load(mImagesNames.get(position))
                .into(holder.img);
        holder.title.setText(mTitles.get(position));
        holder.numberOfTasks.setText(mNumbersOfTasks.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick clicked on:" + mTitles.get(position));
                ArrayList<ChallengeTask> arrayList = mTasks.get(position);

                ArrayList<String> tasks = new ArrayList<>();
                boolean[] completed = new boolean[arrayList.size()];
                int i =0;
                for ( ChallengeTask tsk : arrayList ) {
                    tasks.add(tsk.getTask());
                    completed[i] = tsk.getComplete().equals("true");
                    i++;
                }
                Intent detailActivity = new Intent(context, DetailActivity.class);
                detailActivity.putExtra("position", position);
                detailActivity.putStringArrayListExtra("task",tasks);
                detailActivity.putStringArrayListExtra("taskPath",mTaskPath.get(position));
                detailActivity.putExtra("path",mChallengePath.get(position));
                detailActivity.putExtra("completed",completed);
                detailActivity.putExtra("imgUrl",mImagesNames.get(position));
                detailActivity.putExtra("title", mTitles.get(position));
                if (context instanceof Activity) {
                    ((Activity) context  ).startActivityForResult(detailActivity,1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNumbersOfTasks.size();
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
}
