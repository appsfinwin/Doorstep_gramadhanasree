package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog

import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation.adapter.MemberAdapter
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation.pojo.SelectedMember
import com.finwin.doorstep.gramadhanasree.home.jlg.search_center.SearchCenterActivity
import com.finwin.doorstep.gramadhanasree.home.jlg.search_member.SearchMemberActivity
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.JlgGroupCreationFragmentBinding
import com.finwin.doorstep.gramadhanasree.utils.Constants


class JlgGroupCreationFragment : Fragment() {


    companion object {
        fun newInstance() = JlgGroupCreationFragment()
    }

    private lateinit var viewModel: JlgGroupCreationViewModel
    private lateinit var binding: JlgGroupCreationFragmentBinding
    private lateinit var memberAdapter: MemberAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,
            R.layout.jlg_group_creation_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JlgGroupCreationViewModel::class.java)
        binding.viewModel=viewModel


        setRecyclerView(binding.rvMembers)

        viewModel.mAction.observe(viewLifecycleOwner, Observer {
            viewModel.cancelLoading()
            when(it.action)
            {
                GroupCreationAction.CREATE_GROUP_SUCCESS->
                {
                    SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success")
                        .setContentText("Group Created Successfully!!")
                        .setConfirmClickListener {

                            viewModel.clearData()
                            memberAdapter.memberList.clear()
                            memberAdapter.notifyDataSetChanged()
                            it.cancel()
                        }
                        .show()
                }

                GroupCreationAction.API_ERROR -> {
                    var dialog: SweetAlertDialog = SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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

        binding.btnSelectCenter.setOnClickListener {
            viewModel.clearData()

            var intent: Intent = Intent(activity, SearchCenterActivity::class.java)
            //var intent: Intent = Intent(activity, SearchMemberActivity::class.java)
            startActivityForResult(
                intent,
                Constants.INTENT_SEARCH_CENTER
            )
        }

        binding.btnAddMember.setOnClickListener {


            var intentMember: Intent = Intent(activity, SearchMemberActivity::class.java)
            startActivityForResult(
                intentMember,
                Constants.INTENT_SEARCH_MEMBER
            )
        }
    }

    private fun setRecyclerView(rvMembers: RecyclerView) {

        rvMembers.setHasFixedSize(true)
        rvMembers.layoutManager= LinearLayoutManager(activity)
        memberAdapter= MemberAdapter()
        rvMembers.adapter= memberAdapter
        setObservable(memberAdapter)

    }

    private fun setObservable(memberAdapter: MemberAdapter) {

        memberAdapter.mAction.observe(viewLifecycleOwner, Observer {
            when(it.action)
            {
                GroupCreationAction.CLICK_MEMBER->
                {
                    //Toast.makeText(activity, it.selectedMember.customerName, Toast.LENGTH_SHORT).show()
                }

                GroupCreationAction.CLICK_MEMBER_DELETE->
                {
                    it.selectedMember?.let {
                            selectedMember ->
                        memberAdapter.removeMember(selectedMember)
                        viewModel.removeMember(selectedMember)
                        memberAdapter.notifyDataSetChanged()

                    }

                }


            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.INTENT_SEARCH_CENTER) {
            if (data != null) {
                var centerName: String? = data.getStringExtra("centerName")
                var centerCode: String? = data.getStringExtra("centerCode")
                viewModel.centerName.set(centerName)
                viewModel.centerCode.set(centerCode)
                viewModel.groupDetailsVisibility.set(View.VISIBLE)
                viewModel.memberLayoutVisibility.set(View.VISIBLE)
                viewModel.submitButtonVisibility.set(View.VISIBLE)

            } else {
                //viewModel.centerName.set("")
            }
        } else if (requestCode == Constants.INTENT_RECIEPT_FROM_CASH_DEPOSIT) {
            viewModel.reset()
        }else if (requestCode == Constants.INTENT_SEARCH_MEMBER) {
            if (data != null) {

                var listMember :MutableList<SelectedMember> =  mutableListOf()
                var customerId: String? = data.getStringExtra("customerId")
                var customerName: String? = data.getStringExtra("customerName")
                var customerMobile: String? = data.getStringExtra("customerMobile")
                var accountNumber: String? = data.getStringExtra("accountNumber")

                if (memberAdapter.memberList.size<=0){
                    listMember.add(SelectedMember(customerName,customerId,customerMobile,accountNumber))
                    memberAdapter.setMember(listMember)
                    viewModel.addMember(SelectedMember(customerName,customerId,customerMobile,accountNumber))
                }else
                {
                    if (viewModel.custIdList.contains(customerId))
                    {
                        Toast.makeText(activity, "customer ID is already added!", Toast.LENGTH_SHORT).show()
                    }else{
                    memberAdapter.addMember(SelectedMember(customerName,customerId,customerMobile,accountNumber))
                    viewModel.addMember(SelectedMember(customerName,customerId,customerMobile,accountNumber))
                    }
                }



            }
        }


    }

}