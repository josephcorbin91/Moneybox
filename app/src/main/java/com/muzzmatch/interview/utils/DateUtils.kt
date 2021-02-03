package com.muzzmatch.interview.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
import kotlin.time.ExperimentalTime

const val MILLIS_TO_HOUR = 3600000.0

/** Returns a timestamp associated with a specific date where only the day of the week is bold */
fun Date.separatorTimeStamp(): SpannableStringBuilder {
    val dayOfWeek = SimpleDateFormat("EEEE", Locale.UK).format(this)
    val timeOfDay = SimpleDateFormat("h:mm", Locale.UK).format(this)
    val styledTimeStamp = SpannableStringBuilder()

    styledTimeStamp.append(dayOfWeek)
    styledTimeStamp.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            styledTimeStamp.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    styledTimeStamp.append(" $timeOfDay")
    return styledTimeStamp
}

/**
 * Calculates the time difference between two dates.
 * Rounds up to the 5th decimal due to rounding errors. This calculation is accurate to the second,
 *
 * @param mostRecentDate date that occurs before the older date in chronological order
 * @param olderDate date that occurs after the older date in chronological order
 */
@ExperimentalTime
private fun hourDifferenceBetweenTwoTimes(mostRecentDate: Date, olderDate: Date): Double {
    val timeDifferenceInMilliseconds = mostRecentDate.time - olderDate.time
    val timeDifferenceBeforeRounding = timeDifferenceInMilliseconds / MILLIS_TO_HOUR
    return (timeDifferenceBeforeRounding * 10000.0).roundToInt() / 10000.0
}

/** Calculates if the time difference between two dates is greater than one hour
 *
 * @param mostRecentDate date that occurs before the older date in chronological order
 * @param olderDate date that occurs after the older date in chronological order
 */
@ExperimentalTime
fun isTimeDifferenceGreaterThanOneHour(mostRecentDate: Date, olderDate: Date): Boolean = hourDifferenceBetweenTwoTimes(
        mostRecentDate,
        olderDate
) > 1.0