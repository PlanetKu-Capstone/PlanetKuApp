package com.capstone.planetku.ui.more

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.capstone.planetku.R
import com.capstone.planetku.databinding.FragmentMoreBinding
import com.capstone.planetku.ui.about.AboutActivity
import com.capstone.planetku.ui.login.LoginActivity
import com.capstone.planetku.ui.profile.ProfileActivity
import com.capstone.planetku.ui.welcome.WelcomeActivity

class MoreFragment : Fragment(R.layout.fragment_more) {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMoreBinding.bind(view)

        binding.tabProfile.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }

        binding.tabAboutApp.setOnClickListener {
            startActivity(Intent(requireContext(), AboutActivity::class.java))
        }

        binding.tabLogout.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("IS_FIRST_LAUNCH", true)
            editor.putBoolean("IS_LOGGED_OUT", true)
            editor.apply()

            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

