package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group

import android.content.Context
import android.view.View
import android.widget.AdapterView
import androidx.databinding.*
import androidx.lifecycle.*
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.BR
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.action.SelectGroupAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.room.Member
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Services
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject

class JlgLoanSelectGroupViewModel(private val repository: JlgSelectGroupRepository) : ViewModel() , Observable {
    var groupCode = ObservableField("")
    var selectedOption = 0
    private var registry: PropertyChangeRegistry = PropertyChangeRegistry()
    var option = ObservableField("")

    var optionList: ObservableArrayList<String> = ObservableArrayList()
    var optionListData: ObservableArrayList<OptionData> = ObservableArrayList()
    var mAction: MutableLiveData<SelectGroupAction> = MutableLiveData()
    var mOption: MutableLiveData<String> = MutableLiveData()
    var disbursementAmount : ObservableField<String> = ObservableField("")
    var apiInterface = RetrofitClient().getApi()!!

    init {
        repository.mAction = mAction
        optionList.add("--select option--")
        optionList.add("Disbursement")
        optionList.add("Customer Goods")

        optionListData.add(OptionData("--select option--",""))
        optionListData.add(OptionData("Disbursement","Amount"))
        optionListData.add(OptionData("Customer Goods","Product"))
    }

    var memberData: LiveData<List<Member>> = MutableLiveData()
    var data : List<String> = mutableListOf()
    var disbaursementData : List<Member> = mutableListOf()
    fun insert(member: Member) = viewModelScope.launch {
        repository.insert(member)
    }

    fun clearData() = viewModelScope.launch {
        repository.clearData()
    }

    fun updateMember(member: Member) = viewModelScope.launch {
        repository.updateMember(member)
    }


    fun getDisbursementAmount(): List<Member> {

        viewModelScope.launch {
            disbaursementData=repository.getDisbursementAmount()
        }

        return disbaursementData

    }

    fun getAllMembers(): LiveData<List<Member>> {
        viewModelScope.launch {
            memberData = repository.allMembers().asLiveData()
        }
        return memberData
    }

    @JvmName("getSelectedOption1")
    @Bindable
    fun getSelectedOption(): Int {
        return selectedOption
    }

    @JvmName("setSelectedOption1")
    fun setSelectedOption(selectedOption: Int) {
        this.selectedOption = selectedOption
        registry.notifyChange(this, BR.selectedOption1)
    }

    fun onSelectedOtpion(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {

        option.set(optionListData.get(position).optionValue)
        mOption.value = option.get()

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

    fun clickSearch(view: View) {

    }

//    fun clickSubmit(view: View) {
//
//    }



    public fun getGroup(groupCode: String) {

        try {

            val params = JSONObject()

            //params.put("groupcode", groupCode.get())
            params.put("groupcode", groupCode)


            var request = (params).toString()
            request = (params).toString()
            val body = RequestBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                params.toString()
            )
            repository.getGroup(apiInterface, body)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.remove(callback)
    }

    fun saveMemberData(member: List<Member>) {
        this.disbaursementData= member

        var _disbursementAmount: Double = 0.0

        for (i in member.indices)
        {
            _disbursementAmount += (member[i].disbursementAmount).toDouble()
            var sss= _disbursementAmount
        }
        disbursementAmount.set(_disbursementAmount.toString())
    }

    fun clickSubmit(view: View)
    {

    }
}

class MemberViewmodelFactory(private val repository: JlgSelectGroupRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JlgLoanSelectGroupViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return JlgLoanSelectGroupViewModel(repository) as T
        }

        throw IllegalStateException("unknown viewmodel class")

    }

}

public data class OptionData(val optionName: String,val optionValue: String)