package com.example.project7

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.project7.databinding.FragmentNewNoteBinding


class NewNote : Fragment() {

    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!
    val viewModel : NoteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        val view = binding.root


        //saves the title and description of the new note
        binding.saveButton.setOnClickListener {
            val title = binding.noteTitle.text.toString()
            val description = binding.noteDescription.text.toString()

            Log.i("NewNote", "title: $title")
            Log.i("NewNote", "description: $description")

                val newNote = Note().apply {
                    noteTitle = title
                    noteDescription = description
                }
//add the new note to the view model
                viewModel.addNote(newNote)

                this.findNavController().navigate(R.id.action_newNote_to_noteFragment)


        }





        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}