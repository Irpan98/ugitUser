package id.itborneo.ugithub.core.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import id.itborneo.ugithub.MainActivity
import id.itborneo.ugithub.R


object Notification {
    fun showDailyReminder(
        context: Context,
        message: String?,
        notificationId: Int
    ) {
        val channelId = context.getString(R.string.ch_daily_reminder)
        val channelName = context.getString(R.string.daily_reminder)
        val title = context.getString(R.string.daily_reminder)

        val pendingActionClick = actionClick(context)

        val notificationManagerCompact =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_github_white)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingActionClick)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName, NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompact.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompact.notify(notificationId, notification)

    }

    private fun actionClick(context: Context): PendingIntent? {
        val notifyIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(
            context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}