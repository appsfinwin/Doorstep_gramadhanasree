package com.finwin.doorstep.gramadhanasree.home.jlg

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer


import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation.JlgCenterCreationFragment
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation.JlgGroupCreationFragment
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.JlgLoanCreationActivity
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_pending_lists.JlgPendingListActivity
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing.SplitClosingActivity
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.SplitTransactionActivity
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.JlgFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgViewModel

class JlgFragment : Fragment() {

    companion object {
        fun newInstance() = JlgFragment()
        var DEFAULT: Int = -1
        var CLICK_CENTER_CREATION: Int = 1
        var CLICK_SPLIT_TRANSACTIONS: Int = 2
        var CLICK_GROUP_CREATION: Int = 3
        var CLICK_JLG_LOAN_CREATION: Int = 4
        var CLICK_JLG_SPLIT_CLOSING: Int = 5
        var CLICK_CLICK_PENDING_LIST: Int = 6

    }

    private lateinit var viewModel: JlgViewModel
    private lateinit var binding: JlgFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.jlg_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(JlgViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.mAction.observe(viewLifecycleOwner, Observer {
            when (it) {
                CLICK_CENTER_CREATION -> {
                    val myFragment: Fragment =
                        JlgCenterCreationFragment.newInstance()
                    activity?.getSupportFragmentManager()?.beginTransaction()?.replace(
                        R.id.frame_layout,
                        myFragment
                    )?.addToBackStack(null)?.commit()
                }
                CLICK_GROUP_CREATION -> {
                    val myFragment: Fragment =
                        JlgGroupCreationFragment.newInstance()
                    activity?.getSupportFragmentManager()?.beginTransaction()?.replace(
                        R.id.frame_layout,
                        myFragment
                    )?.addToBackStack(null)?.commit()
                }
                CLICK_JLG_LOAN_CREATION -> {
                    var intent = Intent(activity, JlgLoanCreationActivity::class.java)
                    startActivity(intent)
                }

                CLICK_SPLIT_TRANSACTIONS -> {
                    var intent = Intent(activity, SplitTransactionActivity::class.java)
                    startActivity(intent)
                }

                CLICK_JLG_SPLIT_CLOSING -> {
                    var intent = Intent(activity, SplitClosingActivity::class.java)
                    startActivity(intent)
                }

                CLICK_CLICK_PENDING_LIST -> {
                    var intent = Intent(activity, JlgPendingListActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        viewModel.mAction.value = DEFAULT
    }

}