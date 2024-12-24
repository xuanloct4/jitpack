package com.example.sdksampleapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calendarsdk.domain.entities.HolidayConsensus
import com.example.sdksampleapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var holidayViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        holidayViewModel = MainViewModel()

        setupObservers()

        binding.checkHolidayButton?.setOnClickListener {
            try {
                val year = binding.yearInput?.text.toString()
                val month = binding.monthInput?.text.toString()
                val day = binding.dayInput?.text.toString()
                holidayViewModel.checkHoliday(year, month, day, HolidayConsensus.ANY)
            } catch (e: NumberFormatException) {
                binding.resultText?.text = "Enter a valid date  please"
            }

//            holidayViewModel.checkHoliday(2024, 12, 31, HolidayConsensus.ANY)
        }
    }

    private fun setupObservers() {
        holidayViewModel.isHoliday.observe(this) { isHoliday ->
            binding.resultText?.text = if (isHoliday) "It's a holiday!" else "Not a holiday."
        }

        holidayViewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }
}