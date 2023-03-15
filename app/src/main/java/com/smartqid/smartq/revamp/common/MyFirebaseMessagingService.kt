package com.smartqid.smartq.revamp.common

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage
import com.smartqid.smartq.R
import com.smartqid.smartq.revamp.ui.home.HomeActivity

const val channelId = "notification_channel"
const val channelName = "com.smartqid.smartq"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {

        if (message.notification != null) {
            generateNotification(message.notification!!.title!!, message.notification!!.body!!)
        }

    }

    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String, desc: String): RemoteViews {
        val remoteViews = RemoteViews("com.smartqid.smartq", R.layout.notification)

        remoteViews.setTextViewText(R.id.title, title)
        remoteViews.setTextViewText(R.id.message, desc)
        remoteViews.setImageViewResource(R.id.app_logo, R.mipmap.ic_launcher)

        return remoteViews
    }

    fun generateNotification(title: String, desc: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, desc))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificatonChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificatonChannel)
        }

        notificationManager.notify(0, builder.build())
    }
}