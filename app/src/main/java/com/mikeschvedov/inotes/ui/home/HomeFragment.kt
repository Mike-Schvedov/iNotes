package com.mikeschvedov.inotes.ui.home

import android.app.Dialog
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikeschvedov.inotes.NoteRecyclerAdapter
import com.mikeschvedov.inotes.R
import com.mikeschvedov.inotes.data.database.entities.Note
import com.mikeschvedov.inotes.databinding.FragmentFirstBinding
import com.mikeschvedov.inotes.widgets.NoteListWidget
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        /* Binding */
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        /* ViewModel */
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        /* Adapter */
        val adapter = NoteRecyclerAdapter() { clickedNote ->
            showAlertDialog(clickedNote)
        }
        binding.noteRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.noteRecycler.adapter = adapter

        /* OnClick Listeners */
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        /* Observers */
        homeViewModel.listOfNotes.observe(viewLifecycleOwner) { listOfNotes ->
            adapter.setNewData(listOfNotes)
        }

        return binding.root
    }

    private fun showAlertDialog(clickedNote: Note) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.dialog_animation

        val saveBTN = dialog.findViewById<AppCompatButton>(R.id.save_button)
        saveBTN.setOnClickListener {
            homeViewModel.deleteNoteFromDB(clickedNote)
            Toast.makeText(requireContext(), "Note Deleted", Toast.LENGTH_SHORT).show()
            updateWidget()
            dialog.dismiss()
        }

        val cancelBTN = dialog.findViewById<AppCompatButton>(R.id.cancel_button)
        cancelBTN.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.populateList()
        updateWidget()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateWidget(){
        val applicationContext = requireContext().applicationContext

        val intent = Intent(requireContext(), NoteListWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val ids: IntArray = AppWidgetManager.getInstance(applicationContext)
            .getAppWidgetIds(ComponentName(applicationContext, NoteListWidget::class.java))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        requireContext().sendBroadcast(intent)
    }


}