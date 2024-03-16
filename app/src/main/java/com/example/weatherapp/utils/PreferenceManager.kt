package com.example.weatherapp.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {

    private const val PREF_NAME = "settings"
    private const val KEY_SELECTED_LANGUAGE = "selectedLanguage"
    private const val KEY_SELECTED_TEMPERATURE_UNIT = "selectedTemperatureUnit"
    private const val DEFAULT_TEMPERATURE_UNIT = "Celsius"
    private const val KEY_SELECTED_CITY = "selectedCity"
    private const val KEY_SELECTED_LONG = "selectedLong"
    private const val KEY_SELECTED_LAT = "selectedLat"
    private const val KEY_SELECTED_LOCATION ="selectedLocation"

     var sharedDegree:String ="Default"
    fun saveSelectedLanguage(context: Context, language: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_SELECTED_LANGUAGE, language)
        editor.apply()

    }
    fun saveSelectedCity(context: Context, city: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
            editor.putString(KEY_SELECTED_CITY, city)
        editor.apply()

    }
    fun saveSelectedLongtude(context: Context, long: Long) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong(KEY_SELECTED_LONG, long)
        editor.apply()

    }
    fun saveSelectedLatitude(context: Context, lat: Long) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong(KEY_SELECTED_LAT, lat)
        editor.apply()

    }

    fun saveSelectedTemperatureUnit(context: Context, temperatureUnit: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_SELECTED_TEMPERATURE_UNIT, temperatureUnit)
        editor.apply()
    }

    fun getSelectedLanguage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_SELECTED_LANGUAGE, Constant.En_Language) ?: Constant.En_Language
    }
    fun getSelectedCity(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_SELECTED_CITY, "") ?: ""
    }
    fun getSelectedLong(context: Context): Long {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getLong(KEY_SELECTED_LONG,0) ?: 0
    }   fun getSelectedLat(context: Context): Long {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getLong(KEY_SELECTED_LAT, 0) ?: 0
    }

    fun getSelectedTemperatureUnit(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        return sharedPreferences.getString(KEY_SELECTED_TEMPERATURE_UNIT,Constant.Unit_Celsius) ?: Constant.Unit_Celsius
    }
    fun saveSelectedLocation(context: Context, location: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_SELECTED_LOCATION, location)
        editor.apply()
    }
    fun getSelectedLocation(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_SELECTED_LOCATION, Constant.LOCATION_GPS) ?: "DEFAULT_LOCATION"
    }
    fun setAppLocationByMap(context: Context,long:String,lat:String){
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Constant.MAP_LON, long)
        editor.putString(Constant.MAP_LAT, lat)
        editor.apply()
    }
    fun getAppLocationByMap(context: Context): Pair<String?, String?> {
        val sharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return Pair(sharedPreferences.getString(Constant.MAP_LON, "not available"),
            sharedPreferences.getString(Constant.MAP_LAT, "not available"))
    }
//
fun setAppLocationForAlert(context: Context,long:String,lat:String){
    val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(Constant.ALERT_LON, long)
    editor.putString(Constant.ALERT_LAT, lat)
    editor.apply()
}
    fun getAppLocationForAlert(context: Context): Pair<String?, String?> {
        val sharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return Pair(sharedPreferences.getString(Constant.ALERT_LON, "not available"),
            sharedPreferences.getString(Constant.ALERT_LAT, "not available"))
    }


}
