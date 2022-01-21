package com.finwin.doorstep.gramadhanasree.home.transactions.cash_withdrawal

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit.dialog.ConfirmationViewmodel
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_withdrawal.action.CashWithdrawalAction
import com.finwin.doorstep.gramadhanasree.home.transactions.search_account.SearchActivity
import com.finwin.doorstep.gramadhanasree.utils.Constants
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.FragmentCashWithdrawalBinding
import com.finwin.doorstep.gramadhanasree.databinding.LayoutCollectionConfirmationBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CashWithdrawalFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var viewmodel: CashWithdrawalViewmodel
    lateinit var binding: FragmentCashWithdrawalBinding
    var INTENT_RECIEPT:Int =12
    var INTENT_ACCOUNT:Int =13

    lateinit var dialogViewmodel: ConfirmationViewmodel
    lateinit var confirmDialog: Dialog
    public lateinit var bindingDialog: LayoutCollectionConfirmationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cash_withdrawal, container, false)
        viewmodel=ViewModelProvider(this).get(CashWithdrawalViewmodel::class.java)
        binding.viewmodel=viewmodel
        viewmodel.set(binding)
        setConfirmation()
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CashWithdrawalFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.mAction.observe(viewLifecycleOwner, Observer {

            viewmodel.cancelLoading()

            when(it.action)
            {
                CashWithdrawalAction.CLICK_SEARCH->
                {
                    var intent: Intent = Intent(activity, SearchActivity::class.java)
                    startActivityForResult(intent, Constants.INTENT_SEARCH_ACCOUNT_FROM_CASH_WITHDRAWAL)
                }

                CashWithdrawalAction.GET_ACCOUNT_HOLDER_SUCCESS->
                {
                    viewmodel.setAccountHolderData(it.getAccountHolderResponse.account)
                }
                CashWithdrawalAction.OTP_SUCCESS->
                {
                    viewmodel.otpId.set(it.otpResponse.otp.otp_id)
                }
                CashWithdrawalAction.CLICK_CASH_WITHDRAWAL->
                {
                    showDialog()
                }
            }
        })

    }

    private fun showDialog() {

        dialogViewmodel.setConfirmData(bindingDialog,viewmodel.account,viewmodel.amount.get().toString())
        bindingDialog.btnCancel.setOnClickListener(View.OnClickListener {
            confirmDialog.cancel()

        })
        bindingDialog.btnConfirm.setOnClickListener {
            confirmDialog.cancel()
            viewmodel.clickCashWithDrawal(it)
        }
        confirmDialog.show()

    }

    private fun setConfirmation() {

        confirmDialog = context?.let { Dialog(it) }!!
        bindingDialog = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.layout_collection_confirmation,
            null,
            false
        )

        dialogViewmodel= ConfirmationViewmodel()
        confirmDialog.setContentView(bindingDialog.getRoot())
        confirmDialog.getWindow()?.setLayout(
            (this.resources.displayMetrics.widthPixels * 0.90).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        confirmDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        confirmDialog.setCanceledOnTouchOutside(false)
        confirmDialog.setOnCancelListener(
            DialogInterface.OnCancelListener { confirmDialog.cancel() }
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== Constants.INTENT_SEARCH_ACCOUNT_FROM_CASH_WITHDRAWAL)
        {
            if(data!=null) {
                var accountNumber: String? = data.getStringExtra("account_number")
                viewmodel.accountNumber.set(accountNumber)
            }else{
                viewmodel.accountNumber.set("")
            }
        }else if (requestCode== Constants.INTENT_RECIEPT_FROM_CASH_WITHDRAWAL)
        {
            viewmodel.reset()
        }
    }

    override fun onStop() {
        super.onStop()
        viewmodel.mAction.value= CashWithdrawalAction(CashWithdrawalAction.DEFAULT)
    }
}