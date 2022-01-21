package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing.closing_remittance_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ClosingRemittanceDetailsFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing.SplitClosingActivity
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing.closing_remittance_details.adapter.ClosingGroupAdapter
import java.lang.Exception

class ClosingRemittanceDetailsFragment : Fragment() {

    private  val TAG = "RemittanceDetailsFragme"
    private lateinit var adapter: ClosingGroupAdapter
    companion object {
        fun newInstance() = ClosingRemittanceDetailsFragment()
    }

    private lateinit var viewModel: ClosingRemittanceDetailsViewModel
    private lateinit var binding: ClosingRemittanceDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ClosingRemittanceDetailsViewModel::class.java)
        binding= DataBindingUtil.inflate(inflater,R.layout.closing_remittance_details_fragment, container, false)
        binding.viewModel=viewModel
        setupRecyclerview(binding.rvRemittance)
        return binding.root
    }

    private fun setupRecyclerview(rvRemittance: RecyclerView) {

        rvRemittance.layoutManager= LinearLayoutManager(activity)
        adapter= ClosingGroupAdapter()
        rvRemittance.setHasFixedSize(true)
        rvRemittance.adapter=adapter
        observeAdapter(adapter)
    }

    private fun observeAdapter(adapter: ClosingGroupAdapter) {

        adapter.dataListLiveData.observe(viewLifecycleOwner, Observer {
            (activity as SplitClosingActivity?)?.viewModel?.setAccountData(it)
           // viewModel.setAccountData(it)
        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as SplitClosingActivity?)?.disableTab(0)

        viewModel.mAction.observe(viewLifecycleOwner, Observer {
            when (it.action) {
                JlgAction.CLICK_NEXT_FROM_REMITTANCE_DETAILS -> {

                    try {

                        (activity as SplitClosingActivity?)?.gotoNext()
                      //  (activity as SplitClosingActivity?)?.viewModel?.accountsLiveData?.value = viewModel.accountListData
                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                    }
                }
                JlgAction.CLICK_PREVIOUS_FROM_REMITTANCE_DETAILS -> {
                    (activity as SplitClosingActivity?)?.gotoPrevious()
                    (activity as SplitClosingActivity?)?.disableTab(1)
                }
            }
        })

        (activity as SplitClosingActivity?)?.viewModel?.accountsLiveData?.observe(viewLifecycleOwner, Observer {

            adapter.setData(it)
            adapter.notifyDataSetChanged()
            //viewModel.setAccountData(it)
            (activity as SplitClosingActivity?)?.viewModel?.setAccountData(it)
        })



    }
}