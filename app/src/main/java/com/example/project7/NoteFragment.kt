package com.example.project7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project7.databinding.FragmentNoteBinding


class NoteFragment : Fragment() {

        private var _binding: FragmentNoteBinding? = null
        private val binding get() = _binding!!
        val viewModel : NoteViewModel by activityViewModels()
    private lateinit var adapter: NoteItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        val view = binding.root


        adapter = NoteItemAdapter { note ->
            val action = NoteFragmentDirections.actionNoteFragmentToEditNote(note.noteId,note.noteTitle,note.noteDescription)
            findNavController().navigate(action)
        }




        binding.recyclerView.adapter = adapter
       // binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observe the LiveData with notes from the ViewModel and submit to the adapter
        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            adapter.submitList(notes)
        }




        binding.addNoteButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_noteFragment_to_newNote)
        }

        binding.signOutButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_noteFragment_to_startFragment)
        }


        // Inflate the layout for this fragment
        return view
    }
}