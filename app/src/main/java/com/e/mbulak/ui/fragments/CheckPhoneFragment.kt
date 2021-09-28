package com.e.mbulak.ui.fragments

import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.e.mbulak.ui.viewModels.CheckPhoneViewModel
import com.e.mbulak.data.model.Country
import com.e.mbulak.databinding.CheckPhoneFragmentBinding
import com.e.mbulak.utils.MaskWatcher
import com.e.mbulak.utils.Status
import com.e.mbulak.utils.TextDrawable
import com.e.mbulak.utils.ToastHandler


class CheckPhoneFragment : Fragment() {

    private var _binding: CheckPhoneFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: CheckPhoneViewModel

    var listCountries = arrayListOf<Country>()

    var maskWatcher : TextWatcher? = null
    var model : Country? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = CheckPhoneFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initVieModel()

        binding.buttonPhoneNumber.setOnClickListener(View.OnClickListener {

            var numberStr = binding.editTextPhoneNumber.text.toString()
            numberStr = numberStr.replace("-", "")
            numberStr = numberStr.replace(")", "")
            numberStr = numberStr.replace("(", "")
            numberStr = numberStr.replace(" ", "")
            viewModel.checkPhoneNumber(model?.phone_code + numberStr)

        })

        binding.spinnerAvailableCountries.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                binding.editTextPhoneNumber.text?.clear()
                if(maskWatcher != null) {
                    binding.editTextPhoneNumber.removeTextChangedListener(maskWatcher)
                }

                model = listCountries.get(p2)
                if(model != null) {
                    val code = model!!.phone_code
                    binding.editTextPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(TextDrawable(code), null, null, null)
                    binding.editTextPhoneNumber.compoundDrawablePadding = code.length * 30
                }
                maskWatcher = model?.phone_mask_small?.let {
                    MaskWatcher(
                        it
                    )
                }
                binding.editTextPhoneNumber.addTextChangedListener(maskWatcher)
                binding.editTextPhoneNumber.filters = arrayOf<InputFilter>(LengthFilter(model!!.phone_mask_small.length))

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        })
    }

    fun initVieModel() {


        viewModel = ViewModelProvider(this).get(CheckPhoneViewModel::class.java)
        viewModel.getAvailableCountries().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        it.data?.body()?.result?.let { it1 -> listCountries.addAll(it1) }
                        binding.progressBarCheckPhone.visibility = View.INVISIBLE
                        val adapter: ArrayAdapter<Country> = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            listCountries
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                        binding.spinnerAvailableCountries.setAdapter(adapter)
                    }
                    Status.ERROR -> {
                        binding.progressBarCheckPhone.visibility = View.INVISIBLE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBarCheckPhone.visibility = View.VISIBLE
                    }
                }
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {

            if (it) {
                binding.progressBarCheckPhone.visibility = View.VISIBLE
            } else {
                binding.progressBarCheckPhone.visibility = View.INVISIBLE
            }

        })

        viewModel.checkUserPhone.observe(viewLifecycleOwner, Observer {
            if(it.error != null) {
                Toast.makeText(context, ToastHandler.errorText(it.error.code), Toast.LENGTH_LONG).show()
            } else {
                val direction =
                    CheckPhoneFragmentDirections.actionCheckPhoneFragmentToRegistrationPinFragment(it.result!!.id!!)
                findNavController().navigate(direction)
            }


        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {

            Toast.makeText(context, it, Toast.LENGTH_LONG).show()

        })

    }


}