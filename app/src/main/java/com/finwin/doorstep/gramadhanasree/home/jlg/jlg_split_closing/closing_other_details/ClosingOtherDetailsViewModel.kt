package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing.closing_other_details

import android.app.DatePickerDialog
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.*
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finwin.doorstep.gramadhanasree.BR
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Charge
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Dat
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.InstrumentType
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ClosingOtherDetailsViewModel : ViewModel() ,Observable{

    public var accountList: ObservableArrayList<String> = ObservableArrayList();


    public var instrumentTypeList: ObservableArrayList<String> = ObservableArrayList();
    public var instrumentTypeListData: ObservableArrayList<InstrumentType> = ObservableArrayList();

    public var chargeList: ObservableArrayList<String> = ObservableArrayList();
    public var chargeListData: ObservableArrayList<Charge> = ObservableArrayList();

    private var selectedInstrumentType = 0
    private val registry = PropertyChangeRegistry()


    var accountSelected = ObservableField("")
    var amountSelected = ObservableField("")
    var instrumentDate = ObservableField("select date")
    var instrumentNumber = ObservableField("")
    var instrumenTypeSelected = ObservableField("")
    var submitInstrumentDate = ObservableField("")
    var totalAmount = ObservableField("")
    var particulars = ObservableField("")
    var mAction: MutableLiveData<JlgAction> = MutableLiveData()

    init {
        submitInstrumentDate.set("")
        accountList.clear()
        chargeList.clear()
        accountList.add("--select account--")
        chargeList.add("--select charge--")
        chargeListData.add(Charge("--select charge--", ""))

        instrumentTypeList.clear()
        instrumentTypeListData.clear()
        instrumentTypeList.add("--select instrumentation type--")
        instrumentTypeListData.add(InstrumentType("","--select instrumentation type--",""))
    }





    @Bindable
    fun getSelectedInstrumentType(): Int
    {
        return selectedInstrumentType
    }

    fun setSelectedInstrumentType(selectedInstrumentType :Int)
    {
        this.selectedInstrumentType= selectedInstrumentType
        registry.notifyChange(this, BR.selectedInstrumentType)
    }

    fun onSelectedInstrumentType(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
    {
        if (position!=0)
        {
            instrumenTypeSelected.set(instrumentTypeListData[position].Code)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.remove(callback)
    }

    fun setAccountList(value: List<Dat>?) {
        accountList.clear()
        accountList.add("--select account--")
        var amount=0.0
        if (value != null) {
            for (account in value) {
                accountList.add(account.accountNumber)
                amount += (account.Remittance).toDouble()
            }
        }
        //totalAmount.set(amount.toString())
    }

    fun setInstrumentType(instrumentType: List<InstrumentType>) {
        instrumentTypeList.clear()
        instrumentTypeListData.clear()
        instrumentTypeList.add("--select instrumentation type--")
        instrumentTypeListData.add(InstrumentType("","--select instrumentation type--",""))

        for (i in instrumentType.indices)
        {
            instrumentTypeListData.add(instrumentType[i])
            instrumentTypeList.add(instrumentType[i].Name)
        }
    }

    fun onClickInstrumentDate(view: View) {
        val myCalendar = Calendar.getInstance()
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->


                var _date =
                    (monthOfYear + 1).toString() + "-" + dayOfMonth.toString() + "-" + year.toString()

                instrumentDate.set(_date)
                submitInstrumentDate.set(convertDate(_date))

            }



        val datePickerDialog: DatePickerDialog
        datePickerDialog = DatePickerDialog(
            view.context, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }

    fun onClickSubmit(view: View?)
    {

        if (particulars.get().equals(""))
        {
            Toast.makeText(view?.context, "particulars cannot be empty", Toast.LENGTH_SHORT).show()
        }else if (instrumenTypeSelected.get().equals(""))
        {
            Toast.makeText(view?.context, "Please select instrument type", Toast.LENGTH_SHORT).show()
        }else if (instrumentDate.get().equals(""))
        {
            Toast.makeText(view?.context, "Please select instrument date", Toast.LENGTH_SHORT).show()
        }else if (totalAmount.get().equals(""))
        {
            Toast.makeText(view?.context, "total amount cannot be empty", Toast.LENGTH_SHORT).show()
        }else
        {
            mAction.value= JlgAction(JlgAction.CLICK_SUBMIT_FROM_JLG_SPLIT_OTHER_DETAILS)
        }

    }

    fun convertDate(_date: String): String
    {
        var submitDate=""
        val theDate: String = _date
        val firstFormatter = SimpleDateFormat("MM-dd-yyyy")
        try {
            val date: Date = firstFormatter.parse(theDate)
            val sd = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z")
            submitDate = sd.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return  submitDate
    }

    fun clickPrevious(view: View) {
        mAction.value = JlgAction(JlgAction.CLICK_PREVIOUS_FROM_OTHER_DETAILS)
    }
    fun clearData() {

        accountSelected.set("")
        amountSelected.set("")
        instrumentDate.set("select date")
        instrumentNumber.set("")
        instrumenTypeSelected.set("")
        submitInstrumentDate.set("")
        totalAmount.set("")
        particulars.set("")
        submitInstrumentDate.set("")
        setSelectedInstrumentType(0)

    }


}