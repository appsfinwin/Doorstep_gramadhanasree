package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.adapter.LoanAdapter
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.adapter.LoanConfirmationViewModel
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.SearchLoanNumberActivity
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.LayoutLoanCollectionConfirmationBinding
import com.finwin.doorstep.gramadhanasree.databinding.LoanCollectionFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.home_activity.HomeActivity
import com.finwin.doorstep.gramadhanasree.print_reciept.loan_recipt.LoanReceiptActivity
import com.finwin.doorstep.gramadhanasree.utils.Constants

class LoanCollectionFragment : Fragment() {

    lateinit var binding: LoanCollectionFragmentBinding
    lateinit var adapter: LoanAdapter
    lateinit var dialogViewmodel: LoanConfirmationViewModel
    lateinit var confirmDialog: Dialog
    companion object {
        fun newInstance() = LoanCollectionFragment()
    }

    public lateinit var bindingDialog: LayoutLoanCollectionConfirmationBinding


    private lateinit var viewModel: LoanCollectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding=  DataBindingUtil.inflate(inflater,R.layout.loan_collection_fragment,container,false)
        viewModel = ViewModelProvider(this).get(LoanCollectionViewModel::class.java)
        binding.viewModel= viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        HomeActivity.activityHomeBinding.appBar.tvHeading.text = "Loan Collection"
        setConfirmation()

        setRecyclerView(binding.rvLoanAmount)
        binding.btnSearchLoan.setOnClickListener {

            viewModel.reset()

            var intent = Intent(activity, SearchLoanNumberActivity::class.java)
            startActivityForResult(intent,Constants.INTENT_SEARCH_ACCOUNT_FROM_LOAN_COLLECTION)
        }

        viewModel.mAction.observe(viewLifecycleOwner, Observer {
            viewModel.cancelLoading()
            when(it.action)
            {
                LoanCollectionAction.API_ERROR->{

                    val customView: View =
                        LayoutInflater.from(activity).inflate(R.layout.layout_error_layout, null)
                    val tv_error = customView.findViewById<TextView>(R.id.tv_error)
                    tv_error.setText(it.error)
                    SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ERROR")
                        .setCustomView(customView)
                        .show()


                }
                LoanCollectionAction.GET_LOAN_ACCOUNT_HOLDER_SUCCESS->{
                    viewModel.detailsVisibility.set(View.VISIBLE)

                    it.loanAccountHolderResponse?.let { it1 ->
                        adapter.setLoanData(it1.receiptDetails)
                        viewModel.setCustomerDetails(it1)

                    }

                }
                LoanCollectionAction.CLICK_LOAN_COLLECTION->{
                    showDialog()
                }


                LoanCollectionAction.LOAN_COLLECTION_SUCCESS->{
                    viewModel.cancelLoading()
                    var intent= Intent(activity, LoanReceiptActivity::class.java)

                    it.loanCollectionResponse.let {loanCollectionResponse->

                        if (loanCollectionResponse != null) {
                            intent.putExtra(Constants.TRANSACTION_DATE,loanCollectionResponse.receipt.data.date)
                        }
                        if (loanCollectionResponse != null) {
                            intent.putExtra(Constants.TRANSACTION_TIME,loanCollectionResponse.receipt.data.time)
                        }
                        if (loanCollectionResponse != null) {
                            intent.putExtra(Constants.LOAN_ACCOUNT_NUMBER,loanCollectionResponse.receipt.data.accountNumber)
                        }
                        intent.putExtra(Constants.LOAN_CUSTOMER_NAME,viewModel.customerName.get())
                        intent.putExtra(Constants.REMITTANCE_AMOUNT,viewModel.remittanceAmount.get())
                        startActivityForResult(intent, Constants.INTENT_FROM_LOAN_COLLECTION)

                    }


                }
            }

        })
    }

    @SuppressLint("WrongConstant")
    private fun setRecyclerView(rvLoanAmount: RecyclerView) {

        rvLoanAmount.setHasFixedSize(true)
        rvLoanAmount.layoutManager= LinearLayoutManager(activity,LinearLayout.VERTICAL,false)
        adapter= LoanAdapter()
        rvLoanAmount.adapter=adapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.INTENT_SEARCH_ACCOUNT_FROM_LOAN_COLLECTION) {
            if (data != null) {
                var accountNumber: String? = data.getStringExtra("account_number")
                var schemeCode: String? = data.getStringExtra("scheme_code")
                viewModel.accountNumber.set(accountNumber)
                viewModel.schemeCode.set(schemeCode)
            } else {
                viewModel.accountNumber.set("")
            }
        } else if (requestCode == Constants.INTENT_FROM_LOAN_COLLECTION) {
            viewModel.reset()
        }
    }

    private fun showDialog() {

        dialogViewmodel.setConfirmData(
            bindingDialog,
            viewModel.accountNumber.get(),
            viewModel.remittanceAmount.get().toString(),
            viewModel.customerName.get(),
            viewModel.custId.get()
        )
        bindingDialog.btnCancel.setOnClickListener(View.OnClickListener {
            confirmDialog.cancel()

        })
        bindingDialog.btnConfirm.setOnClickListener {
            confirmDialog.cancel()
            viewModel.loanCollection(it)
        }
        confirmDialog.show()

    }

    private fun setConfirmation() {

        confirmDialog = context?.let { Dialog(it) }!!
        bindingDialog = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_loan_collection_confirmation,
            null,
            false
        )

        dialogViewmodel = LoanConfirmationViewModel()
        confirmDialog.setContentView(bindingDialog.getRoot())
        confirmDialog.getWindow()?.setLayout(
            (this.resources.displayMetrics.widthPixels * 0.90).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        confirmDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        confirmDialog.setCanceledOnTouchOutside(false)
        confirmDialog.setOnCancelListener(
            DialogInterface.OnCancelListener {
                confirmDialog.cancel()
                //viewModel.mAction.value = LoanCollectionAction(CashDepositAction.DEFAULT)
            }
        )
    }




}