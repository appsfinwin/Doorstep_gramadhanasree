package com.finwin.doorstep.gramadhanasree.home.customer_creation

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.*
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.BR
import com.finwin.doorstep.gramadhanasree.home.customer_creation.action.CustomerCretaionAction
import com.finwin.doorstep.gramadhanasree.home.customer_creation.pojo.*
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Services
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CustomerCreationViewModel() : ViewModel(), Observable {


    var mAction: MutableLiveData<CustomerCretaionAction> = MutableLiveData()
    var focusData: MutableLiveData<String> = MutableLiveData()

    var agentId: ObservableField<String> = ObservableField("")
    var branchId: ObservableField<String> = ObservableField("")
    var branchListData: ObservableArrayList<Branch> = ObservableArrayList()
    var branchList: ObservableArrayList<String> = ObservableArrayList()

    var etEmailId: ObservableField<String> = ObservableField("")
    var postOfficeListData: ObservableArrayList<PostOffice> = ObservableArrayList()
    var postOfficeList: ObservableArrayList<String> = ObservableArrayList()

    var genderId: ObservableField<String> = ObservableField("")
    var genderListData: ObservableArrayList<Gender> = ObservableArrayList()
    var genderList: ObservableArrayList<String> = ObservableArrayList()

    var prefixId: ObservableField<String> = ObservableField("")
    var prefixListData: ObservableArrayList<Prefix> = ObservableArrayList()
    var prefixList: ObservableArrayList<String> = ObservableArrayList()

    var idProofIdOne: ObservableField<String> = ObservableField("-1")
    var idProofIdTwo: ObservableField<String> = ObservableField("-1")
    var idProofListData: ObservableArrayList<IdProof> = ObservableArrayList()
    var idProofList: ObservableArrayList<String> = ObservableArrayList()

    var addressTypeId: ObservableField<String> = ObservableField("")
    var addressTypeListData: ObservableArrayList<AddressType> = ObservableArrayList()
    var addressTypeList: ObservableArrayList<String> = ObservableArrayList()

    var locationId: ObservableField<String> = ObservableField("")
    var locationListData: ObservableArrayList<Location> = ObservableArrayList()
    var locationList: ObservableArrayList<String> = ObservableArrayList()

    var phoneTypeId: ObservableField<String> = ObservableField("")
    var phoneTypeData: ObservableArrayList<PhoneType> = ObservableArrayList()
    var phoneTypeList: ObservableArrayList<String> = ObservableArrayList()

    var fromDateOne = ObservableField("Select Date")
    var toDateOne = ObservableField("Select Date")
    var fromDateOneSelected = ObservableField("Select Date")
    var toDateOneSelected = ObservableField("Select Date")

    var fromDateTwo = ObservableField("Select Date")
    var toDateTwo = ObservableField("Select Date")
    var fromDateTwoSelected = ObservableField("Select Date")
    var toDateTwoSelected = ObservableField("Select Date")

    var address = ObservableField("")
    var dob = ObservableField("Select Date of Birth")
    var idProofNumberOne = ObservableField("")
    var idProofNumberTwo = ObservableField("")
    var etPincode = ObservableField("")
    var etPhone = ObservableField("")
    var etHouseName = ObservableField("")
    var et_age = ObservableField("0")
    var otp = ObservableField("")
    var otpId = ObservableField("")

    var idProofOneSideOne64 = ObservableField("")
    // var idProofOneSideTwo64 = ObservableField("")
    var idProofTwoSideOne64 = ObservableField("")
    //var idProofTwoSideTwo64 = ObservableField("")

    var profilePic64 = ObservableField("")
    var signature64 = ObservableField("")
    var etName = ObservableField("")
    var postOfficeName = ObservableField("")

    var obSelectedIdProofOne = ObservableField("")
    var obSelectedIdProofTwo = ObservableField("")

    var obSelectedAddressType = ObservableField("")
    var obSelectedLocation = ObservableField("")
    var obSelectedPhoneType = ObservableField("")
    var obSelectedPrefix = ObservableField("")
    var obSelectedGender = ObservableField("")
    var isPhoneVerified = ObservableField(false)
    var repository: CustomerCreationRepository = CustomerCreationRepository().getInstance()

    var apiInterface: ApiInterface? = RetrofitClient().getApi()
    var verifyPhoneNumber = MutableLiveData(false)

    init {
        repository.mAction = mAction
        initSpinnser()

    }

    private fun initSpinnser() {

        branchListData.clear()
        branchList.clear()
        branchList.add("--Select Branch--")
        branchListData.add(Branch("00", "--Select Branch--"))

        postOfficeList.clear()
        postOfficeListData.clear()
        postOfficeList.add("--Select Post Office--")
        postOfficeListData.add(
            PostOffice(
                "00",
                "",
                "",
                "",
                "",
                "",
                "",
                "--Select Post Office--",
                "",
                "",
                ""
            )
        )

        genderList.clear()
        genderListData.clear()
        genderList.add("--Select Gender--")
        genderListData.add(Gender("-1", "--Select Gender--"))

        prefixList.clear()
        prefixListData.clear()
        prefixList.add("--Select--")
        prefixListData.add(Prefix("-1", "--Select--"))

        idProofList.clear()
        idProofListData.clear()
        idProofList.add("--Select--")
        idProofListData.add(IdProof("-1", "--Select--"))

        addressTypeList.clear()
        addressTypeListData.clear()
        addressTypeList.add("--Select--")
        addressTypeListData.add(AddressType("-1", "--Select--"))

        locationList.clear()
        locationListData.clear()
        locationList.add("--Select--")
        locationListData.add(Location("-1", "--Select--"))

        phoneTypeList.clear()
        phoneTypeData.clear()
        phoneTypeList.add("--Select--")
        phoneTypeData.add(PhoneType("-1", "--Select--"))
    }

    private var selectedBranch = 0
    private var selectedGender = 0
    private var selectedPrefix = 0
    private var selectedIdProofOne = 0
    private var selectedIdProofTwo = 0
    private var selectedAddressType = 0
    private var selectedLocation = 0
    private var selectedPhoneType = 0
    private var selectedPostOffice = 0

    @Bindable
    private var selectedPostOfficeOnly = 0
    private val registry = PropertyChangeRegistry()

    @Bindable
    fun getSelectedBranch(): Int {
        return selectedBranch
    }

    fun setSelectedBranch(selectedBranch: Int) {
        this.selectedBranch = selectedBranch
        registry.notifyChange(this, BR.selectedBranch)
    }

    fun onSelectedBranch(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        branchId.set(branchListData[position].Branch_Code.toString())

    }


    @Bindable
    fun getSelectedGender(): Int {
        return selectedGender
    }

    fun setSelectedGender(selectedGender: Int) {
        this.selectedGender = selectedGender
        registry.notifyChange(this, BR.selectedGender)
    }

    fun onSelectedGender(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        genderId.set(genderListData[position].Code.toString())
        obSelectedGender.set(genderListData[position].Name.toString())

    }

    @Bindable
    fun getSelectedPrefix(): Int {
        return selectedPrefix
    }

    fun setSelectedPrefix(selectedPrefix: Int) {
        this.selectedPrefix = selectedPrefix
        registry.notifyChange(this, BR.selectedPrefix)
    }

    fun onSelectedPrefix(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        prefixId.set(prefixListData[position].Code.toString())
        obSelectedPrefix.set(prefixListData[position].Name.toString())

    }

    @Bindable
    fun getSelectedIdProofOne(): Int {
        return selectedIdProofOne
    }

    fun setSelectedIdProofOne(selectedIdProof: Int) {
        this.selectedIdProofOne = selectedIdProof
        registry.notifyChange(this, BR.selectedIdProofOne)
    }

    fun onSelectedIdProofOne(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        idProofIdOne.set(idProofListData[position].Code.toString())
        obSelectedIdProofOne.set(idProofListData[position].Name)

    }

    @Bindable
    fun getSelectedIdProofTwo(): Int {
        return selectedIdProofOne
    }

    fun setSelectedIdProofTwo(selectedIdProof: Int) {
        this.selectedIdProofOne = selectedIdProof
        registry.notifyChange(this, BR.selectedIdProofTwo)
    }

    fun onSelectedIdProofTwo(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        idProofIdTwo.set(idProofListData[position].Code.toString())
        obSelectedIdProofTwo.set(idProofListData[position].Name)
    }


    @Bindable
    fun getSelectedAddressType(): Int {
        return selectedAddressType
    }

    fun setSelectedAddressType(selectedAddressType: Int) {
        this.selectedAddressType = selectedAddressType
        registry.notifyChange(this, BR.selectedAddressType)
    }

    fun onSelectedAddressType(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        addressTypeId.set(addressTypeListData[position].Code.toString())
        obSelectedAddressType.set(addressTypeListData[position].Name)

    }

    @Bindable
    fun getSelectedLocation(): Int {
        return selectedLocation
    }

    fun setSelectedLocation(selectedLocation: Int) {
        this.selectedLocation = selectedLocation
        registry.notifyChange(this, BR.selectedLocation)
    }

    fun onSelectedLocation(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        locationId.set(locationListData[position].Code.toString())
        obSelectedLocation.set(locationListData[position].Name.toString())
    }

    @Bindable
    fun getSelectedPhoneType(): Int {
        return selectedPhoneType
    }

    fun setSelectedPhoneType(selectedPhoneType: Int) {
        this.selectedPhoneType = selectedPhoneType
        registry.notifyChange(this, BR.selectedPhoneType)
    }

    fun onSelectedPhoneType(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        phoneTypeId.set(phoneTypeData[position].Code.toString())
        obSelectedPhoneType.set(phoneTypeData[position].Name.toString())

    }

    @Bindable
    fun getSelectedPostOffice(): Int {
        return selectedPostOffice
    }

    fun setSelectedPostOffice(selectedPostOffice: Int) {
        this.selectedPostOffice = selectedPostOffice
        registry.notifyChange(this, BR.selectedPostOffice)
    }

    fun onSelectedPostOffice(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {
        postOfficeName.set(postOfficeListData[position].Name.toString())

    }

    fun onClickFromDateOne(view: View) {
        val myCalendar = Calendar.getInstance()
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                //Toast.makeText(view.context,year.toString()+"-"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString(),Toast.LENGTH_LONG).show()
                var mm =
                    changeDateFormat(dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year.toString())
                fromDateOne.set(changeDateFormat(dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year.toString()))
                var _date =
                    (monthOfYear + 1).toString() + "-" + dayOfMonth.toString() + "-" + year.toString()
                //fromDateOneSelected.set(changeDateFormat(_date))

            }
        val datePickerDialog: DatePickerDialog
        datePickerDialog = DatePickerDialog(
            view.context, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }

    fun onClickToDateOne(view: View) {
        val myCalendar = Calendar.getInstance()
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                //Toast.makeText(view.context,year.toString()+"-"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString(),Toast.LENGTH_LONG).show()
                var mm =
                    changeDateFormat(dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year.toString())
                toDateOne.set(changeDateFormat(dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year.toString()))

                var _date =
                    (monthOfYear + 1).toString() + "-" + dayOfMonth.toString() + "-" + year.toString()
                //toDateOneSelected.set(changeDateFormat(_date))
            }
        val datePickerDialog: DatePickerDialog
        datePickerDialog = DatePickerDialog(
            view.context, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }

    fun onClickFromDateTwo(view: View) {
        val myCalendar = Calendar.getInstance()
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                //Toast.makeText(view.context,year.toString()+"-"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString(),Toast.LENGTH_LONG).show()
                var mm =
                    changeDateFormat(dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year.toString())
                fromDateTwo.set(changeDateFormat(dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year.toString()))

                var _date =
                    (monthOfYear + 1).toString() + "-" + dayOfMonth.toString() + "-" + year.toString()
                fromDateTwoSelected.set(changeDateFormat(_date))
            }
        val datePickerDialog: DatePickerDialog
        datePickerDialog = DatePickerDialog(
            view.context, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }

    fun onClickToDateTwo(view: View) {
        val myCalendar = Calendar.getInstance()
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                //Toast.makeText(view.context,year.toString()+"-"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString(),Toast.LENGTH_LONG).show()
                var mm =
                    changeDateFormat(dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year.toString())
                toDateTwo.set(changeDateFormat(dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year.toString()))

                var _date =
                    (monthOfYear + 1).toString() + "-" + dayOfMonth.toString() + "-" + year.toString()
                //toDateTwoSelected.set(Services.convertDate(_date))
                toDateTwoSelected.set(changeDateFormat(_date))
            }

        val datePickerDialog: DatePickerDialog
        datePickerDialog = DatePickerDialog(
            view.context, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }

    fun onClickDob(view: View) {
        val myCalendar = Calendar.getInstance()
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                //Toast.makeText(view.context,year.toString()+"-"+(monthOfYear+1).toString()+"-"+dayOfMonth.toString(),Toast.LENGTH_LONG).show()

                //dob.set(dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year.toString())
                var age =
                    getAge(dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year.toString())

                var dob_date =
                    (monthOfYear + 1).toString() + "/" + dayOfMonth.toString() + "/" + year.toString()
//                val sdf = SimpleDateFormat("MM/dd/yyyy")
//                val dob = Calendar.getInstance()
//                dob.time = sdf.parse(dob_date)
                var mm =
                    changeDateFormat(dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year.toString())
                dob.set(changeDateFormat(dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year.toString()))

                et_age.set(age.toString())
            }
        val datePickerDialog: DatePickerDialog
        datePickerDialog = DatePickerDialog(
            view.context, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }

    public fun getRefCodes() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["Flag"] = "string"

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        apiInterface?.let { repository.getRefCodes(it, body) }
    }
    public fun createOtp() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["Flag"] = "SEND"
        jsonParams["MobileNo"] = etPhone.get()
        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        apiInterface?.let { repository.createOtp(it, body) }
    }
    public fun clickSentOtp(view: View)
    {
        if (etPhone.get().equals("") || (etPhone.get()?.length!! <10))
        {
            toast(view.context, "Please enter a valid phone number!")
        }else
        {
            initLoading(view.context)
            createOtp()
        }
    }

    public fun submitOtp(view: View)
    {
        if (otp.get().equals(""))
        {
            toast(view.context, "Please enter OTP!")
        }else
        {
            initLoading(view.context)
            validateOtp()
        }
    }

    public fun clickResentOtp(view: View)
    {
        if (etPhone.get().equals("") || (etPhone.get()?.length!! <10))
        {
            toast(view.context, "Please enter a valid phone number!")
        }else
        {
            initLoading(view.context)
            resendOtp()
        }
    }
    public fun resendOtp() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["Flag"] = "SEND"
        jsonParams["MobileNo"] = etPhone.get()
        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface?.let { repository.createOtp(it, body) }
    }

    public fun validateOtp() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["Flag"] = "CHECK"
        jsonParams["OTP_ID"] = otpId.get()
        jsonParams["OTP"] = otp.get()
        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface?.let { repository.validateOtp(it, body) }
    }

    public fun customerCreation() {

        try {


            //val params: MutableMap<String, Any?> = HashMap()
            val IdTableItems = JSONObject()
            val IdTableItemsOne = JSONObject()
            val addressItems = JSONObject()

            val params = JSONObject()
            val idTable = JSONArray()
            val addTable = JSONArray()
            params.put("Br_Code", branchId.get())
            params.put("Cust_NameTitle", prefixId.get())
            params.put("Cust_Name", etName.get())
            params.put("Cust_Dob", dob.get())
            params.put("Cust_Age", et_age.get())
            params.put("Cust_Gender", genderId.get())
            params.put("Flag", "INSERT")
            params.put("Add_TypeId", addressTypeId.get())
            params.put("Add_Address", address.get())
            params.put("Add_LocationCode", locationId.get())
            params.put("Add_Postoffice", postOfficeName.get())
            params.put("Add_Pincode", etPincode.get())
            params.put("Add_Phone", etPhone.get())
            params.put("Add_FamilyName", etHouseName.get())
            params.put("Cust_Image", profilePic64.get())
            params.put("Cust_Sign", signature64.get())
            params.put("MakerID", agentId.get())

            IdTableItems.put("IdProofType", obSelectedIdProofOne.get())
            IdTableItems.put("IdProofCode", idProofIdOne.get())
            IdTableItems.put("IdNumber", idProofNumberOne.get())
            IdTableItems.put("Authority", "")
            IdTableItems.put("Details", "")
            IdTableItems.put("DateFrom", fromDateOne.get())
            IdTableItems.put("DateTo", toDateOne.get())
            IdTableItems.put("IdImage1", idProofOneSideOne64.get())
            IdTableItems.put("IdImage2", "")

            IdTableItemsOne.put("IdProofType", obSelectedIdProofTwo.get())
            IdTableItemsOne.put("IdProofCode", idProofIdTwo.get())
            IdTableItemsOne.put("IdNumber", idProofNumberTwo.get())
            IdTableItemsOne.put("Authority", "")
            IdTableItemsOne.put("Details", "")
            if(fromDateOne.get().equals("Select Date")){
                IdTableItems.put("DateFrom", "")
            }else{
                IdTableItems.put("DateFrom", fromDateOne.get())
            }

            if (toDateOne.get().equals("Select Date"))
            {
                IdTableItems.put("DateTo", "")
            }else
            {
                IdTableItems.put("DateTo", toDateOne.get())
            }

            if(fromDateTwo.get().equals("Select Date")){
                IdTableItemsOne.put("DateFrom", "")
            }else{
                IdTableItemsOne.put("DateFrom", fromDateTwo.get())
            }

            if (toDateTwo.get().equals("Select Date"))
            {
                IdTableItemsOne.put("DateTo", "")
            }else
            {
                IdTableItemsOne.put("DateTo", toDateTwo.get())
            }


            IdTableItemsOne.put("IdImage1", idProofTwoSideOne64.get())
            IdTableItemsOne.put("IdImage2","")

            addressItems.put("AddressType", obSelectedAddressType.get())
            addressItems.put("AddstypeCode", addressTypeId.get())
            addressItems.put("Address", address.get())
//        addressItems.put("Adds", address.get()
            addressItems.put("Location", obSelectedLocation.get())
            addressItems.put("LocationCode", locationId.get())
            addressItems.put("PostOffice", postOfficeName.get())
            addressItems.put("Pincode", etPincode.get())
            addressItems.put("PhoneType", obSelectedPhoneType.get())
            addressItems.put("PhonetypeCode", phoneTypeId.get())
            addressItems.put("PhoneNumber", etPhone.get())
            addressItems.put("HouseName", etHouseName.get())
            // addressItems.put("FamilyName", etHouseName.get()
            addressItems.put("MobileNumber", etPhone.get())
            addressItems.put("Fax", "")
            addressItems.put("WebAddress", "")
            addressItems.put("Email", etEmailId.get())


            //IdTable.put("IdTable", IdTableItems

            //addressTable.put("AddsTable", addressItems

            idTable.put(IdTableItems)
            if (!idProofIdTwo.get().equals("-1")) {
                idTable.put(IdTableItemsOne)
            }
            addTable.put(addressItems)


            params.put("IdTable", idTable)
            params.put("AddsTable", addTable)

            var request = (params).toString()
            request = (params).toString()
            val body = RequestBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                params.toString()
            )

            apiInterface?.let { repository.customerCreation(it, body) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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

    public fun clickProfilePhoto(view: View) {
        mAction.value = CustomerCretaionAction(CustomerCretaionAction.CLICK_PROFILE_IMAGE)
    }

    public fun clickSignature(view: View) {
        mAction.value = CustomerCretaionAction(CustomerCretaionAction.CLICK_SIGNATURE)
    }

    public fun clickIdproofSideOne(view: View) {
        mAction.value = CustomerCretaionAction(CustomerCretaionAction.CLICK_ID_PROOF_ONE)
    }


    public fun clickIdproofSideTwo(view: View) {
        mAction.value = CustomerCretaionAction(CustomerCretaionAction.CLICK_ID_PROOF_TWO)
    }


    private fun toast(context: Context?, s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.remove(callback)
    }

    fun setSpinnerData(response: ReferenceCodesResponse) {

        if (response.data.branch.isNotEmpty()) {
            for (i in response.data.branch.indices) {
                branchList.add(response.data.branch[i].Branch_Name)
                branchListData.add(response.data.branch[i])
            }
        }
        if (response.data.gender.isNotEmpty()) {
            for (i in response.data.gender.indices) {
                genderList.add(response.data.gender[i].Name)
                genderListData.add(response.data.gender[i])
            }
        }

        if (response.data.prefix.isNotEmpty()) {
            for (i in response.data.prefix.indices) {
                prefixList.add(response.data.prefix[i].Name)
                prefixListData.add(response.data.prefix[i])
            }
        }

        if (response.data.idProof.isNotEmpty()) {
            for (i in response.data.idProof.indices) {
                idProofList.add(response.data.idProof[i].Name)
                idProofListData.add(response.data.idProof[i])
            }
        }

        if (response.data.addressType.isNotEmpty()) {
            for (i in response.data.addressType.indices) {
                addressTypeList.add(response.data.addressType[i].Name)
                addressTypeListData.add(response.data.addressType[i])
            }
        }

        if (response.data.location.isNotEmpty()) {
            for (i in response.data.location.indices) {
                locationList.add(response.data.location[i].Name)
                locationListData.add(response.data.location[i])
            }
        }
        if (response.data.phoneType.isNotEmpty()) {
            for (i in response.data.phoneType.indices) {
                phoneTypeList.add(response.data.phoneType[i].Name)
                phoneTypeData.add(response.data.phoneType[i])
            }
        }

    }

    fun onPhoneChanged(text: CharSequence?) {
        // TODO do something with text
//        binding.imgCustidVerified.setImageResource(R.drawable.ic_not_verified);
//        isCustIdVerified.set(false);
//        binding.layoutCustomerDetails.setVisibility(View.GONE);
        if (isPhoneVerified.get() == true) {
            verifyPhoneNumber.setValue(false)
        }
    }


    fun onTextChanged(text: CharSequence?) {
        resetPostOffice()
        if (text?.length == 6) {
            RetrofitClient().RetrofitClientPostOfiice()?.create(ApiInterface::class.java)
                ?.let { repository.getPostOffice(it, text.toString()) }
        }

    }

    public fun clickSubmit(view: View) {
        if (branchId.get().equals("00")) {
            toast(view.context, "Please select branch")
            focusData.value = "branch"
        } else if (branchId.get().equals("-1")) {
            toast(view.context, "Please select branch")
            focusData.value = "branch"
        } else if (prefixId.get().equals("-1")) {
            toast(view.context, "Please select prefix")
            focusData.value = "prefix"
        } else if (etName.get().equals("")) {
            toast(view.context, "Name cannot be empty!")
            focusData.value = "name"
        } else if (dob.get().equals("")) {
            toast(view.context, "Please select Date of Birth!")
            focusData.value = "dob"
        } else if (genderId.get().equals("-1")) {
            toast(view.context, "Please select Gender")
            focusData.value = "gender"
        } else if (idProofIdOne.get().equals("-1")) {
            toast(view.context, "Please select an ID proof")
            focusData.value = "idProofId"
        } else if (idProofNumberOne.get().equals("-1")) {
            toast(view.context, "Please enter ID number")
            focusData.value = "idProofNumber"
        }
//        else if (fromDate.get().equals("Select Date")) {
//            toast(view.context, "Please select from date")
//            focusData.value = "fromDate"
//        } else if (toDate.get().equals("Select Date")) {
//            toast(view.context, "Please select expiry date")
//            focusData.value = "toDate"
//        }
        else if (addressTypeId.get().equals("")) {
            toast(view.context, "Please select expiry date")
            focusData.value = "addressType"
        } else if (address.get().equals("")) {
            toast(view.context, "Address cannot be empty")
            focusData.value = "address"
        } else if (locationId.get().equals("")) {
            toast(view.context, "Please select location")
            focusData.value = "locationId"
        } else if (etPincode.get().equals("")) {
            toast(view.context, "Pincode cannot be empty")
            focusData.value = "pincode"
        } else if (postOfficeName.get().equals("")) {
            toast(view.context, "Please select a post office")
            focusData.value = "postOfficeName"
        } else if (phoneTypeId.get().equals("")) {
            toast(view.context, "Please select a phone type")
            focusData.value = "postOfficeName"
        } else if (etPhone.get().equals("")) {
            toast(view.context, "Phone number cannot be empty")
            focusData.value = "phoneNumber"
        }
//        else if (!isPhoneVerified.get()!!) {
//            toast(view.context, "Please verify Phone number")
//            focusData.value = "phoneNumber"
//        }

        else if (etPhone.get()?.length!! < 10) {
            toast(view.context, "Please enter a valid Phone number")
            focusData.value = "phoneNumber"
        } else if (etHouseName.get().equals("")) {
            toast(view.context, "House name cannot be empty")
            focusData.value = "houseName"
        } else if (profilePic64.get().equals("")) {
            toast(view.context, "Please select a profile picture")
            focusData.value = "profilePic"
        } else if (signature64.get().equals("")) {
            toast(view.context, "Signature pic cannot be empty")
            focusData.value = "signature"
        } else if (idProofOneSideOne64.get().equals("")) {
            toast(view.context, "ID proof 1 is empty")
            focusData.value = "idProofSideOne"
        }
        else if( (!idProofIdTwo.get().equals("-1"))&& idProofTwoSideOne64.get().equals("") ){

            toast(view.context, "ID proof 2 is empty")
            focusData.value = "idProofSideTwo"

        }
        else {
            initLoading(view.context)
            customerCreation()
        }

    }

    fun setPostOffice(postOffice: List<PostOffice>) {

        for (i in postOffice.indices) {
            postOfficeList.add(postOffice[i].Name)
            postOfficeListData.add(postOffice[i])
        }
    }

    fun resetPostOffice() {
        postOfficeList.clear()
        postOfficeListData.clear()
        postOfficeList.add("--Select Post Office--")
        postOfficeListData.add(
            PostOffice(
                "00",
                "",
                "",
                "",
                "",
                "",
                "",
                "--Select Post Office--",
                "",
                "",
                ""
            )
        )

    }

    private fun getAge(dobString: String): Int {
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val dob = Calendar.getInstance()
        dob.time = sdf.parse(dobString)

        val today = Calendar.getInstance()

        val curYear = today.get(Calendar.YEAR)
        val dobYear = dob.get(Calendar.YEAR)

        var age = curYear - dobYear

        try {
            // if dob is month or day is behind today's month or day
            // reduce age by 1
            val curMonth = today.get(Calendar.MONTH) + 1
            val dobMonth = dob.get(Calendar.MONTH) + 1
            if (dobMonth > curMonth) { // this year can't be counted!
                age--
            } else if (dobMonth == curMonth) { // same month? check for day
                val curDay = today.get(Calendar.DAY_OF_MONTH)
                val dobDay = dob.get(Calendar.DAY_OF_MONTH)
                if (dobDay < curDay) { // this year can't be counted!
                    age--
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        if (age < 0) {
            age = 0
        }
        return age
    }

    fun clearData() {
        setSelectedBranch(0)
        setSelectedGender(0)
        setSelectedPrefix(0)
        setSelectedIdProofOne(0)
        setSelectedIdProofTwo(0)
        setSelectedAddressType(0)
        setSelectedLocation(0)
        setSelectedPhoneType(0)
        setSelectedPhoneType(0)
        fromDateOne.set("Select Date")
        toDateOne.set("Select Date")
        fromDateTwo.set("Select Date")
        toDateTwo.set("Select Date")
        address.set("")
        dob.set("Select Date of Birth")
        idProofNumberOne.set("")
        idProofNumberTwo.set("")
        etPincode.set("")
        etPhone.set("")
        etHouseName.set("")
        et_age.set("0")
        idProofOneSideOne64.set("")

        idProofTwoSideOne64.set("")

        profilePic64.set("")
        signature64.set("")
        etName.set("")
        postOfficeName.set("")
        obSelectedIdProofOne.set("")
        obSelectedIdProofTwo.set("")
        obSelectedAddressType.set("")
        obSelectedLocation.set("")
        obSelectedPhoneType.set("")
        obSelectedPrefix.set("")
        obSelectedGender.set("")
        isPhoneVerified.set(false)
        verifyPhoneNumber.value=false
        etEmailId.set("")
        mAction.value = CustomerCretaionAction(CustomerCretaionAction.DEFAULT)
    }

    fun changeDateFormat(dates: String?): String? {
        var finaldate: String? = null
        finaldate = try {
            val formatter: DateFormat = SimpleDateFormat("dd/M/yyyy")
            val date = formatter.parse(dates) as Date
            val newFormat = SimpleDateFormat("MM/dd/yyyy")
            newFormat.format(date)
        } catch (e: java.lang.Exception) {
            null
        }
        return finaldate
    }

    class CustomerCreationViewmodelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CustomerCreationViewModel::class.java)) {
                return CustomerCreationViewModel() as T
            }
            throw IllegalStateException("unknown viewmodel class")
        }

    }
}