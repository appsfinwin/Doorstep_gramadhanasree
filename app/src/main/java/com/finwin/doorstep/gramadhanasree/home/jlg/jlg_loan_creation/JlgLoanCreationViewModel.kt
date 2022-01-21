package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.JlgLoanCreationRepository
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.room.Member
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.pojo.ProductData
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Scheme
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Constants
import com.finwin.doorstep.gramadhanasree.utils.Services
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class JlgLoanCreationViewModel(application: Application) : AndroidViewModel(application) {

    var mAction: MutableLiveData<JlgLoanCreationAction> = MutableLiveData()
    var mSchemeCode: MutableLiveData<String> = MutableLiveData()
    var selectedScheme: ObservableField<Scheme> = ObservableField()
    var productData: ObservableArrayList<ProductData> = ObservableArrayList()
    var branchCode: ObservableField<String> = ObservableField("")
    var schemeCode: ObservableField<String> = ObservableField("")
    var applicationNumber: ObservableField<String> = ObservableField("")
    var custId: ObservableField<String> = ObservableField("")
    var groupId: ObservableField<String> = ObservableField("")

    var sectorCode: ObservableField<String> = ObservableField("")
    var subSectorCode: ObservableField<String> = ObservableField("")
    var remark: ObservableField<String> = ObservableField("")
    var groupData: List<Member> = mutableListOf()

    var etEmiType: ObservableField<String> = ObservableField("")
    var loanPeriodType: ObservableField<String> = ObservableField("")
    var loanPeriodDays: ObservableField<String> = ObservableField("")
    var etLoanAmount: ObservableField<String> = ObservableField("")
    var etInterestRate: ObservableField<String> = ObservableField("")
    var etPenalInterest: ObservableField<String> = ObservableField("")
    var etInstallmentAmount: ObservableField<String> = ObservableField("")
    var etInstallmentNumber: ObservableField<String> = ObservableField("")
    var etResolutionNumber: ObservableField<String> = ObservableField("")

    var calculationtype: ObservableField<String> = ObservableField("")
    var applicationDate: ObservableField<String> = ObservableField("--Select application date--")
    var resolutionDate: ObservableField<String> = ObservableField("--Select resolution date--")
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
    var optionType: ObservableField<String> = ObservableField("")
    var sharedPreferences =
        application.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)

    var apiInterface: ApiInterface
    init {
        apiInterface = RetrofitClient().getApi()!!
        JlgLoanCreationRepository.mAction = mAction
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
        JlgLoanCreationRepository.getCodeMasters(apiInterface)
    }

    public fun getLoanPeriod(schemeCode: String?) {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams.put("Sch_Code", schemeCode)
        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        JlgLoanCreationRepository.getLoanPeriod(apiInterface, body)

    }

    public fun getJlgProducts() {

        val params = JSONObject()
        params.put("Br_Code", sharedPreferences.getString(Constants.BRANCH_ID, ""))

        var request = (params).toString()
        request = (params).toString()
        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            params.toString()
        )

        JlgLoanCreationRepository.getJlgProducts(apiInterface, body)

    }

    fun createGroupLoan(view: View) {
        if (schemeCode.get() == "") {
            Toast.makeText(view.context, "scheme code empty", Toast.LENGTH_SHORT).show()
        } else if (branchCode.get() == "") {
            Toast.makeText(view.context, "branch code empty", Toast.LENGTH_SHORT).show()
        } else if (applicationNumber.get() == "") {
            Toast.makeText(view.context, "applicationNumber empty", Toast.LENGTH_SHORT).show()
        } else if (custId.get() == "") {
            Toast.makeText(view.context, "custId empty", Toast.LENGTH_SHORT).show()
        } else if (groupId.get() == "") {
            Toast.makeText(view.context, "groupId empty", Toast.LENGTH_SHORT).show()
        } else if (etLoanAmount.get() == "") {
            Toast.makeText(view.context, "etLoanAmount empty", Toast.LENGTH_SHORT).show()
        } else if (applicationDate.get() == "") {
            Toast.makeText(view.context, "applicationDate empty", Toast.LENGTH_SHORT).show()
        } else if (loanPeriodDays.get() == "") {
            Toast.makeText(view.context, "loanPeriodDays empty", Toast.LENGTH_SHORT).show()
        } else if (loanPeriodType.get() == "") {
            Toast.makeText(view.context, "loanPeriodType empty", Toast.LENGTH_SHORT).show()
        } else if (etInterestRate.get() == "") {
            Toast.makeText(view.context, "etInterestRate empty", Toast.LENGTH_SHORT).show()
        } else if (etInstallmentAmount.get() == "") {
            Toast.makeText(view.context, "etInstallmentAmount empty", Toast.LENGTH_SHORT).show()
        } else if (etResolutionNumber.get() == "") {
            Toast.makeText(view.context, "etResolutionNumber empty", Toast.LENGTH_SHORT).show()
        } else if (resolutionDate.get() == "") {
            Toast.makeText(view.context, "resolutionDate empty", Toast.LENGTH_SHORT).show()
        } else if (etLotNumber.get() == "") {
            Toast.makeText(view.context, "etLotNumber empty", Toast.LENGTH_SHORT).show()
        } else if (collectionDay.get() == "") {
            Toast.makeText(view.context, "collectionDay empty", Toast.LENGTH_SHORT).show()
        } else if (collectionstaff.get() == "") {
            Toast.makeText(view.context, "collectionstaff empty", Toast.LENGTH_SHORT).show()
        } else if (etMoretoriumPeriod.get() == "") {
            Toast.makeText(view.context, "etMoretoriumPeriod empty", Toast.LENGTH_SHORT).show()
        } else if (remark.get() == "") {
            Toast.makeText(view.context, "remark empty", Toast.LENGTH_SHORT).show()
        } else if (paymentMode.get()=="Transfer" && accountNumber.get()=="") {
            Toast.makeText(view.context, "accountNumber empty", Toast.LENGTH_SHORT).show()
        } else if (etEmiType.get() == "") {
            Toast.makeText(view.context, "etEmiType empty", Toast.LENGTH_SHORT).show()
        } else {
            initLoading(view.context)
            createJlgLoan()
        }
    }

    public fun createJlgLoan() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        val group_data = makeJsonObj()

        jsonParams["Schcode"] = schemeCode.get()
        jsonParams["Brcode"] = branchCode.get()
        jsonParams["ApplSlno"] = applicationNumber.get()
        jsonParams["LoanType"] = "JGL"
        jsonParams["Custid"] = custId.get()
        jsonParams["Group_Id"] = groupId.get()
        jsonParams["ShrBrCode"] = branchCode.get()
        jsonParams["ShrSchCode"] = schemeCode.get()
        jsonParams["LoanAmount"] = etLoanAmount.get()
        jsonParams["LoanDate"] = applicationDate.get()
        jsonParams["Period"] = loanPeriodDays.get()
        jsonParams["PeriodType"] = loanPeriodType.get()
        jsonParams["IntRate"] = etInterestRate.get()
        jsonParams["PenalRate"] = etPenalInterest.get()
        jsonParams["InstallmentNo"] = etInstallmentNumber.get()
        jsonParams["InstallmentAmount"] = etInstallmentAmount.get()
        jsonParams["ApprID"] = "0"
        jsonParams["Mainpurpose"] = sectorCode.get()
        jsonParams["Subpurpose"] = subSectorCode.get()
        jsonParams["ResNo"] = etResolutionNumber.get()
        jsonParams["ResDate"] = resolutionDate.get()
        jsonParams["LotNo"] = etLotNumber.get()
        jsonParams["CollectionDay"] = collectionDay.get()
        jsonParams["CollectionStaff"] = collectionstaff.get()
        jsonParams["MoratoriumPeriod"] = etMoretoriumPeriod.get()
        jsonParams["Remarks"] = remark.get()
        jsonParams["Bank_Acc_No"] = accountNumber.get()
        jsonParams["EmiType"] = etEmiType.get()
        jsonParams["flag"] = "INSERT"
        jsonParams["TranMode"] = paymentMode.get()
        jsonParams["CoApplicant"] = group_data
        jsonParams["OptionType"] = optionType.get()

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        var request = JSONObject(jsonParams).toString()
        JlgLoanCreationRepository.createJlgLoan(apiInterface, body)

    }

    fun makeJsonObj(): String? {
        var obj: JSONObject
        val jsonArray = JSONArray()
        try {
            for (i in groupData.indices) {
                obj = JSONObject()
                obj.put("Slno", groupData[i].slno)
                obj.put("CustID", groupData[i].customerId)
                obj.put("Customer Name", groupData[i].customerName)
                obj.put("CoApplicant", groupData[i].slno)
                obj.put("Mobile", groupData[i].mobile)
                obj.put("Address", groupData[i].address)
                obj.put("ConsumerGoods", groupData[i].consumerGoods)
                obj.put("Amount", groupData[i].amount)
                obj.put("Insurance Fee", groupData[i].insuranceFee)
                obj.put("Documentation Fee", groupData[i].documentationFee)
                obj.put("CGST", groupData[i].cgst)
                obj.put("SGST", groupData[i].sgst)
                obj.put("Cess", groupData[i].cess)
                obj.put("Disbursement Amount", groupData[i].disbursementAmount)
                jsonArray.put(obj)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return jsonArray.toString()
    }

    fun setProductData(data: List<ProductData>) {

        productData.clear()
        for (i in data.indices) {
            productData.add(data[i])
        }

    }
}