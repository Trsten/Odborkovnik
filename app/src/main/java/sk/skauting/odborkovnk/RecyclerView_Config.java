package sk.skauting.odborkovnk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sk.skauting.odborkovnk.R;
import sk.skauting.odborkovnk.TaskChallenge;

public class RecyclerView_Config {

    private Context mContext;
    private ChallengeAdapter mChallengeAdapter;

    public void setConfig( RecyclerView recyclerView, Context context, List<TaskChallenge> taskChallenges, List<String> keys) {
        mContext = context;
        mChallengeAdapter = new ChallengeAdapter(taskChallenges,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mChallengeAdapter);
    }

    class ChallengeItem extends RecyclerView.ViewHolder {

        private TextView mUloha;

        private String key;

        public ChallengeItem(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.challenge_list_item,parent,false));
            mUloha = (TextView) itemView.findViewById(R.id.challange_task);

        }

        public void bind(TaskChallenge taskChallenge, String key) {
            mUloha.setText(taskChallenge.getUloha());
            this.key = key;
        }

    }
    class ChallengeAdapter extends RecyclerView.Adapter<ChallengeItem> {
        private List<TaskChallenge> mTaskChalangeList;
        private List<String> mKeys;

        public ChallengeAdapter(List<TaskChallenge> mTaskChalangeList, List<String> mKeys) {
            this.mTaskChalangeList = mTaskChalangeList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public ChallengeItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ChallengeItem(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ChallengeItem holder, int position) {
            holder.bind(mTaskChalangeList.get(position),mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mTaskChalangeList.size();
        }
    }
}
