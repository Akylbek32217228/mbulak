package com.e.mbulak.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.e.mbulak.ui.viewModels.RegistrationSuccessViewModel
import com.e.mbulak.databinding.RegistrationSuccessFragmentBinding

class RegistrationSuccesssFragment : Fragment() {

    private var _binding: RegistrationSuccessFragmentBinding? = null

    private val binding get() = _binding!!


    var args : RegistrationSuccesssFragmentArgs? = null

    companion object {
        fun newInstance() = RegistrationSuccesssFragment()
    }

    private lateinit var viewModel: RegistrationSuccessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = RegistrationSuccessFragmentBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegistrationSuccessViewModel::class.java)

        val bundle = arguments
        if(bundle == null) {
            Log.d("ololo", "bundle null")
        } else {
            args = RegistrationSuccesssFragmentArgs.fromBundle(bundle)

        }

        binding.buttonLogin.setOnClickListener(View.OnClickListener {

            val direction =
                RegistrationSuccesssFragmentDirections.toAuthorization(args!!.uid)
            findNavController().navigate(direction)

        })

    }

}