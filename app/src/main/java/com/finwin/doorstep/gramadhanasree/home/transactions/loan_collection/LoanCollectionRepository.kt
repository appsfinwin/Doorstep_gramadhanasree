package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object LoanCollectionRepository {
    lateinit var mAction: MutableLiveData<LoanCollectionAction>

    @SuppressLint("CheckResult")
    fun getLoanAccountHolder(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getLoanAccountHolder(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    if (response != null) {
                        if (response.status.equals("NC") ) {
                            mAction.value = LoanCollectionAction(
                                LoanCollectionAction.GET_LOAN_ACCOUNT_HOLDER_SUCCESS,
                                response
                            )
                        } else  if (response.status.equals("0") ){
                            var  error=response?.error
                            mAction.value =
                                LoanCollectionAction(LoanCollectionAction.API_ERROR,  error)
                        }
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = LoanCollectionAction(
                                LoanCollectionAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = LoanCollectionAction(
                                LoanCollectionAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                LoanCollectionAction(
                                    LoanCollectionAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }

                }
            )

    }


    @SuppressLint("CheckResult")
    fun loanCollection(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.loanCollection(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    if (response != null) {
                        if (response.receipt.data.status.equals ("Y") ) {
                            mAction.value = LoanCollectionAction(
                                LoanCollectionAction.LOAN_COLLECTION_SUCCESS,
                                response
                            )
                        } else {
                            //var  error=response?.error
                            mAction.value =
                                LoanCollectionAction(LoanCollectionAction.API_ERROR,  "error")
                        }
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = LoanCollectionAction(
                                LoanCollectionAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = LoanCollectionAction(
                                LoanCollectionAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                LoanCollectionAction(
                                    LoanCollectionAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }

                }
            )

    }

}