package com.finwin.doorstep.gramadhanasree.home.customer_creation

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ActivityCustomerCreationBinding
import com.finwin.doorstep.gramadhanasree.home.customer_creation.action.CustomerCretaionAction
import com.finwin.doorstep.gramadhanasree.home.customer_creation.select_image.ImageActivity
import com.finwin.doorstep.gramadhanasree.home.home_activity.HomeActivity
import com.finwin.doorstep.gramadhanasree.logout_listner.BaseActivity
import com.finwin.doorstep.gramadhanasree.utils.Constants
import java.io.File
import java.io.FileInputStream
import java.util.*


class CustomerCreationActivity : BaseActivity() {
    val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
    val REQUEST_CODE_PROFILE_IC = 111
    val REQUEST_CODE_SIGNATURE = 112
    val REQUEST_CODE_ID_PROOD_ONE = 113
    val REQUEST_CODE_ID_PROOD_TWO = 114
    val viewModel: CustomerCreationViewModel  by viewModels {
        CustomerCreationViewModel.CustomerCreationViewmodelFactory()
    }
    lateinit var binding: ActivityCustomerCreationBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_creation)


        binding.viewmodel = viewModel

        viewModel.initLoading(this)
        viewModel.getRefCodes()
        binding.layoutOtp.visibility=View.GONE
        binding.imgBack.setOnClickListener{

           exitDialog()
        }
        viewModel.agentId.set(sharedPreferences.getString(Constants.AGENT_ID, ""))
        viewModel.focusData.observe(this, androidx.lifecycle.Observer {
            when (it) {
                "branch" -> {
                    binding.spinnerBranch.isFocusable = true;
                    binding.spinnerBranch.requestFocusFromTouch()
                }
                "prefix" -> {
                    binding.spinnerPrefix.requestFocusFromTouch()
                }
                "name" -> {
                    binding.etName.editText?.requestFocus()
                }
                "dob" -> {
                    binding.btnDob.requestFocusFromTouch()
                }
                "gender" -> {
                    binding.spinnerGender.requestFocusFromTouch()
                }
                "idProofId" -> {
                    binding.spinnerIdProof.requestFocusFromTouch()
                }
                "idProofNumber" -> {
                    binding.etIdProofNumber.requestFocus()
                }
                "fromDate" -> {
                    binding.btnFrom.requestFocus()
                }
                "toDate" -> {
                    binding.btnTo.requestFocus()
                }
                "addressType" -> {
                    binding.spinnerAddressType.requestFocusFromTouch()
                }
                "address" -> {
                    binding.etAddress.requestFocus()
                    binding.etAddress.editText?.setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
                }
                "locationId" -> {
                    binding.spinnerLocation.requestFocusFromTouch()
                }
                "pincode" -> {
                    binding.etPincode.requestFocus()
                }
                "postOfficeName" -> {
                    binding.spinnerPostOffice.requestFocusFromTouch()
                }
                "phoneNumber" -> {
                    binding.etPhoneNumber.requestFocus()
                }
                "houseName" -> {
                    binding.etHouseName.requestFocus()
                }
                "profilePic" -> {
                    binding.btnProfilePhoto.requestFocus()
                }
                "signature" -> {
                    binding.btnSignature.requestFocus()
                }
                "idProofSideOne" -> {
                    binding.btnIdProofSideOne.requestFocusFromTouch()
                }
                "idProofSideTwo" -> {
                    //binding.btnIdProofSideTwo.requestFocusFromTouch()
                }


            }
        })

        viewModel.mAction.observe(this, androidx.lifecycle.Observer {
            viewModel.cancelLoading()
            when (it.action) {

                CustomerCretaionAction.CLICK_PROFILE_IMAGE -> {
                    selectPic(REQUEST_CODE_PROFILE_IC)
                }
                CustomerCretaionAction.CLICK_SIGNATURE -> {
                    selectPic(REQUEST_CODE_SIGNATURE)
                }
                CustomerCretaionAction.CLICK_ID_PROOF_ONE -> {
                    selectPic(REQUEST_CODE_ID_PROOD_ONE)
                }
                CustomerCretaionAction.CLICK_ID_PROOF_TWO -> {
                    selectPic(REQUEST_CODE_ID_PROOD_TWO)
                }

                CustomerCretaionAction.GET_REF_CODE_SUCCESS -> {
                    viewModel.setSpinnerData(it.referenceCodesResponse)
                }
                CustomerCretaionAction.GET_POST_OFFICE_SUCCESS -> {
                    viewModel.setPostOffice(it.postOfficeResponse.PostOffice)
                }

                CustomerCretaionAction.API_ERROR -> {

                    val customView: View =
                        LayoutInflater.from(this).inflate(R.layout.layout_error_layout, null)
                    val tv_error = customView.findViewById<TextView>(R.id.tv_error)
                    tv_error.setText(it.error)
                    SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ERROR")
                        .setCustomView(customView)
                        .show()


                }
                CustomerCretaionAction.POST_OFFICE_ERROR -> {
                    SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("No Post office found")
                        .setContentText(it.error)
                        .show()
                }
                CustomerCretaionAction.CUSTOMER_CREATION_SUCCESS->{
                    SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Customer Created!")
                        .setContentText("Customer ID="+it.customerCreationResponse.data.Table1[0].ReturnID)
                        .setConfirmClickListener {
                            binding.etName.editText?.requestFocus()
                            viewModel.clearData()
                            binding.imgProfilePhoto.visibility = View.GONE
                            binding.imgSignature.visibility = View.GONE
                            binding.imgIdProofSideOne.visibility = View.GONE
                            binding.imgIdProofSideTwo.visibility = View.GONE
                            it.cancel()
                        }
                        .show()

                }

                CustomerCretaionAction.SENT_OTP_SUCCESS->{
//                    binding.layoutOtp.visibility=View.VISIBLE
//                    viewModel.otpId.set(it.createOtpResponse.otp.otp_id)
//                    binding.tvVerifyOtp.isClickable=false
//                    viewModel.otp.set("")
                }

                CustomerCretaionAction.VALIDATE_OTP_SUCCESS->{

//                    SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
//                        .setTitleText("Success!")
//                        .setContentText("Phone Number Verified")
//                        .setConfirmClickListener {
//                            viewModel.isPhoneVerified.set(true)
//                            binding.tvVerifyOtp.visibility=View.GONE
//                            binding.imgVerified.setImageResource(R.drawable.ic_success)
//                            binding.layoutOtp.visibility=View.GONE
//                            binding.tvVerifyOtp.isClickable=false
//                            it.cancel()
//                        }
//                        .show()

                }
            }
        })

        viewModel.verifyPhoneNumber.observe(this, androidx.lifecycle.Observer {
            if (!it)
            {
//                binding.imgVerified.setImageResource(R.drawable.ic_not_verified)
//                viewModel.isPhoneVerified.set(false)
//
//                binding.layoutOtp.visibility=View.GONE
//                binding.tvVerifyOtp.isClickable=true
//                binding.tvVerifyOtp.visibility=View.VISIBLE
            }
        })
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun checkAndRequestPermissions(): Boolean {
        val permissionSendMessage = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val locationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun selectPic(requestCode: Int) {
        if (checkAndRequestPermissions()) {
            val b = AlertDialog.Builder(Objects.requireNonNull(this))
            b.setTitle("Select Image")
            val inflater = this.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.select_pic_view, null)
            b.setView(dialogView)
            val linCam = dialogView.findViewById<View>(R.id.linr_cam) as LinearLayout
            val linGalry = dialogView.findViewById<View>(R.id.linr_galry) as LinearLayout
            val alertDialog = b.create()
            linCam.setOnClickListener {
                alertDialog.dismiss()
                val id = Intent(this@CustomerCreationActivity, ImageActivity::class.java)
                id.putExtra("IMG_SELECTION_TYPE", "IMG_SELECT_CAMERA")
                startActivityForResult(id, requestCode)
            }
            linGalry.setOnClickListener {
                alertDialog.dismiss()
                val id = Intent(this@CustomerCreationActivity, ImageActivity::class.java)

                id.putExtra("IMG_SELECTION_TYPE", "IMG_SELECT_GALRY")
                startActivityForResult(id, requestCode)
            }
            alertDialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_PROFILE_IC -> {
                if (resultCode == RESULT_OK) {
                    binding.imgProfilePhoto.visibility = View.VISIBLE
                    try {
                        val sUri = data?.getStringExtra("img_result")
                        Glide.with(this@CustomerCreationActivity)
                            .load(sUri)
                            .into(binding.imgProfilePhoto)
                        val file = File(sUri)
                        val buffer = ByteArray(file.length().toInt() + 100)
                        val length = FileInputStream(file).read(buffer)
                        val base64 = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT)
                        viewModel.profilePic64.set("data:image/png;base64,$base64")

                    } catch (e: Exception) {
                    }
                }
            }
            REQUEST_CODE_SIGNATURE -> {

                if (resultCode == RESULT_OK) {
                    binding.imgSignature.visibility = View.VISIBLE
                    try {
                        val sUri = data?.getStringExtra("img_result")
                        Glide.with(this@CustomerCreationActivity)
                            .load(sUri)
                            .into(binding.imgSignature)
                        val file = File(sUri)
                        val buffer = ByteArray(file.length().toInt() + 100)
                        val length = FileInputStream(file).read(buffer)
                        val base64 = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT)
                        viewModel.signature64.set("data:image/png;base64,$base64")


                       // val decodedString = Base64.decode(base64, Base64.DEFAULT)
                    } catch (e: Exception) {
                    }
                }

            }
            REQUEST_CODE_ID_PROOD_ONE -> {

                if (resultCode == RESULT_OK) {
                    binding.imgIdProofSideOne.visibility = View.VISIBLE
                    try {
                        val sUri = data?.getStringExtra("img_result")
                        Glide.with(this@CustomerCreationActivity)
                            .load(sUri)
                            .into(binding.imgIdProofSideOne)
                        val file = File(sUri)
                        val buffer = ByteArray(file.length().toInt() + 100)
                        val length = FileInputStream(file).read(buffer)
                        val base64 = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT)
                        viewModel.idProofOneSideOne64.set("data:image/png;base64,$base64")



                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }
            REQUEST_CODE_ID_PROOD_TWO -> {
                if (resultCode == RESULT_OK) {
                    binding.imgIdProofSideTwo.visibility = View.VISIBLE
                    try {
                        val sUri = data?.getStringExtra("img_result")
                        Glide.with(this@CustomerCreationActivity)
                            .load(sUri)
                            .into(binding.imgIdProofSideTwo)
                        val file = File(sUri)
                        val buffer = ByteArray(file.length().toInt() + 100)
                        val length = FileInputStream(file).read(buffer)
                        val base64 = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT)
                        viewModel.idProofTwoSideOne64.set("data:image/png;base64,$base64")

                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
       exitDialog()
    }

    private fun exitDialog() {


        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Exit?")
            .setMessage("All the details entered will be lost!")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->

                    val intent: Intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                })
            .setNegativeButton("No", null)
            .show()

    }



}