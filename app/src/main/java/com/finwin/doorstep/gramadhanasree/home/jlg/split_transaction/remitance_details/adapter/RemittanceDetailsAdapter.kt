package com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.remitance_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.LayoutItemRemitanceDetailsBinding
import com.finwin.doorstep.gramadhanasree.databinding.LayoutRemittanceBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Dat
import java.util.*

class RemittanceDetailsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataList: List<Dat> = Collections.emptyList()
    var mAction: MutableLiveData<JlgAction> = MutableLiveData()

    var dataListLiveData: MutableLiveData< List<Dat>> = MutableLiveData()

    var subTranType= ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  RecyclerView.ViewHolder {

        val debitBinding: LayoutRemittanceBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_remittance,
                parent,
                false
            )
        var debitHolder =RemittanceDebitViewHolder(debitBinding)

        val creditBinding: LayoutItemRemitanceDetailsBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_item_remitance_details,
                parent,
                false
            )
       var creditHoldder= RemittanceCreditViewHolder(creditBinding)

        return if (viewType==1)
            debitHolder
        else
            creditHoldder
    }

    fun setData(dataList: List<Dat>, subTrantype: String)
    {
        this.subTranType=subTrantype
        this.dataList= Collections.emptyList()
        this.dataList= dataList
        dataListLiveData.value= dataList
        notifyDataSetChanged()


    }

    fun getData(): List<Dat>
    {
        return  dataList
    }


    override fun getItemCount(): Int {
       return dataList.size
    }

    class RemittanceCreditViewHolder(val binding: LayoutItemRemitanceDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun setBindData(
                dat: List<Dat>,
                mAction: MutableLiveData<JlgAction>,
                dataListLiveData: MutableLiveData<List<Dat>>,
                position: Int
            ) {

                binding.apply {
                    binding.viewModel= RemittanceItemViewmodel(dat,dataListLiveData,position)
                }

            }

    }

    class RemittanceDebitViewHolder(val binding: LayoutRemittanceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setBindData(dat: List<Dat>, position: Int, dataListLiveData: MutableLiveData<List<Dat>>) {

            binding.apply {
                binding.viewModel= RemittanceCreditViewmodel(dat,position,dataListLiveData)
            }

        }

    }

    override fun getItemViewType(position: Int): Int {

        var returnType=0
        if (subTranType.equals("Cr")){
            returnType =2
        }else if (subTranType.equals("Dr")){
            returnType=1
        }
        return returnType
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType)
        {
            1->{
                val remitanceDebitViewholder: RemittanceDebitViewHolder = holder as RemittanceDebitViewHolder
                remitanceDebitViewholder.setBindData(dataList,position,dataListLiveData)
            } 2->{
                val remitanceCreditViewholder: RemittanceCreditViewHolder = holder as RemittanceCreditViewHolder
            remitanceCreditViewholder.setBindData(dataList,mAction,dataListLiveData,position)
            }
        }

    }


}