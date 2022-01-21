package com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.remitance_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.SplitTransactionActivity

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.RemittanceDetailsFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.remitance_details.adapter.RemittanceDetailsAdapter

class RemittanceDetailsFragment : Fragment() {


    companion object {
        fun newInstance() = RemittanceDetailsFragment()
    }

    private  val TAG = "RemittanceDetailsFragme"
    private lateinit var viewModel: RemittanceDetailsViewModel
    private lateinit var binding: RemittanceDetailsFragmentBinding
    private lateinit var adapter: RemittanceDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(
            inflater,
            R.layout.remittance_details_fragment,
            container,
            false
        )
        viewModel = ViewModelProviders.of(this).get(RemittanceDetailsViewModel::class.java)
        binding.viewModel=viewModel

        setupRecyclerview(binding.rvRemittance)
        return binding.root
    }

    private fun setupRecyclerview(rvRemittance: RecyclerView) {

        rvRemittance.layoutManager=LinearLayoutManager(activity)
        adapter= RemittanceDetailsAdapter()
        rvRemittance.setHasFixedSize(true)
        rvRemittance.adapter=adapter
        observeAdapter(adapter)
    }

    private fun observeAdapter(adapter: RemittanceDetailsAdapter) {

        adapter.dataListLiveData.observe(viewLifecycleOwner, Observer {
            (activity as SplitTransactionActivity?)?.viewModel?.setAccountData(it)
            viewModel.setAccountData(it)

            (activity as SplitTransactionActivity?)?.viewModel?.mAction?.value= JlgAction(JlgAction.UPDATE_ACCOUNTS_DATA,it)
        })

        adapter.mAction.observe(viewLifecycleOwner, Observer {
            when (it.action) {
                JlgAction.SELECT_ACCOUNT -> {
                }
                JlgAction.DE_SELECT_ACCOUNT -> {
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as SplitTransactionActivity?)?.disableTab(0)

           viewModel.mAction.observe(viewLifecycleOwner, Observer {
               when (it.action) {
                   JlgAction.CLICK_NEXT_FROM_REMITTANCE_DETAILS -> {
//
//                       (activity as SplitTransactionActivity?)?.enableTab(1)

                       (activity as SplitTransactionActivity?)?.gotoNext()
                       //(activity as SplitTransactionActivity?)?.viewModel?.accountsData =
//                       (activity as SplitTransactionActivity?)?.viewModel?.setAccountData(viewModel.accountListData.)
                   }
                   JlgAction.CLICK_PREVIOUS_FROM_REMITTANCE_DETAILS -> {
                       (activity as SplitTransactionActivity?)?.gotoPrevious()
                       (activity as SplitTransactionActivity?)?.disableTab(1)
                   }
               }
           })

        (activity as SplitTransactionActivity?)?.viewModel?.accountsLiveData?.observe(viewLifecycleOwner, Observer {

            (activity as SplitTransactionActivity?)?.viewModel?.subTranType?.get()?.let { it1 ->
                adapter.setData(it, it1)
            }
            (activity as SplitTransactionActivity?)?.viewModel?.setAccountData(it)
            adapter.notifyDataSetChanged()

        })



    }
}