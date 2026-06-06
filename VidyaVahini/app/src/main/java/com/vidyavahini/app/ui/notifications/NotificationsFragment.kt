package com.vidyavahini.app.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vidyavahini.app.appContainer
import com.vidyavahini.app.databinding.FragmentNotificationsBinding
import com.vidyavahini.app.ui.TitleSubtitle
import com.vidyavahini.app.ui.TitleSubtitleAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationsFragment : Fragment() {
    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
        val adapter = TitleSubtitleAdapter()
        binding.notificationList.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationList.adapter = adapter
        adapter.submitList(requireContext().appContainer.notificationRepository.notifications().map {
            TitleSubtitle(it.title, "${it.message} â€¢ ${formatter.format(Date(it.createdAt))}", it.id)
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
