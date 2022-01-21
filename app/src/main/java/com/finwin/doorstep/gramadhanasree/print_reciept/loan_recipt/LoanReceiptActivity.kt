package com.finwin.doorstep.gramadhanasree.print_reciept.loan_recipt


import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ActivityLoanReceiptBinding
import com.finwin.doorstep.gramadhanasree.logout_listner.BaseActivity
import com.finwin.doorstep.gramadhanasree.utils.Constants
import java.util.*

class LoanReceiptActivity : BaseActivity() {

    private var textToSpeechSystem: TextToSpeech? = null
    lateinit var viewModel: LoanReceiptViewModel
    lateinit var binding: ActivityLoanReceiptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loan_receipt)
        viewModel = ViewModelProvider(this)[LoanReceiptViewModel::class.java]
        binding.viewmodel = viewModel

        viewModel.bankName.set(application.resources.getString(R.string.bank_name))



            viewModel.setReceipt(
                intent.getStringExtra(Constants.TRANSACTION_DATE).toString(),
                intent.getStringExtra(Constants.TRANSACTION_TIME).toString(),
                intent.getStringExtra(Constants.LOAN_ACCOUNT_NUMBER).toString(),
                intent.getStringExtra(Constants.LOAN_CUSTOMER_NAME).toString(),
                intent.getStringExtra(Constants.REMITTANCE_AMOUNT).toString(),

            )
        textToSpeech(
            intent.getStringExtra(Constants.LOAN_CUSTOMER_NAME).toString(),
            intent.getStringExtra(Constants.REMITTANCE_AMOUNT).toString(),
        )


    }

    fun textToSpeech(name: String, rupees: String) {
        textToSpeechSystem = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result: Int = textToSpeechSystem?.setLanguage(Locale("en", "IN")) ?: textToSpeechSystem?.setSpeechRate(
                    .8f
                )!!
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    Log.e("error", "This Language is not supported")
                } else {
                    textToSpeechSystem?.speak(
                        "$rupees rupees debited from $name's account",
                        TextToSpeech.QUEUE_FLUSH,
                        null
                    )
                }
            } else Log.e("error", "Initilization Failed!")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}