package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.pojo.Receiptdetail
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.LayoutRowLoanBinding

class LoanAdapter: RecyclerView.Adapter<LoanAdapter.ViewHolder>() {


    var loanData: List<Receiptdetail> = emptyList<Receiptdetail>()
    //var mAction= MutableLiveData<SearchLoanAction>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var binding: LayoutRowLoanBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.layout_row_loan,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setBindData(loanData[position])
    }

    @JvmName("setLoanData1")
    fun setLoanData(list: List<Receiptdetail>)
    {
        this.loanData=list
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return  loanData.size
    }

    class ViewHolder(var binding: LayoutRowLoanBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setBindData(listAmount: Receiptdetail)
        {
            binding.apply {
                this.viewModel= LoanRowViewModel(listAmount)
            }
        }

    }

}