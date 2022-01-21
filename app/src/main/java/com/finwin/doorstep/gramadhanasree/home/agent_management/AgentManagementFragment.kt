package com.finwin.doorstep.gramadhanasree.home.agent_management



import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.AgentManagementFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.agent_management.change_password.ChangePasswordFragment
import com.finwin.doorstep.gramadhanasree.home.home_activity.HomeActivity


class AgentManagementFragment : Fragment() {

    companion object {
        fun newInstance() =
            AgentManagementFragment()
        var CLICK_CHANGE_PASSWORD=1
        var DEFAULT=-1
    }

    private lateinit var viewModel: AgentManagementViewModel
    private lateinit var binding: AgentManagementFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        HomeActivity.activityHomeBinding.appBar.tvHeading.text = "Agent management"
        binding=DataBindingUtil.inflate(inflater,
            R.layout.agent_management_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(AgentManagementViewModel::class.java)
        binding.viewmodel=viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.mAction.observe(viewLifecycleOwner, Observer {

            when(it)
            {
                CLICK_CHANGE_PASSWORD ->
                {
                    val myFragment: Fragment = ChangePasswordFragment()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.frame_layout, myFragment)?.addToBackStack(null)?.commit()
                }
            }
        })

    }

}