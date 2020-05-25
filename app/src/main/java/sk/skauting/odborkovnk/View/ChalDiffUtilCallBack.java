package sk.skauting.odborkovnk.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class ChalDiffUtilCallBack extends DiffUtil.Callback {

    private final List<String> odlList;
    private final List<String> newList;

    public ChalDiffUtilCallBack(List<String> odlList, List<String> newList) {
        this.odlList = odlList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return odlList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return odlList.get(oldItemPosition).getBytes()  == newList.get(newItemPosition).getBytes();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final String oldItem = odlList.get(oldItemPosition);
        final String newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
