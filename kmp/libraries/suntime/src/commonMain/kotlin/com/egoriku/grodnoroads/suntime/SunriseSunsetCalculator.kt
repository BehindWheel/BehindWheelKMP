package com.egoriku.grodnoroads.suntime

import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin
import kotlin.math.tan
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.offsetAt
import kotlinx.datetime.toInstant

/**
 * The algorithm is based on the Ed Williams Sunrise/Sunset algorithm
 * More info can be found [here](http://edwilliams.org/sunrise_sunset_algorithm.htm)
 */

object SunriseSunsetCalculator {

    fun calculate(
        date: LocalDateTime,
        latitude: Double,
        longitude: Double,
        twilight: Twilight,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): SunTime? {
        val sunrise = calculateSunPosition(
            date = date,
            isSunrise = true,
            latitude = latitude,
            longitude = longitude,
            twilight = twilight,
            timeZone = timeZone
        )
        val sunset = calculateSunPosition(
            date = date,
            isSunrise = false,
            latitude = latitude,
            longitude = longitude,
            twilight = twilight,
            timeZone = timeZone
        )

        return when {
            sunrise == null || sunset == null -> null
            else -> SunTime(sunrise = sunrise, sunset = sunset)
        }
    }

    private fun calculateSunPosition(
        date: LocalDateTime,
        isSunrise: Boolean,
        latitude: Double,
        longitude: Double,
        twilight: Twilight,
        timeZone: TimeZone
    ): LocalTime? {
        val dayOfYear = date.dayOfYear
        val lngHour = longitude / 15
        val hourTime = if (isSunrise) 6.0 else 18.0
        val approximateTime = dayOfYear + (hourTime - lngHour) / 24

        // Calculate the suns mean anomaly
        val meanAnomaly = (0.9856 * approximateTime) - 3.289

        // Calculate the sun's true longitude
        val subexpression1 = 1.916 * sin(meanAnomaly.radians)
        val subexpression2 = 0.020 * sin(2 * meanAnomaly.radians)
        val trueLongitude =
            (meanAnomaly + subexpression1 + subexpression2 + 282.634).normalise(360.0)

        // sun's right ascension
        var ra = (atan(0.91764 * tan(trueLongitude.radians)).degrees).normalise(360.0)

        // RA value needs to be in the same quadrant as L
        val lQuadrant = floor(trueLongitude / 90) * 90
        val rAquadrant = floor(ra / 90) * 90
        ra += (lQuadrant - rAquadrant)
        // RA into hours
        ra /= 15

        // declination
        val sinDec = 0.39782 * sin(trueLongitude.radians)
        val cosDec = cos(asin(sinDec))

        // calculate zenith (point right above viewer)
        val zenith = -1 * twilight.degrees + 90

        // local hour angle
        val cosH =
            (cos(zenith.radians) - (sinDec * sin(latitude.radians))) / (cosDec * cos(latitude.radians))

        // no transition
        if (cosH > 1 || cosH < -1) return null

        val tempH = if (isSunrise) {
            360 - acos(cosH).degrees
        } else {
            acos(cosH).degrees
        }
        val h = tempH / 15.0

        // local mean time of rising
        val t = h + ra - (0.06571 * approximateTime) - 6.622
        val uT = (t - lngHour).normalise(24.0)

        val currentInstant = date.toInstant(timeZone)
        val timezoneOffsetHours = (timeZone.offsetAt(currentInstant).totalSeconds / 3600.0)
        val localTime = (uT + timezoneOffsetHours).normalise(24.0)

        val hour = floor(localTime).toInt()
        val minute = floor((localTime - hour) * 60.0).toInt()

        return LocalTime(hour = hour, minute = minute)
    }

    /** Convert from degrees to radians */
    private val Double.radians: Double
        get() = this * PI / 180

    /** Convert from radians to degrees */
    private val Double.degrees: Double
        get() = this * 180 / PI

    /**
     * If [this] is negative, add [maximum] to [this] until [this] will be positive
     * if [this] > [maximum], subtract [maximum] from [this] until [this] will be less than [maximum]
     */
    private fun Double.normalise(maximum: Double): Double {
        var value = this
        while (value < 0) value += maximum
        while (value > maximum) value -= maximum
        return value
    }

    /** Define what position on sky relative to the earth sun should reach */
    sealed interface Twilight {
        data object Official : Twilight
        data object Civil : Twilight
        data object Nautical : Twilight
        data object Astronomical : Twilight
        data class Custom(val degree: Double) : Twilight

        /**
         * Angle, formed by sun position, viewer and a ray (emanating from an observer perpendicular to the zenith)
         * If sun under ray (relative to viewer) the [degrees] < 0
         */
        val degrees: Double
            get() = when (this) {
                is Official -> -0.833
                is Civil -> -6.0
                is Nautical -> -12.0
                is Astronomical -> -15.0
                is Custom -> degree
            }
    }

    data class SunTime(
        val sunrise: LocalTime,
        val sunset: LocalTime
    )
}
