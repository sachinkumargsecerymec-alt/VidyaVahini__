package com.vidyavahini.app.ui.emergency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.vidyavahini.app.appContainer
import com.vidyavahini.app.databinding.FragmentEmergencyBinding
import com.vidyavahini.app.model.EmergencyReport
import kotlinx.coroutines.launch
import java.util.UUID

class EmergencyFragment : Fragment() {
    private var _binding: FragmentEmergencyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEmergencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.sosButton.setOnClickListener { send("SOS", "Immediate help requested from student.") }
        binding.breakdownButton.setOnClickListener { send("Breakdown", "Bus breakdown reported.") }
        binding.sendReportButton.setOnClickListener { send("Report", binding.reportInput.text?.toString().orEmpty().ifBlank { "Safety issue reported." }) }
    }

    private fun send(type: String, message: String) {
        val container = requireContext().appContainer
        val report = EmergencyReport(UUID.randomUUID().toString(), container.routeRepository.selectedRoute().id, type, message, System.currentTimeMillis())
        viewLifecycleOwner.lifecycleScope.launch { container.firebaseTransportRepository.saveEmergency(report) }
        Toast.makeText(requireContext(), "$type sent", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
