package com.example.pecode_test

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.pecode_test.databinding.FragmentCounterBinding


class Counter(
    private val value: Int,
    private val pagerAdapter: ViewPagerAdapter,
    private val viewPager: ViewPager2
) : Fragment() {

    private lateinit var uiBinding: FragmentCounterBinding
    val NOTIFICATION_CHANNEL_ID = "notificChannel"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        uiBinding = FragmentCounterBinding.inflate(layoutInflater, container, false)
        uiBinding.countTextView.text = (value).toString()
        if (value == 1) {
            uiBinding.minusImageView.isGone = true
        }
        uiBinding.frameLayout.setOnClickListener {
            createNotificationChannel()
            val notification =
                NotificationCompat.Builder(requireContext(), NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(getString(R.string.notification_title))
                    .setContentText(getString(R.string.notification_text, value))
                    .setPriority(NotificationCompat.PRIORITY_MIN)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentIntent(createNotificationIntent())
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            resources,
                            R.drawable.notification_icon
                        )
                    )
                    .setAutoCancel(true)
            with(NotificationManagerCompat.from(requireContext())) {
                notify((Math.random() * 1000).toInt(), notification.build())
            }
        }
        uiBinding.plusImageView.setOnClickListener {
            pagerAdapter.createFragment(value)
            viewPager.currentItem = value
            pagerAdapter.notifyDataSetChanged()
        }
        uiBinding.minusImageView.setOnClickListener {
            pagerAdapter.removeFragment(value)
            viewPager.currentItem = value - 1
            pagerAdapter.notifyDataSetChanged()
        }
        return uiBinding.root
    }

    private fun showLog(text: String) {
        Log.e("Logs", text)
    }

    private fun createNotificationIntent(): PendingIntent? {
        val notificationIntent = Intent(requireContext(), MainActivity::class.java)
        notificationIntent.addFlags(FLAG_ACTIVITY_SINGLE_TOP)
        notificationIntent.putExtra("val", value)
        return TaskStackBuilder.create(requireContext()).run {
            addNextIntentWithParentStack(notificationIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager =
                getSystemService(requireContext(), NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}