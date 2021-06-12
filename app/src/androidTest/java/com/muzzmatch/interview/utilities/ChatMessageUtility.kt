package com.muzzmatch.interview.utilities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.muzzmatch.interview.utils.isTimeDifferenceGreaterThanOneHour
import com.muzzmatch.interview.utils.separatorTimeStamp
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import kotlin.time.ExperimentalTime


@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ChatMessageUtility {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @ExperimentalTime
    @Test
    fun isTimeDifferenceGreaterThanOneHour() {
        val currentDateTime = Date.from(Instant.now())
        val oneHourEarlierDateTime = Date.from(
                Instant.now().minus(
                        java.time.Duration.ofHours(
                                ONE_HOUR
                        )
                )
        )
        val twoHourEarlierDateTime = Date.from(
                Instant.now().minus(
                        java.time.Duration.ofHours(
                                TWO_HOUR
                        )
                )
        )
        val thirtyMinutesEarlierDateTime = Date.from(
                Instant.now().minus(
                        java.time.Duration.ofMinutes(
                                THIRTY_MINUTES
                        )
                )
        )

        assert(!isTimeDifferenceGreaterThanOneHour(currentDateTime, oneHourEarlierDateTime))
        assert(isTimeDifferenceGreaterThanOneHour(currentDateTime, twoHourEarlierDateTime))
        assert(!isTimeDifferenceGreaterThanOneHour(currentDateTime, thirtyMinutesEarlierDateTime))
    }

    @ExperimentalTime
    @Test
    fun separatorTimeStamp() {

        val parseFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        val date1 = parseFormat.parse("2012-05-13T10:00:00.000-0500")
        val date2 = parseFormat.parse("2012-03-16T04:00:00.000-0500")
        val date3 = parseFormat.parse("2012-03-15T03:00:00.000-0500")
        assert(date1.separatorTimeStamp().toString() == "Sunday 10:00")
        assert(date2.separatorTimeStamp().toString() == "Friday 3:00")
        assert(date3.separatorTimeStamp().toString() == "Thursday 2:00")
    }

}