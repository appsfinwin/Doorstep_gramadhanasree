package com.finwin.doorstep.gramadhanasree.utils

import android.content.Context
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar


public class Services {
    companion object{
//        public fun showProgressDialog(context: Context?): Dialog? {
//            val warningDialog = Dialog(context!!)
//            warningDialog.setContentView(R.layout.layout_progress_dialog)
//            warningDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            warningDialog.setCanceledOnTouchOutside(false)
//            warningDialog.setCancelable(false)
//            warningDialog.show()
//            return warningDialog
//    }

        fun showProgressDialog(context: Context?): SweetAlertDialog? {
            val sweetAlertDialog =
                SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
            sweetAlertDialog.setCancelable(false)
            sweetAlertDialog.setTitleText("Please wait")
                .show()
            return sweetAlertDialog
        }

         fun showSnakbar(message: String, view: View) {
            val snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG)
            snackbar.show()
        }


    }
}