package sk.skauting.odborkovnk.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sk.skauting.odborkovnk.R;
import sk.skauting.odborkovnk.RegisterActivity;

public class RecycleViewTasksAdapter extends RecyclerView.Adapter<RecycleViewTasksAdapter.ViewHolder> {

    private static final String TAG = "RecycleViewTaskAdapter";

    private ArrayList<String> mTasks = new ArrayList<>();
    private ArrayList<Boolean> mCompleted = new ArrayList<>();
    private Context mContext;

    public RecycleViewTasksAdapter( Context mContext,ArrayList<String> mTasks, ArrayList<Boolean> mCompleted) {
        this.mTasks = mTasks;
        this.mCompleted = mCompleted;
        this.mContext = mContext;
    }

    public RecycleViewTasksAdapter( Context mContext,ArrayList<String> mTasks) {
        this.mTasks = mTasks;
        this.mContext = mContext;
        this.mCompleted = null;
    }

    public ArrayList<Boolean> getStateOfComplete() {
        return mCompleted;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder : called");
        holder.task.setText(mTasks.get(position));

        if ( mCompleted != null ) {
            holder.completed.setChecked(mCompleted.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.completed.toggle();
                    mCompleted.set(position,holder.completed.isChecked());
                }
            });
        } else {
            holder.completed.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView task;
        CheckBox completed;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            task = itemView.findViewById(R.id.taskListItem);
            completed = itemView.findViewById(R.id.completedDetail);
        }
    }

}
