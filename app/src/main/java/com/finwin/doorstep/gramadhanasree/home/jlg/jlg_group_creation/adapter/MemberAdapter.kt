package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation.GroupCreationAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation.pojo.SelectedMember
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.LayoutRowMemberBinding
import java.util.*

class MemberAdapter : RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    var mAction = MutableLiveData<GroupCreationAction>()
    var memberList: MutableList<SelectedMember> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: LayoutRowMemberBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_row_member, parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.setBindingData(memberList[position],mAction)
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    fun addMember(selectedMember: SelectedMember)
    {
        this.memberList.add(selectedMember)
        notifyDataSetChanged()
        var size=memberList.size
         size=memberList.size
    }

    fun setMember(selectedMemberList: MutableList<SelectedMember>)
    {
        this.memberList=selectedMemberList
        notifyDataSetChanged()
        var size=memberList.size
         size=memberList.size
    }

    fun removeMember(selectedMember: SelectedMember)
    {
        this.memberList.remove(selectedMember)
        notifyDataSetChanged()
        var size=memberList.size
         size=memberList.size
    }

    class ViewHolder(binding: LayoutRowMemberBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setBindingData(
            selectedMember: SelectedMember,
            mAction: MutableLiveData<GroupCreationAction>
        ) {
            binding.apply {
                this.viewModel= MemberRowViewModel(selectedMember,mAction)
            }
        }

        var  binding: LayoutRowMemberBinding= binding

    }
}