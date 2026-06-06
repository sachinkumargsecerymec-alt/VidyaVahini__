package com.vidyavahini.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vidyavahini.app.R
import com.vidyavahini.app.appContainer
import com.vidyavahini.app.databinding.FragmentHomeBinding
import com.vidyavahini.app.ui.TitleSubtitle
import com.vidyavahini.app.ui.TitleSubtitleAdapter
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapter = TitleSubtitleAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val container = requireContext().appContainer
        val route = container.routeRepository.selectedRoute()
        binding.greetingText.text = "Hello, ${container.authRepository.currentStudent()?.name ?: "Student"}"
        binding.statusText.text = "Selected: ${route.name}"
        binding.reportList.layoutManager = LinearLayoutManager(requireContext())
        binding.reportList.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            container.routeRepository.pings.collect { pings ->
                adapter.submitList(pings.take(5).map { TitleSubtitle(it.stopName, it.note, it.id) })
            }
        }
        binding.routesButton.setOnClickListener { findNavController().navigate(R.id.routeSelectionFragment) }
        binding.trackButton.setOnClickListener { findNavController().navigate(R.id.liveTrackingFragment) }
        binding.notifyButton.setOnClickListener { findNavController().navigate(R.id.notificationsFragment) }
        binding.emergencyButton.setOnClickListener { findNavController().navigate(R.id.emergencyFragment) }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
