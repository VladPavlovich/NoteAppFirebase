package com.example.project7

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController


class SplashFragment : Fragment() {
        val viewModel : NoteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    //checks if user is signed in and navigates to the appropriate fragment

    override fun onStart() {
        super.onStart()
        val currentUser = viewModel.getCurrentUser()
        Log.i("SplashFragment", "onStart: $currentUser")
        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            if (currentUser != null) {
                viewModel.initializeTheDatabaseReference()
             this.findNavController().navigate(R.id.action_splashFragment_to_noteFragment)
            }
            else {
               this.findNavController().navigate(R.id.action_splashFragment_to_signInFragment)

            }

        }, 0)
    }


}