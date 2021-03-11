package id.itborneo.ugithub.aboutme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.itborneo.ugithub.R
import id.itborneo.ugithub.core.utils.ToastTop
import id.itborneo.ugithub.databinding.FragmentAboutMeBinding


class AboutMeFragment : Fragment() {

    private lateinit var binding: FragmentAboutMeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonListener()
    }

    private fun initButtonListener() {
        binding.btnMe.setOnClickListener {
            ToastTop.blueBackgroundShow(requireContext(), getString(R.string.in_development))
        }

        binding.btnCredits.setOnClickListener {
            ToastTop.blueBackgroundShow(requireContext(), getString(R.string.thanks_to))
        }
    }

}