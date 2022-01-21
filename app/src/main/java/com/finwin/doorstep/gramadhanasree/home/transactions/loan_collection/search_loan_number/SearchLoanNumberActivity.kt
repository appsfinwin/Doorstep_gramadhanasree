package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.action.SearchLoanAction
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.adapter.SearchLoanAdapter

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ActivitySearchLoanNumberBinding
import com.finwin.doorstep.gramadhanasree.logout_listner.BaseActivity

class SearchLoanNumberActivity : BaseActivity() {
    lateinit var viewModel: SearchLoanNumberViewModel
    lateinit var binding: ActivitySearchLoanNumberBinding
    lateinit var adapter: SearchLoanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_search_loan_number)
        viewModel= ViewModelProvider(this).get(SearchLoanNumberViewModel::class.java)
        binding.viewmodel=viewModel

        setRecyclerView(binding.rvLoanAccounts)
        viewModel.initLoading(this)
        viewModel.getLoanSchemes()

        binding.btnCancel.setOnClickListener {
            finish()
        }
        viewModel.mAction?.observe(this, Observer {
            viewModel.cancelLoading()
            when(it.action)
            {
                SearchLoanAction.GET_SCHEME_SUCCESS->{
                    viewModel.setLoanSchemes(it.getLoanSchemes)
                }
                SearchLoanAction.SEARCH_SUCCESS->{
                    adapter.EmptyList()
                    adapter.setSearchData(it.getLoanNumbersRespponse.customerList.data)
                    adapter.notifyDataSetChanged()
                }
                SearchLoanAction.API_ERROR->{
                    adapter.EmptyList()
                    adapter.notifyDataSetChanged()
                    var dialog: SweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    dialog.titleText = "ERROR!"
                    dialog.contentText = it.error
                    dialog.setCancelable(false)
                    dialog.setConfirmClickListener {
                        it.cancel()
                    }

                    dialog.show()
                }
            }
        })
    }

    @SuppressLint("WrongConstant")
    private fun setRecyclerView(rvLoanAccounts: RecyclerView) {

        adapter= SearchLoanAdapter()
        rvLoanAccounts.layoutManager= LinearLayoutManager(this,LinearLayout.VERTICAL,false)
        rvLoanAccounts.setHasFixedSize(true)
        rvLoanAccounts.adapter=adapter

        setObserver(adapter)
    }

    private fun setObserver(adapter: SearchLoanAdapter) {

        adapter.mAction.observe(this, Observer {

            when(it.action){
                SearchLoanAction.CLICK_ACCOUNT->{
                    val intent = intent
                    intent.putExtra("account_number", it.searchLoanData.loanAccountNumber)
                    intent.putExtra("scheme_code", it.searchLoanData.schemeCode)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        })
    }
}