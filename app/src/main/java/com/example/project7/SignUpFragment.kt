package com.example.project7

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.project7.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModel : NoteViewModel by activityViewModels()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


//sign up button navigates to the sign in fragment
        binding.signUpButton.setOnClickListener {
            viewModel.signUp()
            this.findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
//have account button navigates to the sign in fragment
        binding.haveAccountButton.setOnClickListener{
            this.findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }


        // Inflate the layout for this fragment
        return view
    }

}