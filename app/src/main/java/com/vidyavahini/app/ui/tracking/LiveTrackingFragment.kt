package com.vidyavahini.app.ui.tracking

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vidyavahini.app.R
import com.vidyavahini.app.appContainer
import com.vidyavahini.app.databinding.FragmentTrackingBinding
import kotlinx.coroutines.launch

class LiveTrackingFragment : Fragment() {
    private var _binding: FragmentTrackingBinding? = null
    private val binding get() = _binding!!
    private var googleMap: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { map ->
            googleMap = map
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                map.isMyLocationEnabled = true
            }
            renderTracking()
        }
        renderTracking()
        binding.pingButton.setOnClickListener { sendPing() }
        binding.safeButton.setOnClickListener { Toast.makeText(requireContext(), "Safe reach notification sent", Toast.LENGTH_SHORT).show() }
    }

    private fun renderTracking() {
        val container = requireContext().appContainer
        val route = container.routeRepository.selectedRoute()
        val location = container.routeRepository.latestBusLocation(route.id)
        binding.locationText.text = "Bus near ${route.stops.getOrNull(1) ?: route.start} at ${"%.4f".format(location.latitude)}, ${"%.4f".format(location.longitude)}"
        viewLifecycleOwner.lifecycleScope.launch {
            val eta = container.aiRepository.predictEta(route, location, container.routeRepository.pings.value)
            binding.etaText.text = "AI ETA: ${eta.minutes} min â€¢ ${eta.confidence}"
        }
        googleMap?.let { map ->
            val point = LatLng(location.latitude, location.longitude)
            map.clear()
            map.addMarker(MarkerOptions().position(point).title(route.name).snippet("Live bus update"))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13f))
        }
    }

    private fun sendPing() {
        val container = requireContext().appContainer
        val route = container.routeRepository.selectedRoute()
        val loc = container.routeRepository.latestBusLocation(route.id)
        val ping = container.routeRepository.addPing(route.id, route.stops.getOrElse(1) { route.start }, "Crowd sighting: bus seen just now", loc.latitude, loc.longitude)
        viewLifecycleOwner.lifecycleScope.launch { container.firebaseTransportRepository.savePing(ping) }
        Toast.makeText(requireContext(), "Crowd ping sent", Toast.LENGTH_SHORT).show()
        renderTracking()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
