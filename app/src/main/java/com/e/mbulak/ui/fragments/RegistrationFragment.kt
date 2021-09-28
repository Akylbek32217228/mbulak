package com.e.mbulak.ui.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.e.mbulak.R
import com.e.mbulak.ui.viewModels.RegistrationViewModel
import com.e.mbulak.data.model.*
import com.e.mbulak.utils.MaskWatcher
import com.e.mbulak.utils.TextDrawable
import com.e.mbulak.utils.ToastHandler
import com.google.android.material.textfield.TextInputEditText
import net.bohush.geometricprogressview.GeometricProgressView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class RegistrationFragment : Fragment() {

    private lateinit var viewModel: RegistrationViewModel
    lateinit var progressBar: GeometricProgressView
    lateinit var buttonRegistration : CardView
    lateinit var spinnerQuestions : Spinner
    lateinit var spinnerGenders : Spinner
    lateinit var spinnerNationalities : Spinner
    lateinit var spinnerTrafficSources : Spinner
    lateinit var nameEditText : TextInputEditText
    lateinit var phoneNumberEditText : TextInputEditText
    lateinit var phoneNumberEditText2 : TextInputEditText
    lateinit var answerSecretQuiestion : TextInputEditText
    lateinit var lastNameEditText: TextInputEditText
    lateinit var secondNameEditText: TextInputEditText
    lateinit var alreadyRegisteredTextView : TextView
    lateinit var birthDate: TextInputEditText
    lateinit var checkBoxTerm: CheckBox
    var dateAndTime: Calendar = Calendar.getInstance()
    var d : DatePickerDialog.OnDateSetListener? = null

    var args : RegistrationFragmentArgs? = null

    var maskWatcher : TextWatcher? = null
    var model : Country? = null
    var listNationalities = arrayListOf<Country>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)

        val bundle = arguments
        if(bundle == null) {
            Log.d("ololo", "bundle null")
        } else {
            args = RegistrationFragmentArgs.fromBundle(bundle)

        }

        initViewMode()
        initView()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progress_bar_registration)
        buttonRegistration = view.findViewById(R.id.button_registration)
        spinnerQuestions = view.findViewById(R.id.spinner_secret_question)
        nameEditText = view.findViewById(R.id.edit_text_name)
        phoneNumberEditText = view.findViewById(R.id.edit_text_phone)
        phoneNumberEditText2 = view.findViewById(R.id.edit_text_second_phone)
        answerSecretQuiestion = view.findViewById(R.id.edit_text_secret_answer)
        alreadyRegisteredTextView = view.findViewById(R.id.text_already_registered)
        birthDate = view.findViewById(R.id.edit_text_birth)
        spinnerGenders = view.findViewById(R.id.spinner_gender)
        spinnerNationalities = view.findViewById(R.id.spinner_nationality)
        spinnerTrafficSources = view.findViewById(R.id.spinner_traffic_source)
        lastNameEditText = view.findViewById(R.id.edit_text_last_name)
        secondNameEditText = view.findViewById(R.id.edit_text_second_name)
        checkBoxTerm = view.findViewById(R.id.checkbox_terms_of_use)

    }

    private fun initView() {

        alreadyRegisteredTextView.setOnClickListener(View.OnClickListener {
            alreadyRegisteredTextView.setTextColor(Color.GREEN)
        })

        birthDate.setOnClickListener(View.OnClickListener {
            DatePickerDialog(
                requireContext(), d,
                dateAndTime[Calendar.YEAR],
                dateAndTime[Calendar.MONTH],
                dateAndTime[Calendar.DAY_OF_MONTH]
            ).show()
        })

        d = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                dateAndTime[Calendar.YEAR] = year
                dateAndTime[Calendar.MONTH] = monthOfYear
                dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth
                val timeStampFormat = SimpleDateFormat("dd.MM.yyyy")
                val GetDate = dateAndTime.time
                val DateStr = timeStampFormat.format(GetDate)
                birthDate.setText(DateStr.toString())
            }

        buttonRegistration.setOnClickListener(View.OnClickListener {

            if(!checkBoxTerm.isChecked) {
                Toast.makeText(context, "Условия обработки персональных данных не приняты", Toast.LENGTH_LONG).show()
            } else {
                var date : Date? = null
                val format = SimpleDateFormat("dd.mm.yyyy")

                try {
                    date = format.parse(birthDate.text.toString())
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                viewModel.registration(
                    NewUserRegistrationModel(
                        first_name = nameEditText.text.toString(),
                        last_name =  lastNameEditText.text.toString(),
                        second_name = secondNameEditText.text.toString(),
                        u_date = date,
                        gender = spinnerGenders.selectedItemPosition + 1,
                        nationality = spinnerNationalities.selectedItemPosition + 1,
                        first_phone = model?.phone_code + removeUnnecessarySymbols(phoneNumberEditText.text.toString()),
                        second_phone = model?.phone_code + removeUnnecessarySymbols(phoneNumberEditText2.text.toString()),
                        question = spinnerQuestions.selectedItemPosition + 1,
                        response = answerSecretQuiestion.text.toString(),
                        traffic_source = spinnerTrafficSources.selectedItemPosition + 1,
                        sms_code = args?.smsCode,
                        system = 1
                    )
                )
            }


        })

    }

    private fun initViewMode() {

        viewModel.getNeededData()

        viewModel.questions.observe(viewLifecycleOwner, Observer {
            if(it.error != null) {
                Toast.makeText(context, "Traffic Sources " + ToastHandler.errorText(it.error.code), Toast.LENGTH_LONG).show()
            } else {
                val adapter: ArrayAdapter<Question> = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    it.result
                )

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                spinnerQuestions.setAdapter(adapter)
            }


        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {

            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }

        })

        viewModel.gender.observe(viewLifecycleOwner, Observer {
            if(it.error != null) {
                Toast.makeText(context, "Gender " + ToastHandler.errorText(it.error.code), Toast.LENGTH_LONG).show()
            } else {
                val adapter: ArrayAdapter<Gender> = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    it.result
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerGenders.setAdapter(adapter)
            }


        })

        viewModel.nationality.observe(viewLifecycleOwner, Observer {
            if(it.error != null) {
                Toast.makeText(context, "Nationalities " + ToastHandler.errorText(it.error.code), Toast.LENGTH_LONG).show()
            } else {

                listNationalities.addAll(it.result)

                val adapter: ArrayAdapter<Country> = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    it.result
                )

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                spinnerNationalities.setAdapter(adapter)
            }

        })

        spinnerNationalities.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                phoneNumberEditText.text?.clear()
                phoneNumberEditText2.text?.clear()
                if(maskWatcher != null) {
                    phoneNumberEditText.removeTextChangedListener(maskWatcher)
                    phoneNumberEditText2.removeTextChangedListener(maskWatcher)
                }

                model = listNationalities.get(p2)
                if(model != null) {
                    val code = model!!.phone_code
                    phoneNumberEditText.setCompoundDrawablesWithIntrinsicBounds(TextDrawable(code), null, null, null)
                    phoneNumberEditText.compoundDrawablePadding = code.length * 30
                    phoneNumberEditText2.setCompoundDrawablesWithIntrinsicBounds(TextDrawable(code), null, null, null)
                    phoneNumberEditText2.compoundDrawablePadding = code.length * 30
                }
                maskWatcher = model?.phone_mask_small?.let {
                    MaskWatcher(
                        it
                    )
                }
                phoneNumberEditText.addTextChangedListener(maskWatcher)
                phoneNumberEditText.filters = arrayOf<InputFilter>(
                    InputFilter.LengthFilter(
                        model!!.phone_mask_small.length
                    )
                )
                phoneNumberEditText2.addTextChangedListener(maskWatcher)
                phoneNumberEditText2.filters = arrayOf<InputFilter>(
                    InputFilter.LengthFilter(
                        model!!.phone_mask_small.length
                    )
                )

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        })


        viewModel.trafficSource.observe(viewLifecycleOwner, Observer {
            if(it.error != null) {
                Toast.makeText(context, "Traffic Sources " + ToastHandler.errorText(it.error.code), Toast.LENGTH_LONG).show()
            } else {
                val adapter: ArrayAdapter<TrafficSource> = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    it.result
                )

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                spinnerTrafficSources.setAdapter(adapter)
            }

        })

        viewModel.userRegistration.observe(viewLifecycleOwner, {
            if(it.error != null) {
                Toast.makeText(context, ToastHandler.errorText(it.error.code), Toast.LENGTH_LONG).show()
            } else {
                val direction =
                    RegistrationFragmentDirections.registrationToRegistrationSuccess(it.result!!.id)
                findNavController().navigate(direction)
            }


        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {

            Toast.makeText(context, it, Toast.LENGTH_LONG).show()

        })

    }

    fun removeUnnecessarySymbols(text : String) : String {
        var text1 = text
        text1 = text1.replace("-", "")
        text1 = text1.replace(")", "")
        text1 = text1.replace("(", "")
        text1 = text1.replace(" ", "")
        return text
    }

}