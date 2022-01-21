package com.finwin.doorstep.gramadhanasree.home.home_fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.FragmentHomeBinding
import com.finwin.doorstep.gramadhanasree.home.agent_management.AgentManagementFragment
import com.finwin.doorstep.gramadhanasree.home.bc_report.BcReportFragment
import com.finwin.doorstep.gramadhanasree.home.customer_creation.CustomerCreationActivity
import com.finwin.doorstep.gramadhanasree.home.enquiry.enquuiry_fragment.EnquiryFragment
import com.finwin.doorstep.gramadhanasree.home.home_activity.HomeAction
import com.finwin.doorstep.gramadhanasree.home.home_activity.HomeActivity
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgFragment
import com.finwin.doorstep.gramadhanasree.home.support.SupportFragment
import com.finwin.doorstep.gramadhanasree.home.transactions.TransactionsFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {


    lateinit var viewmodel: HomeFragmentViewmodel
    lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewmodel = ViewModelProvider(this).get(HomeFragmentViewmodel::class.java)
        binding.viewmodel = viewmodel
        HomeActivity.activityHomeBinding.appBar.tvHeading.text = "Welcome"
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.mAction.observe(viewLifecycleOwner, Observer {

            when (it.action) {
                HomeAction.CLICK_TRANSACTION -> {
                    val myFragment: Fragment = TransactionsFragment.newInstance()
                    activity?.supportFragmentManager?.beginTransaction()?.replace(
                        R.id.frame_layout,
                        myFragment
                    )?.addToBackStack(null)?.commit()
                }
                HomeAction.CLICK_ENQUIRY -> {
                    val myFragment: Fragment =
                        EnquiryFragment.newInstance()
                    activity?.supportFragmentManager?.beginTransaction()?.replace(
                        R.id.frame_layout,
                        myFragment
                    )?.addToBackStack(null)?.commit()
                }
                HomeAction.CLICK_BC_REPORT -> {
                    val myFragment: Fragment =
                        BcReportFragment.newInstance()
                    activity?.supportFragmentManager?.beginTransaction()?.replace(
                        R.id.frame_layout,
                        myFragment
                    )?.addToBackStack(null)?.commit()
                }
                HomeAction.CLICK_AGENT -> {
                    val myFragment: Fragment =
                        AgentManagementFragment.newInstance()
                    activity?.supportFragmentManager?.beginTransaction()?.replace(
                        R.id.frame_layout,
                        myFragment
                    )?.addToBackStack(null)?.commit()
                }
                HomeAction.CLICK_CUSTOMER_CREATION -> {
//                    val myFragment: Fragment =
//                        CustomerCreationFragment.newInstance()
//                    activity?.supportFragmentManager?.beginTransaction()?.replace(
//                        R.id.frame_layout,
//                        myFragment
//                    )?.addToBackStack(null)?.commit()

                    // var intent: Intent= Intent(this,CustomerCreationActivity.c)

                    val intent = Intent(activity, CustomerCreationActivity::class.java)
                    startActivity(intent)
                }

                HomeAction.CLICK_JLG -> {
                    val myFragment: Fragment =
                        JlgFragment.newInstance()
                    activity?.supportFragmentManager?.beginTransaction()?.replace(
                        R.id.frame_layout,
                        myFragment
                    )?.addToBackStack(null)?.commit()
                }


            }

        })
    }



    override fun onStop() {
        super.onStop()
        viewmodel.mAction.value = HomeAction(HomeAction.DEFAULT)
    }
}