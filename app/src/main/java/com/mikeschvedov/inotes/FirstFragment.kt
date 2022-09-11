package com.mikeschvedov.inotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikeschvedov.inotes.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /* Binding */
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        /* Adapter */
        val adapter = NoteRecyclerAdapter() { shipCallback ->

        }
        binding.noteRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.noteRecycler.adapter = adapter

        /* OnClick Listeners */
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}