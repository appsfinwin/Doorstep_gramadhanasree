package com.finwin.doorstep.gramadhanasree.utils

import android.os.AsyncTask
import org.jsoup.Jsoup
import java.io.IOException

class VersionChecker: AsyncTask<String, String, String>() {
    private var newVersion: String? = null
    private var version: String? = null

    override fun doInBackground(vararg params: String?): String? {
        try {
            newVersion =
                Jsoup.connect("https://play.google.com/store/apps/details?id=" + "com.finwin.doorstep.rightview" + "&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
                    .select(".hAyfc .htlgb")
                    .get(7)
                    .ownText()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (newVersion!=null){
            version=newVersion
        }else{
            version=""
        }
        return version
    }
}