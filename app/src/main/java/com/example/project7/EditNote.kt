package com.example.project7

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.project7.databinding.FragmentEditNoteBinding


class EditNote : Fragment() {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel : NoteViewModel by activityViewModels()
    private val args: EditNoteArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        val view = binding.root

        Log.i("EditNote", "onCreateView: ${args.noteId} ${args.noteTitle} ${args.noteDescription}")
//note title and description handling used safe args
        binding.noteTitle.setText(args.noteTitle)
        binding.noteDescription.setText(args.noteDescription)


        //delete button and dialog fragment handling
        binding.deleteButton.setOnClickListener {
            val dialog = DeleteConfirmationDialog {
                viewModel.noteId = args.noteId
                viewModel.deleteNote()
                this.findNavController().navigate(R.id.action_editNote_to_noteFragment)
            }
            dialog.show(parentFragmentManager, "deleteConfirmation")
        }




//save the updated note
        binding.saveButton.setOnClickListener{

//create the updated note object
            val updateNote = Note().apply {
                noteId = args.noteId
                noteTitle = binding.noteTitle.text.toString()
                noteDescription = binding.noteDescription.text.toString()
            }

//set the updated note to the view model
            viewModel.note.value = updateNote
            viewModel.noteId = args.noteId  // Add this line
            viewModel.updateNote()

            this.findNavController().navigate(R.id.action_editNote_to_noteFragment)

        }

        return view
    }


}