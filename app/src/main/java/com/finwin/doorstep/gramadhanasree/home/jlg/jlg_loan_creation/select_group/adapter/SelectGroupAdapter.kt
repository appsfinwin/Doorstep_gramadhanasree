package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.LayoutRowGroupBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.room.Member
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.action.SelectGroupAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.pojo.ProductData
import java.lang.Exception
import java.util.*

class SelectGroupAdapter( val productData: ObservableArrayList<ProductData>) : RecyclerView.Adapter<SelectGroupAdapter.ViewHolder>() {


    var memberList: List<Member> = Collections.emptyList()
    var optionValue : Boolean?= null
    lateinit var viewHolder: ViewHolder
    var mAction : MutableLiveData<SelectGroupAction> = MutableLiveData()
    var memberListLiveData : MutableLiveData< List<Member>> = MutableLiveData()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var binding: LayoutRowGroupBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.layout_row_group,parent,false)
       // viewHolder= ViewHolder(binding)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this.viewHolder = holder
        optionValue?.let {
            holder.setBindData(memberList,position,
                it, productData,mAction,memberListLiveData)
        }
    }

    fun setOption(option:String)
    {

        try {
            this.optionValue = option.equals("Product")
        }catch (e : Exception)
        {
            e.printStackTrace()
        }

    }

    @JvmName("setMemberList1")
    fun setMemberList(memberList : List<Member>)
    {
        this.memberList= memberList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return memberList.size
    }

    class ViewHolder(val binding: LayoutRowGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setBindData(
            member: List<Member>,
            position: Int,
            optionValue: Boolean,
            productData: ObservableArrayList<ProductData>,
            mAction: MutableLiveData<SelectGroupAction>,
            memberListLiveData: MutableLiveData<List<Member>>
        ) {
            binding.apply {
                this.viewModel= RowGroupViewModel(member,position,optionValue,this, productData,mAction,memberListLiveData)
            }

        }


    }
}