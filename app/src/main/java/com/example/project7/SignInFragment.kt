package com.example.project7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.project7.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

private var _binding: FragmentSignInBinding? = null
private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.signUpButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }


        val viewModel : NoteViewModel by activityViewModels()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



//navigates to the note fragment if the user is signed in
        viewModel.navigateToList.observe(viewLifecycleOwner, { navigate ->
            if (navigate == true) {
                this.findNavController().navigate(R.id.action_signInFragment_to_noteFragment)
                viewModel.onNavigatedToList()
            }
        })



        viewModel.errorHappened.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        })

        // Inflate the layout for this fragment
        return view
    }


}