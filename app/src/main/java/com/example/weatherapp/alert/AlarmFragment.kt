package com.example.weatherapp.alert

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentAlarmBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AlarmFragment : Fragment() {
    private lateinit var binding: FragmentAlarmBinding
    private lateinit var popupWindow: PopupWindow
    private lateinit var calendar: Calendar
    private var selectedDateInMillisBegin: Long = 0
    private var selectedDateInMillisEnd: Long = 0



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveAlarmButton.setOnClickListener {
            showPopup()
        }
    }

    private fun showPopup() {
        val popupView = layoutInflater.inflate(R.layout.popup_layout, null)
        popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        val buttonDatePickerBegin = popupView.findViewById<Button>(R.id.btn_first_date)
        val buttonDatePickerEnd = popupView.findViewById<Button>(R.id.btn_second_date)
        val buttonTimePicker = popupView.findViewById<Button>(R.id.btn_set_time)
        val tvBeginDate=popupView.findViewById<TextView>(R.id.show_first_date)
        val tvEndDate=popupView.findViewById<TextView>(R.id.show_second_date)
        val tvTime=popupView.findViewById<TextView>(R.id.show_time)

        buttonDatePickerBegin.setOnClickListener {
            showDatePicker { date ->
                selectedDateInMillisBegin=date.time
                tvBeginDate.text= SimpleDateFormat("dd-M-yy").format(date).toString()

            }

        }

        buttonDatePickerEnd.setOnClickListener {
            showDatePicker { date ->
                selectedDateInMillisEnd=date.time
                tvEndDate.text=  SimpleDateFormat("dd-M-yy").format(date).toString()

            }
        }

        buttonTimePicker.setOnClickListener {
            showTimePicker { time ->
                tvTime.text=time.hours.toString()+" : "+time.minutes.toString()

            }
        }

        popupWindow.showAtLocation(binding.root, Gravity.CENTER, 0, 0)
    }


    private fun showDatePicker(onDateSelected: (Date) -> Unit) {
        calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                onDateSelected(calendar.time)
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show()
    }

    private fun showTimePicker(onTimeSelected: (Date) -> Unit) {
        calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                onTimeSelected(calendar.time)
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }

    fun createAlertCard(){

    }
}

/*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentAlarmBinding
import com.example.weatherapp.databinding.FragmentSettingsBinding

class AlarmFragment : Fragment() {
    private lateinit var binding:FragmentAlarmBinding

            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveAlarmButton.setOnClickListener{

        }

    }




}

 */