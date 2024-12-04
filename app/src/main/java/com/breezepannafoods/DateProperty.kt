package com.breezepannafoods

import androidx.core.util.Pair
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.CompositeDateValidator
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateProperty {

    fun showDateRangePickerDialog(fm: FragmentManager, calendarConstraints: CalendarConstraints.Builder? = null, callback: (String, String) -> Unit){
        try {
            var dateRangePicker: MaterialDatePicker<Pair<Long, Long>>
            if(calendarConstraints !=null){
                dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("Select dates")
                    //.setSelection(Pair(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
                    .setTheme(R.style.ThemeOverlay_App_DatePickerDialog)
                    .setCalendarConstraints(calendarConstraints!!.build())
                    .build()
            }else{
                dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("Select dates")
                    //.setSelection(Pair(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
                    .setTheme(R.style.ThemeOverlay_App_DatePickerDialog)
                    .build()
            }



            dateRangePicker.show(fm, "date_range_picker")
            dateRangePicker.addOnPositiveButtonClickListener { selection ->
                val startDate = selection.first
                val endDate = selection.second

                val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedStartDate = dateFormatter.format(Date(startDate!!)).toString()
                val formattedEndDate = dateFormatter.format(Date(endDate!!)).toString()

                callback(formattedStartDate, formattedEndDate)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            callback("", "")
        }
    }

    fun showDatePickerDialog(fm: FragmentManager, isDisableFuture: Boolean = false, isDisablePast: Boolean = false, callback: (String) -> Unit) {
        var validatorMin: DateValidatorPointBackward? = null
        var validatorMax: DateValidatorPointForward? = null
        if(isDisableFuture) {
            validatorMin = DateValidatorPointBackward.now()
        }
        if(isDisablePast){
            validatorMax = DateValidatorPointForward.now()
        }

        var datePicker: MaterialDatePicker<Long>? = null

        // Combine validators using CompositeDateValidator
        if(isDisableFuture || isDisablePast){
            val validators : ArrayList<DateValidator> = ArrayList()
            if(validatorMin!=null){
                validators.add(validatorMin)
            }
            if(validatorMax!=null){
                validators.add(validatorMax)
            }
            val validator = CompositeDateValidator.allOf(validators as List<DateValidator>)

            val constraintsBuilder = CalendarConstraints.Builder()
                .setValidator(validator)
                .build()

            datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a date")
                .setCalendarConstraints(constraintsBuilder)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()) // Set initial selection to today
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR) // Use calendar input mode for month selection
                .setTheme(R.style.ThemeOverlay_App_DatePickerDialog)
                .build()
        }else{
            datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()) // Set initial selection to today
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR) // Use calendar input mode for month selection
                .setTheme(R.style.ThemeOverlay_App_DatePickerDialog)
                .build()
        }

        datePicker.addOnPositiveButtonClickListener { selection ->
            val calendar = java.util.Calendar.getInstance()
            calendar.timeInMillis = selection

            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormatter.format(calendar.time).toString()
            callback(formattedDate)
        }

        datePicker.show(fm, "datePicker")
    }


}