package com.mikeschvedov.inotes.ui.add_note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mikeschvedov.inotes.R
import com.mikeschvedov.inotes.databinding.FragmentSecondBinding


class AddNoteFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    lateinit var addNoteViewModel : AddNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /* ViewModel */
        addNoteViewModel = ViewModelProvider(this)[AddNoteViewModel::class.java]

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        binding.buttonAdd.setOnClickListener {
            val input = binding.noteInputEdittext.text.toString()
            if (input.isNotEmpty()){
                // Send input text to ViewModel
                addNoteViewModel.addNoteToDB(input)
                // Clear the old text
                binding.noteInputEdittext.text.clear()
                // Notify the user about success
                Toast.makeText(requireContext(), "Note was added", Toast.LENGTH_SHORT).show()
                //Go back to home fragment
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }else{
                // Notify the user about failure
                Toast.makeText(requireContext(), "Note cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}