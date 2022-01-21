package com.finwin.doorstep.gramadhanasree.home.bc_report.date_to_date_report


import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import cn.pedant.SweetAlert.SweetAlertDialog

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.DateToDateReportFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.bc_report.date_to_date_report.action.DateToDateAction
import com.finwin.doorstep.gramadhanasree.home.home_activity.HomeActivity


class DateToDateReportFragment : Fragment() {

    companion object {
        fun newInstance() = DateToDateReportFragment()
    }

    private lateinit var viewModel: DateToDateReportViewModel
    private lateinit var binding : DateToDateReportFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        HomeActivity.activityHomeBinding.appBar.tvHeading.text = "Date to date report"
        binding=DataBindingUtil.inflate(inflater,
            R.layout.date_to_date_report_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(DateToDateReportViewModel::class.java)
        binding.viewmodel=viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.mAction.observe(viewLifecycleOwner, Observer {
            when(it.action)
            {
                DateToDateAction.BC_REPORT_SUCCESS->{
                    viewModel.cancelLoading()
                    viewModel.setData(it.bcReportResponse.bc_report.data)
                }

                DateToDateAction.API_ERROR->{
                    viewModel.cancelLoading()
                    var dialog: SweetAlertDialog = SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                    dialog.titleText = "ERROR!"
                    dialog.contentText = it.error
                    dialog.setCancelable(false)
                    dialog.setConfirmClickListener {
                        getFragmentManager()?.popBackStack();
                        it.cancel()
                    }

                    dialog.show()
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        viewModel.mAction.value= DateToDateAction(DateToDateAction.DEFAULT)
        viewModel.reset()

    }

}