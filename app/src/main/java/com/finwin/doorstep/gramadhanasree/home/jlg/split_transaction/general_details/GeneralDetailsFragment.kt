package com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.general_details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.search_account_group.SearchGroupAccountActivity
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.SplitTransactionActivity
import com.finwin.doorstep.gramadhanasree.home.transactions.search_account.SearchActivity
import com.finwin.doorstep.gramadhanasree.utils.Constants

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.GeneralDetailsFragmentBinding

class GeneralDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = GeneralDetailsFragment()
    }

    private lateinit var viewModel: GeneralDetailsViewModel
    private lateinit var binding: GeneralDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(
            inflater,
            R.layout.general_details_fragment,
            container,
            false
        )
        viewModel = ViewModelProviders.of(this).get(GeneralDetailsViewModel::class.java)
        binding.viewModel=viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as SplitTransactionActivity?)?.disableTab(0)
        (activity as SplitTransactionActivity?)?.disableTab(1)
        (activity as SplitTransactionActivity?)?.disableTab(2)

        viewModel.mAction.observe(viewLifecycleOwner, Observer {
            viewModel.cancelLoading()
            when (it.action) {


                JlgAction.CLICK_SEARCH_GROUP -> {
                    var intent: Intent = Intent(activity, SearchGroupAccountActivity::class.java)
                    startActivityForResult(intent, Constants.INTENT_SEARCH_GROUP)
                }

                JlgAction.CLICK_NEXT_FROM_GENERAL_DETAILS->
                {
                    (activity as SplitTransactionActivity?)?.gotoNext()

                    (activity as SplitTransactionActivity?)?.viewModel?.tranType?.set(viewModel.tranType.get())
                    (activity as SplitTransactionActivity?)?.viewModel?.effectiveDate?.set(viewModel.effectiveDateSelected.get())
                    (activity as SplitTransactionActivity?)?.viewModel?.transactionDate?.set(viewModel.dateSelected.get())
                    (activity as SplitTransactionActivity?)?.viewModel?.subTranType?.set(viewModel.subTransTYpe.get())
                    (activity as SplitTransactionActivity?)?.viewModel?.groupAccountNumber?.set(viewModel.groupAccountNumber.get())
                    (activity as SplitTransactionActivity?)?.viewModel?.schemeCode?.set(viewModel.schemeCode.get())
                    (activity as SplitTransactionActivity?)?.viewModel?.transferAccountNumber?.set(viewModel.transferAccountNumber.get())
                }

                JlgAction.CLICK_SEARCH_ACCCOUNT_NUMBER->
                {
                    var intent:Intent= Intent(activity, SearchActivity::class.java)
                    startActivityForResult(intent, Constants.INTENT_SEARCH_ACCOUNT_FROM_JLG)
                }

                JlgAction.JLG_GET_GROUP_ACCOUNT_DETAILS->
                {
                  viewModel.initLoading(context)
                    viewModel.groupAccountNumber.get()?.let { it1 ->
                        viewModel.subTransTYpe.get()?.let { it2 ->
                            (activity as SplitTransactionActivity).viewModel.subTranType.set(it2)
                            (activity as SplitTransactionActivity).viewModel.groupAccountDetails(
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

        (activity as SplitTransactionActivity?)?.viewModel?.mAction?.observe(viewLifecycleOwner, Observer {
            (activity as SplitTransactionActivity?)?.viewModel?.cancelLoading()
            viewModel.cancelLoading()
            when(it.action)
            {
                JlgAction.JLG_GET_GROUP_ACCOUNT_DETAILS_SUCCESS -> {
                    viewModel.cancelLoading()
                    if (!it.groupAcccountDetails!!.data[0].AccountNo.isEmpty()) {

                        // DataHolder.dat= it.groupAcccountDetails.dat
                       // (activity as SplitTransactionActivity?)?.viewModel?.setAccountData(it.groupAcccountDetails.dat)
                        (activity as SplitTransactionActivity?)?.viewModel?.accountsLiveData?.value=
                            it.groupAcccountDetails!!.dat
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

                    (activity as SplitTransactionActivity?)?.viewModel?. accountsData?.clear()
                    (activity as SplitTransactionActivity?)?.viewModel?. chargesList?.clear()
                    (activity as SplitTransactionActivity?)?.viewModel?.accountsLiveData?.value= (activity as SplitTransactionActivity?)?.viewModel?. accountsData
                    (activity as SplitTransactionActivity?)?.viewModel?. clearData()
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