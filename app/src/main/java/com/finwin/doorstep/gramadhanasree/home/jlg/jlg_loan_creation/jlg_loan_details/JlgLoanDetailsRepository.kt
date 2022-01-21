package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_details

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class JlgLoanDetailsRepository() {

    lateinit var mAction: MutableLiveData<JlgLoanDetailsAction>

    @SuppressLint("CheckResult")
    fun getCodeMasters(apiInterface: ApiInterface) {
        val observable = apiInterface.getCodeMasters()
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.status.equals("1")) {
                        mAction.value = JlgLoanDetailsAction(
                            JlgLoanDetailsAction.GET_REF_CODES_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = JlgLoanDetailsAction(
                            JlgLoanDetailsAction.API_ERROR, "Something error"
                        )
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = JlgLoanDetailsAction(
                                JlgLoanDetailsAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = JlgLoanDetailsAction(
                                JlgLoanDetailsAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                JlgLoanDetailsAction(
                                    JlgLoanDetailsAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }

                }
            )

    }


}