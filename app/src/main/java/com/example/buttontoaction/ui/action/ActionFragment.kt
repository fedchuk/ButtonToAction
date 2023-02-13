package com.example.buttontoaction.ui.action

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.buttontoaction.MainActivity
import com.example.buttontoaction.R
import com.example.buttontoaction.databinding.FragmentActionBinding
import com.example.buttontoaction.ui.common.CommonFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ActionFragment : CommonFragment<FragmentActionBinding>(FragmentActionBinding::inflate) {
    override val viewModel by viewModel<ActionViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionBtn.setOnClickListener {
            viewModel.getAction()
        }
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                ActionState.AnimationAction -> rotateButton()
                ActionState.CallAction -> openToCall()
                ActionState.NotificationAction -> showNotification(
                    title = getString(R.string.notif_title),
                    body = getString(R.string.notif_body),
                    intent = Intent(requireContext(), MainActivity::class.java)
                )
                ActionState.ToastMessageAction -> showToast()
                else -> { /* ignore */ }
            }
        }
    }

    private fun rotateButton() {
        val rotate = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.duration = 3000
        rotate.interpolator = LinearInterpolator()
        binding.actionBtn.startAnimation(rotate)
    }

    private fun showToast() {
        Toast.makeText(requireContext(), getString(R.string.toast_text), Toast.LENGTH_SHORT).show()
    }

    private fun openToCall() {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "1234567890"))
        startActivity(intent)
    }

    private fun showNotification(title: String, body: String, intent: Intent) {
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1
        val channelId = "channel-001"
        val channelName = "ButtonToAction Name"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(
                channelId, channelName, importance
            )
            notificationManager.createNotificationChannel(mChannel)
        }
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(requireContext())
        stackBuilder.addNextIntent(intent)
        val resultPendingIntent: PendingIntent = stackBuilder.getPendingIntent(
            0,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.FLAG_MUTABLE
            } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        })
        builder.setContentIntent(resultPendingIntent)
        notificationManager.notify(notificationId, builder.build())
    }
}