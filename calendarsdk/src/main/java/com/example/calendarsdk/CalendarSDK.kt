package com.example.calendarsdk

import com.example.calendarsdk.data.repository.HolidayRepositoryImpl
import com.example.calendarsdk.data.retrofit.AbstractApi
import com.example.calendarsdk.data.retrofit.CalendarificApi
import com.example.calendarsdk.data.retrofit.RetrofitBuilder
import com.example.calendarsdk.domain.usecase.CheckHolidayUseCase

class CalendarSDK {
    companion object {
        val instance = CalendarSDK()
    }

    private lateinit var checkHolidayUseCase: CheckHolidayUseCase
    private var calendarificApi: CalendarificApi? = null
    private var abstractApi: AbstractApi? = null

    fun getCheckHolidayUseCase(): CheckHolidayUseCase {

        if (calendarAPIKEY.isNotEmpty() ) {
            calendarificApi = RetrofitBuilder.createCalendarificApi()
        }
        if (holidayAbstractApiKey.isNotEmpty() ) {
            abstractApi = RetrofitBuilder.createAbstractApi()
        }
        checkHolidayUseCase = CheckHolidayUseCase(
            HolidayRepositoryImpl(
                calendarificApi = calendarificApi,
                abstractApi = abstractApi
            )
        )

        return checkHolidayUseCase
    }

    val calendarURL = "https://calendarific.com/"
    var calendarAPIKEY: String = ""

    val holidayAbstractApiURL = "https://holidays.abstractapi.com/"
    var holidayAbstractApiKey: String = ""

    var country: String = "US"
}