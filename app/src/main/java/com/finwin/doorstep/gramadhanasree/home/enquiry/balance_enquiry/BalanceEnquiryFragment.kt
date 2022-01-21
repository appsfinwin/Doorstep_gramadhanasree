package com.finwin.doorstep.gramadhanasree.home.enquiry.balance_enquiry

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import cn.pedant.SweetAlert.SweetAlertDialog

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.BalanceEnquiryFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.enquiry.balance_enquiry.action.BalanceAction
import com.finwin.doorstep.gramadhanasree.home.home_activity.HomeActivity
import com.finwin.doorstep.gramadhanasree.home.transactions.search_account.SearchActivity
import com.finwin.doorstep.gramadhanasree.print_reciept.ReceiptActivity
import com.finwin.doorstep.gramadhanasree.utils.Constants

class BalanceEnquiryFragment : Fragment() {

    companion object {
        fun newInstance() = BalanceEnquiryFragment()
    }

    private lateinit var viewModel: BalanceEnquiryViewModel
    private lateinit var binding: BalanceEnquiryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        HomeActivity.activityHomeBinding.appBar.tvHeading.text = "BALANCE ENQUIRY"
        viewModel = ViewModelProviders.of(this).get(BalanceEnquiryViewModel::class.java)
        binding=DataBindingUtil.inflate(inflater,
            R.layout.balance_enquiry_fragment, container, false)
        binding.viewmodel=viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.mAction.observe(viewLifecycleOwner, Observer {
            viewModel.cancelLoading()

            when(it.action)
            {

                BalanceAction.API_ERROR->{

                    val customView: View =
                        LayoutInflater.from(activity).inflate(R.layout.layout_error_layout, null)
                    val tv_error = customView.findViewById<TextView>(R.id.tv_error)
                    tv_error.setText(it.error)
                    SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ERROR")
                        .setCustomView(customView)
                        .setConfirmClickListener {
//                        getFragmentManager()?.popBackStack();
                            it.cancel()
                        }
                        .show()

                }
                BalanceAction.CLICK_SEARCH->
                {
                    var intent = Intent(activity, SearchActivity::class.java)
                    startActivityForResult(intent, Constants.INTENT_SEARCH_ACCOUNT_FROM_BALANCE_ENQUIRY)
                }

                BalanceAction.BALANCE_ENQUIRY_SUCCESS->
                {
                    viewModel.cancelLoading()
                    var intent= Intent(activity, ReceiptActivity::class.java)
                    intent.putExtra(Constants.FROM, Constants.BALANCE_ENQUIRY)
                    intent.putExtra(Constants.DEPOSIT_DATE,
                        it.balanceEnquiryResponse?.balance?.data?.DATE
                    )
                    intent.putExtra(Constants.CURRENT_BALANCE, it.balanceEnquiryResponse?.balance?.data?.CURRENT_BALANCE)
                    intent.putExtra(Constants.ACCOUNT_NUMBER,it.balanceEnquiryResponse?.balance?.data?.ACC_NO)
                    intent.putExtra(Constants.NAME,it.balanceEnquiryResponse?.balance?.data?.NAME)
                    intent.putExtra(Constants.MOBILE,it.balanceEnquiryResponse?.balance?.data?.MOBILE)
                    startActivityForResult(intent, Constants.INTENT_RECIEPT_FROM_BALANCE_ENQUIRY)
                }

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== Constants.INTENT_SEARCH_ACCOUNT_FROM_BALANCE_ENQUIRY)
        {
            if(data!=null) {
                var accountNumber: String? = data.getStringExtra("account_number")
                viewModel.accountNumber.set(accountNumber)
            }else{
                viewModel.accountNumber.set("")
            }
        }else if (requestCode== Constants.INTENT_RECIEPT_FROM_BALANCE_ENQUIRY)
        {
            viewModel.accountNumber.set("")
        }
    }


}