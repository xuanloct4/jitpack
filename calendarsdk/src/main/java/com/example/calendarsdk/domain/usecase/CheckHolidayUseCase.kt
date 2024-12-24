package com.example.calendarsdk.domain.usecase

import android.annotation.SuppressLint
import android.util.Log
import com.example.calendarsdk.domain.entities.HolidayConsensus
import com.example.calendarsdk.repository.HolidayRepository
import java.text.SimpleDateFormat

class CheckHolidayUseCase(
    private val repository: HolidayRepository
) {
    suspend fun isHoliday(year: Int, month: Int, day: Int, consensus: HolidayConsensus): Boolean {
        val results = repository.getHolidayResults(year, month, day)
        return when (consensus) {
            HolidayConsensus.ANY -> results.any { it.isHoliday }
            HolidayConsensus.ALL -> results.all { it.isHoliday }
            HolidayConsensus.CONSENSUS -> results.count { it.isHoliday } > results.size / 2
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun isValidDate(year: Int, month: Int, day: Int): Boolean {
        if (year < 0 || year > 3000) return false
        if (month < 1 || month > 12) return false
        if (day < 1 || day > 31) return false


        try {
            val dateFormat = SimpleDateFormat("yyyy-M-d")
            dateFormat.isLenient = false

            val dateFromComponents = "${year}-${month}-${day}"
            dateFormat.parse(dateFromComponents)
            return true
        } catch (e: Exception) {
            return false
        }


    }
}