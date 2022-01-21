package com.finwin.doorstep.gramadhanasree.print_reciept

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ActivityReceiptBinding
import com.finwin.doorstep.gramadhanasree.logout_listner.BaseActivity
import com.finwin.doorstep.gramadhanasree.utils.Constants
import java.util.*


class ReceiptActivity : BaseActivity() {

    private var textToSpeechSystem: TextToSpeech? = null
    lateinit var binding: ActivityReceiptBinding
    lateinit var viewmodel: RecieptViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_receipt)
        viewmodel=ViewModelProvider(this).get(RecieptViewmodel::class.java)
        binding.viewmodel=viewmodel

        viewmodel.from.set(intent.getStringExtra(Constants.FROM).toString())
        if (intent.getStringExtra(Constants.FROM).toString().equals(Constants.CASH_DEPOSIT)) {
            viewmodel.setLayoutForCashDeposit()
            textToSpeech(
                intent.getStringExtra(Constants.NAME).toString(),
                intent.getStringExtra(Constants.DEPOSIT_AMOUNT).toString(),
            )
            viewmodel.setReceiptCashDeposit(
                intent.getStringExtra(Constants.DEPOSIT_DATE).toString(),
                intent.getStringExtra(Constants.ACCOUNT_NUMBER).toString(),
                intent.getStringExtra(Constants.TRANSACTION_ID).toString(),
                intent.getStringExtra(Constants.NAME).toString(),
                intent.getStringExtra(Constants.MOBILE).toString(),
                intent.getStringExtra(Constants.PREVIOUS_BALANCE).toString(),
                intent.getStringExtra(Constants.DEPOSIT_AMOUNT).toString(),
                intent.getStringExtra(Constants.CURRENT_BALANCE).toString()
            )
        }else if (intent.getStringExtra(Constants.FROM).toString().equals(Constants.BALANCE_ENQUIRY))
        {
            viewmodel.setLayoutForBalance()
            viewmodel.setReceiptBalanceEnquiry(
                intent.getStringExtra(Constants.DEPOSIT_DATE).toString(),
                intent.getStringExtra(Constants.ACCOUNT_NUMBER).toString(),
                intent.getStringExtra(Constants.NAME).toString(),
                intent.getStringExtra(Constants.MOBILE).toString(),
                intent.getStringExtra(Constants.CURRENT_BALANCE).toString()
            )
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
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
}