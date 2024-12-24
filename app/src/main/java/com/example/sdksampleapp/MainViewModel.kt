package com.example.sdksampleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendarsdk.CalendarSDK
import com.example.calendarsdk.domain.entities.HolidayConsensus
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    private val _isHoliday = MutableLiveData<Boolean>()
    val isHoliday: LiveData<Boolean> get() = _isHoliday

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun checkHoliday(year: String, month: String, day: String, consensus: HolidayConsensus) {
        viewModelScope.launch {



            val calendarSDK = CalendarSDK.instance
                calendarSDK.calendarAPIKEY = "tqjgBSbRun3jDj9gfAkmu2WPbWuFsVX9"
                calendarSDK.holidayAbstractApiKey = "402632df3ba24d988ce1c3e3029ebf3c"
                calendarSDK.country = "US"

            val checkHolidayUseCase = calendarSDK.getCheckHolidayUseCase()

            try {
                val yearInt = year.toInt()
                val monthInt = month.toInt()
                val dayInt = day.toInt()
                if (checkHolidayUseCase.isValidDate(yearInt, monthInt, dayInt) ) {
                    _isHoliday.value = checkHolidayUseCase.isHoliday(yearInt, monthInt, dayInt, consensus)
                } else {
                    _error.value = "Error: The input date is invalid"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }
}