package com.example.calendarsdk.data.repository

import com.example.calendarsdk.CalendarSDK
import com.example.calendarsdk.data.retrofit.AbstractApi
import com.example.calendarsdk.data.retrofit.CalendarificApi
import com.example.calendarsdk.domain.entities.CalendarApiSource
import com.example.calendarsdk.domain.entities.HolidayResponse
import com.example.calendarsdk.domain.entities.isHoliday
import com.example.calendarsdk.repository.HolidayRepository

class HolidayRepositoryImpl(
    private val calendarificApi: CalendarificApi?,
    private val abstractApi: AbstractApi?
) : HolidayRepository {
    override suspend fun getHolidayResults(year: Int, month: Int, day: Int): List<HolidayResponse> {
        val responses = mutableListOf<HolidayResponse>()

        try {

            val calendarificResult = calendarificApi?.checkHoliday(
                year,
                month,
                day,
                country = CalendarSDK.instance.country,
                apiKey = CalendarSDK.instance.calendarAPIKEY
            )
            calendarificResult?.let {
                responses.add(HolidayResponse(it.isHoliday(),
                    CalendarApiSource.CalendarificApi))
            }

        } catch (e: Exception) {
            responses.add(HolidayResponse(false,
                CalendarApiSource.CalendarificApi,
                error = e))
        }

        try {
            val abstractResult = abstractApi?.checkHoliday(
                year,
                month,
                day,
                country = CalendarSDK.instance.country,
                apiKey = CalendarSDK.instance.holidayAbstractApiKey
            )
            abstractResult?.let {
                responses.add(HolidayResponse(it.isHoliday(),
                    CalendarApiSource.AbstractApi))
            }
        } catch (e: Exception) {
            responses.add(HolidayResponse(false,
                CalendarApiSource.AbstractApi,
                error = e))
        }

        return responses
    }
}