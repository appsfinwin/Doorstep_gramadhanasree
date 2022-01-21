package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.general_details

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Scheme
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Sector
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.SubSector
import com.finwin.doorstep.gramadhanasree.BR

import com.finwin.doorstep.gramadhanasree.utils.Constants
import com.finwin.doorstep.gramadhanasree.utils.Services

class JlgLoanGeneralDetailsViewModel(application: Application) : AndroidViewModel(application), Observable {

    var schemeList: ObservableArrayList<String> = ObservableArrayList()
    var schemeListData: ObservableArrayList<Scheme> = ObservableArrayList()
    var selectedSchemeData: ObservableField<Scheme> = ObservableField()
    var schemeCode= ObservableField("")
    var etRemark= ObservableField("")
    var etAppliactionNumber= ObservableField("")

    var sectorList: ObservableArrayList<String> = ObservableArrayList()
    var sectorListData: ObservableArrayList<Sector> = ObservableArrayList()
    var sectorCode = ObservableField("")

    var subSectorList: ObservableArrayList<String> = ObservableArrayList()
    var subSectorListData: ObservableArrayList<SubSector> = ObservableArrayList()
    var subSectorCode = ObservableField("")
    var sharedPreferences: SharedPreferences = application.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = sharedPreferences.edit()

    var mSchemeCode: MutableLiveData<String> = MutableLiveData()


    var mAction: MutableLiveData<JlgLoanGeneralDetailsAction> = MutableLiveData()

    init {
        JlgLoanGeneralDetailsRepository.mAction = mAction

        schemeList.clear()
        schemeListData.clear()

        schemeList.add("--select scheme--")
        schemeListData.add(Scheme("", "", "", "", ""))

        sectorList.clear()
        sectorListData.clear()

        sectorList.add("--select sector--")
        sectorListData.add(Sector("", "", ""))

        subSectorList.clear()
        subSectorListData.clear()

        subSectorList.add("--select sub-sector--")
        subSectorListData.add(SubSector("", "", ""))
    }

    private val registry = PropertyChangeRegistry()
    private var selectedScheme: Int = 0
    private var selectedSector: Int = 0
    private var selectedSubSector: Int = 0

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

    @Bindable
    fun getSelectedScheme(): Int {
        return selectedScheme
    }

    fun setSelectedScheme(selectedScheme: Int) {
        this.selectedScheme = selectedScheme
        registry.notifyChange(this, BR.selectedScheme)
    }

    fun onSelectedScheme(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        if (position!=0) {
            schemeCode.set(schemeListData[position].Sch_Code.toString())
            editor.putString(
                Constants.JLG_SCHEME_CODE,
                schemeListData[position].Sch_Code.toString())
            editor.apply()
            editor.putString(
                Constants.JLG_EMI_TYPE,
                schemeListData[position].Lnp_EMIType.toString())
            editor.apply()

            mSchemeCode.value=   schemeListData[position].Sch_Code.toString()

            mAction.value= JlgLoanGeneralDetailsAction(JlgLoanGeneralDetailsAction.SELECT_SCHEME,schemeListData[position])
        }

    }

    @Bindable
    fun getSelectedSector(): Int {
        return selectedSector
    }

    fun setSelectedSector(selectedSector: Int) {
        this.selectedSector = selectedSector
        registry.notifyChange(this, BR.selectedSector)
    }

    fun onSelectedSector(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        sectorCode.set(sectorListData[position].Code .toString())

    }

    @Bindable
    fun getSelectedSubSector(): Int {
        return selectedSubSector
    }

    fun setSelectedSubSector(selectedSubSector: Int) {
        this.selectedSubSector = selectedSubSector
        registry.notifyChange(this, BR.selectedSubSector)
    }

    fun onSelectedSubSector(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        subSectorCode.set(subSectorListData[position].Code .toString())
        subSectorCode.set(subSectorListData[position].Code .toString())

    }




    fun setSchemeData(scheme: List<Scheme>) {

        for (i in scheme.indices) {
            schemeList.add(scheme[i].Sch_Name)
            schemeListData.add(scheme[i])
        }

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.remove(callback)
    }

    fun setSectorData(sector: List<Sector>) {

        for (i in sector.indices) {
            sectorList.add(sector[i].Name)
            sectorListData.add(sector[i])
        }
    }

    fun setSubSectorData(subSector: List<SubSector>) {
        for (i in subSector.indices) {
            subSectorList.add(subSector[i].Name)
            subSectorListData.add(subSector[i])
        }

    }

    fun clickNext(view:View)
    {
        if (schemeCode.get().equals(""))
        {
            Toast.makeText(view.context, "Please select a scheme", Toast.LENGTH_SHORT).show()
        }else if (sectorCode.get().equals(""))
        {
            Toast.makeText(view.context, "Please select a sector", Toast.LENGTH_SHORT).show()
        }else if (subSectorCode.get().equals(""))
        {
            Toast.makeText(view.context, "Please sub-select a sector", Toast.LENGTH_SHORT).show()
        }else if (etRemark.get().equals(""))
        {
            Toast.makeText(view.context, "Remark cannot be empty", Toast.LENGTH_SHORT).show()
        }else if (etAppliactionNumber.get().equals(""))
        {
            Toast.makeText(view.context, "Application number cannot be empty", Toast.LENGTH_SHORT).show()
        }

        else
        {
            mAction.value= JlgLoanGeneralDetailsAction(JlgLoanGeneralDetailsAction.CLICK_NEXT)
        }
    }

    fun clearData() {
        schemeCode.set("")
        etRemark.set("")
        etAppliactionNumber.set("")
        setSelectedScheme(0)
        setSelectedSector(0)
        setSelectedSubSector(0)

    }

}