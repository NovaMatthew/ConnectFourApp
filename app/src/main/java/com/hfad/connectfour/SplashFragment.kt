package com.hfad.connectfour

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController

class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        //initializes start button
        val startButton = view.findViewById<Button>(R.id.start_button)


        startButton.setOnClickListener {
            //Captures user input and passes it to the gameboard fragment for later use
            val username = view.findViewById<EditText>(R.id.name)
            val input = username.text.toString()
            val bundle = Bundle()
            bundle.putString("data", input)
val fragment = GameboardFragment()
            fragment.arguments = bundle

//On button click app displays game board fragment
            view.findNavController().navigate(R.id.action_splashFragment_to_gameboardFragment,bundle)


        }
        return view
    }


}