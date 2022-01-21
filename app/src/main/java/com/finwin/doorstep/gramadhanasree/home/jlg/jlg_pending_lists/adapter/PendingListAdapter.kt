package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_pending_lists.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.LayoutRowPendingListBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_pending_lists.pojo.PendingData
import java.util.*

class PendingListAdapter: RecyclerView.Adapter<PendingListAdapter.ViewHolder>() {

    var pendingList : List<PendingData> = Collections.emptyList()
    var mAction: MutableLiveData<JlgAction> = MutableLiveData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var binding : LayoutRowPendingListBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_row_pending_list,parent,false)

        return ViewHolder(binding)

    }

    fun setData(pendingList: List<PendingData>)
    {
        this.pendingList = Collections.emptyList()
        this.pendingList=pendingList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setBindingData(pendingList,position,mAction)
    }

    override fun getItemCount(): Int {
        return  pendingList.size
    }
    class ViewHolder(var binding: LayoutRowPendingListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setBindingData(
            pendingList: List<PendingData>,
            position: Int,
            mAction: MutableLiveData<JlgAction>
        ) {

            binding.apply {
                binding.viewModel= RowPendingListViewModel(pendingList,position,mAction)
            }

        }


    }
}