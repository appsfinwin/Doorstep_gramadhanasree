package com.finwin.doorstep.gramadhanasree.home.jlg.search_account_group.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.search_account_group.pojo.GroupAccountData

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.LayoutItemSearchGroupBinding
import java.util.*

class GroupSearchAdapter : RecyclerView.Adapter<GroupSearchAdapter.ViewHolder>() {

    var mAction = MutableLiveData<JlgAction>()
    var groupAccountList: List<GroupAccountData> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutItemSearchGroupBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_item_search_group, parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return groupAccountList.size
    }

    public fun setSearchData(data:List<GroupAccountData>)
    {
        groupAccountList=Collections.emptyList()
        groupAccountList=data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setDataBinding(groupAccountList[position], mAction)
    }


    class ViewHolder(val binding: LayoutItemSearchGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {


        public fun setDataBinding(
            groupAccountData: GroupAccountData,
            mAction: MutableLiveData<JlgAction>
        ) {
            binding.apply {
                binding.viewmodel =
                    GroupSearchItemViewmodel(
                        groupAccountData,
                        mAction
                    )
            }
        }
    }


}