package sk.skauting.odborkovnk.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sk.skauting.odborkovnk.R;

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder : called");
        holder.completed.setChecked(mCompleted.get(position));
        holder.task.setText(mTasks.get(position));
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
