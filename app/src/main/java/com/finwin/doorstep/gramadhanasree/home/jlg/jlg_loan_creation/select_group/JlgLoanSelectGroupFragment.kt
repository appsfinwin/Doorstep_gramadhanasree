package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.JlgLoanSelectGroupFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.JlgLoanCreationAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.JlgLoanCreationActivity
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.room.Member
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.action.SelectGroupAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.adapter.SelectGroupAdapter
import com.finwin.doorstep.gramadhanasree.home.jlg.search_group.SearchGroupActivity
import com.finwin.doorstep.gramadhanasree.logout_listner.MemberApplication
import com.finwin.doorstep.gramadhanasree.utils.Constants

class JlgLoanSelectGroupFragment : Fragment() {

    companion object {
        fun newInstance() = JlgLoanSelectGroupFragment()
    }

    var count: Int = 1
    lateinit var groupAdapter: SelectGroupAdapter
    val viewModel: JlgLoanSelectGroupViewModel by viewModels {
        MemberViewmodelFactory((activity?.application as MemberApplication).repository)
    }
    private lateinit var binding: JlgLoanSelectGroupFragmentBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.jlg_loan_select_group_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        setRecyclerView(binding.rvGroup)
        viewModel.clearData()
        //viewModel.initLoading(view?.context)

        sharedPreferences= activity?.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)!!
        editor=sharedPreferences.edit()
        binding.spinnerOption.isEnabled= false
        binding.btnNext.setOnClickListener {

            if (viewModel.option.get().equals(""))
            {
                Toast.makeText(view?.context, "Option not selected!", Toast.LENGTH_SHORT).show()
            }else
            if (checkDisbursement())
            {

                (activity as JlgLoanCreationActivity ).viewModel.groupData = viewModel.disbaursementData
                (activity as JlgLoanCreationActivity ).viewModel.optionType.set( viewModel.option.get())
                (activity as JlgLoanCreationActivity ).gotoNext()
            }else{
                //Toast.makeText(view?.context, "not saved", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnPrevious.setOnClickListener {
            (activity as JlgLoanCreationActivity ).gotoPrevious()
        }

        binding.btnSubmit.setOnClickListener {
            binding.spinnerOption.isEnabled= false
            viewModel.clearData()
            var intent = Intent(activity, SearchGroupActivity::class.java)
            startActivityForResult(intent, Constants.INTENT_SEARCH_JLG_GROUP)
        }
        viewModel.getAllMembers().observe(viewLifecycleOwner) { member ->

            viewModel.cancelLoading()
            groupAdapter.setMemberList(member)
            viewModel.saveMemberData(member)


        }

        viewModel.mOption.observe(viewLifecycleOwner, Observer {
            if (groupAdapter!= null)
            {
                if (!it.equals("")) {
                    if (groupAdapter!=null) {
                        groupAdapter.setOption(it)
                        groupAdapter.notifyDataSetChanged()
                        binding.rvGroup.visibility= View.VISIBLE
                        (activity as JlgLoanCreationActivity ).viewModel.optionType.set(it)
                    }
                }else
                {
                    binding.rvGroup.visibility= View.GONE
                }
            }
        })

        (activity as JlgLoanCreationActivity ).viewModel.mAction.observe(viewLifecycleOwner, Observer {

            when(it.action)
            {
                JlgLoanCreationAction.JLG_CLEAR_DATA -> {
                    viewModel.clearData()
                }
            }

        })


        viewModel.mAction.observe(viewLifecycleOwner, Observer { action ->
            viewModel.cancelLoading()
            when (action.action) {
                SelectGroupAction.GET_GROUP_SELECT_SUCCESS -> {
                    binding.spinnerOption.isEnabled= true
                    binding.rvGroup.visibility= View.GONE
                    viewModel.clearData()

                    for (i in action.getGroupSelectResponse.data.indices) {
                        var member = Member(
                            action.getGroupSelectResponse.data[i].slNumber,
                            "",
                            "0",
                            "0",
                            "0",
                            "",
                            "",
                            action.getGroupSelectResponse.data[i].customerId,
                            action.getGroupSelectResponse.data[i].customerName,
                            "0",
                            "0",
                            "0",
                            "",
                            "0"
                        )
                        viewModel.insert(member)
                    }

                }
            }
        })
    }

    private fun checkDisbursement():Boolean {

        var isGroupSaved=false

        var disbursementData= viewModel.disbaursementData
        for (i in disbursementData.indices)
        {
            if(disbursementData[i].disbursementAmount == "" || disbursementData[i].disbursementAmount == "0")
            {
                Toast.makeText(view?.context, disbursementData[i].customerName+ " not saved", Toast.LENGTH_SHORT).show()
                isGroupSaved=false
                break
            }else{
                //(activity as JlgLoanCreationActivity ).viewModel.etLoanAmount.set(viewModel.disbursementAmount.get())
                (activity as JlgLoanCreationActivity ).viewModel.mAction.value= JlgLoanCreationAction(
                    JlgLoanCreationAction.JLG_PASS_AMOUNT,viewModel.disbursementAmount.get())
                editor.putString("disbursement_amount", viewModel.disbursementAmount.get())
                editor.apply()
                editor.commit()
                isGroupSaved=true
            }
        }


        return isGroupSaved
    }

    private fun setRecyclerView(rvGroup: RecyclerView) {
        rvGroup.setHasFixedSize(true)
        rvGroup.layoutManager = LinearLayoutManager(activity)
        groupAdapter = SelectGroupAdapter((activity as JlgLoanCreationActivity).viewModel.productData)
        rvGroup.adapter = groupAdapter
        setObservable(groupAdapter)
    }

    private fun setObservable(groupAdapter: SelectGroupAdapter) {

            groupAdapter.mAction.observe(viewLifecycleOwner, Observer {
                when(it.action)
                {
                    SelectGroupAction.CHANGE_DATA->
                    {
                        //Toast.makeText(view?.context, it.error, Toast.LENGTH_SHORT).show()
                        viewModel.initLoading(view?.context)
                        //viewModel.updateMember(it.member)
                    }
                }
            })

        groupAdapter.memberListLiveData.observe(viewLifecycleOwner, Observer {
            viewModel.disbaursementData=it
            viewModel.saveMemberData(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== Constants.INTENT_SEARCH_JLG_GROUP)
        {
            if(data!=null) {
                var groupCode: String? = data.getStringExtra("group_code")
                viewModel.initLoading(activity)
                if (groupCode != null) {
                    viewModel.getGroup(groupCode)
                    (activity as JlgLoanCreationActivity).viewModel.groupId.set(groupCode)
                }
            }else{

            }
        }
    }

}