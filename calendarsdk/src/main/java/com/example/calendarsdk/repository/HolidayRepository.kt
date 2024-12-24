package com.example.calendarsdk.repository

import com.example.calendarsdk.domain.entities.HolidayResponse

interface HolidayRepository {
    suspend fun getHolidayResults(year: Int, month: Int, day: Int): List<HolidayResponse>
}