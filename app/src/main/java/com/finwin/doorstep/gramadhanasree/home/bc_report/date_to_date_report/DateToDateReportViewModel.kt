package com.finwin.doorstep.gramadhanasree.home.bc_report.date_to_date_report


import android.accounts.Account
import android.app.Application
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Services
import com.finwin.doorstep.gramadhanasree.utils.Constants

import com.finwin.doorstep.gramadhanasree.home.bc_report.daily_report.pojo.BcReportData
import com.finwin.doorstep.gramadhanasree.home.bc_report.date_to_date_report.action.DateToDateAction

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*

class DateToDateReportViewModel(application: Application) : AndroidViewModel(application) {

    var sharedPreferences: SharedPreferences = application.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = sharedPreferences.edit()

    var repository: DateToDateReportRepository = DateToDateReportRepository()
    var mAction: MutableLiveData<DateToDateAction> = MutableLiveData()
    var apiInterface= RetrofitClient().getApi()

    init {
        repository.mAction=mAction
    }

    var date: ObservableField<String> = ObservableField("")
    var branchName: ObservableField<String> = ObservableField("")
    var agentName: ObservableField<String> = ObservableField("")
    var agentId: ObservableField<String> = ObservableField("")
    var depositCollected: ObservableField<String> = ObservableField("")
    var depositCollectedAmount: ObservableField<String> = ObservableField("")
    var withdrawals: ObservableField<String> = ObservableField("")
    var withdrawalAmount: ObservableField<String> = ObservableField("")
    var transfers: ObservableField<String> = ObservableField("")
    var transferAmount: ObservableField<String> = ObservableField("")
    var accountsCreated: ObservableField<String> = ObservableField("")
    var customersCreated: ObservableField<String> = ObservableField("")
    var cashInHand: ObservableField<String> = ObservableField("")
    var transactions: ObservableField<String> = ObservableField("")
    var fromDate: ObservableField<String> = ObservableField("Select From date")
    var toDate: ObservableField<String> = ObservableField("Select To date")
    var dateVisibility: ObservableField<Int> = ObservableField(View.VISIBLE)
    var reportVisibility: ObservableField<Int> = ObservableField(View.GONE)


    public fun getDatetoDatReport() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()

        jsonParams["agent_id"] = sharedPreferences.getString(Constants.AGENT_ID, "")
        jsonParams["date_from"] = fromDate.get()
        jsonParams["date_to"] = toDate.get()
        jsonParams["type"] = "DTD"

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface?.let { repository.getDateToDateReport(it, body) }
    }

    fun onClickFromDate(view: View) {
        val myCalendar = Calendar.getInstance()
        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                //Toast.makeText(view.context,year.toString()+"-"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString(),Toast.LENGTH_LONG).show()

                fromDate.set(year.toString()+"-"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString())

            }
        val datePickerDialog: DatePickerDialog
        datePickerDialog = DatePickerDialog(
            view.context, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }

    fun onClickToDate(view: View) {
        val myCalendar = Calendar.getInstance()
        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                //Toast.makeText(view.context,year.toString()+"-"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString(),Toast.LENGTH_LONG).show()

                toDate.set(year.toString()+"-"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString())

            }
        val datePickerDialog: DatePickerDialog
        datePickerDialog = DatePickerDialog(
            view.context, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }
    var loading: SweetAlertDialog? = null
    lateinit var account: Account
    fun initLoading(context: Context?) {
        loading = Services.showProgressDialog(context)
    }

    fun cancelLoading() {
        if (loading != null) {
            loading!!.cancel()
            loading = null
        }
    }

    fun setData(data: BcReportData) {

        dateVisibility.set(View.GONE)
        reportVisibility.set(View.VISIBLE)

        date.set(data.REPORT_GENERATED_DATE)
        branchName.set(sharedPreferences.getString(Constants.BRANCH_NAME, ""))
        agentName.set(sharedPreferences.getString(Constants.AGENT_NAME, ""))
        agentId.set(sharedPreferences.getString(Constants.AGENT_ID, ""))

        depositCollected.set(data.NO_OF_DEPOSITS)
        depositCollectedAmount.set(data.TOTAL_DEPOSITS)
        withdrawals.set(data.NO_OF_WITHDRAWAL)
        withdrawalAmount.set(data.TOTAL_WITHDRAWAL)
        transfers.set(data.NO_OF_TRANSFER)
        transferAmount.set(data.TOTAL_TRANSFER)
        accountsCreated.set(data.NO_OF_ACC_CREATED)
        customersCreated.set(data.NO_OF_CUST_CREATED)
        transactions.set(data.NO_OF_TRANSACTIONS)
        cashInHand.set(data.CASH_IN_HAND)
    }

    public fun clickSubmit(view: View)
    {
        if (fromDate.get().equals("Select From date") )
        {
            Services.showSnakbar("Please Select a from date",view)
        }else if (toDate.get().equals("Select To date"))
        {
            Services.showSnakbar("Please Select a to date",view)
        }else
        {
            initLoading(view.context)
            getDatetoDatReport()
        }
    }


    override fun onCleared() {
        super.onCleared()
        reset()
    }

    public fun reset()
    {
        dateVisibility.set(View.VISIBLE)
        reportVisibility.set(View.GONE)
        fromDate.set("Select From date")
        toDate.set("Select To date")
    }
}