package com.finwin.doorstep.gramadhanasree.home.bc_report

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.BcReportFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.bc_report.daily_report.DailyReportFragment
import com.finwin.doorstep.gramadhanasree.home.bc_report.date_to_date_report.DateToDateReportFragment
import com.finwin.doorstep.gramadhanasree.home.home_activity.HomeActivity


class BcReportFragment : Fragment() {

    companion object {
        public var CLICK_DAILY_REPORT=1
        public var CLICK_DATE_TO_DATE_REPORT=2
        fun newInstance() = BcReportFragment()
    }

    private lateinit var viewModel: BcReportViewModel
    private lateinit var binding: BcReportFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        HomeActivity.activityHomeBinding.appBar.tvHeading.text = "BC Report"
        viewModel = ViewModelProviders.of(this).get(BcReportViewModel::class.java)
        binding=DataBindingUtil.inflate(inflater, R.layout.bc_report_fragment, container, false)
        binding.viewmodel=viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.mAction.observe(viewLifecycleOwner, Observer {
            when(it)
            {
                CLICK_DAILY_REPORT ->{
                    val myFragment: Fragment = DailyReportFragment()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.frame_layout, myFragment)?.addToBackStack(null)?.commit()
                }
                CLICK_DATE_TO_DATE_REPORT ->{
                    val myFragment: Fragment = DateToDateReportFragment()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.frame_layout, myFragment)?.addToBackStack(null)?.commit()
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        viewModel.mAction.value= -1
    }

}