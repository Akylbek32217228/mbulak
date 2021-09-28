package com.e.mbulak.ui.fragments

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
import com.e.mbulak.ui.viewModels.RegistrationPinViewModel
import com.e.mbulak.databinding.RegistrationPinFragmentBinding
import com.e.mbulak.utils.ToastHandler
import kotlinx.android.synthetic.main.registration_pin_fragment.*

class RegistrationPinFragment : Fragment() {

    private var _binding: RegistrationPinFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: RegistrationPinViewModel
    var args : RegistrationPinFragmentArgs? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegistrationPinFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegistrationPinViewModel::class.java)

        val bundle = arguments
        if(bundle == null) {
           Log.d("ololo", "bundle null")
        } else {
            args = RegistrationPinFragmentArgs.fromBundle(bundle)

        }

        binding.buttonRegistrationPin.setOnClickListener(View.OnClickListener {
            if(binding.editTextSmsCode.text.toString() != "") {
                viewModel.checkCode(args!!.id,  binding.editTextSmsCode.text.toString().toInt())
            } else {
                Toast.makeText(context, "пустой код", Toast.LENGTH_LONG).show()
            }

        })

        binding.textViewNotGetSms.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, "Вам отправлено новое смс с кодом", Toast.LENGTH_LONG).show()
        })

        viewModel.success.observe(viewLifecycleOwner, Observer {
            if(it.error != null) {
                Toast.makeText(context, ToastHandler.errorText(it.error.code), Toast.LENGTH_LONG).show()
            } else {
                val direction = RegistrationPinFragmentDirections.actionRegistrationPinFragmentToRegistrationFragment(edit_text_sms_code.text.toString())
                findNavController().navigate(direction)
            }

        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {

            Toast.makeText(context, it, Toast.LENGTH_LONG).show()

        })

    }

}