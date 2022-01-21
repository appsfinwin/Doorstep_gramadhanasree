package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing.closing_general_details.ClosingGeneralDetailsFragment

import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing.closing_other_details.ClosingOtherDetailsFragment
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing.closing_remittance_details.ClosingRemittanceDetailsFragment


class PagerAdapter(var context : Context, fm: FragmentManager): FragmentPagerAdapter(fm) {

    @StringRes
    val TAB_TITLES = intArrayOf(
        R.string.general_details,
        R.string.remittance_details,
        R.string.other_details
    )


    val generalDetailsFragment: ClosingGeneralDetailsFragment = ClosingGeneralDetailsFragment.newInstance()
    val remittanceDetailsFragment: ClosingRemittanceDetailsFragment = ClosingRemittanceDetailsFragment.newInstance()
    val otherDetailsFragment: ClosingOtherDetailsFragment = ClosingOtherDetailsFragment.newInstance()
    override fun getItem(position: Int): Fragment {

        // getItem is called to instantiate the fragment for the given page.
        // Return a PersonalFragment (defined as a static inner class below).
//        return PersonalFragment.newInstance(position + 1);

        var  fragment = Fragment()
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