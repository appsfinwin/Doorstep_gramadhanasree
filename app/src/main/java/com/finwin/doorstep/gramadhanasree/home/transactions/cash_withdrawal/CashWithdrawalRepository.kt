package com.finwin.doorstep.gramadhanasree.home.transactions.cash_withdrawal

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_withdrawal.action.CashWithdrawalAction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CashWithdrawalRepository {
    lateinit var mAction: MutableLiveData<CashWithdrawalAction>

    @SuppressLint("CheckResult")
    fun getAccountHolder(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getAccountHolder(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.account != null) {
                        mAction.value = CashWithdrawalAction(
                            CashWithdrawalAction.GET_ACCOUNT_HOLDER_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = CashWithdrawalAction(CashWithdrawalAction.API_ERROR, response)
                    }
                }, { error ->


                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = CashWithdrawalAction(
                                CashWithdrawalAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = CashWithdrawalAction(
                                CashWithdrawalAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                CashWithdrawalAction(
                                    CashWithdrawalAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                }
            )

    }

    @SuppressLint("CheckResult")
    fun generateOtp(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.generateOtp(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.otp.data != null) {
                        mAction.value = CashWithdrawalAction(
                            CashWithdrawalAction.OTP_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = CashWithdrawalAction(CashWithdrawalAction.API_ERROR, response.otp.error)
                    }
                }, { error ->

                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = CashWithdrawalAction(
                                CashWithdrawalAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = CashWithdrawalAction(
                                CashWithdrawalAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                CashWithdrawalAction(
                                    CashWithdrawalAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                     }
            )

    }


    @SuppressLint("CheckResult")
    fun cashWithdrawal(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.cashWithdrawal(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.receipt.data != null) {
                        mAction.value = CashWithdrawalAction(
                            CashWithdrawalAction.OTP_SUCCESS,
                            response
                        )
                    } else {
                        mAction.value = CashWithdrawalAction(CashWithdrawalAction.API_ERROR, "Error")
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = CashWithdrawalAction(
                                CashWithdrawalAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = CashWithdrawalAction(
                                CashWithdrawalAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                CashWithdrawalAction(
                                    CashWithdrawalAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                }
            )

    }

}