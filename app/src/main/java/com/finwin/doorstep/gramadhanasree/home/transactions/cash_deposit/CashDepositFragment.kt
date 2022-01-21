package com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.FragmentCashDepositBinding
import com.finwin.doorstep.gramadhanasree.databinding.LayoutCollectionConfirmationBinding
import com.finwin.doorstep.gramadhanasree.home.home_activity.HomeActivity
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit.dialog.ConfirmationViewmodel
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.LoanCollectionFragment
import com.finwin.doorstep.gramadhanasree.home.transactions.search_account.SearchActivity
import com.finwin.doorstep.gramadhanasree.print_reciept.ReceiptActivity
import com.finwin.doorstep.gramadhanasree.utils.Constants
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CashDepositFragment : Fragment() {



    private var textToSpeechSystem: TextToSpeech? = null
    private var param1: String? = null
    private var param2: String? = null
    var INTENT_RECIEPT: Int = 12
    var INTENT_ACCOUNT: Int = 13
    public lateinit var binding: FragmentCashDepositBinding
    public lateinit var bindingDialog: LayoutCollectionConfirmationBinding
    lateinit var viewmodel: CashDepositViewmodel
    lateinit var dialogViewmodel: ConfirmationViewmodel
    lateinit var confirmDialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences(
            Constants.PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cash_deposit, container, false)
        viewmodel = ViewModelProvider(this).get(CashDepositViewmodel::class.java)
        binding.viewmodel = viewmodel
        HomeActivity.activityHomeBinding.appBar.tvHeading.text = "Cash deposit"
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CashDepositFragment()
                .apply {
                    arguments = Bundle().apply {}
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setConfirmation()

        viewmodel.mAction.observe(viewLifecycleOwner, Observer {

            viewmodel.cancelLoading()
            when (it.action) {
                CashDepositAction.CLICK_SEARCH -> {
                    var intent: Intent = Intent(activity, SearchActivity::class.java)
                    startActivityForResult(
                        intent,
                        Constants.INTENT_SEARCH_ACCOUNT_FROM_CASH_DEPOSIT
                    )
                }

                CashDepositAction.GET_ACCOUNT_HOLDER_SUCCESS -> {
                    it.getAccountHolderResponse?.let { getAccountHolderResponse ->
                        viewmodel.setAccountHolderData(getAccountHolderResponse.account) }
                }
                CashDepositAction.CLICK_DEPOSIT -> {
                    showDialog()
                }
                CashDepositAction.API_ERROR -> {
                    val customView: View =
                        LayoutInflater.from(context).inflate(R.layout.layout_error_layout, null)
                    val tv_error = customView.findViewById<TextView>(R.id.tv_error)
                    tv_error.setText(it.error)
                    SweetAlertDialog(context, SweetAlertDialog.BUTTON_NEGATIVE)
                        .setTitleText("ERROR")
                        .setCustomView(customView)
                        .show()
                }
                CashDepositAction.CASH_DEPOSIT_SUCCESS -> {
                    viewmodel.cancelLoading()
                    it.cashDepositResponse?.receipt?.data?.let { cashDepositResponse ->
//                        texttoSpeech(
//                            cashDepositResponse.NAME,
//                            cashDepositResponse.DEPOSIT_AMOUNT
//                        )
                        var intent = Intent(activity, ReceiptActivity::class.java)
                        intent.putExtra(Constants.FROM, Constants.CASH_DEPOSIT)
                        intent.putExtra(
                            Constants.TRANSACTION_ID,
                            cashDepositResponse.TRAN_ID
                        )
                        intent.putExtra(
                            Constants.OLD_BALANCE,
                            cashDepositResponse.OLD_BALANCE
                        )
                        intent.putExtra(
                            Constants.DEPOSIT_AMOUNT,
                            cashDepositResponse.DEPOSIT_AMOUNT
                        )
                        intent.putExtra(
                            Constants.CURRENT_BALANCE,
                            cashDepositResponse.CURRENT_BALANCE
                        )
                        intent.putExtra(
                            Constants.DEPOSIT_DATE,
                            cashDepositResponse.DEPOSIT_DATE
                        )
                        intent.putExtra(
                            Constants.PREVIOUS_BALANCE,
                            cashDepositResponse.OLD_BALANCE
                        )
                        intent.putExtra(
                            Constants.ACCOUNT_NUMBER,
                            cashDepositResponse.ACC_NO
                        )
                        intent.putExtra(Constants.NAME, cashDepositResponse.NAME)
                        intent.putExtra(Constants.MOBILE, cashDepositResponse.MOBILE)
                        startActivityForResult(intent, Constants.INTENT_RECIEPT_FROM_CASH_DEPOSIT)

                    }

                }
            }
        })
    }




    private fun showDialog() {

        dialogViewmodel.setConfirmData(
            bindingDialog,
            viewmodel.account,
            viewmodel.amount.get().toString()
        )
        bindingDialog.btnCancel.setOnClickListener(View.OnClickListener {
            confirmDialog.cancel()

        })
        bindingDialog.btnConfirm.setOnClickListener {
            confirmDialog.cancel()
            viewmodel.clickCashDeposit(it)
        }
        confirmDialog.show()

    }

    private fun setConfirmation() {

        confirmDialog = context?.let { Dialog(it) }!!
        bindingDialog = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_collection_confirmation,
            null,
            false
        )

        dialogViewmodel = ConfirmationViewmodel()
        confirmDialog.setContentView(bindingDialog.getRoot())
        confirmDialog.getWindow()?.setLayout(
            (this.resources.displayMetrics.widthPixels * 0.90).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        confirmDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        confirmDialog.setCanceledOnTouchOutside(false)
        confirmDialog.setOnCancelListener(
            DialogInterface.OnCancelListener { confirmDialog.cancel()
                viewmodel.mAction.value = CashDepositAction(CashDepositAction.DEFAULT)}
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.INTENT_SEARCH_ACCOUNT_FROM_CASH_DEPOSIT) {
            if (data != null) {
                var accountNumber: String? = data.getStringExtra("account_number")
                viewmodel.accountNumber.set(accountNumber)
            } else {
                viewmodel.accountNumber.set("")
            }
        } else if (requestCode == Constants.INTENT_RECIEPT_FROM_CASH_DEPOSIT) {
            viewmodel.reset()
        }
    }

    override fun onStop() {
        super.onStop()
        viewmodel.mAction.value = CashDepositAction(CashDepositAction.DEFAULT)
    }

    override fun onResume() {
        super.onResume()
        viewmodel.mAction.value = CashDepositAction(CashDepositAction.DEFAULT)
    }


}