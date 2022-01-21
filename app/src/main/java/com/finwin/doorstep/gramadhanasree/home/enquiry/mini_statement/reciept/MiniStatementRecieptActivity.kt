package com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.reciept

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ActivityMiniStatementRecieptBinding
import com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.action.MiniStatementAction
import com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.adapter.MiniStatementAdapter
import com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.pojo.MiniStatementProfile
import com.finwin.doorstep.gramadhanasree.logout_listner.BaseActivity
import com.finwin.doorstep.gramadhanasree.utils.Constants


class MiniStatementRecieptActivity : BaseActivity() {
    lateinit var viewmodel: MiniStatementReciepViewmodel
    lateinit var binding: ActivityMiniStatementRecieptBinding
    lateinit var adapter: MiniStatementAdapter
    //lateinit var profile: List<MiniStatementProfile>
    var profile: List<MiniStatementProfile> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mini_statement_reciept)
        viewmodel=ViewModelProvider(this).get(MiniStatementReciepViewmodel::class.java)
        binding.viewmodel=viewmodel

        viewmodel.accountNumber.set(intent.getStringExtra(Constants.ACCOUNT_NUMBER))

        setRecyclerview(binding.rvMiniStatement)
        viewmodel.initLoading(this)
        viewmodel.miniStatement()

        viewmodel.mAction.observe(this, Observer {
            viewmodel.cancelLoading()
            when(it.action)
            {
                MiniStatementAction.GET_MINI_STATEMENT_SUCCESS-> {
                    viewmodel.cancelLoading()

                    it.miniStatementResponse?.mini_statement?.let { mini_statement ->
                        viewmodel.setData(mini_statement.data)
                        var mini_s_profile: MiniStatementProfile = MiniStatementProfile(
                           mini_statement.data.ACC_NO,
                           mini_statement.data.CURRENT_BALANCE,
                           mini_statement.data.MOBILE,
                            mini_statement.data.NAME
                        )

                        profile = listOf(
                            MiniStatementProfile(
                                mini_statement.data.ACC_NO,
                                mini_statement.data.CURRENT_BALANCE,
                                mini_statement.data.MOBILE,
                                mini_statement.data.NAME
                            )
                        )
                        adapter.setProfile(profile)
                        adapter.setMiniStatement(mini_statement.data.TRANSACTONS)
                        adapter.notifyDataSetChanged()
                    }
                }

                MiniStatementAction.API_ERROR->
                {
                    val customView: View =
                        LayoutInflater.from(this).inflate(R.layout.layout_error_layout, null)
                    val tv_error = customView.findViewById<TextView>(R.id.tv_error)
                    tv_error.setText(it.error)
                    SweetAlertDialog(this, SweetAlertDialog.BUTTON_NEGATIVE)
                        .setTitleText("ERROR")
                        .setCustomView(customView)
                        .show()
                }
            }
        })

    }

    private fun setRecyclerview(rvMiniStatement: RecyclerView) {
        adapter= MiniStatementAdapter()
        rvMiniStatement.layoutManager=LinearLayoutManager(this)
        rvMiniStatement.setHasFixedSize(true)
        rvMiniStatement.adapter=adapter

    }
}