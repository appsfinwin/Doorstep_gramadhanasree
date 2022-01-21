package com.finwin.doorstep.gramadhanasree.home.jlg.search_center.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.LayoutRowSearchCenterBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.search_center.SearchCenterAction
import com.finwin.doorstep.gramadhanasree.home.jlg.search_center.pojo.CenterSearchData
import java.util.*

class SearchCenterAdapter: RecyclerView.Adapter<SearchCenterAdapter.ViewHolder>() {

    var mAction = MutableLiveData<SearchCenterAction>()
    var centerList: List<CenterSearchData> = Collections.emptyList()

    fun setCenterData(list :List<CenterSearchData>)
    {
        this.centerList=list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      var binding: LayoutRowSearchCenterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
          R.layout.layout_row_search_center,parent,false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.setBindData(centerList[position],mAction)
    }

    override fun getItemCount(): Int {
        return centerList.size
    }

    class ViewHolder(binding: LayoutRowSearchCenterBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: LayoutRowSearchCenterBinding =binding
        fun setBindData(
            centerSearchData: CenterSearchData,
            mAction: MutableLiveData<SearchCenterAction>
        ) {
            binding.apply {
                binding.viewModel= RowSearchGroupViewModel(data =centerSearchData,
                    mAction = mAction
                )
            }
        }

    }
}