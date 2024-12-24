package com.example.calendarsdk.domain.entities

import com.example.calendarsdk.data.retrofit.AbstractResponse
import com.example.calendarsdk.data.retrofit.CalendarificResponse


enum class CalendarApiSource {
    CalendarificApi, AbstractApi
}

data class HolidayResponse(
    val isHoliday: Boolean,
    val apiSource: CalendarApiSource,
    val error: Exception? = null
)

fun CalendarificResponse.isHoliday(): Boolean {
    return this.response?.holidays?.isNotEmpty() ?: false
}

fun List<AbstractResponse>.isHoliday(): Boolean {
    return this.isNotEmpty()
}