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
    private val alarmReceiver = AlarmReceiver()

    companion object {
        private const val ALARM_TIME = "9:00"
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
        updateUI()
        initButtonListener()
        initSwitchListener()
    }

    private fun updateUI() {
        binding.tvLanguage.text = getLanguage()
    }

    private fun setAlarm(activeIt: Boolean) {
        if (activeIt) {
            setDailyReminder()
        } else {
            removeDailyReminder()
        }
    }

    private fun setDailyReminder() {
        alarmReceiver.setRepeatingAlarm(
            requireContext(),
            ALARM_TIME,
            getString(R.string.lets_find_github_users)
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.daily_reminder_success),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun removeDailyReminder() {
        alarmReceiver.cancelAlarm(
            requireContext(),
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
        binding.llChangeLanguage.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        binding.llDailyReminder.setOnClickListener {
            binding.swDaily.apply {
                isChecked = !isChecked
            }
        }
    }

    private fun getLanguage() = Locale.getDefault().displayLanguage
}

