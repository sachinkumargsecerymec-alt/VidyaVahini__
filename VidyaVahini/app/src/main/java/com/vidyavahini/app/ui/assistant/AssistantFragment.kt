package com.vidyavahini.app.ui.assistant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vidyavahini.app.appContainer
import com.vidyavahini.app.databinding.FragmentAssistantBinding
import com.vidyavahini.app.model.ChatMessage
import com.vidyavahini.app.ui.TitleSubtitle
import com.vidyavahini.app.ui.TitleSubtitleAdapter
import kotlinx.coroutines.launch

class AssistantFragment : Fragment() {
    private var _binding: FragmentAssistantBinding? = null
    private val binding get() = _binding!!
    private val messages = mutableListOf(ChatMessage(false, "Ask me about your route, ETA, delays or safety."))
    private val adapter = TitleSubtitleAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAssistantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.chatList.layoutManager = LinearLayoutManager(requireContext())
        binding.chatList.adapter = adapter
        render()
        binding.sendButton.setOnClickListener {
            val question = binding.messageInput.text?.toString().orEmpty().trim()
            if (question.isEmpty()) return@setOnClickListener
            binding.messageInput.setText("")
            messages += ChatMessage(true, question)
            render()
            viewLifecycleOwner.lifecycleScope.launch {
                val container = requireContext().appContainer
                val answer = container.aiRepository.askAssistant(question, container.routeRepository.selectedRoute(), container.routeRepository.pings.value)
                messages += ChatMessage(false, answer)
                render()
            }
        }
    }

    private fun render() {
        adapter.submitList(messages.mapIndexed { index, message ->
            TitleSubtitle(if (message.fromUser) "You" else "Vidya Assistant", message.text, index.toString())
        })
        binding.chatList.scrollToPosition(messages.lastIndex.coerceAtLeast(0))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
