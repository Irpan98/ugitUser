package id.itborneo.ugithub.aboutme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import id.itborneo.ugithub.R
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
            Toast.makeText(requireContext(), getString(R.string.in_development), Toast.LENGTH_SHORT)
                .show()
        }

        binding.btnCredits.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.thanks_to), Toast.LENGTH_SHORT)
                .show()
        }
    }

}