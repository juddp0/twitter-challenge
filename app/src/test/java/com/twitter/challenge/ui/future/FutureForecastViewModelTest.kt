package com.twitter.challenge.ui.future

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.twitter.challenge.data.*
import com.twitter.challenge.repository.WeatherRepositoryContract
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class FutureForecastViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var weatherRepo: WeatherRepositoryContract

    private val TEST_FORECASTS = mutableListOf<Forecast>(
        createWeatherObjectWithTemperature(0, 10F),
        createWeatherObjectWithTemperature(1, 11F),
        createWeatherObjectWithTemperature(2, 12F),
        createWeatherObjectWithTemperature(3, 13F),
        createWeatherObjectWithTemperature(4, 14F),
    )
    //expected numbers were calculated on excel so we need to be about correct
    private val EXPECTED_MEAN = 12
    private val EXPECTED_VARIANCE = 2
    private val EXPECTED_STANDARD_DEVIATION = 1
    private lateinit var viewModel: FutureForecastViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = FutureForecastViewModel(weatherRepo)
    }

    @Test
    fun calculateMean_testForecasts() {
        // When
        val result = viewModel.calculateMean(TEST_FORECASTS)

        // Then
        assertEquals(EXPECTED_MEAN, result.toInt())
    }

    @Test
    fun calculateVariance_testForecasts() {
        // When
        val result = viewModel.calculateVariance(TEST_FORECASTS)

        // Then - expected numbers were calculated on excel so we need to be about correct
        assertEquals(EXPECTED_VARIANCE, result.toInt())
    }

    @Test
    fun calculateStandardDeviation_testForecasts() {
        // When
        val result = viewModel.calculateStandardDeviation(TEST_FORECASTS)

        // Then - expected numbers were calculated on excel so we need to be about correct
        assertEquals(EXPECTED_STANDARD_DEVIATION, result.toInt())
    }

    private fun createWeatherObjectWithTemperature(day: Int, temp: Float): Forecast {
        return Forecast(
            day,
            WeatherResponse(
                Coordinates(0.0,0.0),
                Weather(
                    temperatureInCelsius = temp,
                    0,
                    0
                ),
                Wind(0F, 0),
                Rain(0),
                Clouds(0)
            )
        )
    }
}