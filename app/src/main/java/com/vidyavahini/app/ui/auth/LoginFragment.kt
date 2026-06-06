package com.vidyavahini.app.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vidyavahini.app.R
import com.vidyavahini.app.appContainer
import com.vidyavahini.app.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text?.toString().orEmpty().ifBlank { "student@vidyavahini.local" }
            val password = binding.passwordInput.text?.toString().orEmpty().ifBlank { "vidya123" }
            viewLifecycleOwner.lifecycleScope.launch {
                requireContext().appContainer.authRepository.login(email, password)
                Toast.makeText(requireContext(), "Welcome to Vidya Vahini", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_login_to_home)
            }
        }
        binding.signupLink.setOnClickListener { findNavController().navigate(R.id.action_login_to_signup) }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
