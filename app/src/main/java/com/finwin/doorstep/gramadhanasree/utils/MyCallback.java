package com.finwin.doorstep.gramadhanasree.utils;

import androidx.databinding.ObservableList;

public class MyCallback extends ObservableList.OnListChangedCallback<ObservableList> {

    public void onChanged(ObservableList sender) {
       // Log.i(tag, "list changed");
    }

    public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
       // Log.i(tag, "item range changed");
    }

    public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
       // Log.i(tag, "item range inserted");
    }

    public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount)  {
       // Log.i(tag, "item range moved");
    }

    public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount)  {
      //  Log.i(tag, "item range removed");
    }
}