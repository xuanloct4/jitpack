package com.example.calendarsdk.data.retrofit

import com.example.calendarsdk.CalendarSDK
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    companion object {
        fun createCalendarificApi(): CalendarificApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(CalendarSDK.instance.calendarURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(CalendarificApi::class.java)

            return api
        }

        fun createAbstractApi(): AbstractApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(CalendarSDK.instance.holidayAbstractApiURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(AbstractApi::class.java)

            return api
        }
    }

}