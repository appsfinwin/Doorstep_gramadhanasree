package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation

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
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation.pojo.SelectedMember
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Constants
import com.finwin.doorstep.gramadhanasree.utils.Services
import com.finwin.doorstep.gramadhanasree.BR
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject

class JlgGroupCreationViewModel(application: Application) : AndroidViewModel(application), Observable {

    var sharedPreferences: SharedPreferences= application.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = sharedPreferences.edit()

    var centerName: ObservableField<String> = ObservableField("")
    var centerCode: ObservableField<String> = ObservableField("")
    var groupName: ObservableField<String> = ObservableField("")
    var groupCode: ObservableField<String> = ObservableField("")
    var groupDetailsVisibility : ObservableField<Int> = ObservableField(View.GONE)
    var memberLayoutVisibility : ObservableField<Int> = ObservableField(View.GONE)
    var submitButtonVisibility : ObservableField<Int> = ObservableField(View.GONE)

    var member: ObservableField<String> = ObservableField("")
    var memberList = ObservableArrayList<Any>()
    var custIdList = ObservableArrayList<Any>()
    var memberListData = ObservableArrayList<SelectedMember>()
    private val registry = PropertyChangeRegistry()
    private var selectedMember = 0
    private var apiInterface: ApiInterface

    var mAction: MutableLiveData<GroupCreationAction> = MutableLiveData()
    init {
        apiInterface = RetrofitClient().getApi()!!
        JlgGroupCreationRepository.mAction= mAction
        memberList.clear()
        custIdList.clear()
        memberListData.clear()

        memberList.add("--Select a leader--")
        custIdList.add("")
        memberListData.add(SelectedMember("--Select a leader--","--Select a leader--","--Select a leader--","--Select a leader--"))
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

    @Bindable
    fun getSelectedMember(): Int {
        return selectedMember
    }

    fun addMember(selectedMember: SelectedMember)
    {
        memberList.add(selectedMember.customerName)
        custIdList.add(selectedMember.customerId)
        memberListData.add(selectedMember)
    } fun removeMember(selectedMember: SelectedMember)
    {
        custIdList.remove(selectedMember.customerId)
        memberList.remove(selectedMember.customerName)
        memberListData.remove(selectedMember)
    }

    fun setSelectedMember(selectedMember: Int) {
        this.selectedMember = selectedMember
        registry.notifyChange(this, BR.selectedBranch)
    }

    fun onSelectedMember(parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {

        member.set( memberListData.get(position).customerName)

    }

    fun clickCreateGroup(view:View)
    {
        if (centerName.get().equals(""))
        {
            Toast.makeText(view.context, "Please select a center", Toast.LENGTH_SHORT).show()
        }
        else if (groupName.get().equals(""))
        {
            Toast.makeText(view.context, "Group name cannot be empty", Toast.LENGTH_SHORT).show()
        }else if (groupCode.get().equals(""))
        {
            Toast.makeText(view.context, "Group code cannot be empty", Toast.LENGTH_SHORT).show()
        }else if (memberList.size<=1)
        {
            Toast.makeText(view.context, "Please add members", Toast.LENGTH_SHORT).show()
        }else if (member.get().equals("--Select a leader--"))
        {
            Toast.makeText(view.context, "Please select a leader", Toast.LENGTH_SHORT).show()
        }else{
            initLoading(view.context)
            groupCreation()
        }
    }

    fun reset() {
        TODO("Not yet implemented")
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.remove(callback)
    }

    public fun groupCreation() {

        try {


            //val params: MutableMap<String, Any?> = HashMap()


            val params = JSONObject()
            val custTable = JSONArray()


            params.put("Brcode", sharedPreferences.getString(Constants.BRANCH_ID, ""))
            params.put("CenterCode", centerCode.get())
            params.put("GroupCode", groupCode.get())
            params.put("GroupName", groupName.get())
            params.put("Flag", "INSERT")


            params.put("MakerId", sharedPreferences.getString(Constants.AGENT_ID, ""))

            for (i in memberListData.indices)
            {
                var custTableItems= JSONObject()
                custTableItems.put("Cust_Id", memberListData[i].customerId)
                //custTableItems.put("Cust_Id", memberListData[i].accountNumber)
                    if (i!=0) {
                        custTable.put(custTableItems)
                    }
            }


            params.put("Custtable", (custTable).toString())

            var request = (params).toString()
            request = (params).toString()
            val body = RequestBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                params.toString()
            )
            JlgGroupCreationRepository.createGroup(apiInterface, body)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clearData() {

        centerName.set("")
        centerCode.set("")
        groupName.set("")
        groupCode.set("")
        groupDetailsVisibility.set (View.GONE)
        submitButtonVisibility.set (View.GONE)
        memberLayoutVisibility.set (View.GONE)

        memberList.clear()
        memberListData.clear()
        custIdList.clear()

        memberList.add("--Select a leader--")
        memberListData.add(SelectedMember("--Select a leader--","--Select a leader--","--Select a leader--","--Select a leader--"))


    }
}