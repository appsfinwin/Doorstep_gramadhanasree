package com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.LayoutItemMiniStatementBinding
import com.finwin.doorstep.gramadhanasree.databinding.LayoutItemMiniStatementPofileBinding
import com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.pojo.MiniStatementProfile
import com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.pojo.TRANSACTONS

import java.util.*

class MiniStatementAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var miniStatementList: List<TRANSACTONS> = Collections.emptyList();
    var miniStatementProfileList: List<MiniStatementProfile> = Collections.emptyList();
    //lateinit var miniStatementBinding: LayoutItemMiniStatementBinding
   // lateinit var miniStatementProfileBinding: LayoutItemMiniStatementPofileBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

           if (viewType==1) {

               var miniStatementBinding: LayoutItemMiniStatementBinding = DataBindingUtil.inflate(
                   LayoutInflater.from(parent.context),
                   R.layout.layout_item_mini_statement,
                   parent,
                   false
               )
               return MiniStatementViewholder(miniStatementBinding)

            }
        else {

               val miniStatementProfileBinding: LayoutItemMiniStatementPofileBinding =
                   DataBindingUtil.inflate(
                       LayoutInflater.from(parent.context),
                       R.layout.layout_item_mini_statement_pofile,
                       parent,
                       false
                   )

               return ProfileViewholder(miniStatementProfileBinding)
           }
    }

    public fun setProfile(list: List<MiniStatementProfile>)
    {
        miniStatementProfileList=list
        notifyDataSetChanged()
    }

    public fun setMiniStatement(list: List<TRANSACTONS>)
    {
        miniStatementList=list
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        var size : Int=0

            size=miniStatementList.size + miniStatementProfileList.size
       return size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType)
        {
            1 -> {
                val viewHolderMiniStatement: MiniStatementViewholder =
                    holder as MiniStatementViewholder
                holder.setBindData(miniStatementList.get(position-1))
            }
            0 -> {
                val viewHolderProfile: ProfileViewholder = holder as ProfileViewholder
                holder.setBindData(miniStatementProfileList.get(0))
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }


    override fun getItemViewType(position: Int): Int {

        if (position==0)
        {
            return 0
        }else{
            return 1
        }

    }

    class MiniStatementViewholder(val binding: LayoutItemMiniStatementBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

        public fun setBindData(get: TRANSACTONS)
        {
            binding.apply {
                this.viewmodel= MiniStatementItemViewmodel(get, binding, itemView.context)
            }
//            if (binding.viewmodel==null)
//            {
//                binding.viewmodel= MiniStatementItemViewmodel(get,binding,itemView.context)
//            }
        }

    }

    class ProfileViewholder(val binding: LayoutItemMiniStatementPofileBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

        public fun setBindData(get: MiniStatementProfile)
        {
//            if (binding.viewmodel==null)
//            {
//                binding.viewmodel= ProfileViewmodel(get)
//            }

            binding.apply {
                this.viewmodel= ProfileViewmodel(get)
            }
        }

    }

}