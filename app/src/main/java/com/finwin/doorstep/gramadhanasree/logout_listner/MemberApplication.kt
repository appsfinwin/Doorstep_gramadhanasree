package com.finwin.doorstep.gramadhanasree.logout_listner

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.room.MemberDatabase
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.JlgSelectGroupRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.util.*

class MemberApplication : Application(), LifecycleObserver {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { MemberDatabase.getDataBase(this, applicationScope) }
    val repository by lazy { JlgSelectGroupRepository(database.memberDao()) }

    companion object {
        private val ONE_MINUTE = 60000
        private val FIVE_MINUTE = 300000
        private var timer: Timer? = null
        private var logoutListener: LogoutListener? = null
        fun userSessionStart() {
            timer?.cancel()
            timer = Timer()
            timer!!.schedule(object : TimerTask() {
                override fun run() {
                    logoutListener?.onSessionLogout()
                }
            }, FIVE_MINUTE.toLong())
        }

        fun resetSession() {
            userSessionStart()
        }

        fun registerSessionListener(listener: LogoutListener) {
            logoutListener = listener
        }




    }

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onTrimMemory(level: Int) {
        // this method is called when the app goes in background.
        // you can perform your logout service here
        super.onTrimMemory(level)
        logoutListener?.background()
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        //logoutListener.background();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        //Log.d("MyApp", "App in foreground")
        logoutListener?.foreground()
    }

}