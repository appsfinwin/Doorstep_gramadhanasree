package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_pending_lists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ActivityJlgPendingListBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_pending_lists.adapter.PendingListAdapter

class JlgPendingListActivity : AppCompatActivity() {
    lateinit var binding: ActivityJlgPendingListBinding
    lateinit var viewModel : JlgPendingListViewModel
    lateinit var pendingListAdapter: PendingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_jlg_pending_list)
        viewModel= ViewModelProvider(this)[JlgPendingListViewModel::class.java]
        binding.viewModel=viewModel

        setRecyclerView(binding.rvPendingList)

        viewModel.initLoading(this)
        viewModel.getPendingList()

        viewModel.mAction.observe(this, Observer {
            viewModel.cancelLoading()
            when(it.action)
            {
                JlgAction.JLG_PENDING_LIST_SUCCESS->
                {
                    it.getPendingListResponse?.let { it1 -> pendingListAdapter.setData(it1.data) }
                    pendingListAdapter.notifyDataSetChanged()
                }

                JlgAction.REMOVE_ACCOUNT_SUCCESS->
                {

                    SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("SUCCESS!")
                        .setContentText(it.removeAccountResponse?.msg)
                        .setConfirmClickListener {
                            it.cancel()
                            viewModel.initLoading(it.context)
                            viewModel.getPendingList()
                        }
                        .show()
                } JlgAction.API_ERROR->
                {


                    val customView: View =
                        LayoutInflater.from(this).inflate(R.layout.layout_error_layout, null)
                    val tv_error = customView.findViewById<TextView>(R.id.tv_error)
                    tv_error.setText(it.error)
                    SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ERROR")
                        .setCustomView(customView)
                        .show()


                }
            }
        })

    }

    private fun setRecyclerView(rvPendingList: RecyclerView) {

        rvPendingList.setHasFixedSize(true)
        rvPendingList.layoutManager= LinearLayoutManager(this)
        pendingListAdapter= PendingListAdapter()
        rvPendingList.adapter= pendingListAdapter
        setObservable(pendingListAdapter)

    }

    private fun setObservable(pendingListAdapter: PendingListAdapter) {

        pendingListAdapter.mAction.observe(this, Observer {

            when(it.action)
            {
               JlgAction.CLICK_REMOVE_ACCOUNT->
               {
                   it.pendingData?.let { it1 -> viewModel.removeAccount(accountNumber = it1.accountNumber) }
               }
            }

        })

    }
}