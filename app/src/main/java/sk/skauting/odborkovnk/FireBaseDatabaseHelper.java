package sk.skauting.odborkovnk;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FireBaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<TaskChallenge> taskChallenges = new ArrayList<>();


    public interface DataStatus {
        void DataIsLoaded(List<TaskChallenge> taskChallenges, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FireBaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("odborky/Botanik");

    }

    public void readTaskChelenges(final DataStatus dataStatus) {
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskChallenges.clear();
                List<String> keys = new ArrayList<>();
                for ( DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    TaskChallenge taskChallenge = keyNode.getValue(TaskChallenge.class);
                    taskChallenges.add(taskChallenge);
                }

                dataStatus.DataIsLoaded(taskChallenges,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
