package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_details

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.*
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.BR
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_details.pojo.DD
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_details.pojo.LoanPeriodData
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Mode
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Scheme

import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_details.JlgLoanDetailsAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_details.JlgLoanDetailsRepository
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.CollectionStaff
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Services
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*

class JlgLoanDetailsViewModel() : ViewModel(),
    Observable {

    var mAction: MutableLiveData<JlgLoanDetailsAction> = MutableLiveData()
    var repository: JlgLoanDetailsRepository = JlgLoanDetailsRepository()
    private val registry: PropertyChangeRegistry = PropertyChangeRegistry()

    var loanPeriodList: ObservableArrayList<String> = ObservableArrayList()

    var loanPeriodDaysList: ObservableArrayList<String> = ObservableArrayList()
    var loanPeriodDaysListData: ObservableArrayList<DD> = ObservableArrayList()

    var dayList: ObservableArrayList<String> = ObservableArrayList()
    var dayListData: ObservableArrayList<DD> = ObservableArrayList()

    var weekList: ObservableArrayList<String> = ObservableArrayList()
    var weekListData: ObservableArrayList<DD> = ObservableArrayList()

    var monthList: ObservableArrayList<String> = ObservableArrayList()
    var monthListData: ObservableArrayList<DD> = ObservableArrayList()

    var paymentModeList: ObservableArrayList<String> = ObservableArrayList()
    var paymentModeListData: ObservableArrayList<Mode> = ObservableArrayList()

    var collectionStaffList: ObservableArrayList<String> = ObservableArrayList()
    var collectionDayList: ObservableArrayList<String> = ObservableArrayList()
    var collectionStaffListData: ObservableArrayList<CollectionStaff> = ObservableArrayList()

    var etEmiType: ObservableField<String> = ObservableField("")
    var loanPeriodType: ObservableField<String> = ObservableField("")
    var loanPeriodDays: ObservableField<String> = ObservableField("")
    var etLoanAmount: ObservableField<String> = ObservableField("")
    var etInterestRate: ObservableField<String> = ObservableField("")
    var etPenalInterest: ObservableField<String> = ObservableField("")
    var etInstallmentAmount: ObservableField<String> = ObservableField("")
    var etInstallmentNumber: ObservableField<String> = ObservableField("")
    var etResolutionNumber: ObservableField<String> = ObservableField("")
    var schemeCode: ObservableField<String> = ObservableField("")
    var branchCode: ObservableField<String> = ObservableField("")
    var collectionDay: ObservableField<String> = ObservableField("")
    var collectionstaff: ObservableField<String> = ObservableField("")

    var etLotNumber: ObservableField<String> = ObservableField("")
    var etMoretoriumPeriod: ObservableField<String> = ObservableField("")
    var etNarration: ObservableField<String> = ObservableField("")
    var periodType: ObservableField<String> = ObservableField("")
    var period: ObservableField<String> = ObservableField("")
    var disbursementAmount: ObservableField<String> = ObservableField("")
    var paymentMode: ObservableField<String> = ObservableField("")
    var accountNumber: ObservableField<String> = ObservableField("")

    var calculationtype: ObservableField<String> = ObservableField("")
    var applicationDate: ObservableField<String> = ObservableField("--Select application date--")
    var resolutionDate: ObservableField<String> = ObservableField("--Select resolution date--")
    var accountNumberVisibility: ObservableField<Int> = ObservableField(View.GONE)


    private var selectedLoanPeriod = 0
    private var selectedLoanPeriodDays: Int = 0
    private var selectedCollectionDay: Int = 0
    private var selectedCollectionStaff: Int = 0
    private var selectedPaymentMode: Int = 0

    private var apiInterface: ApiInterface

    init {
        apiInterface = RetrofitClient().getApi()!!
        repository.mAction = mAction

        paymentModeList.clear()
        paymentModeListData.clear()

        paymentModeList.add("--select payment mode--")
        paymentModeListData.add(Mode("",""))

        loanPeriodList.clear()
        loanPeriodList.add("--select loan period--")
        loanPeriodList.add("Daily")
        loanPeriodList.add("Weekly")
        loanPeriodList.add("Monthly")

        collectionDayList.clear()
        collectionDayList.add("--select collection day--")
        collectionDayList.add("Sunday")
        collectionDayList.add("Monday")
        collectionDayList.add("Tuesday")
        collectionDayList.add("Wednesday")
        collectionDayList.add("Thursday")
        collectionDayList.add("Friday")
        collectionDayList.add("Saturday")

        collectionStaffList.clear()
        collectionStaffListData.clear()
        collectionStaffList.add("--select collection staff--")
        collectionStaffListData.add(CollectionStaff("0",""))
        loanPeriodDaysList.clear()
        loanPeriodDaysList.add("--select--")
        resetDayList()
    }

    private fun resetDayList() {



        loanPeriodDaysListData.clear()
        loanPeriodDaysListData.add(DD("--select--", "", "", ""))

        dayList.clear()
        dayListData.clear()
        dayList.add("--select--")


        monthList.clear()
        monthListData.clear()
        monthList.add("--select--")


        weekList.clear()
        weekListData.clear()
        weekList.add("--select--")

    }

    var loading: SweetAlertDialog? = null
    fun initLoading(context: Context?) {
        loading = Services.showProgressDialog(context)
    }

    fun cancelLoading() {
        if (loading != null) {
            loading!!.cancel()
            loading = null
        }
    }

    public fun getCodeMasters() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()


        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        repository.getCodeMasters(apiInterface)

    }




    @JvmName("getSelectedLoanPeriod1")
    @Bindable
    fun getSelectedLoanPeriod(): Int {
        return selectedLoanPeriod
    }

    @JvmName("setSelectedLoanPeriod1")
    fun setSelectedLoanPeriod(selectedLoanPeriod: Int) {
        this.selectedLoanPeriod = selectedLoanPeriod
        registry.notifyChange(this, BR.selectedLoanPeriod1)
    }

    fun onSelectedLoanPeriod(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        loanPeriodType.set(loanPeriodList[position])
        when (position) {
            0 -> {
            }
            1 -> {
                loanPeriodDaysListData.clear()
                loanPeriodDaysList.clear()
                loanPeriodDaysList.add("--select--")
                loanPeriodDaysListData.add(DD("--select--", "", "", ""))
                for (i in dayListData.indices) {
                    loanPeriodDaysListData.add(dayListData[i])
                    loanPeriodDaysList.add(dayListData[i].Ln_Period)

                }

            }
            2 -> {
                loanPeriodDaysListData.clear()
                loanPeriodDaysList.clear()
                loanPeriodDaysList.add("--select--")
                loanPeriodDaysListData.add(DD("--select--", "", "", ""))
                for (i in weekListData.indices) {
                    loanPeriodDaysListData.add(weekListData[i])
                    loanPeriodDaysList.add(weekListData[i].Ln_Period)

                }
            }
            3 -> {
                loanPeriodDaysListData.clear()
                loanPeriodDaysList.clear()
                loanPeriodDaysList.add("--select--")
                loanPeriodDaysListData.add(DD("--select--", "", "", ""))
                for (i in monthListData.indices) {
                    loanPeriodDaysListData.add(monthListData[i])
                    loanPeriodDaysList.add(monthListData[i].Ln_Period)

                }

            }
        }
        if (position == 0) {


        }

    }

    @JvmName("getSelectedLoanPeriodDays1")
    @Bindable
    fun getSelectedLoanPeriodDays(): Int {
        return selectedLoanPeriodDays
    }

    fun setLoanPeriodDays(selectedLoanPeriodDays: Int) {
        this.selectedLoanPeriodDays = selectedLoanPeriodDays
        registry.notifyChange(this, BR.selectedLoanPeriodDays1)
    }

    fun onSelectedLoanPeriodDays(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position != 0) {
            loanPeriodDays.set(loanPeriodDaysListData[position].Ln_PeriodType)
            etInterestRate.set(loanPeriodDaysListData[position].Ln_IntRate)
            etPenalInterest.set(loanPeriodDaysListData[position].Ln_PenalRate)
            periodType.set(loanPeriodDaysListData[position].Ln_PeriodType)
            period.set(loanPeriodDaysListData[position].Ln_Period)

            var installmentAmount = periodType.get()?.let {
                period.get()?.let { it1 ->
                    etInterestRate.get()?.let { it2 ->
                        calculationtype.get()?.let { it3 ->
                            disbursementAmount.get()?.let { it4 ->
                                CalcInstallmentAmount(
                                    it,
                                    it1, it4, it2, etEmiType.get(), it3
                                )
                            }
                        }
                    }
                }
            }
            etInstallmentAmount.set(installmentAmount.toString())
            etInstallmentNumber.set(periodType.get()?.let {
                period.get()?.let { it1 ->
                    setInstallmentNO(
                        it,
                        it1
                    )
                }
            })

            Toast.makeText(view?.context, installmentAmount.toString(), Toast.LENGTH_SHORT).show()


        }

    }

    @SuppressLint("DefaultLocale")
    fun setInstallmentNO(PeriodType: String, Period: String): String? {
        var InstlmntNO = ""
        InstlmntNO = if (PeriodType == "DD") {
            val period = Period.toDouble()
            val periodnew = period / 30
            String.format("%.0f", periodnew)
        } else {
            Period
        }
        return InstlmntNO
    }

    fun CalcInstallmentAmount(
        PeriodType: String, Period: String, Amount: String,
        Interest: String, Emitype: String?, Loancalctype: String
    ): Double {
        return try {
            var installamt = 0.0
            when (Emitype) {
                "N" -> {
                    val loanamt: Float = if (Amount == "") "0".toFloat() else Amount.toFloat()
                    val loaninterest: Float =
                        if (Interest == "") "0".toFloat() else Interest.toFloat()
                    val loanperiod: Int = if (Period == "") "0".toInt() else Period.toInt()
                    if (Loancalctype == "FLT") {
                        when (PeriodType) {
                            "DD" -> if (loanperiod >= 30) {
                                val loanperiodnw = (loanperiod / 30).toFloat()
                                val IntAmount = loanamt * loaninterest * loanperiodnw / 36500
                                val TotLoanAmt = loanamt + IntAmount
                                installamt = (TotLoanAmt / loanperiodnw).toDouble()
                                installamt = Math.round(installamt).toDouble() //0
                                installamt
                            } else {
                                val loanperiodnw = 1f
                                val IntAmount = loanamt * loaninterest * loanperiodnw / 36500
                                val TotLoanAmt = loanamt + IntAmount
                                installamt = (TotLoanAmt / loanperiodnw).toDouble()
                                installamt = Math.round(installamt).toDouble()
                                installamt
                            }
                            "WW" -> {
                                val IntAmount = loanamt * loaninterest * loanperiod / 5200
                                val TotLoanAmt = loanamt + IntAmount
                                installamt = (TotLoanAmt / loanperiod).toDouble()
                                installamt = Math.round(installamt).toDouble()
                                installamt
                            }
                            else -> {
                                val IntAmount = loanamt * loaninterest * loanperiod / 1200
                                val TotLoanAmt = loanamt + IntAmount
                                installamt = (TotLoanAmt / loanperiod).toDouble()
                                installamt = Math.round(installamt).toDouble()
                                installamt
                            }
                        }
                    } else {
                        if (PeriodType == "DD") {
                            if (loanperiod >= 30) {
                                val loanperiodnw = (loanperiod / 30).toFloat()
                                installamt = (loanamt / loanperiodnw).toDouble()
                                installamt = Math.round(installamt).toDouble()
                                installamt
                            } else {
                                val loanperiodnw = 1f
                                installamt = (loanamt / loanperiodnw).toDouble()
                                installamt = Math.round(installamt).toDouble()
                                installamt
                            }
                        } else {
                            installamt = (loanamt / loanperiod).toDouble()
                            installamt = Math.round(installamt).toDouble()
                            installamt
                        }
                    }
                }
                "Y" -> {
                    val P: Float = if (Amount == "") "0".toFloat() else Amount.toFloat()
                    var r: Float = if (Interest == "") "0".toFloat() else Interest.toFloat()
                    r = r / 1200
                    val n: Int = if (Period == "") "0".toInt() else Period.toInt()
                    //if (PeriodType == "DD")
                    //{
                    //    int n = nd / 30;
                    //    installamt = ((P * r) * Math.Pow(1 + r, n)) / (Math.Pow(1 + r, n) - 1);
                    //    installamt = Math.Round(installamt, 0);
                    //    return installamt;
                    //}
                    //else
                    //{
                    installamt = P * r * Math.pow(
                        (1 + r).toDouble(),
                        n.toDouble()
                    ) / (Math.pow((1 + r).toDouble(), n.toDouble()) - 1)
                    installamt = Math.round(installamt).toDouble()
                    installamt
                }
                else -> installamt
            }
        } catch (e: Exception) {
            0.0
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.remove(callback)
    }

    fun setLoanPeriodData(data: LoanPeriodData) {
        resetDayList()
        for (i in data.DD.indices) {
            dayList.add(data.DD[i].Ln_Period)
            dayListData.add(data.DD[i])
        }

        for (i in data.MM.indices) {
            monthList.add(data.MM[i].Ln_Period)
            monthListData.add(data.MM[i])
        }

        for (i in data.WW.indices) {
            weekList.add(data.WW[i].Ln_Period)
            weekListData.add(data.WW[i])
        }
    }

    fun setCodeMasterData(scheme: List<Scheme>) {


    }

    fun onClickApplicationDate(view: View) {
        val myCalendar = Calendar.getInstance()
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                //Toast.makeText(view.context,year.toString()+"-"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString(),Toast.LENGTH_LONG).show()
                var mm =
                    (monthOfYear + 1).toString() + "/" + dayOfMonth.toString() + "/" + year.toString()
                applicationDate.set((monthOfYear + 1).toString() + "/" + dayOfMonth.toString() + "/" + year.toString())


            }
        val datePickerDialog: DatePickerDialog
        datePickerDialog = DatePickerDialog(
            view.context, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }

    fun onClickResolutionDate(view: View) {
        val myCalendar = Calendar.getInstance()
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                //Toast.makeText(view.context,year.toString()+"-"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString(),Toast.LENGTH_LONG).show()
                var mm =
                    (monthOfYear + 1).toString() + "/" + dayOfMonth.toString() + "/" + year.toString()
                resolutionDate.set((monthOfYear + 1).toString() + "/" + dayOfMonth.toString() + "/" + year.toString())


            }
        val datePickerDialog: DatePickerDialog = DatePickerDialog(
            view.context, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }


    @Bindable
    fun getSelectedCollectionDay(): Int {
        return selectedCollectionDay
    }

    fun setSelectedCollectionDay(selectedCollectionDay: Int) {
        this.selectedCollectionDay = selectedCollectionDay
        registry.notifyChange(this, BR.selectedCollectionDay)
    }

    fun onSelectedCollectionDay(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        collectionDay.set(collectionDayList[position])
    }

    @Bindable
    fun getSelectedCollectionStaff(): Int {
        return selectedCollectionStaff
    }

    fun setSelectedCollectionStaff(selectedCollectionStaff: Int) {
        this.selectedCollectionStaff = selectedCollectionStaff
        registry.notifyChange(this, BR.selectedCollectionStaff)
    }

    fun onSelectedCollectionStaff(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        collectionstaff.set(collectionStaffListData[position].Cust_ID)
    }

    @Bindable
    fun getSelectedPaymentMode(): Int {
        return selectedPaymentMode
    }

    fun setSelectedPaymentMode(selectedPaymentMode: Int) {
        this.selectedPaymentMode = selectedPaymentMode
        registry.notifyChange(this, BR.selectedPaymentMode)
    }

    fun onSelectedPaymentMode(parent: AdapterView<*>?, view: View, position: Int, id: Long) {

        paymentMode.set(paymentModeListData[position].Code)
        if(paymentModeListData[position].Name=="Transfer")
        {
            accountNumberVisibility.set(View.VISIBLE)

        }else  if(paymentModeListData[position].Name=="Cash")
        {
            accountNumber.set("")
            accountNumberVisibility.set(View.GONE)
        }


    }

    fun setCollectionStaff(collectionStaff: List<CollectionStaff>) {

        for (i in collectionStaff.indices) {
            collectionStaffList.add(collectionStaff[i].Name)
            collectionStaffListData.add(collectionStaff[i])
        }
    }

    fun setPaymentMode(mode: List<Mode>) {
        for (i in mode.indices)
        {
            paymentModeList.add(mode[i].Name)
            paymentModeListData.add(mode[i])
        }
    }

    fun clickPrevious(view:View)
    {
        mAction.value= JlgLoanDetailsAction(JlgLoanDetailsAction.CLICK_PREVIOUS)
    }

    fun clickSearchAccount(view:View)
    {
        mAction.value= JlgLoanDetailsAction(JlgLoanDetailsAction.CLICK_SEARCH_ACCOUNT)
    }

    fun clickSubmit(view: View)
    {
        if (loanPeriodType.get()=="--select loan period--")
        {
            Toast.makeText(view.context, "Please select Loan period", Toast.LENGTH_SHORT).show()
        }else if (loanPeriodDays.get()==""){
            Toast.makeText(view.context, "Please select Loan period", Toast.LENGTH_SHORT).show()
        }else if (etResolutionNumber.get()==""){
            Toast.makeText(view.context, "Resolution number cannot be empty", Toast.LENGTH_SHORT).show()
        }else if (applicationDate.get()=="--Select application date--"){
            Toast.makeText(view.context, "Please select an application date", Toast.LENGTH_SHORT).show()
        }else if (resolutionDate.get()=="--Select resolution date--"){
            Toast.makeText(view.context, "Please select a resolution date", Toast.LENGTH_SHORT).show()
        }else if (etLotNumber.get()==""){
            Toast.makeText(view.context, "Lot number cannot be empty", Toast.LENGTH_SHORT).show()
        }else if (collectionDay.get()=="--select collection day--"){
            Toast.makeText(view.context, "Please select a collection day", Toast.LENGTH_SHORT).show()
        }else if (collectionstaff.get()=="--select collection staff--"){
            Toast.makeText(view.context, "Please select a collection staff", Toast.LENGTH_SHORT).show()
        }else if (etMoretoriumPeriod.get()==""){
            Toast.makeText(view.context, "Moretorium period cannot be epty", Toast.LENGTH_SHORT).show()
        }else if (paymentMode.get()==""){
            Toast.makeText(view.context, "Please select a payment mode", Toast.LENGTH_SHORT).show()
        }else if (paymentMode.get()=="Transfer" && accountNumber.get()==""){
            Toast.makeText(view.context, "Please enter an account number", Toast.LENGTH_SHORT).show()
        }else if (etNarration.get()=="Transfer" ){
            Toast.makeText(view.context, "narration cannot be empty", Toast.LENGTH_SHORT).show()
        }else{
            mAction.value= JlgLoanDetailsAction(JlgLoanDetailsAction.CLICK_SUBMIT)
        }
    }

    fun clearData() {

        setSelectedLoanPeriod(0)
        setLoanPeriodDays(0)
        etLoanAmount.set("")
        etInterestRate.set("")
        etPenalInterest.set("")
        etInstallmentNumber.set("")
        etResolutionNumber.set("")
        applicationDate.set("--Select application date--")
        resolutionDate.set("--Select resolution date--")
        etLotNumber.set("")
        setSelectedCollectionDay(0)
        setSelectedCollectionStaff(0)
        etMoretoriumPeriod.set("")
        etEmiType.set("")
        setSelectedPaymentMode(0)
        etNarration.set("")

    }
}


