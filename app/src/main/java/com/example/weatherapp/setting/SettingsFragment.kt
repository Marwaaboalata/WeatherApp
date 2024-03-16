package com.example.weatherapp.setting

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentFavouriteBinding
import com.example.weatherapp.databinding.FragmentSettingsBinding
import com.example.weatherapp.utils.Constant
import com.example.weatherapp.utils.PreferenceManager
import java.util.Locale


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedLanguage = PreferenceManager.getSelectedLanguage(requireContext())
        if (selectedLanguage == Constant.Ar_Language) {
            binding.arabicRadioButton.isChecked = true
        } else {
            binding.englishRadioButton.isChecked = true
        }
        val selectedTemperatureUnit = PreferenceManager.getSelectedTemperatureUnit(requireContext())
        when (selectedTemperatureUnit) {
            Constant.Unit_Celsius-> binding.celsiusRadioButton.isChecked = true
            Constant.Unit_Fahrenheit -> binding.fahrenheitRadioButton.isChecked = true
            Constant.Unit_kelvin -> binding.kelvinRadioButton.isChecked = true
        }

        binding.arabicRadioButton.setOnClickListener{
            PreferenceManager.saveSelectedLanguage(requireContext(), Constant.Ar_Language)
           setLocale("ar",requireContext())
            restartActivity()
        }

        binding.englishRadioButton.setOnClickListener{
            PreferenceManager.saveSelectedLanguage(requireContext(), Constant.En_Language)
            setLocale("en",requireContext())
            restartActivity()
        }
/*
        binding.arabicRadioButton.setOnClickListener{
            PreferenceManager.saveSelectedLanguage(requireContext(),Constant.Ar_Language)
           // changeLanguageLocaleTo("ar")
            setAppLocale("ar",requireContext())


        }
        binding.englishRadioButton.setOnClickListener{
            PreferenceManager.saveSelectedLanguage(requireContext(),Constant.En_Language)
          //  changeLanguageLocaleTo("en")
            setAppLocale("en",requireContext())

        }
        */
        binding.celsiusRadioButton.setOnClickListener{
            PreferenceManager.saveSelectedTemperatureUnit(requireContext(),Constant.Unit_Celsius)    }
        binding.kelvinRadioButton.setOnClickListener{

            PreferenceManager.saveSelectedTemperatureUnit(requireContext(),Constant.Unit_kelvin)

        }
        binding.fahrenheitRadioButton.setOnClickListener{

            PreferenceManager.saveSelectedTemperatureUnit(requireContext(),Constant.Unit_Fahrenheit)
        }
        binding.englishRadioButton.setOnClickListener{
            PreferenceManager.saveSelectedLanguage(requireContext(),Constant.En_Language)
        }
        binding.arabicRadioButton.setOnClickListener{
            PreferenceManager.saveSelectedLanguage(requireContext(),Constant.Ar_Language)
        }

        binding.gpsRadioButton.setOnClickListener{
            PreferenceManager.saveSelectedLocation(requireContext(),Constant.LOCATION_GPS)

        }
        binding.mapRadioButton.setOnClickListener{

            PreferenceManager.saveSelectedLocation(requireContext(),Constant.LOCATION_MAP)
            val bundle = bundleOf("fav" to "map")
            Navigation.findNavController(view)
                .navigate(R.id.action_settingsFragment_to_mapFragment,bundle)
        }


    }

    fun setLocale(languageCode: String, context: Context) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    private fun restartActivity() {
        val intent = requireActivity().intent
        requireActivity().finish()
        startActivity(intent)
    }
}