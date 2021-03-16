package id.itborneo.ugithub.aboutme

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import id.itborneo.ugithub.R
import id.itborneo.ugithub.core.utils.AlarmReceiver
import id.itborneo.ugithub.core.utils.KsPref
import id.itborneo.ugithub.databinding.FragmentSettingsBinding
import java.util.*


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    companion object {
        private const val ALARM_TIME = "09:00"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonListener()
        binding.tvLanguage.text = getLanguage()

        initButtonListener()
        initSwitchListener()
    }

    private fun setAlarm(activeIt: Boolean) {
        if (activeIt) {
            setDailyReminder()
        } else {
            removeDailyReminder()
        }

    }

    private fun setDailyReminder() {
        val alarmReceiver = AlarmReceiver()

        val repeatMessage = "Lets find new popular Github User "
        alarmReceiver.setRepeatingAlarm(
            requireContext(),
            AlarmReceiver.TYPE_REPEATING,
            ALARM_TIME,
            repeatMessage
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.daily_reminder_success),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun removeDailyReminder() {
        val alarmReceiver = AlarmReceiver()

        alarmReceiver.cancelAlarm(
            requireContext(),
            AlarmReceiver.TYPE_REPEATING
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.daily_reminder_removed),
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun initSwitchListener() {
        binding.swDaily.apply {
            isChecked = KsPref.isDailyReminderActive()
            setOnCheckedChangeListener { _, isChecked ->
                KsPref.setDailyReminder()
                setAlarm(isChecked)
            }
        }
    }

    private fun initButtonListener() {
        binding.llSetLanguange.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
    }

    private fun getLanguage() = Locale.getDefault().displayLanguage
}

