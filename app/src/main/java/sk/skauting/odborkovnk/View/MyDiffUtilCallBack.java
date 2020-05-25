package sk.skauting.odborkovnk.View;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class MyDiffUtilCallBack extends DiffUtil.Callback {

    private List<String> odlList;
    private List<String> newList;

    public MyDiffUtilCallBack(List<String> odlList, List<String> newList) {
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
        return oldItemPosition == newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}
