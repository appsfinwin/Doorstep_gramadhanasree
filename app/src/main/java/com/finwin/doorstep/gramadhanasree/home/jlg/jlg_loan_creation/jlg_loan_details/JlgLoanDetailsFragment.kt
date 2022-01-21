package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_details

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import cn.pedant.SweetAlert.SweetAlertDialog

import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.JlgLoanCreationAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.JlgLoanCreationActivity
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_details.JlgLoanDetailsViewModel

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.JlgLoanDetailsFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.transactions.search_account.SearchActivity
import com.finwin.doorstep.gramadhanasree.utils.Constants

class JlgLoanDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = JlgLoanDetailsFragment()
    }

    //lateinit var viewModel: JlgLoanDetailsViewModel

    lateinit var viewModel: JlgLoanDetailsViewModel
    lateinit var binding: JlgLoanDetailsFragmentBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.jlg_loan_details_fragment, container, false)

        viewModel= ViewModelProvider(this).get(JlgLoanDetailsViewModel::class.java)
        sharedPreferences=activity?.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)!!
        editor= sharedPreferences.edit()
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this).get(JlgLoanDetailsViewModel::class.java)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel= ViewModelProvider(this).get(JlgLoanDetailsViewModel::class.java)
        viewModel.initLoading(view?.context)
        viewModel.getCodeMasters()
        viewModel.disbursementAmount.set(sharedPreferences.getString("disbursement_amount","0"))

        viewModel.schemeCode.set(sharedPreferences.getString("JLG_SCHEME_CODE",""))
        viewModel.branchCode.set(sharedPreferences.getString(Constants.BRANCH_ID,""))
        viewModel.mAction.observe(viewLifecycleOwner, Observer {
            viewModel.cancelLoading()

            when (it.action) {
                JlgLoanDetailsAction.GET_REF_CODES_SUCCESS -> {

                }
                JlgLoanDetailsAction.GET_LOAN_PERIOD_SUCCESS -> {

                }
                JlgLoanDetailsAction.CLICK_PREVIOUS -> {
                (activity as JlgLoanCreationActivity).gotoPrevious()
                }
                JlgLoanDetailsAction.CLICK_SUBMIT->{
                    (activity as JlgLoanCreationActivity).viewModel.loanPeriodDays.set(viewModel.loanPeriodDays.get())
                    (activity as JlgLoanCreationActivity).viewModel.loanPeriodType.set(viewModel.loanPeriodType.get())
                    (activity as JlgLoanCreationActivity).viewModel.etLoanAmount.set(viewModel.etLoanAmount.get())
                    (activity as JlgLoanCreationActivity).viewModel.etInterestRate.set(viewModel.etInterestRate.get())
                    (activity as JlgLoanCreationActivity).viewModel.etPenalInterest.set(viewModel.etPenalInterest.get())
                    (activity as JlgLoanCreationActivity).viewModel.etInstallmentNumber.set(viewModel.etInstallmentNumber.get())
                    (activity as JlgLoanCreationActivity).viewModel.etInstallmentAmount.set(viewModel.etInstallmentAmount.get())
                    (activity as JlgLoanCreationActivity).viewModel.etResolutionNumber.set(viewModel.etResolutionNumber.get())
                    (activity as JlgLoanCreationActivity).viewModel.applicationDate.set(viewModel.applicationDate.get())
                    (activity as JlgLoanCreationActivity).viewModel.resolutionDate.set(viewModel.resolutionDate.get())
                    (activity as JlgLoanCreationActivity).viewModel.etLotNumber.set(viewModel.etLotNumber.get())
                    (activity as JlgLoanCreationActivity).viewModel.collectionDay.set(viewModel.collectionDay.get())
                    (activity as JlgLoanCreationActivity).viewModel.collectionstaff.set(viewModel.collectionstaff.get())
                    (activity as JlgLoanCreationActivity).viewModel.etEmiType.set(viewModel.etEmiType.get())
                    (activity as JlgLoanCreationActivity).viewModel.paymentMode.set(viewModel.paymentMode.get())
                    (activity as JlgLoanCreationActivity).viewModel.accountNumber.set(viewModel.accountNumber.get())
                    (activity as JlgLoanCreationActivity).viewModel.etNarration.set(viewModel.etNarration.get())
                    (activity as JlgLoanCreationActivity).viewModel.etMoretoriumPeriod.set(viewModel.etMoretoriumPeriod.get())

                    view?.let { it1 ->
                        (activity as JlgLoanCreationActivity).viewModel.createGroupLoan(
                            it1
                        )
                    }

                }
                JlgLoanDetailsAction.CLICK_SEARCH_ACCOUNT->
                {
                    var intent: Intent = Intent(activity, SearchActivity::class.java)
                    startActivityForResult(
                        intent,
                        Constants.INTENT_SEARCH_ACCOUNT_FROM_JLG_LOAN_CREATION
                    )
                }
            }
        })

        (activity as JlgLoanCreationActivity).viewModel.mAction.observe(
            viewLifecycleOwner,
            Observer {
                (activity as JlgLoanCreationActivity).viewModel.cancelLoading()
                viewModel.cancelLoading()
                when (it.action) {
                    JlgLoanCreationAction.GET_LOAN_PERIOD_SUCCESS -> {
                        it.getLoanPeriodResponse?.receipt?.let { it1 ->
                            viewModel.setLoanPeriodData(
                                it1.data)
                        }
                        viewModel.etEmiType.set((activity as JlgLoanCreationActivity).viewModel.selectedScheme.get()?.Lnp_EMIType.toString())
                        viewModel.calculationtype.set((activity as JlgLoanCreationActivity).viewModel.selectedScheme.get()?.Lnp_IntCalcType.toString())

                    }
                    JlgLoanCreationAction.CODE_MASTERS_SUCCESS -> {
                        it.codeMasterResponse?.let {

                                it1 -> viewModel.setCodeMasterData(it1.Scheme)

                            viewModel.setCollectionStaff(it1.CollectionStaff)
                            viewModel.setPaymentMode(it1.Mode)
                        }


                    }
                    JlgLoanCreationAction.API_ERROR -> {
                        SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ERROR!")
                            .setConfirmClickListener {
                                it.cancel()
                            }
                            .setContentText(it.error)
                            .show()
                    }

                    JlgLoanCreationAction.JLG_PASS_AMOUNT -> {
                        viewModel.etLoanAmount.set(it.amount)
                    }

                    JlgLoanCreationAction.CREATE_JLG_LOAN_SUCCESS->{
                        SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("SUCCESS!")
                            .setContentText("AccountNumber ="+ it.createJlGLoanResponse!!.AccountNo)
                            .setConfirmClickListener {
                                it.cancel()
                                viewModel.clearData()
                                (activity as JlgLoanCreationActivity?)?.setTab(0)
                                (activity as JlgLoanCreationActivity?)?.viewModel?.mAction?.value= JlgLoanCreationAction(
                                    JlgLoanCreationAction.JLG_CLEAR_DATA)
                            }
                            //.setContentText(it.createJlGLoanResponse?.msg)
                            .show()
                    }
                }
            })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.INTENT_SEARCH_ACCOUNT_FROM_JLG_LOAN_CREATION) {
            if (data != null) {
                var accountNumber: String? = data.getStringExtra("account_number")
                viewModel.accountNumber.set(accountNumber)
            } else {
                viewModel.accountNumber.set("")
            }
        }
    }


}