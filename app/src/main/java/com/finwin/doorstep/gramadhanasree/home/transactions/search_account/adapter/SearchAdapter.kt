package com.finwin.doorstep.gramadhanasree.home.transactions.search_account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.home.transactions.search_account.SearchAction
import com.finwin.doorstep.gramadhanasree.home.transactions.search_account.SearchData

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ItemSearchLayoutBinding
import com.finwin.doorstep.gramadhanasree.home.transactions.search_account.adapter.SearchAdapter.Viewholder

class SearchAdapter() : RecyclerView.Adapter<Viewholder>() {


     var searchDataList: List<SearchData>
     var mAction= MutableLiveData<SearchAction>()

    init {
        searchDataList= emptyList<SearchData>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {

        val binding: ItemSearchLayoutBinding =DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_search_layout,parent,false)
        return Viewholder(binding)
    }

    override fun getItemCount(): Int {
       return searchDataList.size
    }

    fun setSearchData(searchData: List<SearchData>)
    {
        this.searchDataList= emptyList<SearchData>()
        this.searchDataList=searchData
        notifyDataSetChanged()
    }

    fun EmptyList()
    {
        this.searchDataList= emptyList<SearchData>()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
       holder.setBindingData(searchDataList[position],mAction)
    }

    class Viewholder(val binding: ItemSearchLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setBindingData(
            searchData: SearchData,
            mAction: MutableLiveData<SearchAction>
        )
        {

            binding.apply {
                binding.viewmodel= SearchItemViewmodel(this,searchData,mAction)
            }

//            if (binding.viewmodel==null)
//            {
//
//            }else{
//
//               // binding.viewmodel.setData(searchData)
//            }
        }
    }

}