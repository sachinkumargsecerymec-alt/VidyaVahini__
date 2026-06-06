package com.vidyavahini.app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vidyavahini.app.R
import com.vidyavahini.app.appContainer
import com.vidyavahini.app.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val container = requireContext().appContainer
        val student = container.authRepository.currentStudent()
        binding.nameText.text = student?.name ?: "Student"
        binding.emailText.text = student?.email ?: "student@vidyavahini.local"
        binding.routeText.text = "Selected route: ${container.routeRepository.selectedRoute().name}"
        binding.settingsButton.setOnClickListener { findNavController().navigate(R.id.settingsFragment) }
        binding.logoutButton.setOnClickListener {
            container.authRepository.logout()
            findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
