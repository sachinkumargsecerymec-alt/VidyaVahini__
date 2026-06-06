package com.vidyavahini.app.ui.routes

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vidyavahini.app.appContainer
import com.vidyavahini.app.databinding.FragmentRoutesBinding
import com.vidyavahini.app.model.BusRoute
import com.vidyavahini.app.ui.TitleSubtitle
import com.vidyavahini.app.ui.TitleSubtitleAdapter

class RouteSelectionFragment : Fragment() {
    private var _binding: FragmentRoutesBinding? = null
    private val binding get() = _binding!!
    private lateinit var routes: List<BusRoute>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRoutesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repo = requireContext().appContainer.routeRepository
        routes = repo.allRoutes()
        val adapter = TitleSubtitleAdapter {
            repo.selectRoute(it.id)
            Toast.makeText(requireContext(), "Route selected: ${it.title}", Toast.LENGTH_SHORT).show()
        }
        binding.routeList.layoutManager = LinearLayoutManager(requireContext())
        binding.routeList.adapter = adapter
        fun render(query: String = "") {
            adapter.submitList(routes.filter { it.name.contains(query, true) || it.stops.any { stop -> stop.contains(query, true) } }
                .map { TitleSubtitle(it.name, "${it.start} to ${it.end} â€¢ ${it.distanceKm} km", it.id) })
        }
        render()
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = render(s?.toString().orEmpty())
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
