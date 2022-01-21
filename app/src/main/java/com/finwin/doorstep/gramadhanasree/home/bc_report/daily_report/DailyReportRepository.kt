package com.finwin.doorstep.gramadhanasree.home.bc_report.daily_report

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.home.bc_report.daily_report.action.DailyReportAction

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class DailyReportRepository {
    lateinit var mAction: MutableLiveData<DailyReportAction>


    @SuppressLint("CheckResult")
    fun getDailyReport(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.bcReport(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.bc_report.data != null) {
                        mAction.value = DailyReportAction(
                            DailyReportAction.DAILY_REPORT_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = DailyReportAction(DailyReportAction.API_ERROR, response.bc_report.error+" on "+response.bc_report.TXN_DATE)
                    }
                }, { error ->

                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = DailyReportAction(
                                DailyReportAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = DailyReportAction(
                                DailyReportAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                DailyReportAction(
                                    DailyReportAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                }
            )

    }
}