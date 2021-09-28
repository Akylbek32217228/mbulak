package com.e.mbulak.ui.fragments

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.e.mbulak.ui.viewModels.AuthorizationViewModel
import com.e.mbulak.R
import com.e.mbulak.databinding.AuthorizationFragmentBinding
import com.e.mbulak.utils.Status
import com.e.mbulak.utils.ToastHandler
import kotlinx.android.synthetic.main.authorization_fragment.*

class AuthorizationFragment : Fragment() {



    private var _binding: AuthorizationFragmentBinding? = null

    private val binding get() = _binding!!

    var args : AuthorizationFragmentArgs? = null

    private lateinit var viewModel: AuthorizationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = AuthorizationFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        viewModel = ViewModelProvider(this).get(AuthorizationViewModel::class.java)

        val bundle = arguments
        if (bundle != null) {
            if(bundle.isEmpty) {
                Log.d("ololo", "bundle null")
            } else {
                args = AuthorizationFragmentArgs.fromBundle(bundle)

            }
        }

        binding.buttonLogin.setOnClickListener(View.OnClickListener {

            viewModel.login(binding.editTextLogin.text.toString(), binding.editTextPassword.text.toString(), uid = "1").
            observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            if(resource.data!!.body()?.error == null) {
                                binding.progressBarAuth.visibility = View.INVISIBLE
                                Toast.makeText(context, "Logged", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(context,
                                    resource.data.body()?.error?.code?.let { it1 ->
                                        ToastHandler.errorText(
                                            it1
                                        )
                                    }, Toast.LENGTH_LONG).show()
                                binding.progressBarAuth.visibility = View.INVISIBLE
                            }

                        }
                        Status.ERROR -> {
                            binding.progressBarAuth.visibility = View.INVISIBLE
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            binding.progressBarAuth.visibility = View.VISIBLE
                        }
                    }
                }
            })


        })


        binding.textRegistration.setOnClickListener( View.OnClickListener {

            text_registration.setTextColor(Color.GREEN)
            findNavController()
                .navigate(R.id.action_authorizationFragment_to_checkPhoneFragment)
        })


    }

}