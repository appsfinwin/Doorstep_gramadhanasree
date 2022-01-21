package com.finwin.doorstep.gramadhanasree.home.bc_report.date_to_date_report

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.home.bc_report.date_to_date_report.action.DateToDateAction


import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class DateToDateReportRepository {

    lateinit var mAction: MutableLiveData<DateToDateAction>
    @SuppressLint("CheckResult")
    fun getDateToDateReport(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.bcReport(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.bc_report.data != null) {
                        mAction.value = DateToDateAction(
                            DateToDateAction.BC_REPORT_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = DateToDateAction(DateToDateAction.API_ERROR, response.bc_report.error+" on "+response.bc_report.TXN_DATE)
                    }
                }, { error ->

                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = DateToDateAction(
                                DateToDateAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = DateToDateAction(
                                DateToDateAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                DateToDateAction(
                                    DateToDateAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                }
            )

    }
}