package com.example.calendarsdk

import com.example.calendarsdk.domain.entities.CalendarApiSource
import com.example.calendarsdk.domain.entities.HolidayConsensus
import com.example.calendarsdk.domain.entities.HolidayResponse
import com.example.calendarsdk.domain.usecase.CheckHolidayUseCase
import com.example.calendarsdk.repository.HolidayRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class CheckHolidayUseCaseTest {

    private lateinit var repository: HolidayRepository
    private lateinit var useCase: CheckHolidayUseCase

    @Before
    fun setUp() {
        repository = mock(HolidayRepository::class.java)
        useCase = CheckHolidayUseCase(repository)
    }

    @Test
    fun `isValidDate returns false for invalid year`() {
        assertFalse(useCase.isValidDate(-1, 1, 1))
        assertFalse(useCase.isValidDate(3001, 1, 1))
    }

    @Test
    fun `isValidDate returns false for invalid month`() {
        assertFalse(useCase.isValidDate(2023, 0, 1))
        assertFalse(useCase.isValidDate(2023, 13, 1))
    }

    @Test
    fun `isValidDate returns false for invalid day`() {
        assertFalse(useCase.isValidDate(2023, 1, 0))
        assertFalse(useCase.isValidDate(2023, 1, 32))
    }

    @Test
    fun `isValidDate returns true for valid date`() {
        assertTrue(useCase.isValidDate(2023, 12, 25))
        assertTrue(useCase.isValidDate(2000, 2, 29)) // Leap year
    }

    @Test
    fun `isHoliday returns true for ANY consensus when any API indicates holiday`() = runBlocking {
        val mockResults = listOf(
            HolidayResponse(isHoliday = false, apiSource = CalendarApiSource.CalendarificApi),
            HolidayResponse(isHoliday = true, apiSource = CalendarApiSource.AbstractApi)
        )
        `when`(repository.getHolidayResults(2023, 12, 25)).thenReturn(mockResults)

        val result = useCase.isHoliday(2023, 12, 25, HolidayConsensus.ANY)

        assertTrue(result)
    }

    @Test
    fun `isHoliday returns false for ALL consensus when any API indicates non-holiday`() = runBlocking {
        val mockResults = listOf(
            HolidayResponse(isHoliday = true, apiSource = CalendarApiSource.CalendarificApi),
            HolidayResponse(isHoliday = false, apiSource = CalendarApiSource.AbstractApi)
        )
        `when`(repository.getHolidayResults(2023, 12, 25)).thenReturn(mockResults)

        val result = useCase.isHoliday(2023, 12, 25, HolidayConsensus.ALL)

        assertFalse(result)
    }

    @Test
    fun `isHoliday returns true for CONSENSUS when majority of APIs indicate holiday`() = runBlocking {
        val mockResults = listOf(
            HolidayResponse(isHoliday = true, apiSource = CalendarApiSource.CalendarificApi),
            HolidayResponse(isHoliday = true, apiSource = CalendarApiSource.AbstractApi),
            HolidayResponse(isHoliday = false, apiSource = CalendarApiSource.AbstractApi)
        )
        `when`(repository.getHolidayResults(2023, 12, 25)).thenReturn(mockResults)

        val result = useCase.isHoliday(2023, 12, 25, HolidayConsensus.CONSENSUS)

        assertTrue(result)
    }

    @Test
    fun `isHoliday returns false for CONSENSUS when majority of APIs indicate non-holiday`() = runBlocking {
        val mockResults = listOf(
            HolidayResponse(isHoliday = false, apiSource = CalendarApiSource.CalendarificApi),
            HolidayResponse(isHoliday = true, apiSource = CalendarApiSource.AbstractApi),
            HolidayResponse(isHoliday = false, apiSource = CalendarApiSource.AbstractApi)
        )
        `when`(repository.getHolidayResults(2023, 12, 25)).thenReturn(mockResults)

        val result = useCase.isHoliday(2023, 12, 25, HolidayConsensus.CONSENSUS)

        assertFalse(result)
    }

    @Test
    fun `isHoliday handles empty API responses`() = runBlocking {
        `when`(repository.getHolidayResults(2023, 12, 25)).thenReturn(emptyList())

        val result = useCase.isHoliday(2023, 12, 25, HolidayConsensus.ANY)

        assertFalse(result)
    }

    @Test
    fun `isHoliday throws exception on repository error`() = runBlocking {
        `when`(repository.getHolidayResults(2023, 12, 25)).thenThrow(RuntimeException("API error"))

        try {
            useCase.isHoliday(2023, 12, 25, HolidayConsensus.ANY)
            fail("Expected an exception to be thrown")
        } catch (e: RuntimeException) {
            assertEquals("API error", e.message)
        }
    }
}