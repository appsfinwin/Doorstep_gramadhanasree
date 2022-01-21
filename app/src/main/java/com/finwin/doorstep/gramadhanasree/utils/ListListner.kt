package com.finwin.doorstep.gramadhanasree.utils

import androidx.databinding.ObservableList
import androidx.databinding.ObservableList.OnListChangedCallback


class ListListner : OnListChangedCallback<ObservableList<*>?>() {


    override fun onChanged(sender: ObservableList<*>?) {
        //Log.i(tag, "list changed")
    }

    override fun onItemRangeChanged(
        sender: ObservableList<*>?,
        positionStart: Int,
        itemCount: Int
    ) {
      //  Log.i(tag, "item range changed")
    }

    override fun onItemRangeInserted(
        sender: ObservableList<*>?,
        positionStart: Int,
        itemCount: Int
    ) {
        //Log.i(tag, "item range inserted")
    }

    override fun onItemRangeMoved(
        sender: ObservableList<*>?,
        fromPosition: Int,
        toPosition: Int,
        itemCount: Int
    ) {
        //Log.i(tag, "item range moved")
    }

    override fun onItemRangeRemoved(
        sender: ObservableList<*>?,
        positionStart: Int,
        itemCount: Int
    ) {
        //Log.i(tag, "item range removed")
    }
}