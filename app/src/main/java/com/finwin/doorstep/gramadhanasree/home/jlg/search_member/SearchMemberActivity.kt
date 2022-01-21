package com.finwin.doorstep.gramadhanasree.home.jlg.search_member

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ActivitySearchMemberBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.search_member.action.SearchMemberAction
import com.finwin.doorstep.gramadhanasree.home.jlg.search_member.adapter.SearchMemberAdapter
import com.finwin.doorstep.gramadhanasree.logout_listner.BaseActivity
import kotlinx.android.synthetic.main.nav_header_main.*

class SearchMemberActivity : BaseActivity() {
    private lateinit var viewModel: SearchMemberViewModel
    private lateinit var binding: ActivitySearchMemberBinding
    private lateinit var memberAdapter: SearchMemberAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_search_member)
        viewModel = ViewModelProvider(this).get(SearchMemberViewModel::class.java)
        binding.viewModel=viewModel

        setRecyclerView(binding.rvMemberList)

        viewModel.mAction.observe(this, Observer {
            viewModel.cancelLoading()
            when(it.action)
            {
                SearchMemberAction.GET_MEMBER_SUCCESS->
                {
                    memberAdapter.setMemberData(it.getSearchMemberResponse.customerList.data)
                    memberAdapter.notifyDataSetChanged()
                }

                SearchMemberAction.API_ERROR->{

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

    private fun setRecyclerView(rvMemberList: RecyclerView) {

        rvMemberList.setHasFixedSize(true)
        rvMemberList.layoutManager=LinearLayoutManager(this)
        memberAdapter= SearchMemberAdapter()
        rvMemberList.adapter= memberAdapter

        setObservable(memberAdapter)
    }

    private fun setObservable(memberAdapter: SearchMemberAdapter) {

        memberAdapter.mAction.observe(this, Observer {

            when(it.action)
            {
                SearchMemberAction.CLICK_MEMBER->
                {
                    val intent = intent
                    intent.putExtra("customerId",it.memberData.customerId)
                    intent.putExtra("customerName",it.memberData.customerName)
                    intent.putExtra("customerMobile",it.memberData.mobile)
                    intent.putExtra("accountNumber",it.memberData.accountNumber)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }SearchMemberAction.CHANGE_DATA->
                {
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                    memberAdapter.memberList[it.position].customerName=it.error
                    memberAdapter.notifyDataSetChanged()
                }
            }
        })


    }


}