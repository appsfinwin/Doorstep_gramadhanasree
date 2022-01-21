package com.finwin.doorstep.gramadhanasree.home.support


import android.content.Context

import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.utils.Services

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SupportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SupportFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var webView: WebView
    lateinit var layoutNoInternet: ConstraintLayout
    lateinit var layoutNoErrorConnection: ConstraintLayout
    private var mySwipeRefreshLayout: SwipeRefreshLayout? = null
    private var mOnScrollChangedListener: ViewTreeObserver.OnScrollChangedListener? = null
    var loading: SweetAlertDialog? = null
    var sharedPreferences: SharedPreferences? = null
    var edit: SharedPreferences.Editor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mySwipeRefreshLayout = activity?.findViewById<SwipeRefreshLayout>(R.id.swipeContainer)
        webView =view.findViewById(R.id.webview_support)

            layoutNoErrorConnection = view.findViewById<ConstraintLayout>(R.id.layout_error_connection)
                    layoutNoInternet = view?.findViewById<ConstraintLayout>(R.id.layout_no_internet)
        val webSettings: WebSettings = webView?.settings
        webSettings.javaScriptEnabled = true
        webSettings.javaScriptEnabled = true // Done above
        webSettings.domStorageEnabled = true // Try
        webSettings.setSupportZoom(false)
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        webView?.setWebChromeClient( WebChromeClient())



        if (isNetworkOnline()) {
            initLoading(context)
            webView?.loadUrl("http://finwintechnologies.com:8088/taskmanagement/minimal/login/loginform.php")
            layoutNoInternet.visibility = View.GONE
            layoutNoErrorConnection.visibility = View.GONE
            webView?.visibility = View.VISIBLE
        } else {
            layoutNoInternet.visibility = View.VISIBLE
            layoutNoErrorConnection.visibility = View.GONE
            webView?.visibility = View.GONE
            //mySwipeRefreshLayout?.visibility= View.GONE
        }

        //cart
        //webView.loadUrl("http://192.168.0.221:149/index.aspx")
        //webView.loadUrl("http://192.168.0.221:224/") //bliss
        //webView.loadUrl("http://pickcartshopy.com/")


        webView?.webViewClient = WebViewClient()

        webView?.settings?.loadWithOverviewMode = true
        webView?.settings?.useWideViewPort = true


        webView?.settings?.javaScriptEnabled = true
        webView?.settings?.javaScriptCanOpenWindowsAutomatically = true;



        webView?.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            (scrollY == 0).also {
                mySwipeRefreshLayout?.isEnabled = it
            }

            //mySwipeRefreshLayout?.isEnabled = (scrollY==0)
        }
        mySwipeRefreshLayout?.setOnRefreshListener {
            if (isNetworkOnline()) {
                //webView.loadUrl("http://testcartmob.digicob.in/login?UserName=$username&Password=$password")
                layoutNoInternet.visibility = View.GONE
                webView?.visibility = View.VISIBLE
                layoutNoErrorConnection.visibility = View.GONE
                webView?.reload();
                initLoading(context)
            } else {
                mySwipeRefreshLayout?.isRefreshing = false
                layoutNoInternet.visibility = View.VISIBLE
                layoutNoErrorConnection.visibility = View.GONE
                webView?.visibility = View.GONE
            }
        }
        webView?.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                //initLoading(this@MainActivity)
                mySwipeRefreshLayout?.isEnabled = false
                if (url.startsWith("tel:")) {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                    startActivity(intent)
                    return true
                }
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {

                mySwipeRefreshLayout?.isRefreshing = false
                mySwipeRefreshLayout?.isEnabled = false
                cancelLoading()
            }
            override fun onReceivedError(
                view: WebView?,
                errorCod: Int,
                description: String,
                failingUrl: String?
            ) {
                layoutNoInternet.visibility = View.GONE
                layoutNoErrorConnection.visibility = View.VISIBLE
                webView?.visibility = View.VISIBLE
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_support, container, false)
    }

    companion object {


        @JvmStatic
        fun newInstance() =
            SupportFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    fun initLoading(context: Context?) {
        loading = Services.showProgressDialog(context)
    }

    fun cancelLoading() {
        if (loading != null) {
            loading!!.cancel()
            loading = null
        }
    }

    fun isNetworkOnline(): Boolean {
        var status = false
        try {
            val cm = (activity?.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager)
            var netInfo = cm.getNetworkInfo(0)
            if (netInfo != null && netInfo.state == NetworkInfo.State.CONNECTED) {
                status = true
            } else {
                netInfo = cm.getNetworkInfo(1)
                if (netInfo != null && netInfo.state == NetworkInfo.State.CONNECTED) status = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return status
    }
}