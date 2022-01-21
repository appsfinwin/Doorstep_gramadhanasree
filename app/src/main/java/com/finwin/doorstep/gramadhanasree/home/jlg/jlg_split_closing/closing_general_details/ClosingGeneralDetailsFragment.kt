package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing.closing_general_details

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ClosingGeneralDetailsFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing.SplitClosingActivity
import com.finwin.doorstep.gramadhanasree.home.jlg.search_account_group.SearchGroupAccountActivity
import com.finwin.doorstep.gramadhanasree.home.transactions.search_account.SearchActivity
import com.finwin.doorstep.gramadhanasree.utils.Constants

class ClosingGeneralDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ClosingGeneralDetailsFragment()
    }

    private lateinit var viewModel: ClosingGeneralDetailsViewModel
    lateinit var binding : ClosingGeneralDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ClosingGeneralDetailsViewModel::class.java)
        binding= DataBindingUtil.inflate(inflater,R.layout.closing_general_details_fragment, container, false)
        binding.viewModel= viewModel
        return  binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.spinnerSubTranType.isEnabled=false

        (activity as SplitClosingActivity?)?.disableTab(0)
        (activity as SplitClosingActivity?)?.disableTab(1)
        (activity as SplitClosingActivity?)?.disableTab(2)


        (activity as SplitClosingActivity?)?.viewModel?.initLoading(context)
        (activity as SplitClosingActivity?)?.viewModel?.getCodeMasters()

        viewModel.mAction.observe(viewLifecycleOwner, Observer {
            viewModel.cancelLoading()
            when (it.action) {


                JlgAction.CLICK_SEARCH_GROUP -> {
                    var intent: Intent = Intent(activity, SearchGroupAccountActivity::class.java)
                    startActivityForResult(intent, Constants.INTENT_SEARCH_GROUP)
                }

                JlgAction.CLICK_NEXT_FROM_GENERAL_DETAILS->
                {
                    (activity as SplitClosingActivity?)?.gotoNext()
                    //(activity as SplitClosingActivity?)?.enableTab(1)
                    (activity as SplitClosingActivity?)?.viewModel?.tranType?.set(viewModel.tranType.get())
                    (activity as SplitClosingActivity?)?.viewModel?.effectiveDate?.set(viewModel.effectiveDateSelected.get())
                    (activity as SplitClosingActivity?)?.viewModel?.transactionDate?.set(viewModel.dateSelected.get())
                    (activity as SplitClosingActivity?)?.viewModel?.subTranType?.set(viewModel.subTransTYpe.get())
                    (activity as SplitClosingActivity?)?.viewModel?.groupAccountNumber?.set(viewModel.groupAccountNumber.get())
                    (activity as SplitClosingActivity?)?.viewModel?.schemeCode?.set(viewModel.schemeCode.get())
                    (activity as SplitClosingActivity?)?.viewModel?.transferAccountNumber?.set(viewModel.transferAccountNumber.get())
                }

                JlgAction.CLICK_SEARCH_ACCCOUNT_NUMBER->
                {
                    var intent: Intent = Intent(activity, SearchActivity::class.java)
                    startActivityForResult(intent, Constants.INTENT_SEARCH_ACCOUNT_FROM_JLG)
                }

                JlgAction.JLG_GET_GROUP_ACCOUNT_DETAILS->
                {
                    viewModel.initLoading(context)
                    viewModel.groupAccountNumber.get()?.let { it1 ->
                        viewModel.subTransTYpe.get()?.let { it2 ->
                            (activity as SplitClosingActivity).viewModel.subTranType.set(it2)
                            (activity as SplitClosingActivity).viewModel.groupAccountDetails(
                                it1, it2
                            )
                        }
                    }
                }

                JlgAction.API_ERROR->{
                    SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ERROR!")
                        .setContentText(it.error)
                        .show()
                }


            }
        })

        (activity as SplitClosingActivity?)?.viewModel?.mAction?.observe(viewLifecycleOwner, Observer {
            (activity as SplitClosingActivity?)?.viewModel?.cancelLoading()
            viewModel.cancelLoading()
            when(it.action)
            {
                JlgAction.JLG_GET_GROUP_ACCOUNT_DETAILS_SUCCESS -> {
                    viewModel.cancelLoading()
                    if (!it.groupAcccountDetails!!.data[0].AccountNo.isEmpty()) {

                        (activity as SplitClosingActivity?)?.viewModel?.accountsLiveData?.value= it.groupAcccountDetails!!.dat
                        it.groupAcccountDetails?.data?.get(0)?.let { it1 ->
                            viewModel.setAccountDetails(
                                it1
                            )
                        }
                    }
                } JlgAction.JLG_CODE_MASTERS_SUCCESS -> {
                it.codeMasterResponse?.let { it1 -> viewModel.setSpinnerData(it1) }
            }
                JlgAction.API_ERROR->{
                    SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ERROR!")
                        .setContentText(it.error)
                        .show()
                }
                JlgAction.JLG_CLEAR_DATA->{
                    viewModel.clearData()

                    (activity as SplitClosingActivity?)?.viewModel?. accountsData?.clear()
                    (activity as SplitClosingActivity?)?.viewModel?. chargesList?.clear()
                    (activity as SplitClosingActivity?)?.viewModel?. accountsLiveData?.value=  (activity as SplitClosingActivity?)?.viewModel?. accountsData
                    (activity as SplitClosingActivity?)?.viewModel?.clearData()

                }

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== Constants.INTENT_SEARCH_GROUP)
        {
            if(data!=null) {
                var accountNumber: String? = data.getStringExtra("account_number")
                viewModel.groupAccountNumber.set(accountNumber)
            }else{
                viewModel.groupAccountNumber.set("")
            }
        }else if (requestCode== Constants.INTENT_SEARCH_ACCOUNT_FROM_JLG)
        {
            if(data!=null) {
                var accountNumber: String? = data.getStringExtra("account_number")
                viewModel.transferAccountNumber.set(accountNumber)
            }else{
                viewModel.transferAccountNumber.set("")
            }
        }
    }

}