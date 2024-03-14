package com.example.weatherapp.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentFavouriteBinding
import com.example.weatherapp.databinding.FragmentSettingsBinding
import com.example.weatherapp.utils.Constant
import com.example.weatherapp.utils.PreferenceManager


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
            PreferenceManager.saveSelectedLanguage(requireContext(),Constant.Ar_Language)
            changeLanguageLocaleTo("ar")


        }
        binding.englishRadioButton.setOnClickListener{
            PreferenceManager.saveSelectedLanguage(requireContext(),Constant.En_Language)
            changeLanguageLocaleTo("en")

        }
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


    }
    private fun changeLanguageLocaleTo(lan: String) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(lan)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}