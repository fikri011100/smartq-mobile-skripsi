package com.smartqid.smartq.revamp.ui.auth

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.smartqid.smartq.R


class LoginActivity : AppCompatActivity() {

    private lateinit var appUpdateManager: AppUpdateManager
    private val RC_APP_UPDATE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        appUpdateManager = AppUpdateManagerFactory.create(this)
        appUpdateManager.appUpdateInfo.addOnSuccessListener { result ->
            if (result!!.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        result,
                        AppUpdateType.IMMEDIATE,
                        this,
                        RC_APP_UPDATE
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
//        appUpdateManager.registerListener(installStateUpdatedListener)
    }

    private var installStateUpdatedListener: InstallStateUpdatedListener =
        InstallStateUpdatedListener { state ->
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                showCompletedUpdate()
            }
        }

    override fun onStop() {
//        if (appUpdateManager != null) appUpdateManager.unregisterListener(installStateUpdatedListener)
        super.onStop()
    }

    private fun showCompletedUpdate() {
        var snackbar: Snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            "Aplikasi versi terbaru sudah tersedia...",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction("Install", View.OnClickListener {
            appUpdateManager.completeUpdate()
        })
        snackbar.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_APP_UPDATE && resultCode != RESULT_OK) {

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager.appUpdateInfo.addOnSuccessListener { result ->
            if (result!!.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS)
                {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        result,
                        AppUpdateType.IMMEDIATE,
                        this,
                        RC_APP_UPDATE
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }
}