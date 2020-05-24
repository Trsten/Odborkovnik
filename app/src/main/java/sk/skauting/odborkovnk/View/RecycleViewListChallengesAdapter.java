package sk.skauting.odborkovnk.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import sk.skauting.odborkovnk.R;

public class RecycleViewListChallengesAdapter extends RecyclerView.Adapter<RecycleViewListChallengesAdapter.ViewHolder> {

    private static final String TAG = "RecycleViewLCAdapter";

    private ArrayList<String> mImagesNames = new ArrayList<>();
    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mNumbersOfTasks = new ArrayList<>();
    private Context context;

    public RecycleViewListChallengesAdapter(Context context,ArrayList<String> mImagesNames, ArrayList<String> mTitles, ArrayList<String> mNumbersOfTasks ) {
        this.mImagesNames = mImagesNames;
        this.mTitles = mTitles;
        this.mNumbersOfTasks = mNumbersOfTasks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder : called.");
        Glide.with(context)
                .asBitmap()
                .load(mImagesNames.get(position))
                .into(holder.img);
        holder.title.setText(mTitles.get(position));
        holder.numberOfTasks.setText(mNumbersOfTasks.get(position));

//        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG,"onClick clicked on:" + mTitles.get(position));
//                Toast.makeText(context, mTitles.get(position), Toast.LENGTH_SHORT).show();
//            }
//        });
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
}
