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
import com.vidyavahini.app.databinding.FragmentSignupBinding
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.signupButton.setOnClickListener {
            val name = binding.nameInput.text?.toString().orEmpty().ifBlank { "Vidya Student" }
            val email = binding.emailInput.text?.toString().orEmpty().ifBlank { "student@vidyavahini.local" }
            val password = binding.passwordInput.text?.toString().orEmpty().ifBlank { "vidya123" }
            viewLifecycleOwner.lifecycleScope.launch {
                requireContext().appContainer.authRepository.signup(name, email, password)
                Toast.makeText(requireContext(), "Account ready", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signup_to_home)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
