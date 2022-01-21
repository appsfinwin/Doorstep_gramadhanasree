package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.JlgLoanCreationAction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object JlgLoanCreationRepository {

    lateinit var mAction: MutableLiveData<JlgLoanCreationAction>

    @SuppressLint("CheckResult")
    fun getLoanPeriod(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getJlgLoanPeriod(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    if (response?.receipt?.status == "1") {
                        mAction.value = response?.let {
                            JlgLoanCreationAction(
                                JlgLoanCreationAction.GET_LOAN_PERIOD_SUCCESS,
                                it
                            )
                        }
                    } else {
                        mAction.value = response?.receipt?.error?.msg?.let {
                            JlgLoanCreationAction(
                                JlgLoanCreationAction.API_ERROR, it
                            )
                        }
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = JlgLoanCreationAction(
                                JlgLoanCreationAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = JlgLoanCreationAction(
                                JlgLoanCreationAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                JlgLoanCreationAction(
                                    JlgLoanCreationAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }

                }
            )

    }

    @SuppressLint("CheckResult")
    fun getCodeMasters(apiInterface: ApiInterface) {
        val observable = apiInterface.getCodeMasters()
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.status.equals("1")) {
                        mAction.value = JlgLoanCreationAction(
                            JlgLoanCreationAction.CODE_MASTERS_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = JlgLoanCreationAction(
                            JlgLoanCreationAction.API_ERROR, "Something error"
                        )
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = JlgLoanCreationAction(
                                JlgLoanCreationAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = JlgLoanCreationAction(
                                JlgLoanCreationAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                JlgLoanCreationAction(
                                    JlgLoanCreationAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }

                }
            )

    }

    @SuppressLint("CheckResult")
    fun getJlgProducts(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getJlgProducts(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    if (response != null) {
                        if (response.status.equals("1")) {
                            mAction.value = JlgLoanCreationAction(
                                JlgLoanCreationAction.GET_JLG_PRODUCTS_SUCCESS,
                                response
                            )
                        } else {

                            mAction.value = JlgLoanCreationAction(
                                JlgLoanCreationAction.API_ERROR, response.message
                            )
                        }
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = JlgLoanCreationAction(
                                JlgLoanCreationAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = JlgLoanCreationAction(
                                JlgLoanCreationAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                JlgLoanCreationAction(
                                    JlgLoanCreationAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }

                }
            )

    }

    @SuppressLint("CheckResult")
    fun createJlgLoan(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.createJlgLoan(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    if (response != null) {
                        if (response.status.equals("1")) {
                            mAction.value = JlgLoanCreationAction(
                                JlgLoanCreationAction.CREATE_JLG_LOAN_SUCCESS,
                                response
                            )
                        } else {

                            mAction.value = JlgLoanCreationAction(
                                JlgLoanCreationAction.API_ERROR, "Something error"
                            )
                        }
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = JlgLoanCreationAction(
                                JlgLoanCreationAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = JlgLoanCreationAction(
                                JlgLoanCreationAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> { mAction.value =
                            JlgLoanCreationAction(
                                JlgLoanCreationAction.API_ERROR,
                                error.message.toString()
                            )
                        }
                    }

                }
            )

    }
}