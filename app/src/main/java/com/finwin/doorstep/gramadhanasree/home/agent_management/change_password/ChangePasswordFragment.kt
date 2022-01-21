package com.finwin.doorstep.gramadhanasree.home.agent_management.change_password

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
import com.finwin.doorstep.gramadhanasree.databinding.ChangePasswordFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.agent_management.change_password.action.ChangePasswordAction
import com.finwin.doorstep.gramadhanasree.home.home_activity.HomeActivity


class ChangePasswordFragment : Fragment() {

    companion object {
        fun newInstance() = ChangePasswordFragment()
    }

    private lateinit var viewModel: ChangePasswordViewModel
    private lateinit var binding : ChangePasswordFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        HomeActivity.activityHomeBinding.appBar.tvHeading.text = "Change password"
        binding=DataBindingUtil.inflate(inflater,
            R.layout.change_password_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel::class.java)
        binding.viewModel=viewModel
        viewModel.setDataBinding(binding)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

       viewModel.mAction.observe(viewLifecycleOwner, Observer {
           viewModel.cancelLoading()
           when(it.action)
           {

               ChangePasswordAction.CHANGE_PASSWORD_SUCCESS->{
                   viewModel.reset()
                   SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                       .setTitleText("SUCCESS!")
                       .setContentText(it.changePasswordResponse.msg)
                       .show()
               }
               ChangePasswordAction.API_ERROR->{

                   val customView: View =
                       LayoutInflater.from(context).inflate(R.layout.layout_error_layout, null)
                   val tv_error = customView.findViewById<TextView>(R.id.tv_error)
                   tv_error.setText(it.error)
                   SweetAlertDialog(context, SweetAlertDialog.BUTTON_NEGATIVE)
                       .setTitleText("ERROR")
                       .setCustomView(customView)
                       .show()


               }

           }
       })
    }

}