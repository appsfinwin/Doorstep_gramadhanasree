package com.finwin.doorstep.gramadhanasree.home.jlg.search_group.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.LayoutRowSearchGroupBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.search_group.action.SearchGroupAction
import com.finwin.doorstep.gramadhanasree.home.jlg.search_group.pojo.SearchGroupData
import java.util.*

class SearchGroupAdapter : RecyclerView.Adapter<SearchGroupAdapter.ViewHolder>() {


    var groupList : List<SearchGroupData> = Collections.emptyList()
    var mAction: MutableLiveData<SearchGroupAction> = MutableLiveData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding : LayoutRowSearchGroupBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.layout_row_search_group,parent,false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setBindData(groupList[position],mAction)
    }

    fun setGroupData(groupList:List<SearchGroupData> )
    {
        this.groupList= groupList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return groupList.size
    }

    class ViewHolder(var binding: LayoutRowSearchGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setBindData(groupData: SearchGroupData, mAction: MutableLiveData<SearchGroupAction>) {
            binding.apply {
                this.viewModel= RowSearchGroupViewModel(groupData,mAction)
            }
        }

    }
}