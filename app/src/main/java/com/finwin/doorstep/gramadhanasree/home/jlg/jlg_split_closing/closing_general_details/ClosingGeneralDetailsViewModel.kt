package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing.closing_general_details

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.AdapterView
import androidx.databinding.*
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.BR
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.CodeMasterResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Data
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Mode
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.SubTranType
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.utils.Services
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ClosingGeneralDetailsViewModel : ViewModel(),Observable {


    var transactionTypeList: ObservableArrayList<String> = ObservableArrayList()
    var transactionTypeListData: ObservableArrayList<Mode> = ObservableArrayList()
    var subTransactionTypeList: ObservableArrayList<String> = ObservableArrayList()
    var subTransactionTypeListData: ObservableArrayList<SubTranType> = ObservableArrayList()
    lateinit var apiInterface: ApiInterface
    var mAction: MutableLiveData<JlgAction> = MutableLiveData()

    var date: ObservableField<String> = ObservableField("--Select Date--")
    var dateSelected: ObservableField<String> = ObservableField("--Select Date--")
    var effectiveDate: ObservableField<String> = ObservableField("--Select Effective Date--")
    var effectiveDateSelected: ObservableField<String> = ObservableField("")
    var groupAccountNumber: ObservableField<String> = ObservableField("")
    var transferAccountNumber: ObservableField<String> = ObservableField("")
    var subTransTYpe: ObservableField<String> = ObservableField("")
    var tranType: ObservableField<String> = ObservableField("")
    var branch: ObservableField<String> = ObservableField("")
    var loanType: ObservableField<String> = ObservableField("")
    var scheme: ObservableField<String> = ObservableField("")
    var schemeCode: ObservableField<String> = ObservableField("")
    var loanAmount: ObservableField<String> = ObservableField("")
    var loanDate: ObservableField<String> = ObservableField("")
    var dueDate: ObservableField<String> = ObservableField("")
    var roi: ObservableField<String> = ObservableField("")
    var accountVisibility: ObservableField<Int> = ObservableField(View.GONE)
    var groupDetailsVisibility: ObservableField<Int> = ObservableField(View.GONE)

    var isValidAccount= ObservableField(false)

    fun onClickDate(view: View) {
        val myCalendar = Calendar.getInstance()
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                //Toast.makeText(view.context,year.toString()+"-"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString(),Toast.LENGTH_LONG).show()

                var _date =
                    (monthOfYear + 1).toString() + "-" + dayOfMonth.toString() + "-" + year.toString()
                date.set(_date)
                dateSelected.set(convertDate(_date))

            }
        val datePickerDialog: DatePickerDialog
        datePickerDialog = DatePickerDialog(
            view.context, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }


    fun onClickEffectiveDate(view: View) {
        val myCalendar = Calendar.getInstance()
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                var _date =
                    (monthOfYear + 1).toString() + "-" + dayOfMonth.toString() + "-" + year.toString()
                effectiveDate.set(_date)
                effectiveDateSelected.set(convertDate(_date))

            }
        val datePickerDialog = DatePickerDialog(
            view.context, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }

    private var selectedTransactionType = 0
    private var selectedSubTransactionType = 0
    private val registry = PropertyChangeRegistry()

    @Bindable
    fun getSelectedTransactionType(): Int {
        return selectedTransactionType
    }

    fun setSelectedTransactionType(selectedTransactionType: Int) {
        this.selectedTransactionType = selectedTransactionType
        registry.notifyChange(this, BR.selectedTransactionType)
    }

    fun onSelectedTransactionType(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        if (transactionTypeListData[position].Code.equals("T")) {
            transferAccountNumber.set("")
            accountVisibility.set(View.VISIBLE)
        } else {
            accountVisibility.set(View.GONE)
        }
        tranType.set(transactionTypeListData[position].Code)

    }


    @Bindable
    fun getSelectedSubTransactionType(): Int {
        return selectedSubTransactionType
    }

    fun setSelectedSubTransactionType(selectedSubTransactionType: Int) {
        this.selectedSubTransactionType = selectedSubTransactionType
        registry.notifyChange(this, BR.selectedSubTransactionType)
    }

    fun onSelectedSubTransactionType(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        clearAccountDetails()
        subTransTYpe.set(subTransactionTypeListData[position].Code)
    }

    private fun clearAccountDetails() {
        groupAccountNumber.set("")
        groupDetailsVisibility.set(View.GONE)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.remove(callback)
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

    fun setSpinnerData(codeMasterResponse: CodeMasterResponse) {

        for (transactionType in codeMasterResponse.Mode) {
            transactionTypeList.add(transactionType.Name)
            transactionTypeListData.add(transactionType)
        }

//        for (i in idProofListData.indices) {
//            if (idProofListData[i].Name == "PAN CARD")
//            {
//                setSelectedIdProofTwo(i)
//            }
//        }

        for (subTransactionType in codeMasterResponse.SubTranType) {

            if (subTransactionType.Code == "Cr") {
                subTransactionTypeList.add(subTransactionType.Name)
                subTransactionTypeListData.add(subTransactionType)
            }
        }

        for (i in subTransactionTypeList.indices) {

            if (subTransactionTypeListData[i].Code == "Cr") {
                setSelectedSubTransactionType(i)
            }
        }

    }

    init {
        transactionTypeList.add("--Select Transaction Type--")
        transactionTypeListData.add(Mode("", "--Select Transaction Type--"))

        subTransactionTypeList.add("--Select Sub-Transaction Type--")
        subTransactionTypeListData.add(SubTranType("", "--Select Sub-Transaction Type--"))

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val monthFirstFormat = SimpleDateFormat("MM-dd-yyyy")
//        var  dateNow = Calendar.getInstance().time
        var dateInString = sdf.format(Date())
        var dateMonthFirst = sdf.format(Date())


        date.set((dateInString))
        dateSelected.set(convertDate(dateMonthFirst))
        effectiveDate.set((dateInString))
        effectiveDateSelected.set(convertDate(dateMonthFirst))

    }

    public fun clickSearchGroupAccountNumber(view: View) {
        groupDetailsVisibility.set(View.GONE)
        mAction.value = JlgAction(JlgAction.CLICK_SEARCH_GROUP)
    }

    public fun clickAccountNumber(view: View) {
        mAction.value = JlgAction(JlgAction.CLICK_SEARCH_ACCCOUNT_NUMBER)
    }

    public fun clickSubmitAccountNumber(view: View) {

        if (groupAccountNumber.get().equals("")) {
            Services.showSnakbar("Account number cannot be empty!", view)
        } else if (subTransTYpe.get().equals("")) {
            Services.showSnakbar("Please select Sub- transaction type", view)
        } else {
            //initLoading(view.context)
//            groupAccountDetails()
            mAction.value = JlgAction(JlgAction.JLG_GET_GROUP_ACCOUNT_DETAILS)
        }
    }

    fun setAccountDetails(data: Data) {
        isValidAccount.set(true)
        groupDetailsVisibility.set(View.VISIBLE)
        branch.set(data.Branch)
        loanType.set(data.Type)
        scheme.set(data.Scheme)
        schemeCode.set(data.Sch_Code)
        loanAmount.set(data.Ln_LoanAmount)
        loanDate.set(data.Ln_LoanDate)
        dueDate.set(data.Ln_DueDate)
        roi.set(data.Ln_IntRate)
    }

    fun clickNext(view: View) {

        if (tranType.get().equals("")) {
            Services.showSnakbar("Please select transaction type", view)
        }else  if (tranType.get().equals("T") && transferAccountNumber.get().equals("") ) {
            Services.showSnakbar("Account number cannot be empty", view)
        }
        else if (effectiveDateSelected.get().equals("")) {
            Services.showSnakbar("Please select effective date", view)
        } else if (dateSelected.get().equals("")) {
            Services.showSnakbar("Please select date", view)
        } else if (groupAccountNumber.get().equals("")) {
            Services.showSnakbar("Account number cannot be empty!", view)
        }else if (isValidAccount.get()==false) {
            Services.showSnakbar("Please submit group account number ", view)
        }
        else if (subTransTYpe.get().equals("-1")) {
            Services.showSnakbar("Please select Sub-transaction type", view)
        } else {
            mAction.value = JlgAction(JlgAction.CLICK_NEXT_FROM_GENERAL_DETAILS)
        }

    }

    fun convertDate(_date: String): String
    {
        var submitDate=""
        val theDate: String = _date
        val firstFormatter = SimpleDateFormat("MM-dd-yyyy")
        try {
            val date: Date = firstFormatter.parse(theDate)
            val sd = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            submitDate = sd.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return  submitDate
    }

    fun clearData() {
        isValidAccount.set(false)
        setSelectedTransactionType(0)
        setSelectedSubTransactionType(0)
        tranType.set("")
        transferAccountNumber.set("")
        effectiveDateSelected.set("")
        dateSelected.set("")
        groupAccountNumber.set("")
        subTransTYpe.set("")

        groupDetailsVisibility.set(View.GONE)
        branch.set("")
        loanType.set("")
        scheme.set("")
        schemeCode.set("")
        loanAmount.set("")
        loanDate.set("")
        dueDate.set("")
        roi.set("")

    }

    fun onGroupAccountNumberChanged(text: CharSequence?) {
        isValidAccount.set(false)
        groupDetailsVisibility.set(View.GONE)
    }

}