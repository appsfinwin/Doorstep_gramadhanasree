package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation.pojo.CenterData

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.LayoutItemJlgCenterBinding
import java.util.*

class JlgCenterAdapter : RecyclerView.Adapter<JlgCenterAdapter.ViewHolder>() {

    var centerList: List<CenterData> = Collections.emptyList()
    var mAction : MutableLiveData<JlgAction> = MutableLiveData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var binding: LayoutItemJlgCenterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.layout_item_jlg_center,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        var size=centerList.size
        return centerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setBindData(centerList.get(position),mAction)
    }

    public fun setCenterData(centerList:List<CenterData>)
    {
        this.centerList=Collections.emptyList()
        this.centerList=centerList
        notifyDataSetChanged()
    }


    class ViewHolder(val binding: LayoutItemJlgCenterBinding) : RecyclerView.ViewHolder(binding.root) {

       public fun setBindData(
           centerData: CenterData,
           mAction: MutableLiveData<JlgAction>
       ){
           binding.apply {
               binding.viewModel = CenterItemViewModel(centerData,mAction)
           }
        }

    }


}