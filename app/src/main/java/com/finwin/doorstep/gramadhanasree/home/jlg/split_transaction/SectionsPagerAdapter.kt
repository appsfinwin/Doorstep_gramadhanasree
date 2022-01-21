package com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.general_details.GeneralDetailsFragment
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.other_details.OtherDetailsFragment
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.remitance_details.RemittanceDetailsFragment

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.utils.Constants

class SectionsPagerAdapter(
    context: Context,
    fm: FragmentManager
    //, mAction: MutableLiveData<JlgAction>
) : FragmentPagerAdapter(fm) {

    @StringRes
    val TAB_TITLES = intArrayOf(
        R.string.general_details,
        R.string.remittance_details,
        R.string.other_details
    )

    var context: Context=context
    var status_Completed: Int=1
    val generalDetailsFragment: GeneralDetailsFragment = GeneralDetailsFragment.newInstance()
    val remittanceDetailsFragment: RemittanceDetailsFragment = RemittanceDetailsFragment.newInstance()
    val otherDetailsFragment: OtherDetailsFragment = OtherDetailsFragment.newInstance()
    override fun getItem(position: Int): Fragment {

        // getItem is called to instantiate the fragment for the given page.
        // Return a PersonalFragment (defined as a static inner class below).
//        return PersonalFragment.newInstance(position + 1);
        val sharedPreferences: SharedPreferences = context.getApplicationContext()
            .getSharedPreferences(
                Constants.PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )

        var  fragment: Fragment= Fragment()
         //status_Completed= sharedPreferences.getInt(Constants.JLG_FRAGMENT_STATUS, 1);
        when(position)
        {
            0->{fragment=  generalDetailsFragment}
            1->{fragment=  remittanceDetailsFragment}
            2->{fragment=  otherDetailsFragment}
        }
        return  fragment
    }

    override fun getCount(): Int {
        return  3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getResources().getString(TAB_TITLES.get(position))
    }
}