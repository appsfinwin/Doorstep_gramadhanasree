package com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.other_details

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
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Charges
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Dat
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.InstrumentType
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class OtherDetailsViewModel : ViewModel(), Observable {


    public var accountList: ObservableArrayList<String> = ObservableArrayList();


    public var instrumentTypeList: ObservableArrayList<String> = ObservableArrayList();
    public var instrumentTypeListData: ObservableArrayList<InstrumentType> = ObservableArrayList();

    public var chargeList: ObservableArrayList<String> = ObservableArrayList();
    public var chargeListData: ObservableArrayList<Charge> = ObservableArrayList();
    private var selectedAccountNumber = 0
    private var selectedCharges = 0
    private var selectedInstrumentType = 0
    private val registry = PropertyChangeRegistry()

    var chargeSelected = ObservableField("")
    var chargeId = ObservableField("")
    var accountSelected = ObservableField("")
    var amountSelected = ObservableField("")
    var instrumentDate = ObservableField("select date")
    var instrumentNumber = ObservableField("")
    var instrumenTypeSelected = ObservableField("")
    var submitInstrumentDate = ObservableField("")
    var totalAmount = ObservableField("")
    var particulars = ObservableField("")
    var chargeListVisibility= ObservableField(View.GONE)
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
    fun getSelectedAccountNumber(): Int {
        return selectedAccountNumber
    }

    fun setSelectedAccountNumber(selectedAccountNumber: Int) {
        this.selectedAccountNumber = selectedAccountNumber
        registry.notifyChange(this, BR.selectedAccountNumber)
    }

    fun onSelectedAccountNumber(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        if (position != 0) {
            accountSelected.set(accountList[position])
        }
    }

    @Bindable
    fun getSelectedInstrumentType(): Int
    {
        return selectedInstrumentType
    }

    fun setSelectedInstrumentType(selectedInstrumentType :Int)
    {
        this.selectedInstrumentType= selectedInstrumentType
        registry.notifyChange(this,BR.selectedInstrumentType)
    }

    fun onSelectedInstrumentType(parent: AdapterView<*>?, view: View?, position: Int,id: Long)
    {
        if (position!=0)
        {
            instrumenTypeSelected.set(instrumentTypeListData[position].Code)
        }
    }

    @Bindable
    fun getSelectedCharges(): Int {
        return selectedCharges
    }

    fun setSelectedCharges(selectedCharges: Int) {
        this.selectedCharges = selectedCharges
        registry.notifyChange(this, BR.selectedCharges)
    }

    fun onSelectedCharges(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        if (position != 0) {
            chargeSelected.set(chargeListData[position].Display_Name)
            chargeId.set(chargeListData[position].Gl_Code)
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
                amount += (if (account.Remittance == "") "0" else account.Remittance).toDouble()
            }
        }
        totalAmount.set(amount.toString())
    }

    fun setCharges(charges: List<Charge>) {

        chargeList.clear()
        chargeListData.clear()
        chargeList.add("--select charge--")
        chargeListData.add(Charge("--select charge--", ""))

        for (i in charges.indices) {
            chargeList.add(charges[i].Display_Name)
            chargeListData.add(charges[i])
        }
    }

    fun clickAddCharges(view: View?) {
        if (accountSelected.get() == "") {
            Toast.makeText(view?.context, "Please select an account number", Toast.LENGTH_SHORT)
                .show()
        } else if (chargeSelected.get() == "") {
            Toast.makeText(view?.context, "Please select a charge", Toast.LENGTH_SHORT).show()
        } else if (amountSelected.get() == "") {
            Toast.makeText(view?.context, "Please enter amount", Toast.LENGTH_SHORT).show()
        } else {
            var charge = accountSelected.get()?.let {account->
                chargeId.get()?.let { chargeId ->
                    chargeSelected.get()?.let { chargeSelected ->
                        amountSelected.get()?.let { amount ->
                            Charges(accountNumber = account, chargeId = chargeId, charges = chargeSelected, amount = amount)
                        }
                    }
                }
            }
            mAction.value = charge?.let { JlgAction(JlgAction.ADD_CHARGES, it) }
        }
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

        chargeSelected.set("")
        chargeId.set("")
        accountSelected.set("")
        amountSelected.set("")
        instrumentDate.set("select date")
        instrumentNumber.set("")
        instrumenTypeSelected.set("")
        submitInstrumentDate.set("")
        totalAmount.set("")
        particulars.set("")
        chargeListVisibility.set(View.GONE)
        submitInstrumentDate.set("")
        setSelectedCharges(0)
        setSelectedInstrumentType(0)

    }


}