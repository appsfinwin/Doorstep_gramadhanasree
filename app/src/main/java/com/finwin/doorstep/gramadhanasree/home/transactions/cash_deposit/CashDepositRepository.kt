package com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CashDepositRepository() {
    lateinit var mAction: MutableLiveData<CashDepositAction>

    @SuppressLint("CheckResult")
    fun getAccountHolder(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getAccountHolder(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.account.data != null) {
                        mAction.value = CashDepositAction(
                            CashDepositAction.GET_ACCOUNT_HOLDER_SUCCESS,
                            response
                        )
                    } else {
                       var  error=response.account.error
                        mAction.value = CashDepositAction(CashDepositAction.API_ERROR, error)
                    }
                }, { error ->

                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = CashDepositAction(
                                CashDepositAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = CashDepositAction(
                                CashDepositAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                CashDepositAction(
                                    CashDepositAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                }
            )

    }

    @SuppressLint("CheckResult")
    fun cashDeposit(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.cashDeposit(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.receipt.data != null) {
                        mAction.value = CashDepositAction(
                            CashDepositAction.CASH_DEPOSIT_SUCCESS, response
                        )
                    } else {
                        var error= response.receipt.error
                        mAction.value = CashDepositAction(CashDepositAction.API_ERROR, error)
                    }
                }, { error ->

                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = CashDepositAction(
                                CashDepositAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = CashDepositAction(
                                CashDepositAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                CashDepositAction(
                                    CashDepositAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                }
            )

    }
}