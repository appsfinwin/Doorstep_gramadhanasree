package com.finwin.doorstep.gramadhanasree.home.jlg.search_member.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.LayoutRowSearchMemberBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.search_member.action.SearchMemberAction
import com.finwin.doorstep.gramadhanasree.home.jlg.search_member.pojo.MemberData
import java.util.*

class SearchMemberAdapter : RecyclerView.Adapter<SearchMemberAdapter.ViewHolder>() {
    var mAction = MutableLiveData<SearchMemberAction>()
    var memberList: List<MemberData> = Collections.emptyList()
    var testLiveData: MutableLiveData<String> = MutableLiveData("")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: LayoutRowSearchMemberBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.layout_row_search_member,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.setBindingData(memberList[position],mAction,testLiveData,position)
    }

    override fun getItemCount(): Int {
      return memberList.size
    }

    fun setMemberData(memberData: List<MemberData>)
    {
       this.memberList=memberData
        notifyDataSetChanged()
    }

    class ViewHolder(binding: LayoutRowSearchMemberBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: LayoutRowSearchMemberBinding= binding
        fun setBindingData(
            memberData: MemberData,
            mAction: MutableLiveData<SearchMemberAction>,
            testLiveData: MutableLiveData<String>,
            position: Int
        ) {

            binding.apply {
                this.viewModel= SearchMemberRowViewModel(memberData, mAction,testLiveData,position)
            }
        }



    }
}