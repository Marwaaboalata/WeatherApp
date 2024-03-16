package com.example.weatherapp.alert

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mvvm.main.view.MyFactory
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentAlarmBinding
import com.example.weatherapp.db.LocalDataSourceImp
import com.example.weatherapp.fav.FavViewModel
import com.example.weatherapp.fav.Listener
import com.example.weatherapp.fav.MyFavAdapterList
import com.example.weatherapp.model.AlertTable
import com.example.weatherapp.model.FavTable
import com.example.weatherapp.model.ReposatoryImp
import com.example.weatherapp.network.api.RemoteDataSourceImp
import com.example.weatherapp.utils.ApiState
import com.example.weatherapp.utils.PreferenceManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Year
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class AlarmFragment : Fragment() ,Listener{
    private lateinit var binding: FragmentAlarmBinding
    private lateinit var popupWindow: PopupWindow
    private lateinit var calendar: Calendar
    private var selectedDateInMillisBegin: Long = 0
    private var selectedDateInMillisEnd: Long = 0
     var year:Int=0
    var month:Int=0
    var day :Int=0
    var hour:Int=0
    var mins:Int=0

    var myAdapter = MyAlertAdapterList(this)



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
            //
            val myFactory=
                MyFactory(ReposatoryImp(RemoteDataSourceImp(), LocalDataSourceImp(requireContext())))
          val  alertviewModel = ViewModelProvider(this, myFactory).get(AlertViewModel::class.java)
            alertviewModel.getAlerts()

            //myAdapter = MyFavAdapterList(this)
          var  linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.alarmRcyc.apply {
                adapter = myAdapter
                layoutManager = linearLayoutManager
            }


            lifecycleScope.launch {
                alertviewModel.listData.collectLatest {
                    when (it) {
                        is ApiState.Loading ->{
                            //  binding.progressBar.visibility = View.VISIBLE
                        }
                        is ApiState.Success -> {
//                        binding.progressBar.visibility = View.GONE
//                        productAdapter.submitList(it.data)
                            myAdapter.submitList(it.data)
                        }

                        else -> {}
                    }
                }
            }

        //
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
        val saveBtn=popupView.findViewById<Button>(R.id.save_alert_btn)

        buttonDatePickerBegin.setOnClickListener {
            showDatePicker { date ->
                selectedDateInMillisBegin=date.time
                tvBeginDate.text= SimpleDateFormat("dd-M-yy").format(date).toString()
               // start=date as Long
            }

        }

        buttonDatePickerEnd.setOnClickListener {
            showDatePicker { date ->
                selectedDateInMillisEnd=date.time
                tvEndDate.text=  SimpleDateFormat("dd-M-yy").format(date).toString()
               // end=date as Long
                this.year=date.year
                this.month=date.month
                this.day=date.day

            }
        }

        buttonTimePicker.setOnClickListener {
            showTimePicker { time ->
                tvTime.text=time.hours.toString()+" : "+time.minutes.toString()
                this.hour=time.hours
                this.mins=time.minutes

            }
        }
        saveBtn.setOnClickListener{
            val myFactory=
                MyFactory(ReposatoryImp(RemoteDataSourceImp(), LocalDataSourceImp(requireContext())))
            val  alertviewModel = ViewModelProvider(this, myFactory).get(AlertViewModel::class.java)
            alertviewModel.insertAlert(AlertTable("1",tvTime.text.toString(),tvEndDate.text.toString()))
            popupWindow.dismiss()
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

    override fun onClickDelete(fav: FavTable) {
        TODO("Not yet implemented")
    }

    override fun onClickCard(fav: FavTable, view: View) {
        TODO("Not yet implemented")
    }
/////////
    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(requireContext())) {
            val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
            alertDialogBuilder.setTitle(getString(R.string.weather_alarm))
                .setMessage(getString(R.string.features))
                .setPositiveButton(getString(R.string.ok)) { dialog: DialogInterface, i: Int ->
                    var myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                    startActivity(myIntent)
                    dialog.dismiss()
                }.setNegativeButton(
                    getString(R.string.cancel)
                ) { dialog: DialogInterface, i: Int ->
                    dialog.dismiss()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                }.show()
        }
    }

/*
    fun alertNotificationOrAlarm(start: DateAlertModel, end: DateAlertModel, type: String) {
        if(start !=null && end !=null ){

            var startDate = Calendar.getInstance()
            startDate.set(start.year, start.month, start.day, start.hour, start.minute)

            var endDate = Calendar.getInstance()
            endDate.set(end.year, end.month, end.day, end.hour, end.minute)

            var diff = (endDate.timeInMillis / 1000L) - (startDate.timeInMillis / 1000L)
            val inputData = Data.Builder()
                .putString("title", "Weather")
                .putString("content", "current weather statue")
                .putString("typeAlert", type)
                .build()

            /*  val fireAlertConstraints = Constraints.Builder()
                  .setRequiresBatteryNotLow(true)
                  .setRequiredNetworkType(NetworkType.CONNECTED)
                  .setRequiresCharging(true)
                  .setRequiresStorageNotLow(true)
                  .setRequiresDeviceIdle(true)
                  .build()*/

            val fireAlertConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<AlertWorker>()
                .setInitialDelay(diff, TimeUnit.SECONDS)
                .setInputData(inputData)
                .setConstraints(fireAlertConstraints)
                .build()

            var alertModel=AlertTable((oneTimeWorkRequest.id).toString(),//timeStart.text.toString(),
                tvTime.text.toString()//,dateStart.text.toString()
                ,.text.toString())
            WorkManager.getInstance(requireContext().applicationContext).enqueue(oneTimeWorkRequest)
            viewmodel.insertAlertModel(alertModel)

        }
        //   WorkManager.getInstance().cancelWorkById(oneTimeWorkRequest.id)

    }

 */


}

data class DateAlertModel (var year:Int, var month:Int, var day:Int, var hour:Int, var minute:Int)

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