package com.example.project7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.project7.databinding.FragmentStartBinding

//userScreen fragment


class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    val viewModel : NoteViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        val view = binding.root

        //button set up for start fragment to navigate to sign in and sign up fragments and sign out.


        binding.signInButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_startFragment_to_signInFragment)
        }

        binding.signUpButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_startFragment_to_signUpFragment)
        }

        binding.signOutButton.setOnClickListener{
            Toast.makeText(context, "You are now signed out", Toast.LENGTH_SHORT).show()
            viewModel.signOut()

        }


        // Inflate the layout for this fragment
        return view
    }


}